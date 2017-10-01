package com.clouby.peg;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class Button {
	private int fontSize;
	private Color c;
	private ActionListener listener;
	private String title;
	
	private float width, height;
	private float x, y;
	
	public Button(ActionListener listener, int fontSize, String title, Color c, Graphics g){
		this.c = c;
		this.listener = listener;
		this.fontSize = fontSize;
		this.title = title;
		
		calcWidthAndHeight(g);
	}
	
	public void setXAndY(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	
	public void paint(Graphics g){
		g.setColor(c);  
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(title, g2d);
		g2d.drawString(title, x,y );  
	}
	
	private void calcWidthAndHeight(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(title, g2d);
		width = (float) r.getWidth();
		height = (float) r.getHeight();
	}
	
}
