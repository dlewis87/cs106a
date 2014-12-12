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
		int theWidth = getWidth();
		int theHeight = getHeight();
		int firstLayerWidth = BRICK_WIDTH * BRICKS_IN_BASE;
		int margins = theWidth - firstLayerWidth;
		int startingPositionX = margins / 2;
		int startingPositionY = theHeight - BRICK_HEIGHT;
		int bricksInLayer = BRICKS_IN_BASE;
	
		for (int i=0; i<BRICKS_IN_BASE;i++)	{
			
			for (int x=0; x<bricksInLayer;x++) {
				add(new GRect(startingPositionX, startingPositionY, BRICK_WIDTH, BRICK_HEIGHT));
				startingPositionX += BRICK_WIDTH;
			}
			bricksInLayer--;
			startingPositionX = margins / 2;
			startingPositionX += (BRICK_WIDTH / 2) * (i + 1);
			startingPositionY -= BRICK_HEIGHT;
		}	
	}
	
	
	
}

	
