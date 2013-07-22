package com.rt;

import com.rt.core.*;
import java.util.ArrayList;

public class TestDriver {

	public TestDriver() {
		positionTests();
		waypointTests();
		legTests();
		mapDataTest();
		mdmTest();
	}

	public void positionTests() {
		System.out.println("Begginning Position and Distance Tests");

		ArrayList<Position> p = new ArrayList<Position>();

		for(double i = 1.0f; i < 5.0f; i+=1.0f) {
			for(double j = 1.0f; j < 5.0f; j+=1.0f) {
				p.add(new Position(i, j));
			}
		}

		Position last = p.get(0);

		for(int i = 1; i < p.size(); i++) {
			System.out.println("\t" + i + " (" + p.get(i).xCoord + ", " + p.get(i).yCoord + ") " +
								"(" + last.xCoord + ", " + last.yCoord + ") " + 
								last.distance(p.get(i)));
		}

		System.out.println("Position Tests finished\n\n");
	}

	public void waypointTests() {
		System.out.println("Begginning Waypoint Tests");

		ArrayList<Waypoint> w = new ArrayList<Waypoint>();

		for(double i = 1.0f; i < 5.0f; i+=1.0f) {
			for(double j = 1.0f; j < 5.0f; j+=1.0f) {
				w.add(new Waypoint(new Position(i, j)));
			}
		}

		for(int i = 0; i < w.size(); i++) {
			System.out.println("\tWaypoint at : (" + w.get(i).centerPoint.xCoord + ", " +
								w.get(i).centerPoint.yCoord + ")");
		}

		System.out.println("Waypoint Tests finished\n\n");
	}
	
	public void legTests() {
		System.out.println("Beginning Leg Tests");

		ArrayList<Leg> l = new ArrayList<Leg>();

		for(int i = 0; i < 10; i++) {
			ArrayList<Position> points = new ArrayList<Position>();

			for(double j = 0.0f;  j < 10.0f; j+=1.0f) {
				Position p = new Position(Math.cos(j*0.5f), Math.sin(j*0.5f));
				points.add(p);
			}

			l.add(new Leg(null, null, points));
		}

		for(int i = 0; i < l.size(); i++) {
			Leg ll = l.get(i);
			System.out.println("\tLeg at (" + ll.centerPoint.xCoord + ", " + ll.centerPoint.yCoord + ") ");

			System.out.printf("\t\t");

			for(int j = 0; j < ll.points.size(); j++) {
				System.out.printf("(%2f, %2f) ", ll.points.get(j).xCoord, ll.points.get(j).yCoord);
			}
			System.out.printf("\n");
		}

		System.out.println("Leg Tests finished\n\n");
	}
	
	public void mapDataTest() {
		System.out.println("Beginning Map Data Tests");
		ArrayList<Waypoint> w = new ArrayList<Waypoint>();
		
		for(int i = 0; i < 4; i++) {
			Waypoint ww = new Waypoint(new Position((double)(i%2), (double)(i/2)));
			w.add(ww);
		}

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

		for(int i = 0; i < w.size(); i++) {
			Waypoint ww = w.get(i);
			System.out.printf("\tWaypoint at (%f, %f) with Legs: \n\t\t", ww.centerPoint.xCoord, ww.centerPoint.yCoord);
		

			for(int j = 0; ww.legA != null && j < ww.legA.points.size(); j++) {
				System.out.printf("(%f, %f) ", ww.legA.points.get(j).xCoord, ww.legA.points.get(j).yCoord);
			}

			System.out.printf("\n\t\t");

			for(int j = 0; ww.legB != null && j < ww.legB.points.size(); j++) {
				System.out.printf("(%f, %f) ", ww.legB.points.get(j).xCoord, ww.legB.points.get(j).yCoord);
			}

			System.out.printf("\n");
		}

		System.out.println("Finishing Map Data Tests\n\n");

	}
	
	public void mdmTest() {
		System.out.println("Beginning Map Data Tests");
		ArrayList<Position> testPoints = new ArrayList<Position>();

		testPoints.add(new Position(0.0f, 0.0f));
		testPoints.add(new Position(1.0f, 0.0f));
		testPoints.add(new Position(0.0f, 1.0f));
		testPoints.add(new Position(1.0f, 1.0f));
		testPoints.add(new Position(0.66f, 0.33f));
		testPoints.add(new Position(0.33f, 0.33f));

		ArrayList<Waypoint> w = new ArrayList<Waypoint>();
		
		for(int i = 0; i < 4; i++) {
			Waypoint ww = new Waypoint(new Position((double)(i%2), (double)(i/2)));
			w.add(ww);
		}

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

		mdm.printMapData();
		System.out.println("\n");

		for(int i = 0; i < testPoints.size(); i++) {
			MapElement got = mdm.getElement(testPoints.get(i));

			if(got == null) {
				System.out.printf("\t(%f, %f) got null\n", testPoints.get(i).xCoord, testPoints.get(i).yCoord);
				continue;
			}

			if(got instanceof Waypoint) {
				System.out.printf("\t(%f, %f) got Waypoint with pos (%f, %f)\n", 
					testPoints.get(i).xCoord, testPoints.get(i).yCoord,
					got.centerPoint.xCoord, got.centerPoint.yCoord);
			} else {
				System.out.printf("\t(%f, %f) got Leg with pos (%f, %f)\n\t\t", 
					testPoints.get(i).xCoord, testPoints.get(i).yCoord,
					got.centerPoint.xCoord, got.centerPoint.yCoord);

				ArrayList<Position> pp = ((Leg) got).points;
				for(int j = 0; j < pp.size(); j++) {
					System.out.printf("(%f, %f) ", pp.get(j).xCoord, pp.get(j).yCoord);
				}

				System.out.printf("\n");
			}
		}

		System.out.printf("\tTotal Distance is %f\n", mdm.getTotalDistance());

		System.out.println("Finishing Map Data Tests\n\n");
	}

	public static void main(String[] args) {
		parseArgs(args);
		TestDriver driver = new TestDriver();
		System.exit(0);
	}

	public static void parseArgs(String[] args) {

	}
}