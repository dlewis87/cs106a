/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	
/** Total number of guesses allowed */	
	private static final int GUESS_LIMIT = 8;
	
	
	public void init() {
		 canvas = new HangmanCanvas();
		 add(canvas);
		}
	

    public void run() {
    	welcomeMessage();
    	while(true) {
	    	canvas.reset();			
			setUp();
			while (true) {
				runGame();
				if (guessesLeft < 1) { //end game if user runs out of guesses
					println("You lose!!!");	
					println("The correct word was " + word);
					break;
				}
				/*Option for game win if user guesses correct word
				else if (checkWord())  { //end game if user guesses correct word
					println("You win!!!");
					break;
				}
				*/				
				else if (correctGuesses == word.length()) { //end game if user has guessed all letters
					println("You win!!!");
					break;
				}
				println(guessesLeft + " guesses left");
			}
    	}
	}

/**Show welcome message  */  
    private void welcomeMessage() {
    	println("Welcome to hangman!");
    	
    }
    
    
/**Set game up ready to play, loading and selecting words  */    
    private void setUp() {    	
    	selectWord();
    	setWord();
    	   	
    }
    
/**Select word by passing random number to list of words and returning word as string*/    
    private void selectWord() {
    	int wordCount = loadWords.getWordCount() - 1;
    	int wordNumber = rgen.nextInt(0, wordCount);
    	word = loadWords.getWord(wordNumber);
    	   	
    }
    
  
 /** create blank word, guesses and send to user */    
    private void setWord () {
    	wordLength = word.length();
    	wordToDash(word, wordLength); 
    	println("Your new word looks like this: " + wordDashed);
    	guessesLeft = GUESS_LIMIT;
    	println("You have " + guessesLeft + " guesses left.");
    }
    
 /**Change letter in word to dashes*/   
    private void wordToDash(String word, int wordLength) {
    	wordDashed = "";
    	for (int i=0; i< wordLength; i++) {
    		wordDashed += "-";    		
    	}    	
    }
    
    
/** Run game  */    
    private void runGame() {    	
    	guess = readLine("Your guess: ");
    	guess = guess.toUpperCase();
    	/*Option to check if character is a letter
    	if (checkValid(guess).length() > 0) { 
    		println("These characters are invalid " + checkValid(guess));
    	}
    	else {
    	*/
    		if (guess.length() > 1){ 
    			println("Too many letters, guess again!");
        		/* Option to check for word, user can guess word 
    			if (guess.length() != word.length()) {
        			println("Invalid guess, incorrect length");
        			//canvas.noteIncorrectGuess("A", guessesLeft);
        			guessesLeft--;
        		}
        			
        		else { 
        			if(checkWord() == false) {
        				println("Incorrect guess");
        				//canvas.noteIncorrectGuess("A", guessesLeft);
        				
        				guessesLeft--;
        			}
        		}
        		*/  		
        	}
    			
        	else {         	        		
        		wordDashed = checkLetter();
        		canvas.displayWord (wordDashed);
        	}    		
    		//} 
    	println("Your word looks like this: " + wordDashed);
    }
    
/** 
 * Check to see if characters are valid x    
    private String checkValid(String guess) {
    	String invalidChar = "";
    	//check to see if characters are valid
    	//invalidChar += i + ", ";
    	return invalidChar;
    	
    }
 */ 
    
    
/** 
 * check to see if letter is in word then return dashed word with letters replaced
 * increase correct guesses if correct 
 * replace dash with letter guessed if correct
 * increase guesses left if letter already guessed
 * subtract guesses left  
 */
    private String checkLetter() {
    	String result = "";
    	char guessCh = guess.charAt(0);    
    	for(int i= 0; i < word.length(); i++) {
    		char wordCh = word.charAt(i);
    		if (guessCh == wordCh) {
    			if (guessCh != wordDashed.charAt(i)) {
    				correctGuesses++;    				
    			}    			
    			result += guess;    			
    		 }
    		else {    			    				
    			result += wordDashed.charAt(i);    			
    		}    	   		
    	}
    	if (!word.contains(guess)){
    		guessesLeft--;
    		canvas.noteIncorrectGuess(guessCh, guessesLeft);
    	}    	
    	return result;
    }
    
  /** Check to see if guessed word is correct   
    private boolean checkWord() {
    	if (word.equals(guess)) return true;
    	else return false;     	
    }
    */
    
    /* count how many guesses user has made*/
    private int guessesLeft;
    /* count how many correct guesses*/ 
    private int correctGuesses;
    /*Set word for game*/ 
    private String word;
    /* Set word length to variable*/
    private int wordLength;
    /*Replace letters of word with dashes*/
    private String wordDashed;
    /* Users last guess  */
    private String guess;
    /* Generate random number for word select */
	 private RandomGenerator rgen = RandomGenerator.getInstance();
    /* Load new word list object */
	 HangmanLexicon loadWords = new HangmanLexicon();
	 
	 private HangmanCanvas canvas;
	 
	 
	
	 
	 
}
