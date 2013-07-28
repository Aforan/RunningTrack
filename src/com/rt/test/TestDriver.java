package com.rt.test;

import java.util.ArrayList;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.rt.core.Leg;
import com.rt.core.Waypoint;
import com.rt.runtime.Metric;
import com.rt.runtime.maps.GMapsInterfacer;

import static com.rt.runtime.MetricsAggregator.writeMetric;
import static com.rt.runtime.MetricsAggregator.buildMetrics;
public class TestDriver {
	public static void gmiTests() {
		
		Waypoint a = new Waypoint(new LatLng(34.2f, -100.7f));
		Waypoint b = new Waypoint(new LatLng(34.2f, -100.8f));
		
		Leg l = GMapsInterfacer.getPath(a, b);
		
		if(l == null) {
			log("Leg is null");
		} else {
			log("Leg is " + l.wayA.centerPoint + " -> " + l.wayB.centerPoint);
			String ss = "";
			
			for(LatLng ll : l.points) {
				ss += ll;
			}
		
			log("Points: " + ss);
		}
	}
	
	public static void metricTests(Activity caller) {
		log("Attempting to write metrics");
		writeMetric(5.0f, 6.0f, caller);
		log("Attempting to build Metrics");
		ArrayList<Metric> metrics = buildMetrics(caller);
		
		if(metrics == null) {
			log("Metrics null");
			return;
		}
		
		log("Size = " + metrics.size());
		
		for(Metric m : metrics) {
			log("\t{" + m.date + ", " + m.dist + "," + m.time + "}");
		}
	}
	
	public static void log(String s) {
		System.out.println(s);
	}
}
