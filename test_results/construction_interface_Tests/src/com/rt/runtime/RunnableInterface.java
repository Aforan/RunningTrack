package com.rt.runtime;

import java.util.*;
import java.lang.Runnable;

public abstract class RunnableInterface implements Runnable{
	public EventQueue eventQueue;

	public RunnableInterface() {
		eventQueue = new EventQueue();
	}

	public abstract void run();
	 
	public abstract void tick(long time);
	 
	public abstract void handleEvents(); 
}