package com.rt.runtime;

import java.util.*;

import com.rt.core.*;
import com.rt.ui.*;

public class LocationMonitor extends RunnableInterface{
	
	public Leg CurrentLeg;
	public Waypoint nextWaypoint;
	public double distanceRan;
	public double distanceLeft;
	public MapDataManager mdm;
	public RunningActivity ra;
	private Position lastPosition;
	
	public LocationMonitor(RunningActivity ra, MapDataManager mdm){
		this.ra = ra;
		this.mdm = mdm;
		int i = 0;
		int j = 0;
		
		//Grabs the total distance to run 
		//Runs through each leg 
		for(i=0; i < mdm.getLegs().size(); i++){
			
			//Check to see if there are points
			if(mdm.getLegs().get(i).points.size() <= 0)
				continue;
			
			//Init the position
			tempPos = mdm.getLegs().get(i).points.get(0);
			
			double legDistance = 0;
			
			//Runs through each point in the leg 
			for(j=0; j < mdm.getLegs().at(i).points.size();j++){
				legDistance =  legDistance + tempPos.distance(mdm.getLegs().at(i).points.at(j));
			}
			
			distanceLeft = distanceLeft + legDistance;
		}
	}
	
	private boolean IsValidGPS(Position p){
		return true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public void tick(long time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleEvents() {
		// TODO Auto-generated method stub		
	}
}