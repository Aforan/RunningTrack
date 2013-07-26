package com.rt.core;

import java.util.*;

import com.google.android.gms.maps.model.LatLng;

public class Leg extends MapElement{
	public ArrayList<LatLng> points;
	public Waypoint wayA;
	public Waypoint wayB;

/*	
 *	NOTE:	We should do some sort of validation here,
 *			to mape sure waypoints are valid for this leg
 */
	public Leg(Waypoint wayA, Waypoint wayB, LatLng p) {
		super(p);
		this.wayA = wayA;
		this.wayB = wayB;
		points = new ArrayList<LatLng>();
	}

	public Leg(Waypoint wayA, Waypoint wayB, ArrayList<LatLng> points) {
		super(points.get(points.size()/2));

		this.wayA = wayA;
		this.wayB = wayB;
		this.points = points;
	}
}