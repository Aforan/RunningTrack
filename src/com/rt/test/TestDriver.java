package com.rt.test;

import com.google.android.gms.maps.model.LatLng;
import com.rt.core.Leg;
import com.rt.core.Waypoint;
import com.rt.runtime.maps.GMapsInterfacer;

public class TestDriver {
	public static void runTests() {
		
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
	
	public static void log(String s) {
		System.out.println(s);
	}
}
