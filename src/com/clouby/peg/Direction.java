package com.clouby.peg;

public enum Direction {
	
	//In theory it is possible to move in hexagonal direction from any peg
	//These enum objects will give us an easy way to traverse the triangle array
	TOP_LEFT(-1, -1),
	TOP_RIGHT(0, -1),
	LEFT(-1, 0),
	RIGHT(1, 0),
	BOT_LEFT(1, 1),
	BOT_RIGHT(2, 1);
	
	
	private int movement;
	private int rowNumAdd;
	
	Direction(int movement, int rowNumAdd){
		this.movement = movement;
		this.rowNumAdd = rowNumAdd;
	}
	
	public int getMovement(){
		return movement; 
	}
	
	public int rowNumAdd(){
		return rowNumAdd; 
	}
}
