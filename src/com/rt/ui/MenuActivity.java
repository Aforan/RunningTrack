package com.rt.ui;
import com.rt.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		//Set up click listeners for all the buttons
		View runButton = findViewById(R.id.run_button);
		runButton.setOnClickListener(this);
		
		//Set up click listeners for all the buttons
		View planButton = findViewById(R.id.plan_button);
		planButton.setOnClickListener(this);
		
		View statisticsButton = findViewById(R.id.statistics_button);
		statisticsButton.setOnClickListener(this);
		
		View helpButton = findViewById(R.id.help_button);
		helpButton.setOnClickListener(this);
		
		
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.run_button:
			Intent i = new Intent(this, RunningActivity.class);
			startActivity(i);
			break;
		case R.id.plan_button:
			Intent x = new Intent(this, PlanningActivity.class);
			startActivity(x);
			break;
		
		case R.id.statistics_button:
			Intent y = new Intent(this, StatisticsActivity.class);
			startActivity(y);
			break;
		case R.id.help_button:
			Intent z = new Intent(this, HelpActivity.class);
			startActivity(z);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}