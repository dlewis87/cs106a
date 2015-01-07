/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.util.ErrorException;

public class HangmanCanvas extends GCanvas {
	
	
	

/** Resets the display so that only the scaffold appears */
	public void reset() {
			
		removeAll();
		
		body = new GLine ((getWidth() / 2),BODY_Y,(getWidth() / 2),BODY_Y + BODY_LENGTH);
		head = new GOval ((getWidth() / 2) - HEAD_RADIUS,BODY_Y - (HEAD_RADIUS * 2), 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		rope = new GLine (head.getX() + HEAD_RADIUS, head.getY() - ROPE_LENGTH, head.getX() + HEAD_RADIUS, head.getY());
		beam = new GLine (rope.getX() - BEAM_LENGTH, rope.getY(), rope.getX(), rope.getY());
		scaffold = new GLine (beam.getX(),beam.getY(), beam.getX(), beam.getY() + SCAFFOLD_HEIGHT);
		leftUpperArm = new GLine (body.getX() - UPPER_ARM_LENGTH, body.getY() + ARM_OFFSET_FROM_HEAD, body.getX(), body.getY() + ARM_OFFSET_FROM_HEAD);
		rightUpperArm = new GLine (body.getX() + UPPER_ARM_LENGTH, body.getY() + ARM_OFFSET_FROM_HEAD, body.getX(), body.getY() + ARM_OFFSET_FROM_HEAD);
		leftLowerArm = new GLine (leftUpperArm.getX(), leftUpperArm.getY(), leftUpperArm.getX(),  leftUpperArm.getY() + LOWER_ARM_LENGTH);
		rightLowerArm = new GLine (rightUpperArm.getX(), rightUpperArm.getY(), rightUpperArm.getX(),  rightUpperArm.getY() + LOWER_ARM_LENGTH);
		hip = new GLine(body.getX() - HIP_WIDTH, body.getY() + BODY_LENGTH, body.getX() + HIP_WIDTH, body.getY() + BODY_LENGTH);		
		leftLeg = new GLine(hip.getX(), hip.getY() + LEG_LENGTH, hip.getX(), hip.getY());
		rightLeg = new GLine(hip.getX() + (HIP_WIDTH * 2), hip.getY() + LEG_LENGTH, hip.getX() + (HIP_WIDTH * 2), hip.getY());
		leftFoot = new GLine(leftLeg.getX(), leftLeg.getY(), leftLeg.getX() - FOOT_LENGTH, leftLeg.getY());
		rightFoot = new GLine(rightLeg.getX(), rightLeg.getY(), rightLeg.getX() + FOOT_LENGTH, rightLeg.getY());
		
		
		add(beam);
		add(scaffold);
		add(rope);
		
		
		correctLetters = new GLabel("",100, getHeight() - 50);		
		add(correctLetters);
		incorrectLetters = new GLabel("",100, getHeight() - 20);		
		add(incorrectLetters);
		
		
		letters = "";
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {			
		correctLetters.setLabel(word);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter, int guessesLeft) {
		
		letters += letter;
		incorrectLetters.setLabel(letters);
		
		
		
		
		switch (guessesLeft) {
			case 7: add(head); 
					break;
			case 6: add(body); 
					add(hip); 
					break;						
			case 5: add(leftUpperArm); 
					add(leftLowerArm);
					break;
			case 4: add(rightUpperArm); 
					add(rightLowerArm);
					break;
			case 3: add(leftLeg);
					break;
			case 2: add(rightLeg);
					break;
			case 1: add(leftFoot);
					break;
			case 0: add(rightFoot);
					break;
			default: throw new ErrorException("noteIncorrectGuess: Illegal index");		
		}
		
		
		
		
		
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int BODY_Y = 100;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
	
	
	private GLine body;
	private GOval head;
	private GLine beam;
	private GLine rope;
	private GLine scaffold;
	private GLine leftUpperArm;
	private GLine rightUpperArm;
	private GLine leftLowerArm;
	private GLine rightLowerArm;
	private GLine hip;
	private GLine leftLeg;
	private GLine rightLeg;
	private GLine leftFoot;
	private GLine rightFoot;
	
	private GLabel correctLetters;
	private GLabel incorrectLetters;
	private String letters = "";

}

	

