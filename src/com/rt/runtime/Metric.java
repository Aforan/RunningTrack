package com.rt.runtime;

public class Metric extends MetricsAggregator{
	
	public String date;
	public double time;
	public double dist;
	
	public Metric(double distance, String date, double runTime){
		this.date = date;
		this.time = runTime;
		this.dist = distance;
	}
	
	@Override
	public String toString() {
		return  "On " + date + " you ran " + dist + " Miles in " + time + " Minutes!";
	}
	
}
