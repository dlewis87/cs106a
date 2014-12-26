/*
* File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	/** Width of each box in pixels */
	private static final int BOX_WIDTH = 140;

	/** Width of each box in pixels */
	private static final int BOX_HEIGHT = 50;
	
	/** Graphical program to show diagram for program hierarchy for graphical program*/
	public void run() {	
		
		
		/*Find the entre point using width and height of window*/
		int centreX = getWidth() / 2; 
		int centreY = getHeight() / 2; 
		
		/*Set co-ordinate of first box*/
		int x = centreX - (BOX_WIDTH / 2); 
		int y = (centreY - (BOX_HEIGHT * 2)); 
		
		
		/*Add lines*/
		add(newLine(centreX,centreY,centreX,(y + BOX_HEIGHT))); 
		add(newLine(centreX,centreY - BOX_HEIGHT,centreX + BOX_WIDTH,centreY)); 
		add(newLine(centreX,centreY - BOX_HEIGHT,centreX - BOX_WIDTH,centreY));
		
		
		/* Add boxes and labels */
		add(classBox(x,y)); 
		add(classLabel(x,y,"Program")); 
		y = centreY; //reset y co-ordinate to centre
		add(classBox(x,y));
		add(classLabel(x,y,"ConsoleProgram"));
		x += (x / 2); //move x co-ord		
		add(classBox(x,y));
		add(classLabel(x,y,"GraphicsProgram"));
		x = centreX - (BOX_WIDTH / 2); //move x co-ord
		x -= (x / 2);
		add(classBox(x,y));
		add(classLabel(x,y,"Program"));
	}
	
	
	/* Add new line */
	private GLine newLine (int x1, int y1, int x2, int y2) { 
		GLine line = new GLine(x1, y1, x2, y2); 		
		return line; 
	}
	
	/* Add new box */
	private GRect classBox (int x, int y) { 
		GRect box = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT); 		
		return box; 
	}
	
	/* Add label by using co-ord for last box created and centering using half size of label */
	private GLabel classLabel (int x, int y, String label) {
		x += BOX_WIDTH / 2;
		y += BOX_HEIGHT / 2;
		GLabel labelText = new GLabel(label,x,y);
		labelText.setLocation(x - (labelText.getWidth() /2 ), y + (labelText.getHeight() /2 ));
		return labelText; 
	}
	
}

