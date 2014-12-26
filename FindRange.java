/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	/*Compute the largest and smallest from a list of numbers until user enters sentinal*/
	public void run() {
		println("This program finds the largest and smallest numbers.");
		int smallest = 0;
		int largest = 0;
		/*Keep asking user for input, if input is smaller/larger than smallest/largest then change var to that value*/
		while (true) {
			int a = readInt("?");			
			if (a == SENTINEL) break;
			if (a < smallest) smallest = a;
			if (a > largest) largest = a;
		}
		/*Print message if first entry is sentinel */
		if (smallest == SENTINEL && largest == SENTINEL) println("First entry is the sentinal");
		/* if sentinel is entered on second entry then set both smallest and largest to same value */
		else {
			if (smallest == SENTINEL && largest != SENTINEL) smallest = largest;
			else if (smallest != SENTINEL && largest == SENTINEL) largest = smallest;
			println("Smallest " + smallest);
			println("Largest " + largest);
		}		
	}	
	
	private static final int SENTINEL = 0; //sentinel value
}

