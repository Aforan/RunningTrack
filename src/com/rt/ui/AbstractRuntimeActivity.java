package com.rt.ui;

import java.util.ArrayList;
import android.support.v4.app.FragmentActivity;


import com.google.android.gms.maps.GoogleMap;
import com.rt.core.Leg;
import com.rt.core.MapDataManager;
import com.rt.core.MapElement;
import com.rt.core.Waypoint;

public abstract class AbstractRuntimeActivity extends FragmentActivity{
	protected static MapDataManager mdm;
	abstract void recreateMap(ArrayList<Waypoint> waypoints, ArrayList<Leg> legs);
}
