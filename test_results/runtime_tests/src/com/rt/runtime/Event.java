package com.rt.runtime;

import com.rt.core.*;
import java.util.*;

public class Event {
	public int type;
	public Position position;

	public Event (int type, Position pos) {
		position = pos;
		this.type = type;
	}
}