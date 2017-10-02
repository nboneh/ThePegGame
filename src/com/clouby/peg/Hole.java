package com.clouby.peg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.clouby.peg.util.BoundDetect;
import com.clouby.peg.util.CircleBoundDetect;
import com.clouby.peg.util.DrawShapes;

public class Hole {
	private Color c;
	private boolean hasPeg,highlight;
	private int x, y, radius, radiusOfPeg;
	private Stroke stroke;
	private Stroke highlightStroke;
	private BoundDetect detect;

	public Hole(int x, int y, int radius){
		hasPeg = false;
		highlight = false;
		this.radius = radius;
		this.x = x;
		this.y = y;
		radiusOfPeg = (int) (radius * .9f);
		detect = new CircleBoundDetect(x,y, radius);
		stroke = new BasicStroke(1);
		highlightStroke = new BasicStroke(3);
	}

	public void removePeg(){
		hasPeg = false;
	}

	public void putPeg(Color c){
		this.c = c;
		hasPeg = true;
	}
	
	public Color getPegColor(){
		if(hasPeg)
			return c;
		else 
			return null;
	}

	public void setHighlight(boolean highlight){
		this.highlight = highlight;
	}

	public boolean hasPeg(){
		return hasPeg;
	}

	public boolean isHighlighted(){
		return highlight;
	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		DrawShapes.fillCenteredCircle(g2d,x,y,radius);
		if(hasPeg){
			g2d.setColor(c);
			DrawShapes.fillCenteredCircle(g2d,x,y,radiusOfPeg);
		}
		
		if(highlight){
			g2d.setStroke(highlightStroke);
			g2d.setColor(Color.YELLOW);
			DrawShapes.drawCenteredCircle(g2d,x,y,radius);
		} else {
			g2d.setStroke(stroke);
			g2d.setColor(Color.GRAY);
			DrawShapes.drawCenteredCircle(g2d,x,y,radius);
		}

	}
	
	public boolean isInBound(int x, int y){
		return detect.isInBound(x, y);
	}

}
