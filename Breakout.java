/*
* File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
	
/** Set paddle position */	
	private static final int PADDLE_Y_POSIT = HEIGHT - PADDLE_Y_OFFSET;
	

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;
	

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	



/** Runs the Breakout program. */
	public void run() {		
		setupGame();
		runGame();			
	}



/** Sets the gameboard up ready to play placing bricks, paddle and ball */
	private void setupGame() {
		setBricks();
		setPaddle();
		totalBricks = NBRICK_ROWS * NBRICKS_PER_ROW;
		brickCounter();		
		addMouseListeners();
	}
	
/** PLaces bricks down moving one row down every iteration, changing colour every other iteration*/	   
	private void setBricks() {		
		int startingPositionY = BRICK_Y_OFFSET;
		Color color = Color.RED;
		for (int i=0; i<NBRICK_ROWS; i++) {//iterate through rows
			int startingPositionX = BRICK_SEP / 2;
			
			for (int j=0; j<NBRICKS_PER_ROW; j++) {//iterate through, adding bricks
				GRect brick = new GRect (startingPositionX, startingPositionY, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setColor(color);
				add(brick);				
				startingPositionX += (BRICK_WIDTH + BRICK_SEP);
			}
			/**Change colour after every odd row starting from 1*/
			if (i==1) color = Color.ORANGE;
			else if  (i==3) color = Color.YELLOW;
			else if  (i==5) color = Color.GREEN;
			else if  (i==7) color = Color.CYAN;
			startingPositionY += (BRICK_HEIGHT + BRICK_SEP);
			
		}
		
	}
	
	/** Set starting posit of Paddle and add to canvas*/
	private void setPaddle() {		
		int startingPositionX = (WIDTH / 2) - (PADDLE_WIDTH / 2);
		paddle = new GRect (startingPositionX, PADDLE_Y_POSIT, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);		
	}
	
	/** Listen to mouse event and move paddle to position of mouse on X */	
	public void mouseMoved(MouseEvent e) {		
		if ((e.getX() + (PADDLE_WIDTH / 2)) < WIDTH && (e.getX() - (PADDLE_WIDTH / 2)) > 0) {
			paddle.setLocation((e.getX() - (PADDLE_WIDTH / 2)), PADDLE_Y_POSIT);			
		}
	}
	
	
	/** Position and add ball to canvas  */ 
	private void setBall() {
		ball = new GOval ((WIDTH / 2) - (BALL_RADIUS / 2),(HEIGHT / 2) - (BALL_RADIUS / 2), BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
	
	/** Add bricks left counter */
	private void brickCounter() {
		counter = new GLabel("" + totalBricks);
		counter.setFont("Times New Roman-36");
		add(counter, (WIDTH / 2) - (counter.getWidth() / 2), 50);
		
	}
	
	
	/** Start game */
	private void runGame() {		
		while (turn <= NTURNS) {			
			paddleHits = 0;
			delay = 10;
			setBall();
			initialBallDirection();			
			while (ball != null) {
				checkWall();
				checkObject();
				if (ball.getY() + BALL_RADIUS >= HEIGHT - 1) break;
				moveBall();								
			}
			remove(ball); //remove ball when it hits bottom wall
			turn++; // increase turn every time ball hits bottom wall
			
		}
	}
	
	
	/**Move ball first time at random angle in random direction  */
	private void initialBallDirection() {		
		vx = rgen.nextDouble(1.0, 3.0);
		vy = 1;
		if (rgen.nextBoolean(0.5)) vx = -vx;					
	}
	
	/** Move ball */
	private void moveBall() {					
		ball.move(vx, vy);			 
		pause(delay);	
	}
	
	/** Check to see if there is a wall and reverse direction of ball */
	private void checkWall () {
		if (ball.getX() + BALL_RADIUS >= WIDTH - 1 | ball.getX() <= 1) {
			vx = -vx;
		}
		else if (ball.getY() <= 1) {
			vy = -vy;
		}		
	}
	
	/** Check to see what object is present  */
	private void checkObject() {		
		GObject collider = getCollidingObject();
		if (collider != null) {
			if (collider == paddle) {
				if (paddleHits % 5 == 0) { //increase speed every 5 hits
					if (delay != 0) delay--;
					vx =+ 1;
				}
				paddleHits++;
			}
			else {
				remove(collider); //remove brick
				totalBricks--;
				counter.setLabel("" + totalBricks);
			}
			vy = -vy; //change dirction
			bounceClip.play(); //play sound on hit
		}
						
	}
	
	
	/** Check four points of ball to see if there is an object then return object */
	private GObject getCollidingObject() {
		double ballPositX = ball.getX();
		double ballPositY = ball.getY();
		GObject gobj;
		gobj = getElementAt(ballPositX, ballPositY);
		if (gobj != null) return gobj;
		gobj = getElementAt(ballPositX + (BALL_RADIUS * 2), ballPositY);
		if (gobj != null) return gobj;
		gobj = getElementAt(ballPositX + (BALL_RADIUS * 2), ballPositY + (BALL_RADIUS * 2));
		if (gobj != null) return gobj;
		gobj = getElementAt(ballPositX, ballPositY + (BALL_RADIUS * 2));
		if (gobj != null) return gobj;
		else {
			return null;
		}
	}
	
	
	
	/* private instance variable for paddle */
	 private GRect paddle; 
	 /* private instance variable for ball/*/
	 private GOval ball;
	 /* x and y velocity of ball*/
	 private double vx, vy;
	 /* Generate random number for initial ball move */
	 private RandomGenerator rgen = RandomGenerator.getInstance();
	 /* count number of turns taken*/
	 private int turn = 1;
	 /** Animation cycle delay */
	 private double delay;
	 /* Count for how many paddle hits there are  */
	 private int paddleHits;
	 /* count total of bricks left */
	 private int totalBricks;
	 /* counter labe */
	 private GLabel counter;
	 /*load audio clip */
	 AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
}
