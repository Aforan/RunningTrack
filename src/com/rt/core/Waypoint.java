package com.rt.core;

import com.google.android.gms.maps.model.LatLng;

public class Waypoint extends MapElement {
	public Leg legA;
	public Leg legB;

/*	
 *	NOTE:	We should do some sort of validation here,
 *			to make sure the Legs are valid for this wp
 */
	public Waypoint(Leg legA, Leg legB, LatLng p) {
		super(p);

		this.legA = legA;
		this.legB = legB;
	}

	public Waypoint(LatLng p) {
		super(p);
	}
}