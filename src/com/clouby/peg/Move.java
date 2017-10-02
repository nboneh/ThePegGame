package com.clouby.peg;

public class Move{
	private Hole movePeg;
	private Hole jumpPeg;
	private Hole landHole;
	
	Move(Hole movePeg, Hole jumpPeg, Hole landHole){
		this.movePeg = movePeg;
		this.jumpPeg = jumpPeg;
		this.landHole = landHole;
	}
	
	Hole getLandHole(){
		return landHole;
	}
	
	void makeMove(){
		jumpPeg.removePeg();
		landHole.putPeg(movePeg.getPegColor());
		movePeg.removePeg();
	}
}
