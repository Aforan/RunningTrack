package com.rt.runtime;

import java.util.*;

import com.rt.core.*;

public class RunnableTest extends RunnableInterface {
	public static final int TEST_EVENT = 0;
	public static final int KILL_EVENT = 1;
	private boolean running;

	public RunnableTest() {
		super();
		running = true;
	}

	public void run() {
		System.out.println("Begginning Runnable Tests");

		while(running) {
			tick(0);
			handleEvents();
		}
		System.out.println("Done Runnable Tests");
	}
	 
	public void tick(long time) {

	}
	 
	public void handleEvents() {
		while(eventQueue.peekEvent() != null) {
			Event e = eventQueue.pollEvent();

			switch(e.type) {
			case TEST_EVENT:
				System.out.printf("\tEvent: %d at (%f, %f)\n", e.type, e.position.xCoord, e.position.yCoord);
				break;
			case KILL_EVENT:
				System.out.println("\tGot a Kill Event Dying ... ");
				running = false;
				break;
			default:
				System.out.printf("\tGot an unknown event: %d at (%f, %f)\n", e.type, e.position.xCoord, e.position.yCoord);
				break;
			}
		}
	}
}