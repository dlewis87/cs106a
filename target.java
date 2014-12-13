/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	public void run() {
		double x = getWidth() / 2; //Find middle of height
		double y = getHeight() / 2; //Find middle of width
		
		add(filledCircle(x,y,inchToPixel(1),Color.RED)); //add outer red circle 
		add(filledCircle(x,y,inchToPixel(0.65),Color.WHITE)); //add inner whie circle
		add(filledCircle(x,y,inchToPixel(0.3),Color.RED)); //add inner red circle
	}
	
		
	private GOval filledCircle (double x, double y,double r,Color color) { 
		GOval circle = new GOval(x-r, y-r, 2*r, 2*r); // add circle that is positioned centrally
		circle.setFilled(true); //fill circle
		circle.setColor(color); //set color of fill
		return circle; 
	}
			
		
	private double inchToPixel (double n) { //convert inches to pixels
		return n * 72;
	}
		
	
}
