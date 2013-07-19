package com.rt.ui;

import java.util.ArrayList;
import android.app.Activity;
import com.rt.core.MapElement;

public abstract class AbstractRuntimeActivity extends Activity{

	abstract void updateMapElements(ArrayList<MapElement> elements);

}
