/*
* File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}
	
	
	
	/** Play game resets scores, iterates through players  */
	private void playGame() {
		//Sets all scores to -1
		resetScores();
		//Loop through categories
		for (int i = 0; i < N_SCORING_CATEGORIES; i++){
			//loop through players
			for (int j = 1; j <= nPlayers; j++) {
				//Initial roll is is different as there are no categories to select.
				initialRoll(j);
				//Iterate through three dice rolls
				while(diceRoll <= 3) {	
					display.printMessage("Select dice to keep and roll again");
					display.waitForPlayerToSelectDice();
					rollDice();								
				}
				selectCategory(j);
				//Sets score for players turn
				setScore(j);
				//set total
				calculateTotal(j);
			}
		}
		//sets final score for game
		finalScores();
		//Selects winner
		winner();
	}
	
	
	/** Sets all scores for every player to -1, this is to show that category has not been selected */
	private void resetScores() {
		score = new int[nPlayers][N_CATEGORIES];
		for (int i = 0; i < nPlayers; i++) {
			for (int j = 0; j < N_CATEGORIES; j++) {
				score[i][j] = -1;
			}			
		}		
	}
	              
	
	/**
	 * Set's roll message, creates dice score array
	 * sets dice array to rand number between 1 and 6
	 * @param player 
	 **/
	private void initialRoll(int player) {
		diceRoll = 1;
		display.printMessage(playerNames[player - 1] + "'s turn to roll, click Roll Dice.");
		//set dice array 
		diceScores = new int[N_DICE];
		display.waitForPlayerToClickRoll(player);
		//set each item of dice array to rand number between one and six
		for(int i = 1; i <= N_DICE; i++) {
			diceScores[i - 1] = rgen.nextInt(1,6);
				
		}
		display.displayDice(diceScores);
		diceRoll++;
	}
	
	
	/**
	 *Checks to see if dice have been selected, if they have then rolls non selected dice else skips
	 *and just displays dice
	 *cheat mode commented out for selecting dice manually for testing  
	 **/
	private void rollDice() {			
		//Check to see if any dice have been selected
		diceSelected = 0;
		for(int i = 0; i < N_DICE; i++) {
			if(display.isDieSelected(i)){
				diceSelected++;
			}			
		}
		//Cheat mode commented out
		//set all dice that haven't been selected to random number between 1-6, skip if no dice selected
		if (diceSelected > 0) {
			//IODialog dialog = getDialog();
			for(int i = 1; i <= N_DICE; i++) {
				if(!display.isDieSelected(i - 1)){
					diceScores[i - 1] = rgen.nextInt(1,6);
					//diceScores[i - 1] = dialog.readInt("Enter dice no.");
				}			
			}			
		}		
		display.displayDice(diceScores);
		diceRoll++;
	}
	
	/**
	 * Checks to see if category player has clicked has been clicked before
	 * keeps looping, waiting for click until player selects category that is -1
	 * All categories are set to -1 at start
	 * @param player to set which player is selecting category
	 */
	private void selectCategory(int player) {
		while(true) {
			display.printMessage("Select a category to play");
			categorySelected = display.waitForPlayerToSelectCategory();	
			if (score[player - 1][categorySelected - 1] == -1) break;
		}		
	}
	                      
	
	/**
	 * Calculate score for round
	 * Update scorecard with score
	 * update score array with score
	 * Update total score
	 * @param player
	 */
	private void setScore (int player) {
		//Calls calculate score method
		roundScore = calculateScore(diceScores, categorySelected);
		//update score card
		display.updateScorecard(categorySelected, player, roundScore);
		//update score array
		score[player - 1][categorySelected - 1] = roundScore;		
	}
	
	
	/**
	 * Calculates the score taking in the dice array and category
	 * First checks if category is valid if not then score is 0
	 * @param scores
	 * @param category
	 * @return roundScore
	 */
	private int calculateScore(int[] scores, int category) {
		roundScore = 0;
		if (checkCategory(scores, category)) {
			switch (category) {
				case 1: //ones
				case 2: //twos
				case 3: //threes
				case 4: //fours
				case 5: //fives
				case 6: //sixes
					//add every occurence of dice that equals category number
					for (int i = 1; i <= N_DICE; i++) {
						if (scores[i - 1] == category) {
							roundScore += scores[i - 1];
						}
					}				
					break;
				case 9: //three of a kind
				case 10: //four of a kind
				case 15: //chance
					//add all dice together
					for (int i = 1; i <= N_DICE; i++) {					
						roundScore += scores[i - 1];					
					}
					break;
				case 11: //full house
					roundScore = 25;
					break;
				case 12: //small straight
					roundScore = 30;
					break;
				case 13: //large straight
					roundScore = 40;
					break;
				case 14: //yahtzee
					roundScore = 50;
					break;
				default: 
					break;
			}
			display.printMessage("Your score is " + roundScore);
		}
		else {
			display.printMessage("Category not valid your score is" + roundScore);
		}	
		return roundScore;
	}
	
	
	/**
	 * Checks if category is valid
	 * @param scores
	 * @param category
	 * @return
	 */
	private boolean checkCategory(int[] scores, int category) {
		boolean categoryIsCorrect = false;
		switch (category) {
			case 1: //ones
			case 2: //twos
			case 3: //threes
			case 4: //fours
			case 5: //fives
			case 6: //sixes
			case 15: //chance	
				//always return true
				categoryIsCorrect = true;			
				break;
			case 9: //three of a kind
				//create two versions of array and check each value against all values in other array 
				//if value occurs three times then true
				for (int i = 0; i < scores.length; i++) {
					int checkDice = 0;
					for (int j = 0; j < scores.length; j++) {
						if (scores[i] == scores[j]) {
							checkDice++;
							if (checkDice >= 3) {
								categoryIsCorrect = true;
							}
						}
					}
				}
				break;
			case 10: //four of a kind
				//create two versions of array and check each value against all values in other array 
				//if value occurs four times then true
				for (int i = 0; i < scores.length; i++) {
					int checkDice = 0;
					for (int j = 0; j < scores.length; j++) {
						if (scores[i] == scores[j]) {
							checkDice++;
							if (checkDice >= 4) {
								categoryIsCorrect = true;
							}
						}
					}
				}
				break;
			case 11: //full house
				//create two versions of array and check each value against all values in other array 
				//if value occurs three times then check remaining dice
				int three = 0;
				for (int i = 0; i < scores.length; i++) {
					int checkDice = 0;
					for (int j = 0; j < scores.length; j++) {
						if (scores[i] == scores[j]) {
							checkDice++;
							if (checkDice == 3) {
								three = scores[i];
							}
						}
					}					
				}
				//check all values that do not equal value that appears three times
				//if it appears twice then true
				if (three > 0) {
					for (int i = 0; i < scores.length; i++) {
						int checkDice = 0;
						for (int j = 0; j < scores.length; j++) {
							if (scores[i] == scores[j] && scores[i] != three) {
								checkDice++;
								if (checkDice == 2) {
									categoryIsCorrect = true;
								}								
							}
						}
						
					}
				}
				break;
			case 12: //small straight
				//create two versions of array and check each value against all values in other array
				//if +1, +2 and +3 appear 
				for (int i = 0; i < scores.length; i++) {
					original = false;
					firstStep = false;
					secondStep = false;
					thirdStep = false;
					for (int j = 0; j < scores.length; j++) {
						if((scores[i]) == scores[j]) original = true;
						else if((scores[i] + 1) == scores[j]) firstStep = true;
						else if((scores[i] + 2) == scores[j]) secondStep = true;
						else if((scores[i] + 3) == scores[j]) thirdStep = true;
						if (original == true && firstStep == true && secondStep == true && thirdStep == true){
							categoryIsCorrect = true;
						}
					}
					
				}					
								
				break;
			case 13: //large straight
				//create two versions of array and check each value against all values in other array
				//if +1, +2, +3 and +4 appear 
				for (int i = 0; i < scores.length; i++) {
					original = false;
					firstStep = false;
					secondStep = false;
					thirdStep = false;
					fourthStep = false;
					for (int j = 0; j < scores.length; j++) {
						if((scores[i]) == scores[j]) original = true;
						else if((scores[i] + 1) == scores[j]) firstStep = true;
						else if((scores[i] + 2) == scores[j]) secondStep = true;
						else if((scores[i] + 3) == scores[j]) thirdStep = true;
						else if((scores[i] + 4) == scores[j]) fourthStep = true;
					}					
					if (original == true && firstStep == true && secondStep == true && thirdStep == true && fourthStep == true){
						categoryIsCorrect = true;
					}
				}
				break;
			case 14: //yahtzee
				//create two versions of array and check each value against all values in other array 
				//if value occurs five times then true
				for (int i = 0; i < scores.length; i++) {
					int checkDice = 0;
					for (int j = 0; j < (scores.length - i); j++) {
						if (scores[i] == scores[j]) {
							checkDice++;
							if (checkDice >= 5) {
								categoryIsCorrect = true;
							}
						}
					}
				}
				break;
			default: 
				categoryIsCorrect = false;
				break;	
		}
		return categoryIsCorrect;
		
		
	}
	
	/**
	 * Update total score for player after every turn
	 * iterates through categories adding any that is above -1
	 * update scorecard with total
	 * @param player
	 */
	private void calculateTotal(int player) {
		playerTotal = 0;
		for (int i = 0; i < score[player - 1].length; i++) {
			if (score[player - 1][i] != -1) {				
				playerTotal += score[player - 1][i];
			}
		}
		display.updateScorecard(TOTAL, player, playerTotal);
	}
	
	/**
	 * Calculate upper and lower scores
	 * check for upper score bonus
	 * calculate final score
	 */
	private void finalScores () {
		//iterate through players
		for (int i = 1; i <= nPlayers; i++) {
			//reset upper and lower scores
			upperScore = 0;
			lowerScore = 0;
			//set upper score	
			for (int j = 0; j < UPPER_SCORE - 1 ; j++) {
				upperScore += score[i - 1][j];
			}
			//check for upper score bonus
			if (upperScore >= 63) {
				display.updateScorecard(UPPER_BONUS, i, 35);
				score[i - 1][UPPER_BONUS - 1] = 35;
			}
			else {
				display.updateScorecard(UPPER_BONUS, i, 0);
				score[i - 1][UPPER_BONUS - 1] = 0;
			}
			//update upper score on score card and score array
			display.updateScorecard(UPPER_SCORE, i, upperScore);
			score[i - 1][UPPER_SCORE - 1] = upperScore;
			
			
			//calculate lower score
			for (int j = 8; j < LOWER_SCORE - 1; j++) {
				lowerScore += score[i - 1][j];
			}
			//update lower score on score card and score array
			score[i - 1][LOWER_SCORE - 1] = lowerScore;
			display.updateScorecard(LOWER_SCORE, i, lowerScore);
			
			//calculate final score
			int finalScore = score[i - 1][UPPER_BONUS - 1] + upperScore + lowerScore;
			
			//update total score on score card and score array
			display.updateScorecard(TOTAL, i, finalScore);
			score[i - 1][TOTAL - 1] = finalScore;
		}			
	}
	
	/**
	 * Check final scores to find winning score and winning player
	 */
	private void winner() {
		for (int i = 1; i <= nPlayers; i++) {
			winningScore = 0;
			//check score against previous high score, if higher then replace
			if (score[i - 1][TOTAL - 1] > winningScore) {
				winningScore = score[i - 1][TOTAL - 1];
				winningPlayer = i;
			}		
		}
		display.printMessage(playerNames[winningPlayer - 1] + " Wins!");
	}
	
		
/* Private instance variables */
	/* no of players */
	private int nPlayers;
	/* array of player names */
	private String[] playerNames;
	/* graphical display for game */
	private YahtzeeDisplay display;
	/* random number generator */
	private RandomGenerator rgen = new RandomGenerator();
	/* count how many times dice rolles in turn */
	private int diceRoll;
	/*category selected by player */
	private int categorySelected;
	/* array of dice numbers*/
	private int[] diceScores;
	/*Score for round*/
	private int roundScore;
	/*Array of all players scores */
	private int score[][];
	/*total score for player*/
	private int playerTotal;
	/*upper score*/
	private int upperScore;
	/* lower score*/
	private int lowerScore;
	/*check to see amount of dice selected */
	private int diceSelected;
	/*winning player and score*/
	private int winningScore;
	private int winningPlayer;
	/* variables for checking small and large straights*/
	boolean original;
	boolean firstStep;
	boolean secondStep;
	boolean thirdStep;
	boolean fourthStep;

}
