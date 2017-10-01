package com.clouby.peg;

public class SquareBoundDetect implements BoundDetect {
	
	private float left, right, top, bot;
	
	public SquareBoundDetect(float width, float height, float x, float y){
		left = x;
		right = x + width;
		top = y;
		bot = y + height;
	}

	@Override
	public boolean isInBound(float x, float y){
		return x >= left && x <= right && y >= top && y <= bot;
	}
}
