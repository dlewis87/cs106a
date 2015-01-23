/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {		
		for(int i = 0; i < 5; i++) {
			names[i] = null;			
		}
		entryNumber = 0;
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {	
		names[entryNumber] = entry;
		if (entryNumber < 4) {
			entryNumber++;
		}
		else {
			entryNumber = 0;
		}	
		
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		
		//Remove all objects from canvas
		removeAll();
		
		
		double x = 0;
		double y = 0;
		
		//Top and bottom margins and graph lines
		GLine topLine;
		GLine bottomLine;		
		GLine graphLine;
		
		//Lines connecting graph points
		GLine rankLine;
		//Position for end of line
		double lineEnd;
		//Label for rank 
		GLabel rankLabel;
		//Label of years on x 
		GLabel yearLabel;
		//Colour for labels and lines
		Color color = Color.BLACK;
		
		
		//Add top and bottom lines to canvas
		add(topLine = new GLine(x, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE));
		add(bottomLine = new GLine(x, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE));
		
		
		//Calculate space between lines
		double graphLineSpace = getWidth() / NDECADES;
		//Calculate space between margins
		double graphSize = getHeight() - GRAPH_MARGIN_SIZE * 2;
		//Calculate position of rank
		double graphPointSpace = graphSize / 1000;
		
		//Starting position for graph
		int years = START_DECADE;
		
		
		//Add graph lines and years to canvas
		for(int j = 0; j < NDECADES; j++){
			add(graphLine = new GLine(x, y, x, getHeight()));
			add(yearLabel = new GLabel("" + years));
			yearLabel.setLocation(x,(double) getHeight() - GRAPH_MARGIN_SIZE  + yearLabel.getHeight());
			x += graphLineSpace;
			years += 10;
		}
		
		
		//Add name entrys to graph
		for (int i = 0; i < 5; i++) {
			//Change colour every entry
			if (i==1) color = Color.ORANGE;
			else if  (i==2) color = Color.RED;
			else if  (i==3) color = Color.GREEN;
			else if  (i==4) color = Color.BLUE;
			
			//Reset x/y positions and decade
			x = 0;
			y = 0;
			years = START_DECADE;
			
			
			//If nameSurferEntry exists add rank to arrays
			if(names[i] != null) {
				for (int k = 0; k < NDECADES; k++) {
					ranks[k] = names[i].getRank(k + 1);					
				}
				//Add ranks to graph
				for(int j = 0; j < NDECADES; j++){	
					//Set rank poistion on graph
					double rankPosition = y + GRAPH_MARGIN_SIZE + (ranks[j] * graphPointSpace);
					
					//Add rank position to graph and line 
					if(ranks[j] > 0) {				
						add(rankLabel = new GLabel(names[i].getName() + " " + ranks[j]));
						rankLabel.setLocation(x, rankPosition);
						rankLabel.setColor(color);
						
						//Set end of line to position of next rank in array
						if (j < NDECADES - 1) {
							if (ranks[j + 1] > 0) {
								lineEnd = y + GRAPH_MARGIN_SIZE + (ranks[j + 1] * graphPointSpace);	
							}
							else {
								lineEnd = getHeight() - GRAPH_MARGIN_SIZE;
							}
							//Add line to canvas
							add(rankLine = new GLine(x, rankPosition,x + graphLineSpace, lineEnd));
							rankLine.setColor(color);
						}				
					}
					//If rank is outside top 1000 set to bottom of grpah
					else {
						add(rankLabel = new GLabel(names[i].getName() + "*"));				
						rankLabel.setLocation(x, getHeight() - GRAPH_MARGIN_SIZE);
						
						if (j < NDECADES - 1) {
							if (ranks[j + 1] > 0) {
								lineEnd = y + GRAPH_MARGIN_SIZE + (ranks[j + 1] * graphPointSpace);	
							}
							else {
								lineEnd = getHeight() - GRAPH_MARGIN_SIZE;
							}
							add(rankLine = new GLine(x, getHeight() - GRAPH_MARGIN_SIZE,x + graphLineSpace, lineEnd));
							rankLine.setColor(color);
						}	
						
					}					
					x += graphLineSpace;
					years += 10;
				}
			}	
		}		
	}
	
	
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	
	//Array to hold name entrys
	private NameSurferEntry names[] = new NameSurferEntry[5];
	
	//Array to hold ranks for name entry
	private int ranks[] = new int[NDECADES];
	
	//Set position in names[]
	private int entryNumber = 0;
	
	
}
