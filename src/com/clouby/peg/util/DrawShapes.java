package com.clouby.peg.util;

import java.awt.Graphics2D;

public class DrawShapes {

	public static void fillCenteredCircle(Graphics2D g, int x, int y, int r) {
		x = x-r;
		y = y-r;
		g.fillOval(x,y,r*2,r*2);
	}
	
	public static void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		x = x-r;
		y = y-r;
		g.drawOval(x,y,r*2,r*2);
	}
}
