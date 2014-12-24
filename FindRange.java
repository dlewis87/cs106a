/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	public void run() {
		println("This program finds the largest and smallest numbers.");
		int smallest = 0;
		int largest = 0;		
		while (true) {
			int a = readInt("?");			
			if (a == SENTINEL) break;
			if (a < smallest) smallest = a;
			if (a > largest) largest = a;
		}
		if (smallest == 0 && largest == 0) println("First entry is the sentinal");		
		else {
			if (smallest == 0 && largest != 0) smallest = largest;
			else if (smallest != 0 && largest == 0) largest = smallest;
			println("Smallest " + smallest);
			println("Largest " + largest);
		}		
	}	
	private static final int SENTINEL = 0;
}

