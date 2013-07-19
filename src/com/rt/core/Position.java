package com.rt.core;

public class Position {
	public double xCoord;
	public double yCoord;

	public Position() {
		this(0.0f, 0.0f);
	}

	public Position(double x, double y) {
		this.xCoord = x;
		this.yCoord = y;
	}

	public double distance(Position p) {
		return Math.sqrt(Math.pow(p.xCoord - xCoord, 2) + 
			Math.pow(p.yCoord + yCoord, 2));
	}
}