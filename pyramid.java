 * File: Pyramid.java

import acm.graphics.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	public void run() {
		layBricks();
		
	}

	private void layBricks () {
		int theWidth = getWidth(); //find with of window
		int theHeight = getHeight(); //find height of window
		int firstLayerWidth = BRICK_WIDTH * BRICKS_IN_BASE; //find width of first layer
		int margins = theWidth - firstLayerWidth; //find difference between width of first layer and width of window
		int startingPositionX = margins / 2; //divide difference by two so that there is equal space wither side of first layer
		int startingPositionY = theHeight - BRICK_HEIGHT; //starting position is from top corner of rect
		int bricksInLayer = BRICKS_IN_BASE; //because bricks in base is constant
	
		for (int i=0; i<BRICKS_IN_BASE;i++)	{ //this loops through the layers depending on how many bricks in base
			
			for (int x=0; x<bricksInLayer;x++) { // this loops through bricks accross
				add(new GRect(startingPositionX, startingPositionY, BRICK_WIDTH, BRICK_HEIGHT)); //this draws rectangle
				startingPositionX += BRICK_WIDTH; //This moves the bricks along one brick every loop
			}
			bricksInLayer--; //reduce bricks in layer by one
			startingPositionX = margins / 2; //resets starting position
			startingPositionX += (BRICK_WIDTH / 2) * (i + 1); //moves starting brick along half a brick * layer number 
			startingPositionY -= BRICK_HEIGHT; //move layer up one
		}	
	}
	
	
	
}

	
