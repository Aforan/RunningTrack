package com.rt.runtime;

import java.util.*;
import java.lang.*;

 public abstract class RunnableInterface implements Runnable{
	 
	 public abstract void run();
	 
	 public abstract void tick(long time);
	 
	 public abstract void handleEvents();
	 
 }