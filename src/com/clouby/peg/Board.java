package com.clouby.peg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;

public class Board {

	//Using lower triangular matrix to represent the board
	private Hole[][] holes;
	private int numOfRows;
	private int width,height,x,y;
	private int[] xs;
	private int[] ys;
	private Color woodColor;
	private Stroke stroke;
	private Color[] pegColors;
	
	public Board(int x, int y, int width, int height, int numOfRows){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.numOfRows = numOfRows;
		xs = new int[]{x, x + width/2, x + width};
		ys = new int[]{y+ height, y , y + height};
		woodColor = new Color(222,184,135);
		stroke = new BasicStroke(3);
		holes = new Hole[numOfRows][numOfRows];
		
		int heightDiff = height/numOfRows;
		int widthDiff = width/numOfRows;
		
		int holeRadius = (int) ((Math.min(width, height)/numOfRows) * .3f);
		for(int i = 0; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				int holeX = x+ widthDiff * j;
			    int holeY = y + heightDiff * i;
				holes[i][j] = new Hole(holeX,holeY,holeRadius);
			}
		}
		pegColors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
	}
	
	public void setup(){
		//Sets up the board can also be used to reset to initial game state
		for(int i = 1; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				Color c = pegColors[(int)(Math.random() * pegColors.length)];
				holes[i][j].putPeg(c);
			}
		}
	}
	
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(woodColor);
		g2d.fillPolygon(xs, ys, 3);
		g2d.setColor(Color.GRAY);
		g2d.setStroke(stroke);
		g2d.drawPolygon(xs,ys,3);
		
		for(int i = 0; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				holes[i][j].paint(g2d);
			}
		}
	
	}
	
	public void detect(int x, int y){
		for(int i = 0; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				Hole hole = holes[i][j];
				if(hole.isInBound(x, y)){
					hole.setHighlight(!hole.isHighlighted());
				}
			}
		}
	}
}
