package com.rt.runtime;

import java.util.*;
import java.lang.*;
import java.io.*;

import android.os.*;
import android.util.*;

public class MetricsAggregator{
	
	public void buildMetrics() throws IOException{
		
		File temp = new File("temp.dat");
		
		//Grabs file from SD card 
		File file = new File(Environment.getExternalStorageDirectory(), "metric.dat");
		
		//Check to see if file exist.
		if(!file.exists()){
			
			//Create the file
			File metric = new File("metric.dat");
			
			//Probably don't have to do this but i did it anyways
			copy(file, metric);
		}
		
		//File exist  
		else{
			//Copy file
			copy(file,temp);
		}
		
		//Writing new data to the file 
		try{
			
			Calendar c = Calendar.getInstance();
			
			//Need to know who to call the metric class 
			String date = ("date: " + c.MONTH + c.DAY_OF_MONTH + c.YEAR + " " );
			String time = ("Time: " + /*Time gets here */ " ");
			String distance = ("Distance: " + /*whatever the distance is*/ "\n ");

			StringBuilder sb = new StringBuilder();
			sb.append(date);
			sb.append(time);
			sb.append(distance);
			
			String data = sb.toString();
			
			FileWriter fw = new FileWriter(temp.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//THis is where we add what need to be written 
			bw.write(data);
			bw.close();
		}
		
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//Copies the file 
	public static void copy(File src, File dst) throws IOException{
		
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);
		
		byte[] buffer = new byte[1024];
		int length;
		while ((length = in.read(buffer))>0){
			out.write(buffer, 0, length);
		}
		
		in.close();
		out.close();
	}

	public static void write(File src, metric data){
		
	}
	
}