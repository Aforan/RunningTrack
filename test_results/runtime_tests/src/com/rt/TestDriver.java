package com.rt;

import com.rt.runtime.*;
import com.rt.core.*;

import java.util.ArrayList;

public class TestDriver {

	public TestDriver() {
		eventTest();
		eventQueueTest();
		runnableTest();
	}

	public void eventTest() {
		System.out.println("Beginning Event Tests");

		ArrayList<Event> e = new ArrayList<Event>();

		for(int i = 0; i < 10; i++) {
			Event newEvent = new Event(i, new Position(i*5.0f, i*8.0f));
			e.add(newEvent);
		}

		for(int i = 0; i < e.size(); i++) {
			Event ee = e.get(i);
			System.out.printf("\t%d: (%f, %f)\n", ee.type, ee.position.xCoord, ee.position.yCoord);
		}

		System.out.println("Done Event Tests\n\n");
	}

	public void eventQueueTest() {
		System.out.println("Beginning Event Queue Tests");

		EventQueue e = new EventQueue();

		for(int i = 0; i < 10; i++) {
			Event newEvent = new Event(i, new Position(i*5.0f, i*8.0f));
			e.addEvent(newEvent);
		}

		while(e.peekEvent() != null) {
			Event ee = e.pollEvent();
			System.out.printf("\t%d: (%f, %f)\n", ee.type, ee.position.xCoord, ee.position.yCoord);
		}

		System.out.println("Done Event Queue Tests\n\n");		
	}

	public void runnableTest() {
		RunnableTest rt = new RunnableTest();
		Thread t = new Thread(rt);
		t.start();

		for(int i = 0; i < 10; i++) {
			Event newEvent = new Event(RunnableTest.TEST_EVENT, new Position(i*5.0f, i*8.0f));
			rt.eventQueue.addEvent(newEvent);
		}

		System.out.println("Sending Kill Signal ...");
		rt.eventQueue.addEvent(new Event(RunnableTest.KILL_EVENT, new Position()));
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
			
		}
	}

	public static void main(String[] args) {
		parseArgs(args);
		TestDriver driver = new TestDriver();
		System.exit(0);
	}

	public static void parseArgs(String[] args) {

	}
}