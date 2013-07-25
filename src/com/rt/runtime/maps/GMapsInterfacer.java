package com.rt.runtime.maps;

import java.util.*;

import javax.xml.*;
import javax.xml.parsers.*;

import com.rt.core.*;

public class GMapsInterfacer{
	
	//SAXParserFactory parseFactory; 
	//parseFactory.newSAXParser();
	
	//Leg not void
	public Leg getPath(Waypoint start, Waypoint end){
		ArrayList<Position> pp = new ArrayList<Position>();
		pp.add(start.centerPoint);
		pp.add(end.centerPoint);
		return new Leg(start, end, pp);
	}
	
	//String not void
	private void query(Position start, Position end) {
		
	}
	
	//List not void and not sure about the input	
	private void constructMapData() {
		
	}
}