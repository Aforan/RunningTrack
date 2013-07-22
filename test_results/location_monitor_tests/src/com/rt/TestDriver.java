package com.rt;

import com.rt.runtime.*;
import com.rt.core.*;

import java.util.ArrayList;

public class TestDriver {

	public TestDriver() {
		System.out.println("Beginning Location Monitor Tests");
		System.out.println("Initializing MDM");

		ArrayList<Waypoint> w = new ArrayList<Waypoint>();
		
		w.add(new Waypoint(new Position(0.0f, 0.0f)));
		w.add(new Waypoint(new Position(10.0f, 10.0f)));

		Waypoint last = w.get(0);

		for(int i = 1; i < w.size(); i++) {
			Double xd = (w.get(i).centerPoint.xCoord - last.centerPoint.xCoord) / 3.0f;
			Double yd = (w.get(i).centerPoint.yCoord - last.centerPoint.yCoord) / 3.0f;

			ArrayList<Position> points = new ArrayList<Position>();

			for(int j = 0; j < 4; j++) {
				Position p = new Position(	last.centerPoint.xCoord + (xd*(double)j),
											last.centerPoint.yCoord + (yd*(double)j));

				points.add(p);
			}

			Leg l = new Leg(last, w.get(i), points);
			last.legB = l;
			w.get(i).legA = l;

			last = w.get(i);
		}

		MapDataManager mdm = new MapDataManager();

		for(int i = 0; i < w.size(); i++) {
			mdm.addWaypoint(w.get(i));
		}

		System.out.println("MDM Initialized");
		
		mdm.printMapData();
		System.out.println("\n");
		
		System.out.println("Beginning Test Sequence");

		LocationMonitor lm = new LocationMonitor(null, mdm);
		Thread t = new Thread(lm);
		t.start();

		long elapsed = 0;
		long lastTime = System.currentTimeMillis();

		while (elapsed < 5000) {
			long curTime = System.currentTimeMillis();
			elapsed += curTime - lastTime;
			lastTime = curTime;

			System.out.printf("\tNow at pos (%f, %f)\n", lm.lastPosition.xCoord, lm.lastPosition.yCoord);

			Leg ll = lm.currentLeg;

			if(ll != null) {
				System.out.printf("\t\tCurrent Leg is: ", lm.lastPosition);

				for(int i = 0; i < ll.points.size(); i++) {
					System.out.printf("(%f, %f) ", ll.points.get(i).xCoord, ll.points.get(i).yCoord);
				}

				System.out.printf("\n");

			} else {
				System.out.printf("\t\tCurrent Leg is null\n", lm.lastPosition);
			}

			try {
				Thread.sleep(500);
			} catch(Exception e) {

			} 		
		}

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