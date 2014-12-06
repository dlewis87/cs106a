/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	
	/*
	 * (non-Javadoc)
	 * @see stanford.karel.SuperKarel#run()
	 * Run assumes Karel is facing east on 1,1
	 * If there is no wall in front of karel it moves left and travles up column placing beepers on epty spaces
	 * Karel then descends the wall
	 * Then moves to next column
	 * On final column karel ascends and descends, finishing facing east
	 */
	

	public void run (){
		while (frontIsClear()){	
			RepairColumn();
			MoveToNextColumn();
		}
		RepairColumn();
	}
	
	
	public void RepairColumn () {
		turnLeft();
		PutBeepersInColumn();
		turnAround();
		moveToWall();
		turnLeft();
	}
	
	
	
	
	/*
	 * PutBeepersInColumn checks if front is clear then moves placing beepers in empty spaces	  
	 */
	
	private void PutBeepersInColumn () {
		while (frontIsClear()) {
			if (noBeepersPresent()){
				putBeeper();			
			}
				move();
		}
		if (noBeepersPresent()){
			putBeeper();			
		}
	}
	
	/*
	 * Moves to next column assumes Karel is facing east
	 * 
	 */
	
	private void MoveToNextColumn () {
		if (frontIsClear()) {
			for (int i=0;i<4;i++){
				move();
			}
		}
		
	}
	
	/*
	 * Karel travels back down column then turns east
	 * 
	 */
	
	private void moveToWall (){
		while (frontIsClear()){
			move();
		}		
	}
	
	
}
