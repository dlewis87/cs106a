/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	
	/*Compute length of third side using user input of two sides  */ 
	public void run() {		
		println("Enter values to compute Pythagorean theorem");
		int a = readInt("Enter a: ");
		int b = readInt("Enter b: ");
		double c = Math.sqrt((a * a) + (b * b));
		println("C = " + c);
			
	}
	

}
