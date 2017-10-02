package com.clouby.peg;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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

	private Image offScreenBuffer = null;


	//Designing the game based around these pixel dimensions
	//game will look best when window has 16:9 ratio but can work in other sizes
	int width = 960;
	int height = 540;

	private float widthScreenRatio;
	private float  heightScreenRatio;

	//Button playButton;
	public void init(){
		Frame c = (Frame)this.getParent().getParent();
		c.setTitle("Peg Game");
		addMouseListener(this);
		addMouseMotionListener(this);
		state = GameState.MAIN_MENU;
		setSize((int)width, (int)height);

		int rows = 5;
		if(this.getParameter("rows") != null)
			rows = Integer.parseInt(this.getParameter("rows"));


		board = new Board((int)(width * .1f), (int)(height * .1f), (int)(width * .8f), (int)(height * .8f), rows);

		playButton = new Button( 40,  "Start Game", Color.white, getGraphics());
		playButton.setXAndY((int)((width - playButton.getWidth())/2), (int)(height * .8f));

		playAgainButton = new Button( 40,  "Play Again", Color.white, getGraphics());
		playAgainButton.setXAndY((int)((width - playButton.getWidth())/2), (int)(height * .8f));
	}


	public void paint(Graphics g) { 
		//Scaling to fit window size
		widthScreenRatio = this.getWidth()/(float)width;
		heightScreenRatio = this.getHeight()/(float)height;

		((Graphics2D) g).scale(widthScreenRatio,heightScreenRatio);

		//Adding antialising for smoothness
		((Graphics2D) g).setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		((Graphics2D) g).setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int)width, (int)height);
		switch(state){
		case MAIN_MENU:
			drawATitle("The Peg Game!", Color.WHITE, g);
			playButton.paint(g);
			break;
		case PLAYING:
			board.paint(g);
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

	public void update(Graphics g)
	{
		//Using double buffer to prevent flickering
		//This is rather simplistic double buffering it does not work on window resize
		Graphics gr;
		if (offScreenBuffer==null ||
				(! (offScreenBuffer.getWidth(this) == this.getWidth()
				&& offScreenBuffer.getHeight(this) == this.getHeight())))
		{
			offScreenBuffer = this.createImage(this.getWidth(), this.getHeight());
		}
		gr = offScreenBuffer.getGraphics();
		paint(gr); 
		g.drawImage(offScreenBuffer, 0, 0, this);     
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
		int x = (int) (e.getX() / widthScreenRatio);
		int y = (int) (e.getY()/ heightScreenRatio);

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

		int x = (int) (e.getX() / widthScreenRatio);
		int y = (int) (e.getY()/ heightScreenRatio);

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
		int x = (int) (e.getX() / widthScreenRatio);
		int y = (int) (e.getY()/ heightScreenRatio);

		if(state == GameState.PLAYING){
			if(board.detect(x, y)){
				//If move has been made check for win or lost
				if(board.won()){
					state = GameState.WIN;
				} else if(board.lost()){
					state = GameState.LOSE;
				}
			}
			repaint();
			e.consume();
			return;
		}

		Button b = playAgainButton;
		if(state == GameState.MAIN_MENU)
			b = playButton;

		if(b.isInBound(x, y) && b.isPressed()){
			state = GameState.PLAYING;
			board.setup();
			this.repaint();
		}

		e.consume();
	}


}
