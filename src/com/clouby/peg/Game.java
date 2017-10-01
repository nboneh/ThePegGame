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
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;


public class Game extends Applet implements MouseListener, MouseMotionListener {     
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502078364530436195L;
	private Board board;
	private GameState state;
	private Button playButton;
	private Button playAgainButton;


	//Designing the game based around these pixel dimensions
	//game will look best when window has 16:9 ratio but can work in other sizes
	float width = 960;
	float height = 540;

	//Button playButton;
	public void init(){
		Frame c = (Frame)this.getParent().getParent();
		c.setTitle("Peg Game");
		addMouseListener(this);
		addMouseMotionListener(this);
		state = GameState.MAIN_MENU;
		setSize((int)width, (int)height);

		playButton = new Button( 40,  "Start Game", Color.white, getGraphics());
		playButton.setXAndY((width - playButton.getWidth())/2, height * .8f);

		playAgainButton = new Button( 40,  "Play Again", Color.white, getGraphics());
		playAgainButton.setXAndY((width - playButton.getWidth())/2, height * .8f);
	}


	public void paint(Graphics g) { 
		//Scaling to fit window size
		((Graphics2D) g).scale(this.getWidth()/width, this.getHeight()/height);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int)width, (int)height);
		switch(state){
		case MAIN_MENU:
			drawATitle("The Peg Game!", Color.WHITE, g);
			playButton.paint(g);
			break;
		case PLAYING:
			drawATitle("YAYA", Color.WHITE, g);
			break;
		case WIN:
			drawATitle("You Win!", Color.GREEN, g);
			playAgainButton.paint(g);
			break;
		case LOSE:
			drawATitle("You Lose!", Color.RED, g);
			playAgainButton.paint(g);
			break;
		default:
			break;
		}
	}
	
	public void update(Graphics g) { 
		paint(g);
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


	@Override
	public void mouseDragged(MouseEvent e) {
		//Track mouse movement even if held down or not held down
		mouseMoved(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(state == GameState.PLAYING)
			return;
		float x = e.getX() * (width/this.getWidth());
		float y = e.getY()* (height/this.getHeight());
		
		Button b = playAgainButton;
		if(state == GameState.MAIN_MENU)
			b = playButton;

		if(b.isInBound(x, y) && !b.isHovered()){
			b.setIsHovered(true);
			repaint();
		} else if(!b.isInBound(x, y) && b.isHovered()){
			b.setIsHovered(false);
			b.setIsPressed(false);
			repaint();
		}
		
		e.consume();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(state != GameState.PLAYING)
			return;
		
		float x = e.getX() * (width/this.getWidth());
		float y = e.getY()* (height/this.getHeight());
		
		
		
		e.consume();
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if(state == GameState.PLAYING)
			return;
		
		float x = e.getX() * (width/this.getWidth());
		float y = e.getY()* (height/this.getHeight());
		
		Button b = playAgainButton;
		if(state == GameState.MAIN_MENU)
			b = playButton;

		if(b.isInBound(x, y) && !b.isPressed()){
			b.setIsPressed(true);
			repaint();
		} else if(!b.isInBound(x, y) && b.isPressed()){
			b.setIsPressed(false);
			repaint();
		}
		
		e.consume();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if(state == GameState.PLAYING)
			return;
		float x = e.getX() * (width/this.getWidth());
		float y = e.getY()* (height/this.getHeight());
		
		Button b = playAgainButton;
		if(state == GameState.MAIN_MENU)
			b = playButton;

		if(b.isInBound(x, y) && b.isPressed()){
			state = GameState.PLAYING;
			this.repaint();
		}
		
		e.consume();
	}


}
