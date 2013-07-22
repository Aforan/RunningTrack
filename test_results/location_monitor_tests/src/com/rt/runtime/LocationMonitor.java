package com.rt.runtime;

import java.util.*;

import com.rt.core.*;
import com.rt.ui.RunningActivity;

public class LocationMonitor extends RunnableInterface{
	public static final long GPS_WAIT_TIME = 500;

	public static final int PAUSE_EVENT = 0;
	public static final int KILL_EVENT = 1;
	public static final int UN_PAUSE_EVENT = 2;

	public Leg currentLeg;
//	public Waypoint nextWaypoint;
	public double distanceRan;
	public double distanceLeft;
	public MapDataManager mdm;
	public RunningActivity ra;
	public Position lastPosition;
	private boolean running;
	private long lastGPSTime;
	private boolean paused;

	public LocationMonitor(RunningActivity ra, MapDataManager mdm){
		this.ra = ra;
		this.mdm = mdm;

		if(mdm != null) {
			distanceLeft = mdm.getTotalDistance();
			
			mdm.updatePosition();
			lastPosition = mdm.currentPos;
			lastGPSTime = System.currentTimeMillis();

			running = true;
			paused = false;
		} else {
			//Uh oh, need to error out here ... 
			running = false;
		}

		distanceRan = 0.0f;
		mdm.updatePosition();
		lastPosition = mdm.currentPos;
	}
	
	private boolean IsValidGPS(Position p){
		return true;
	}

	@Override
	public void run() {
		while(running) {
			if(!paused) {
				long curTime = System.currentTimeMillis();
				tick(curTime - lastGPSTime);
			}
			
			handleEvents();
			try {
				Thread.sleep(100);
			} catch(Exception e) { /* ... */}
		}
	}

	@Override
	public void tick(long time) {
		if(time >= GPS_WAIT_TIME) {
			mdm.updatePosition();
			Position newPos = mdm.currentPos;
			lastGPSTime = System.currentTimeMillis();

			MapElement onElement = mdm.getElement(newPos);

			//	If we are currently on a map element (eg reasonably close)
			if(onElement != null) {
				if(onElement instanceof Leg) {
					currentLeg = (Leg) onElement;
				} 
				
				double dist = newPos.distance(lastPosition);
				distanceRan += dist;
				distanceLeft -= dist;
			} else currentLeg = null;

			lastPosition = newPos;
		}
	}

	@Override
	public void handleEvents() {
		while(eventQueue.peekEvent() != null) {
			Event e = eventQueue.pollEvent();

			switch(e.type) {
			case PAUSE_EVENT:
				paused = true;
				break;
			case KILL_EVENT:
				running = false;
				break;
			case UN_PAUSE_EVENT:
				paused = false;
				break;
			default:
				break;
			}
		}
	}
}