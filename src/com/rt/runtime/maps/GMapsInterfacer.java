package com.rt.runtime.maps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.rt.core.Leg;
import com.rt.core.Waypoint;

import static com.rt.test.TestDriver.log;

/*
 * NOTE:
 * In an effort to not reinvent the wheel, a majority of the code in this class was derived from
 * two sources, and they each deserve credit.
 * 
 * Jeffrey Sambells - Decoding PolyLine points. http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
 * George Mathew - Using Google Direction API with Android - http://wptrafficanalyzer.in/blog/drawing-driving-route-directions-between-two-locations-using-google-directions-in-google-map-android-api-v2/
 * 
 * Modifications and fixes were done by Andrew Foran.
 */
public class GMapsInterfacer {
	
	//SAXParserFactory parseFactory; 
	//parseFactory.newSAXParser();
	
	//Leg not void
	public static Leg getPath(Waypoint start, Waypoint end){
		log("Attempting to get the directions url");
		
		String url = getDirectionsUrl(start.centerPoint, end.centerPoint);
		
		log("Obtained directions url: " + url);
		String response;
		
		try {
			log("Attempting to get response");
			//response = query(url);
			
			GMapsParser gmp = new GMapsParser(url);
			Thread t = new Thread(gmp);
			t.start();
			
			response = gmp.getResponse();			
		
			log("Got Response: " + response);
			JSONObject r = new JSONObject(response);
			ArrayList<LatLng> list = constructMapData(r);

			if(list.size() <= 0) return null;

			Leg l = new Leg(start, end, list);
			return l;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//String not void
	private static String query(String strUrl) throws Exception{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            log("Attempting to open connection");
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection == null) log("urlConnection was null");
            else log("urlConnection was not null");
            
            log("Opened connection, trying to connect");
            // Connecting to url
            urlConnection.connect();
            log("Connected, attempting to get input stream");
            // Reading data from url
            iStream = urlConnection.getInputStream();
            log("Got input stream, attempting to read stream");
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
        	e.printStackTrace();
        	throw new Exception("Exception while fetching url");
        }finally{
        	if(iStream != null) iStream.close();
            if(urlConnection != null) urlConnection.disconnect();
        }
        return data;		
	}
	
	//List not void and not sure about the input	
	private static ArrayList<LatLng> constructMapData(JSONObject jObject) {
		ArrayList<LatLng> list = new ArrayList<LatLng>();

		try {		
			JSONArray jRoutes = jObject.getJSONArray("routes");

			/** Traversing all routes */
			for(int i = 0; i < jRoutes.length(); i++){
				JSONObject op = ((JSONObject)jRoutes.get(i)).getJSONObject("overview_polyline");

				if(op.length() > 0) {                
					String polyline = "";
					polyline = (String)op.get("points");

					list.addAll(decodePoly(polyline));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	 private static ArrayList<LatLng> decodePoly(String encoded) {
 
		ArrayList<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;
 
		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;
 
			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
 
			LatLng p = new LatLng((((double) lat / 1E5)),
				(((double) lng / 1E5)));
			poly.add(p);
		}
 
		return poly;
	}

	private static String getDirectionsUrl(LatLng origin, LatLng dest){
		// Origin of route
		String str_origin = "origin="+origin.latitude+","+origin.longitude;
		
		log("Origin: " + str_origin);
		
		// Destination of route
		String str_dest = "destination="+dest.latitude+","+dest.longitude;
		log("Destination: " + str_dest);
		
		// Sensor enabled
		String sensor = "sensor=true";
		
		String mode = "mode=walking";
 
		log("Sensor: " + sensor);
		// Building the parameters to the web service
		String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;
 
		log("Parameters: " + parameters);
		
		// Output format
		String output = "json";
 
		log("Output: " + output);
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
		
		return url;
	}
	
	private static class GMapsParser implements Runnable {
		
		private String url;
		private String response;
		private boolean done;
		
		public GMapsParser(String url) {
			this.url = url;
			done = false;
			response = "";
		}
		
		@Override
		public void run() {
			try {
				response = query(url);
				done = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				done = true;
			}
		}
		
		public String getResponse() {
			while(!done) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return response;
		}
	}
}