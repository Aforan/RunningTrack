package com.rt.runtime;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;

public class MetricsAggregator{
	
	public static ArrayList<Metric> buildMetrics(Activity caller) {
		
		ArrayList<Metric> mets;
		mets = new ArrayList<Metric>();
		
		try {
			FileInputStream fis = caller.openFileInput("metric.dat");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			while(true) {
				String line = br.readLine();
				
				if(line == null) break;
				if(line.charAt(0) == '#') continue;
				
				String[] splitLine = line.split(",");
				
				if(splitLine.length == 3) {
					String dateString = splitLine[0];
					String timeString = splitLine[1];
					String distString = splitLine[2];
					
					Metric newMet = new Metric(Double.parseDouble(distString), dateString, Double.parseDouble(timeString));
					mets.add(newMet);
				}
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mets;
	}
	
	public static void writeMetric(double distance, double time, Activity caller){
		//Writing new data to the file 
		try{
			Calendar c = Calendar.getInstance();
			
			//Need to know who to call the metric class 
			String dateStr = (c.get(Calendar.MONTH) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));
			String timeStr = ("" + time);
			String distanceStr = ("" + distance);
			
			String line = dateStr + "," + timeStr + "," + distanceStr + "\n";
	
			//THis is where we add what need to be written 
			
			FileOutputStream fos = caller.openFileOutput("metric.dat", Context.MODE_APPEND);
			fos.write(line.getBytes());
			fos.close();
		} catch (IOException e){
			e.printStackTrace();
		}		
	}
	
}