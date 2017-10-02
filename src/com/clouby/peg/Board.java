package com.clouby.peg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

public class Board {

	//Using lower triangular matrix to represent the board
	private Hole[][] holes;
	private int numOfRows;
	private int[] xs;
	private int[] ys;
	private Color woodColor;
	private Stroke stroke;
	private Color[] pegColors;
	private List<Move> currentMoves;

	public Board(int x, int y, int width, int height, int numOfRows){
		this.numOfRows = numOfRows;
		xs = new int[]{x, x + width/2, x + width};
		ys = new int[]{y+ height, y , y + height};
		woodColor = new Color(222,184,135);
		stroke = new BasicStroke(3);
		holes = new Hole[numOfRows][numOfRows];
		
		//The circles will be formed inside a smaller triangle as to keep them in bound
		float widthScalar = .8f;
		float heightScalar = .9f;
		int circleTriangleWidth = (int) (width * widthScalar);
		int circleTriangleHeight = (int) (height * heightScalar);
		int circleTriangleX = (int) (x + ((1-widthScalar)/2) * width);
		int circleTriangleY = (int) (y + ((1-heightScalar)/2) * height);
		
		int holeRadius = (int) ((Math.min(circleTriangleWidth, circleTriangleHeight)/numOfRows) * .37f);
		int holeDiameter =holeRadius *2;
    	float heightDiff = holeDiameter + (circleTriangleHeight - holeDiameter *numOfRows)/((float)(numOfRows -1));
		float widthDiff =  holeDiameter + (circleTriangleWidth - holeDiameter *numOfRows)/((float)(numOfRows -1));
		float horzShift =  (circleTriangleWidth/2);	
		float vertShift = holeRadius * 1.5f;
		for(int i = 0; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				int holeX = (int) (circleTriangleX+ widthDiff * j + horzShift );
				int holeY = (int) (circleTriangleY + heightDiff * i + vertShift);
				holes[i][j] = new Hole(holeX,holeY,holeRadius);
			}
			horzShift -= widthDiff/2;
		}
		pegColors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
		currentMoves = new ArrayList<Move>();
	}

	public void setup(){
		currentMoves.clear();
		holes[0][0].removePeg();
		//Sets up the board can also be used to reset to initial game state
		for(int i = 1; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				Color c = pegColors[(int)(Math.random() * pegColors.length)];
				holes[i][j].putPeg(c);
				holes[i][j].setHighlight(false);
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
	
	public boolean won(){
		int numOfPegs = 0;
		for(int i = 0; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				if(holes[i][j].hasPeg()){
					numOfPegs++;
				}
				if(numOfPegs > 1)
					return false;
			}
		}
		return true;
	}
	
	public boolean lost(){
		//Checking if there is no moves left
		for(int i = 0; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				if(holes[i][j].hasPeg() && hasMoves(i,j)){
					//Short circuit evaluation, if one move available then game is not over  
					return false;
				}
			}
		}
		return true;
	}

	public boolean detect(int x, int y){
		//This returns if a move as been made or not to let the game
		//know if it should test if the game is won or lost
		
		//Clearing all highlights
		for(int i = 0; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				holes[i][j].setHighlight(false);
			}
		}

		for(int i = 0; i < numOfRows; i++){
			for(int j = 0; j < i+1; j++){
				Hole hole = holes[i][j];
				if(hole.isInBound(x, y)){
					//Normal click of a peg
					if(hole.hasPeg() ){
						hole.setHighlight(true);
						fillCurrentListOfMoves(i, j);
						for(Move move : currentMoves){
							//Highlighting moves available
							move.getLandHole().setHighlight(true);
						}
					}
					//Checking if player tried to make a move
					else {
						for(Move move : currentMoves){
							if(hole == move.getLandHole()){
								move.makeMove();
								return true;
							}
						}
					}
					return false;
				}
			}
		}
		return false;
	}


	private void fillCurrentListOfMoves(int i, int j){
		checkMoves( i,  j, true);
	}

	private boolean hasMoves(int i, int j){
		return checkMoves(i,j, false);
	}

	private boolean checkMoves(int i, int j, boolean fillCurrentListOfMoves){
		//The boolean return value does not matter if we filling current list of moves
		//This is a helper method to avoid redundancy 
		Hole movePeg = holes[i][j];
		if(fillCurrentListOfMoves)
			currentMoves.clear();
		Direction[] directions = Direction.values();
		for(Direction direction : directions){
			try{
				Hole jumpPeg = holes[i + direction.getDownAdd()][j + direction.getRightAdd()];
				Hole landHole =  holes[i + direction.getDownAdd()*2][j + direction.getRightAdd()*2];
				if(jumpPeg.hasPeg() && !landHole.hasPeg()){
					if(fillCurrentListOfMoves)
						currentMoves.add(new Move(movePeg, jumpPeg,landHole));
					else 
						return true;
				}
			} catch(Exception e){
				//Will raise exception if jump is out of bounds
			}
		}
		return false;
	}
}
