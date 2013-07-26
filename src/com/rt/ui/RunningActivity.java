package com.rt.ui;
import java.util.ArrayList;

import com.rt.core.MapElement;
import com.rt.runtime.LocationMonitor;

import android.os.Bundle;
import android.app.Activity;

import com.rt.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import android.location.LocationManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


public class RunningActivity extends AbstractRuntimeActivity
implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
	
	private GoogleMap map;
	private LocationManager lm;
	private LocationClient lc;
	private static final LocationRequest REQUEST = LocationRequest.create()
		      .setInterval(5000)         // 5 seconds
		      .setFastestInterval(16)    // 16ms = 60fps
		      .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	
   void setUpMapIfNeeded(){
	   if(map == null){
		// Try to obtain the map from the SupportMapFragment.
		      map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
		             .getMap();
		      // Check if we were successful in obtaining the map.
		      if (map != null) {
		        map.setMyLocationEnabled(true);
		      }
	   }
   }
   
   private void setUpLocationClientIfNeeded() {
	    if (lc == null) {
	      lc = new LocationClient(
	          getApplicationContext(),
	          this,  // ConnectionCallbacks
	          this); // OnConnectionFailedListener
	    }
   }

@Override
public void onLocationChanged(Location location) {
	//tracing of user here?
	
}

@Override
public void onConnectionFailed(ConnectionResult result) {
	//do nothing
	
}

@Override
public void onConnected(Bundle connectionHint) {
	lc.requestLocationUpdates(
	        REQUEST,
	        this);
	
}

@Override
public void onDisconnected() {
	//do nothing
	
}

@Override
public
void updateMapElements(ArrayList<MapElement> elements,
		ArrayList<MapElement> selectedElements) {
	// TODO Auto-generated method stub
	
}
	
	


}
