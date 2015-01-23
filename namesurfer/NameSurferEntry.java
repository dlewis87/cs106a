/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		fullLine = line;
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {		
	 int nameStart = 0;
	 int nameEnd = fullLine.indexOf(" ");
	 name = fullLine.substring(nameStart, nameEnd);		
	 return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		//Set ranks to start from character after first space
		int ranksStart = fullLine.indexOf(" ") + 1;
		ranks = fullLine.substring(ranksStart);
		
		
		int rankStart = 0;
		//Move to next space * decade
		if (decade > 1) {
			for (int i=1; i < decade; i++) {
				rankStart = ranks.indexOf(" ", rankStart);
				rankStart++;
			}		
		}
		
		//end rank with space except for last entry
		if (decade == NDECADES) {
			rank = Integer.parseInt(ranks.substring(rankStart));
		}
		else {
			int rankEnd = ranks.indexOf(" ", rankStart);
			rank = Integer.parseInt(ranks.substring(rankStart, rankEnd));
		}		
		return rank;
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String fullString = name + "[" + ranks + "]"; 
		return fullString;
	}
	
	/* Private instance variables */
	private String fullLine;
	private String name;
	private int rank;
	private String ranks;

}

