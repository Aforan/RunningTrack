package com.rt.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.rt.core.Leg;
import com.rt.core.MapDataManager;
import com.rt.core.Waypoint;
import com.rt.runtime.Event;
import com.rt.runtime.LocationMonitor;
import com.rt.runtime.MetricsAggregator;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rt.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Location;
import android.content.Intent;
import android.graphics.Color;

import static com.rt.test.TestDriver.log;


//TODO:	Should see if there is an onPause method or something like that
//		So that we can pause the LM.
//		Should add distanceLeft, distanceRan, timeRan UI elements.
//		All of that data should be easily accessed from the LM
//		(Public access, don't worry about concurency)
public class RunningActivity extends AbstractRuntimeActivity implements
		ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

	private GoogleMap map;
	private LocationMonitor lm;
	private Location currentLoc;
	private LocationClient lc;
	private Semaphore lock;
	private Leg selectedLeg;
	private Leg lastLeg;
	private ArrayList<Polyline> lines;
	private boolean needRefresh;
	boolean paused;

	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(mdm == null){
			mdm = new MapDataManager();
		}
		lock = new Semaphore(1);
		lm = new LocationMonitor(this, mdm);
		Thread t = new Thread(lm);
		t.start();
		needRefresh = false;
		paused = false;

		lines = new ArrayList<Polyline>();
		setContentView(R.layout.activity_running);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		setUpLocationClientIfNeeded();
		lc.connect();
	}

	void setUpMapIfNeeded() {
		if (map == null) {
			// Try to obtain the map from the SupportMapFragment.
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {
				map.setMyLocationEnabled(true);
				recreateMap(mdm.getWaypoints(), mdm.getLegs());
			}
		}
	}

	private void setUpLocationClientIfNeeded() {
		if (lc == null) {
			lc = new LocationClient(getApplicationContext(), this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		System.out.println("Location changed called");
		// Tracing of user
		if(!paused){
			map.addPolyline(new PolylineOptions()
				.add(new LatLng(location.getLatitude(), location.getLongitude()),
						new LatLng(currentLoc.getLatitude(), currentLoc
								.getLongitude())).width(5).color(Color.YELLOW));
		
		}
		
		currentLoc = location;
		
		lm.addEvent(new Event(LocationMonitor.UPDATE_POS, new LatLng(location.getLatitude(), location.getLongitude())));
		
		try {
			lock.acquire();
			//If we need a Refresh
			if(needRefresh) {
				log("Needed refresh");
				needRefresh = false;
				
				log("lastLeg is " + (lastLeg == null ? "null" : "not null"));
				log("selectedLeg is " + (selectedLeg == null ? "null" : "not null"));
				
				//And the leg has actually changed
				if(lastLeg != selectedLeg && selectedLeg != null) {

					boolean lastFound = false;
					boolean selectedFound = false;
					//Find both legs
					for(int i=0; i<lines.size(); i++){

						if(selectedFound && lastFound)
							break;

						Polyline thisLine = lines.get(i);

						//Find the match for the last leg that was selected
						if(!lastFound && lastLeg != null && (lastLeg.points).equals(thisLine.getPoints())){
							log("Found last lg, reset color to blue");
							thisLine.setColor(Color.BLUE);
							lastFound = true;
						}
						//Find the match for the newly selected leg
						if(!selectedFound && selectedLeg != null && selectedLeg.points.equals(thisLine.getPoints())){
							log("Found current leg reset color to red");
							thisLine.setColor(Color.RED);
							selectedFound = true;
						}
					}

					if(lastFound)
						System.out.println("Last leg was matched");
					if(selectedFound)
						System.out.println("Selected leg was matched");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.release();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// do nothing

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		lc.requestLocationUpdates(REQUEST, this);
		currentLoc = lc.getLastLocation();

		if (currentLoc != null) {
			CameraUpdate startCam = CameraUpdateFactory.newLatLngZoom(
					new LatLng(currentLoc.getLatitude(), currentLoc
							.getLongitude()), 17f);
			map.moveCamera(startCam);
		}

	}

	@Override
	public void onDisconnected() {
		// do nothing

	}

	@Override
	public void recreateMap(ArrayList<Waypoint> waypoints, ArrayList<Leg> legs) {

		System.out.println(waypoints.size());
		for (int i = 0; i < waypoints.size(); i++) {
			map.addMarker(new MarkerOptions()
					.position(waypoints.get(i).centerPoint)
					.title("Waypoint")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		//This should only be called once for this class, so we create a new array for our polylines
		lines = new ArrayList<Polyline>();

		System.out.println(legs.size());
		for (int i = 0; i < legs.size(); i++) {
			Polyline addLine = map.addPolyline(new PolylineOptions().addAll(legs.get(i).points)
												.width(5).color(Color.BLUE));

			lines.add(addLine);
		}
	}

	public void updateCurrentLeg(Leg l) {
		try {
			log("Acquiring lock for updateLeg");
			lock.acquire();
			log("Lock Acquired leg is " + (l == null ? "null" : "Not null"));
			lastLeg = selectedLeg;
			selectedLeg = l;
			needRefresh = true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.release();
		}
	}
	
	//Stop button
	public void stopRun(View view){
		lm.addEvent(new Event(LocationMonitor.KILL_EVENT, new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude())));
		MetricsAggregator.writeMetric(lm.distanceRan, lm.totalTime, this);  
		Intent y = new Intent(this, StatisticsActivity.class);
		startActivity(y);
	}
	
	//Pause/Resume button
	public void pauseOrResumeRun(View view){
		lm.addEvent(new Event(LocationMonitor.PAUSE_EVENT, new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude())));
		
		paused = !paused;
		
		if(paused){
			Toast toast = Toast.makeText(getApplicationContext(), "Paused Run", Toast.LENGTH_SHORT);
			toast.show();
		}
		
		else {
			Toast toast = Toast.makeText(getApplicationContext(), "Resumed Run", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
		
}