package com.rt.runtime;

import java.util.*;

public class metric extends MetricsAggregator{
	
	public Calendar c;
	public int mon;
	public int day;
	public int year;
	public double time;
	public double dist;
	
	public metric(double distance, Calendar date, double runTime){
		
		this.c = date;
		this.mon = c.MONTH;
		this.day = c.DAY_OF_MONTH;
		this.year = c.YEAR;
		this.time = runTime;
		this.dist = distance;
		
	}
	
}
