/*
 File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;




public class MidpointFindingKarel extends SuperKarel {
	
	/*(non-Javadoc)
	 * @see stanford.karel.SuperKarel#run()
	 * Initially place beepers on outer edges of grid, turn around, then move in one space.
	 * keep moving until beeper is found, pick it up, turn around and move it on space in
	 */
	
	public void run () {
		PlaceInitialBeepers(); //place beepers on outer corners of grid
		while (noBeepersPresent()){ 
			MoveBeeperIn(); 
		}
		pickBeeper();//pick up last beeper
	}
	
	private void PlaceInitialBeepers () {
		putBeeper();
		while (frontIsClear()) {
			move();			
		}
			putBeeper();
			turnAround();
			move(); //must not be on place with beeper or preceding loop will not start
	}

	
	private void MoveBeeperIn () {	
		while (noBeepersPresent()){
			move();//keep moving until beeper is found
		}
		pickBeeper(); 
		turnAround();
		move();
		putBeeper();
		move(); //must not be on space with beeper or while loopp will end
		
			
	}
}
