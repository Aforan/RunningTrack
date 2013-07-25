package com.rt.core;

public class Waypoint extends MapElement {
	public Leg legA;
	public Leg legB;

/*	
 *	NOTE:	We should do some sort of validation here,
 *			to make sure the Legs are valid for this wp
 */
	public Waypoint(Leg legA, Leg legB, Position p) {
		super(p);

		this.legA = legA;
		this.legB = legB;
	}

	public Waypoint(Position p) {
		super(p);
	}
}