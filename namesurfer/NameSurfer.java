/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	
	

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		
		//Add Graph canvas
		graph = new NameSurferGraph();
		add(graph);
		 
		
		//Text field and button interactors
		nameField = new JTextField(10);
	    add(new JLabel("Graph"), SOUTH);
	    add(nameField, SOUTH);
	    graphButton = new JButton("Graph");
	    add(graphButton, SOUTH);	    
	    graphButton.addActionListener(this);
	    
	    //Clear button
	    clearButton = new JButton("Clear");
	    add(clearButton, SOUTH);
	    clearButton.addActionListener(this);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == graphButton) {
			//Text entered to string
			String name = nameField.getText();
			
			//Search database for name
			NameSurferEntry entry = db.findEntry(name);	
			
			//If name in database add name to graph
			if (entry != null){
				graph.addEntry(entry);
				graph.update();		
			}			
		}
		//Clear name data
		else if (e.getSource() == clearButton) {
			graph.clear();
			graph.update();
		}
	}


	/* Private instance variables */
	/*For Name entry */
	 private JTextField nameField;
	 private JButton clearButton;
	 private JButton graphButton;
	 
	 //New database from text file
	 NameSurferDataBase db = new NameSurferDataBase(NAMES_DATA_FILE);
	 //Graph object
	 private NameSurferGraph graph;

}
