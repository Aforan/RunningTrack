package com.rt.ui;

import java.util.ArrayList;
import android.support.v4.app.FragmentActivity;

import com.rt.core.MapDataManager;
import com.rt.core.MapElement;

public abstract class AbstractRuntimeActivity extends FragmentActivity{
	protected MapDataManager mdm;
	abstract void updateMapElements(ArrayList<MapElement> elements, ArrayList<MapElement> selectedElements);

}
