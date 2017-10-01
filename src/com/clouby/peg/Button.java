package com.clouby.peg;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Button {
	private int fontSize;
	private Color c;
	private Color darkC;
	private String title;

	private float width, height;
	private float x, y;
	private BoundDetect detect;

	private boolean hovered;
	private boolean pressed;

	public Button( int fontSize, String title, Color c, Graphics g){
		this.c = c;
		this.darkC = c.darker();
		this.fontSize = fontSize;
		this.title = title;
		hovered = false;
		pressed = false;

		calcWidthAndHeight(g);
	}

	public void setXAndY(float x, float y){
		this.x = x;
		this.y = y;
		detect = new SquareBoundDetect(width, height,x, y);
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}

	public boolean isInBound(float x, float y){
		return detect.isInBound(x, y);
	}

	public void setIsHovered(boolean hovered){
		this.hovered = hovered;
	}

	public boolean isHovered(){
		return hovered;
	}

	public void setIsPressed(boolean pressed){
		this.pressed = pressed;
	}

	public boolean isPressed(){
		return pressed;
	}


	public void paint(Graphics g){

		if(hovered){
			g.setColor(darkC); 
		}else {
			g.setColor(c); 
		}

		Graphics2D g2d = (Graphics2D) g;
		float rightShift = 0;
		if(pressed){
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, (int) (fontSize * .9f)));
			FontMetrics fm = g2d.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(title, g2d);
			rightShift = width/2 - (float) (r.getWidth()/2);
		}
		else 
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
		g2d.drawString(title, x + rightShift,y +height);  
	}

	private void calcWidthAndHeight(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(title, g2d);
		width = (float) r.getWidth();
		height = (float) fm.getAscent() - fm.getDescent();
	}

}
