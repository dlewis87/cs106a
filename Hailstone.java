/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	
	/* Program takes user input n, if n is even then divides by two, if n is odd then multiply n by 3 then add 1
	 * stop when n == 1 
	 * count number of steps it takes to reach one*/ 
	public void run() {
		int n = readInt("Enter a number: ");
		int steps = 0;
		
		while (true) {			
			if (n==1) break;
			int temp = n;
			if (n % 2 == 0) {				
				n /= 2;
				println (temp + " is even so i take half: " + n);
			}
			else {
				n = (n * 3) + 1;
				println (temp + " is odd so i make 3n + 1: " + n);
			}
		steps++;	
		}
		println ("The process took " + steps + " to reach 1" );
	}
}

