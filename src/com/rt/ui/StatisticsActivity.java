package com.rt.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rt.R;
import com.rt.R.layout;
import com.rt.R.menu;
import com.rt.runtime.Metric;
import com.rt.runtime.MetricsAggregator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TextView;

import com.androidplot.xy.*;

import static com.rt.test.TestDriver.log;

public class StatisticsActivity extends Activity {
	    @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	    	
	    	super.onCreate(savedInstanceState);
	    	 
	        // fun little snippet that prevents users from taking screenshots
	        // on ICS+ devices :-)
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
	                                 WindowManager.LayoutParams.FLAG_SECURE);
	 
	        setContentView(R.layout.activity_statistics);
	 
	        // initialize our XYPlot reference:
	        XYPlot distancePlot = (XYPlot) findViewById(R.id.distance_plot);
	        //XYPlot speedPlot = (XYPlot) findViewById(R.id.speed_plot);

	        ArrayList<Metric> metrics = MetricsAggregator.buildMetrics(this);
	        
	        List<Number> distances = new ArrayList<Number>();
	        List<Number> speeds = new ArrayList<Number>();
	        
	        for(Metric m : metrics) {
	        	distances.add(m.dist);
	        	speeds.add(m.dist / m.time);
	        }
	        	 
	        // same as above
	        XYSeries distanceSeries = new SimpleXYSeries(distances, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Distances");
	 
	        // same as above:
	        LineAndPointFormatter distanceFormat = new LineAndPointFormatter();
	        distanceFormat.setPointLabelFormatter(new PointLabelFormatter());
	        distanceFormat.configure(getApplicationContext(),
	                R.layout.line_point_formatter_with_plf2);
	        distancePlot.addSeries(distanceSeries, distanceFormat);
	 
	        // reduce the number of range labels
	        distancePlot.setTicksPerRangeLabel(3);
	        distancePlot.getGraphWidget().setDomainLabelOrientation(-45);
	/*        
	        // same as above
	        XYSeries speedSeries = new SimpleXYSeries(speeds, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Speeds");
	 
	        // same as above:
	        LineAndPointFormatter speedFormat = new LineAndPointFormatter();
	        speedFormat.setPointLabelFormatter(new PointLabelFormatter());
	        speedFormat.configure(getApplicationContext(),
	                R.layout.line_point_formatter_with_plf2);
	        distancePlot.addSeries(speedSeries, speedFormat);
	 
	        // reduce the number of range labels
	        speedPlot.setTicksPerRangeLabel(3);
	        speedPlot.getGraphWidget().setDomainLabelOrientation(-45);
	        */
	/*        super.onCreate(savedInstanceState);
	 
	        // fun little snippet that prevents users from taking screenshots
	        // on ICS+ devices :-)
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
	                                 WindowManager.LayoutParams.FLAG_SECURE);
	 
	        setContentView(R.layout.activity_statistics);
	 
	        // initialize our XYPlot reference:
	        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
			
	        ArrayList<Metric> metrics = MetricsAggregator.buildMetrics(this);
	        	        
	        List<Number> distances = new ArrayList<Number>();
	        
	        for(Metric m : metrics) {
	        	distances.add(m.dist);
	        }
	        
	        log("distances size: " + distances.size());
	        
	        // Turn the above arrays into XYSeries':
	        XYSeries series1 = new SimpleXYSeries(
	                distances,          
	                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, 
	                "Distance (Miles)");                    
	 
	        // Create a formatter to use for drawing a series using LineAndPointRenderer
	        // and configure it from xml:
	        LineAndPointFormatter series1Format = new LineAndPointFormatter();
	        series1Format.setPointLabelFormatter(new PointLabelFormatter());
	        series1Format.configure(getApplicationContext(),
	                R.layout.line_point_formatter_with_plf2);
	 
	        // add a new series' to the xyplot:
	        plot.addSeries(series1, series1Format);
	 
	        // reduce the number of range labels
	        plot.setTicksPerRangeLabel(1);
	        plot.setTicksPerDomainLabel(1);
	        plot.getGraphWidget().setDomainLabelOrientation(-45);*/
	 
	    }
	
	
	
/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		
		ArrayList<Metric> metrics = MetricsAggregator.buildMetrics(this);
		TableLayout statTable = (TableLayout)findViewById(R.id.stat_table);

		for(Metric m : metrics) {
			TextView tv = new TextView(getApplicationContext());
			tv.setText(m.toString());
			statTable.addView(tv);		
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}
*/
}
