package com.clouby.peg;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;


public class Game extends Applet implements MouseListener, ActionListener {     
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502078364530436195L;
	private Board board;
	private GameState state;
	private Button playButton;
	
	//Designing the game based around these pixel dimensions
	//game will look best when window has 16:9 ratio but can work in other sizes
	float width = 960;
	float height = 540;

	//Button playButton;
	public void init(){
		Frame c = (Frame)this.getParent().getParent();
		c.setTitle("Peg Game");
		addMouseListener( this );
		state = GameState.MAIN_MENU;
		setSize((int)width, (int)height);
		playButton = new Button(this, 40,  "Start Game", Color.white, getGraphics());
		playButton.setXAndY((width - playButton.getWidth())/2, height * .8f);
	}

	public void paint(Graphics g) {   
		setBackground(Color.black);
		//Scaling to fit window size
		((Graphics2D) g).scale(this.getWidth()/width, this.getHeight()/height);
		switch(state){
		case MAIN_MENU:
			drawATitle("The Peg Game!", Color.WHITE, g);
			playButton.paint(g);
			break;
		case PLAYING:
			break;
		case WIN:
			break;
		case LOSE:
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent eve) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void drawATitle(String title,Color c, Graphics g ){
		g.setColor(c);    
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 60)); 
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(title, g2d);
		int x = (int) ((width - (int) r.getWidth()) / 2);
		int y = (int) ((height) / 2);
		g2d.drawString(title, x,y );  
	}
	
	
}
