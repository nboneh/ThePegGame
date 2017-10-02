package com.clouby.peg;

public enum Direction {
	
	//In theory it is possible to move in hexagonal direction from any peg
	//These enum objects will give us an easy way to traverse the lower triangle array
	//That fits with the equilateral triangle model
	TOP_LEFT(-1, -1),
	TOP_RIGHT(0, -1),
	LEFT(-1, 0),
	RIGHT(1, 0),
	BOT_LEFT(1, 1),
	BOT_RIGHT(0, 1);
	
	
	private int rightAdd;
	private int downAdd;
	
	Direction(int rightAdd, int downAdd){
		this.rightAdd = rightAdd;
		this.downAdd = downAdd;
	}
	
	public int getRightAdd(){
		return rightAdd; 
	}
	
	public int getDownAdd(){
		return downAdd; 
	}
}
