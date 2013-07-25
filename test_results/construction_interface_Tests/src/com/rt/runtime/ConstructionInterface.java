package com.rt.runtime;

import java.util.*;
import com.rt.core.*;
import com.rt.ui.*;

public class ConstructionInterface extends RunnableInterface {
	public static final long GPS_UPDATE_TIME = 1500;

	public static final int CLICK_EVENT = 0;
	public static final int CONNECT_EVENT = 1;
	public static final int REMOVE_EVENT = 2;
	public static final int GPS_UPDATE_EVENT = 3;
	public static final int ADD_WAYPOINT_EVENT = 4;

	public ArrayList<MapElement> selectedList;
	public MapDataManager mdm;
	//public GMapsInterfacer gmi;
	//public PlanningActivity pa;	

	public long lastGPSUpdate;

	public ConstructionInterface(RunningActivity activity) {
		super();

		//pa = activity;
		selectedList = new ArrayList<MapElement>();
		mdm = new MapDataManager();
		//gmi = new GMapsInterfacer();
	}

	@Override
	public void run() {
		long last = System.currentTimeMillis();
		lastGPSUpdate = -1;

		while(true) {
			long cur = System.currentTimeMillis();
			tick(cur-last);

			handleEvents();
		}
	}

	@Override
	public void tick(long time) {
	}

	@Override
	public void handleEvents() {
		while(eventQueue.peekEvent() != null) {
			Event e = eventQueue.pollEvent();
			
			switch(e.type) {
				case CLICK_EVENT:
					MapElement selectedElement = mdm.getElement(e.position);

					if(selectedElement == null){
						System.out.println("Was a null");
						break;
					} 

					if(selectedElement instanceof Leg) {
						
						if(selectedList.size() > 1) {
							selectedList.clear();
						}

						selectedList.add(selectedElement);
					} else if(selectedElement instanceof Waypoint ) {
						System.out.println("Was a waypoint");
						if(selectedList.size() >= 2) {
							MapElement a = selectedList.get(0);
							MapElement b = selectedList.get(1);

							selectedList.clear();

							if(a instanceof Waypoint && b instanceof Waypoint) {
								selectedList.add(b);
								selectedList.add(selectedElement);
							} else {
								if(a instanceof Waypoint) {
									selectedList.add(a);
									selectedList.add(selectedElement);
								} else {
									selectedList.add(b);
									selectedList.add(selectedElement);									
								}
							}
						} else {
							if(	selectedList.size() > 0 && 
								selectedList.get(0) instanceof Leg) {
									selectedList.clear();
								}

							selectedList.add(selectedElement);
						}
					}

					break;
				case CONNECT_EVENT:
					if(selectedList.size() == 2) {
						if(selectedList.get(0) instanceof Waypoint && 
							selectedList.get(1) instanceof Waypoint) {
							Leg addLeg = null;//gmi.getPath(selectedList.get(0).position, selectedList.get(1).position);

							if(addLeg != null) {
								addLeg.wayA = (Waypoint) selectedList.get(0);
								addLeg.wayB = (Waypoint) selectedList.get(1);
							}

							ArrayList<MapElement> newElements = new ArrayList<MapElement>();
							newElements.addAll(mdm.getLegs());
							newElements.addAll(mdm.getWaypoints());

							selectedList.clear();
							selectedList.add(addLeg);

							//pa.updateMapElements(newElements, selectedList);
						}
					}
					
					break;
				case REMOVE_EVENT:
					if(selectedList.size() > 0) {
						MapElement rem = selectedList.get(0);
						selectedList.remove(0);

						mdm.removeElement(rem);
						ArrayList<MapElement> newElements = new ArrayList<MapElement>();
						newElements.addAll(mdm.getLegs());
						newElements.addAll(mdm.getWaypoints());

						//pa.updateMapElements(newElements, selectedList);
					}
					break;
				case GPS_UPDATE_EVENT:
					mdm.updatePosition(e.position);
					break;
				case ADD_WAYPOINT_EVENT:
					if(mdm.getElement(e.position) == null) {
						mdm.addWaypoint(new Waypoint(e.position));
						System.out.println("Added wp");
					}
					break;
				default:
					break;
			}

		}
	}
}