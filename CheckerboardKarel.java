/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	/*
	 * (non-Javadoc)
	 * @see stanford.karel.SuperKarel#run()
	 * Create checkerboard effect on square grid using beepers
	 * Pre-con: Karel stanging on 1,1 facing east
	 */
	
	public void run () {		
		if(frontIsBlocked()) {
			turnLeft();
		}//check if grid is one wide, if it is then turn left and go straight up
		while(frontIsClear()){
			makeRow();
			changeRowOdd();
			makeRow();
			changeRowEven();
		}//create row, turn around, make row, turn around until top wall is hit
	}

	/*Checks if front is clear
	 * First time this is called there will be no beeper on starting position so it places one
	 * moves to next position
	 * checks again if front is clear
	 * if it is, it moves to next position and places a beeper.
	 * When you return to start of while loop you will be on position with beeper
	 * so this will prevent you from doubling beepers.
	 */
	private void makeRow() {		
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
			}//puts beeper on starting corner
			move();//moves to next position
			if(frontIsClear()) {
				move();
				putBeeper();
			}//checks again to see if front is clear then moves and places beeper
		}
	}
	
	/*This is for the turn on the first row and subsequent odd rows, basically a left turn
	 * To prevent from just making columns of beepers, if the last row of current line has a beeper then it moves the 
	 * second last rather than the last column of the next line and starts from there.
	 */
	private void changeRowOdd() {
		turnLeft();
		if(frontIsClear()){
			if (beepersPresent()) {
				move();
				turnLeft();
				move();
			}//if last of row has beeper then it moves to second last of second row.
			else {
				move();
				turnLeft();
			}// else it moves to last of second row.				
		}
	}
	
	                       
	/*Same as changeRowOdd() but for second and subsequent rows which require a right turn.
	 * 	
	 */
	private void changeRowEven() {
		turnRight();
		if(frontIsClear()){
			if (beepersPresent()) {
				move();
				turnRight();
				move();
			}
			else {
				move();
				turnRight();
			}				
		}
		
	}
}
