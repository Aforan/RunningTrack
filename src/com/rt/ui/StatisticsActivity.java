package com.rt.ui;

import java.util.ArrayList;

import com.rt.R;
import com.rt.R.layout;
import com.rt.R.menu;
import com.rt.runtime.Metric;
import com.rt.runtime.MetricsAggregator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class StatisticsActivity extends Activity {

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

}
