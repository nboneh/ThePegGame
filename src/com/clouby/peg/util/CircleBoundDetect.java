package com.clouby.peg.util;

public class CircleBoundDetect implements BoundDetect {

    private int centerX, centerY, radius;
    
	public CircleBoundDetect(int centerX, int centerY, int radius){
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
	}

	@Override
	public boolean isInBound(int x, int y) {
		int horzDist = centerX - x;
		int vertDist = centerY - y;
		int dist =  (int) Math.sqrt(horzDist * horzDist + vertDist * vertDist);
		return dist <= radius;
	}

}
