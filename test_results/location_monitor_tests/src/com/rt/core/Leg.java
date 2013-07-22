package com.rt.core;

import java.util.*;

public class Leg extends MapElement{
	public ArrayList<Position> points;
	public Waypoint wayA;
	public Waypoint wayB;

/*	
 *	NOTE:	We should do some sort of validation here,
 *			to mape sure waypoints are valid for this leg
 */
	public Leg(Waypoint wayA, Waypoint wayB, Position p) {
		super(p);
		this.wayA = wayA;
		this.wayB = wayB;
		points = new ArrayList<Position>();
	}

	public Leg(Waypoint wayA, Waypoint wayB, ArrayList<Position> points) {
		super(points.get(points.size()/2));

		this.wayA = wayA;
		this.wayB = wayB;
		this.points = points;
	}
}