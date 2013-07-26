package com.rt.runtime;

import com.google.android.gms.maps.model.LatLng;
import com.rt.core.*;
import java.util.*;

public class Event {
	public int type;
	public LatLng position;

	public Event (int type, LatLng pos) {
		position = pos;
		this.type = type;
	}
}