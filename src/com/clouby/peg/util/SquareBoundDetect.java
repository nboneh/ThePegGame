package com.clouby.peg.util;

public class SquareBoundDetect implements BoundDetect {
	
	private int left, right, top, bot;
	
	public SquareBoundDetect(int width, int height, int x, int y){
		left = x;
		right = x + width;
		top = y;
		bot = y + height;
	}

	@Override
	public boolean isInBound(int x, int y){
		return x >= left && x <= right && y >= top && y <= bot;
	}
}
