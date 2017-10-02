package com.clouby.peg;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main {
	public static void main(String[] args){
		int rows = 5;
		for(int i = 0; i < args.length; i++){
			try{
				//Trying to find an argument that's a number and setting it to number of rows
				rows = Integer.parseInt(args[i]);
			} catch(Exception e){
			}
		}
		Game game=new Game(rows);
		Frame myFrame=new Frame("Peg Game");


		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});

		myFrame.add(game);
		myFrame.pack();
		myFrame.setVisible(true);

		game.init();
		myFrame.setSize(960, 540);
	}
}
