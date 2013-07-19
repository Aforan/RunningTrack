package com.rt.runtime;

import java.util.*;

import com.rt.core.Position;

public class ConstructionInterface extends RunnableInterface {
	public static final long GPS_UPDATE_TIME = 1500;

	public static final int CLICK_EVENT = 0;
	public static final int CONNECT_EVENT = 1;
	public static final int REMOVE_EVENT = 0;

	public ArrayList<MapElement> selectedList;
	public MapDataManager mdm;
	public GMapsInterfacer gmi;
	public PlanningActivity pa;	

	public long lastGPSUpdate;

	public ConstructionInterface(PlanningActivity activity) {
		super();

		pa = activity;
		selectedList = new ArrayList<MapElement>();
		mdm = new MapDataManager();
		gmi = new GMapsInterfacer();
		pa = new PlanningActivity();
	}

	@Override
	public void run() {
		long last = System.getCurrentTimeMillis();
		lastGPSUpdate = -1;

		while(1) {
			long cur = System.getCurrentTimeMillis();
			tick(cur-last);

			handleEvents();
		}
	}

	@Override
	public void tick(long time) {
		if (lastGPSUpdate == -1 || lastGPSUpdate + time >= GPS_UPDATE_TIME) {
			lastGPSUpdate = 0;
			mdm.updatePosition();
		}

		lastGPSUpdate += time;
	}

	@Override
	public void handleEvents() {
		while(eventQueue.peekEvent() != NULL) {
			Event e = eventQueue.pollEvent();
			
			switch(e.type) {
				case CLICK_EVENT:
					MapElement selectedElement = mdm.getElement(e.position);

					if(selectedElement instanceof(Leg)) {
						if(selectedList.size() > 1) {
							selectedList.clear();
						}

						selectedList.add(selectedElement);
					} else if(selectedElement instanceof(Waypoint)) {
						if(selectedList.size() >= 2) {
							MapElement a = selectedList.at(0);
							MapElement b = selectedList.at(1);

							selectedList.clear();

							if(a instanceof(Waypoint) && b instanceof(Waypoint)) {
								selectedList.add(a);
								selectedList.add(selectedElement);
							} else {
								if(a instanceof(Waypoint)) {
									selectedList.add(a);
									selectedList.add(selectedElement);
								} else {
									selectedList.add(b);
									selectedList.add(selectedElement);									
								}
							}
						}
					}

					break;
				case CONNECT_EVENT:
					if(selectedList.size() == 2) {
						if(selectedList.at(0) instanceof(Waypoint) && 
							selectedList.at(1) instanceof(Waypoint)) {
							Leg addLeg = gmi.getPath(selectedList.at(0).position, selectedList.at(1).position);

							if(addLeg != NULL) {
								mdm.addLeg(addLeg);
							}

							ArrayList<MapElement> newElements = new ArrayList<MapElement>();
							newElements.addAll(mdm.getLegs());
							newElements.addAll(mdm.getWaypoints());

							pa.updateMapElements(newElements);
						}
					}
					
					break;
				case REMOVE_EVENT:
					if(selectedList.size() > 0) {
						MapElement rem = selectedList.at(0);
						selectedList.remove(0);

						mdm.removeElement(rem);
						ArrayList<MapElement> newElements = new ArrayList<MapElement>();
						newElements.addAll(mdm.getLegs());
						newElements.addAll(mdm.getWaypoints());

						pa.updateMapElements(newElements);

					}
					break;
				default:
					break;
			}

		}
	}
}