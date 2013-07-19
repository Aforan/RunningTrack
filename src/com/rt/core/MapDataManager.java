package com.rt.core;

import java.util.ArrayList;

public class MapDataManager {
	//	Need to find suitible distance threshold
	private static final double DIST_THRESHOLD = 0.5f;

	private ArrayList<Waypoint> waypoints;
	private ArrayList<Leg> legs;
	public Position currentPos;

	public MapDataManager() {
		waypoints = new ArrayList<Waypoint>();
		legs = new ArrayList<Leg>();
		updatePosition();
	}

	public ArrayList<Waypoint> getWaypoints() {
		return waypoints;
	}

	public ArrayList<Legs> getLegs() {
		return legs;
	}

	public void addWaypoint(Waypoint w) {
		waypoints.add(w);
	}

	public boolean removeElement(MapElement e) {
		if(e instanceof(Waypoint)) {
			return removeWaypoint((Waypoint) e);
		} else {
			return removeLeg((Leg) e);
		}
	}

	public boolean removeLeg(Leg l) {
		return legs.remove(l);
	}

	public boolean removeWaypoint(Waypoint w) {
		return waypoints.remove(w);
	}

	public MapElement getElement(Position p) {
		Waypoint closestWaypoint = getWaypoint(p);
		Leg closestLeg  = getLeg(p);

		double wayDist = p.distance(closestWaypoint.position);
		double legDist = p.distance(closestLeg.position);

		MapElement r = (wayDist > legDist)  ? closestLeg : closestWaypoint;

		if(r.position.dist(p) < DIST_THRESHOLD) return r;
		else return null;
	}

	public Waypoint getWaypoint(Position p)  {
		Waypoint r = null;
		double lastDist = 0.0f;

		for(int i = 0; i < waypoints.size(); i++) {
			if(r == null) {
				r = waypoints.at(i);
				lastDist = p.distance(r.position);

				break;
			}
			double newDist = p.distance(waypoints.at(i).position);

			if(newDist < lastDist) {
				lastDist = newDist;
				r = waypoints.at(i);
			}
		}

		return r;
	}

	public Leg getLeg(Position p) {
		Leg r = null;
		double lastDist = 0.0f;

		for(int i = 0; i < legs.size(); i++) {
			if(r == null) {
				r = legs.at(i);
				lastDist = p.distance(r.position);

				break;
			}

			double newDist = -1.0f;

			//	Find closest point in this leg
			for(int j  = 0; j < legs.at(i).points.size(); j++) {
				if(newDist == -1.0f) {
					newDist = p.distance(legs.at(i).points.at(j)); 
					break;
				}

				double t = p.distance(legs.at(i).points.at(j));
			
				if(t < newDist) {
					newDist = t;
				}
			}

			if(newDist < lastDist) {
				lastDist = newDist;
				r = legs.at(i);
			}
		}

		return r;
	}

	public void updatePosition() {
		//	Need to do this from android
	}
}