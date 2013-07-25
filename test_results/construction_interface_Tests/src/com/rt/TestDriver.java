package com.rt;

import com.rt.runtime.*;
import com.rt.core.*;

import java.util.ArrayList;

public class TestDriver {

	public TestDriver() {
		System.out.println("Beginning Location Monitor Tests");

		System.out.println("Beginning Test Sequence");

		ConstructionInterface ci = new ConstructionInterface(null);
		Thread t = new Thread(ci);
		t.start();

		long elapsed = 0;
		long lastTime = System.currentTimeMillis();

		ci.eventQueue.addEvent(new Event(ConstructionInterface.CLICK_EVENT, 
			new Position(10.0f, 10.0f)));
		
		try {
			Thread.sleep(250);
		} catch(Exception e) {

		}

		ArrayList<MapElement> sel = new ArrayList<MapElement>();
		sel.addAll(ci.selectedList);
		
		System.out.println("Size: " + sel.size() + "Should be 0");
		
		System.out.println("Adding waypoint at 10 10:");

		ci.eventQueue.addEvent(new Event(ConstructionInterface.ADD_WAYPOINT_EVENT, 
			new Position(10.0f, 10.0f)));
		
		try {
			Thread.sleep(250);
		} catch(Exception e) {

		}

		ci.eventQueue.addEvent(new Event(ConstructionInterface.CLICK_EVENT, 
			new Position(10.0f, 10.0f)));
		
		try {
			Thread.sleep(250);
		} catch(Exception e) {

		}
		
		sel.clear();
		sel.addAll(ci.selectedList);
		
		System.out.println("Size: " + sel.size() + "Should be 1");


		System.out.println("Finished Location Monitor Tests");

	}	
	public static void main(String[] args) {
		parseArgs(args);
		TestDriver driver = new TestDriver();
		System.exit(0);
	}

	public static void parseArgs(String[] args) {

	}
}