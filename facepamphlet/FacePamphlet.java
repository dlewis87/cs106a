/* 
 * File: FacePamphlet.java

 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {		
		
		/* Create new canvas  */
		canvas = new FacePamphletCanvas();
		canvas.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		add(canvas);
		canvas.showMessage("Add user to start");
		
		
		
		/* North Interactors */
		//Name text field
		add(new JLabel("Name"), NORTH);
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add(nameField, NORTH);
		
		//Add name button
		addButton = new JButton("Add");
	    add(addButton, NORTH);	    
	    addButton.addActionListener(this);
	    
	    //Delete profile button
	    deleteButton = new JButton("Delete");
	    add(deleteButton, NORTH);	    
	    deleteButton.addActionListener(this);
	    
	    //Lookup name button
	    lookupButton = new JButton("Lookup");
	    add(lookupButton, NORTH);	    
	    lookupButton.addActionListener(this);
	    
	    
	    /* West Interactors */
	    //Change status field
		statusField = new JTextField(TEXT_FIELD_SIZE);
		add(statusField, WEST);
		statusField.addActionListener(this);
		
		//Add status button
		statusButton = new JButton("Change Status");
	    add(statusButton, WEST);	    
	    statusButton.addActionListener(this);
		
		//Empty label for spacing
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		//Change picture field
		pictureField = new JTextField(TEXT_FIELD_SIZE);
		add(pictureField, WEST);
		pictureField.addActionListener(this);
		
		//Add picture button
		pictureButton = new JButton("Change Picture");
	    add(pictureButton, WEST);	    
	    pictureButton.addActionListener(this);
	    
	    //Empty label for spacing
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		//Add friend field
		friendField = new JTextField(TEXT_FIELD_SIZE);
		add(friendField, WEST);
		friendField.addActionListener(this);
		
		//Add picture button
		friendButton = new JButton("Add Friend");
	    add(friendButton, WEST);	    
	    friendButton.addActionListener(this);
		
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	//Add user
    	if (e.getSource() == addButton) {
    		String name = nameField.getText();
    		//Check to see if user exists
    		if(!db.containsProfile(name)) {
    			FacePamphletProfile newProfile = new FacePamphletProfile(name);
    			db.addProfile(newProfile);    			
    			currentProfile = newProfile; 
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("New profile for " + currentProfile.getName());
    		}
    		else {
    			//If user already exists then go to user profile
    			currentProfile = db.getProfile(name);
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Profile already exists for " + currentProfile.getName());
    		}
    		
    	}    	
    	//Delete user button
    	else if (e.getSource() == deleteButton) {
    		String name = nameField.getText();
    		//Check to see if user exists
    		if(db.containsProfile(name)) {
    			currentProfile = db.getProfile(name);
    			//Delete user from friends list of friends
				while(currentProfile.getFriends().hasNext()) {
					FacePamphletProfile friend = db.getProfile(currentProfile.getFriends().next());
					friend.removeFriend(currentProfile.getName());
					currentProfile.removeFriend(friend.getName());
				}
				//Delete profile then set profile to null
    			db.deleteProfile(name);
    			currentProfile = null;
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Deleted: Profile of " + name + " deleted");     			
    		}
    		else {
    			//Display meesage if user does not exist
    			canvas.showMessage("Deleted: Profile of " + name + " does not exist");    			
    		}
    		currentProfile = null;
    	}
    	//User search
    	else if (e.getSource() == lookupButton) {
    		String name = nameField.getText();
    		//check to see if user exists
    		if(db.containsProfile(name)) {
    			currentProfile = db.getProfile(name);
    			canvas.displayProfile(currentProfile);   
    			canvas.showMessage("Current profile is " + currentProfile.getName());
    		}
    		//Display message if user does not exist
    		else {
    			currentProfile = null;
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Profile does not exist: " + name);
    			
    		}    		
    	}
    	//Status update
    	else if (e.getSource() == statusField | e.getSource() == statusButton) {
    		String status = statusField.getText();
    		//Check to see if a profile is selected
    		if (currentProfile != null) {
    			currentProfile.setStatus(status);
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Status changed");
    		}
    		//If no profile selected then display message
    		else {
    			canvas.showMessage("No profile selected, select profile");
    		}
    	}
    	//Add picture
    	else if (e.getSource() == pictureField | e.getSource() == pictureButton) {
    		String picture = pictureField.getText();
    		GImage image = null;
    		//Check to see if field is not empty
    		if (!"".equals(picture)) {
    			//check to see if a user profile is selected
	    		if (currentProfile != null) {
	    			try {
	    				 image = new GImage(picture);
	    				 } catch (ErrorException ex) {
	    					 canvas.displayProfile(currentProfile);
	    					 canvas.showMessage("Invalid image file");
	    				 }
	    			//If image exists then add to profile	 
	    			if(image != null) {	 
		    			currentProfile.setImage(image);
		    			canvas.displayProfile(currentProfile);
		    			canvas.showMessage("Image changed");
	    			}
	    		}
	    		//Show message if no user selected
	    		else {
	    			canvas.showMessage("No profile selected, select profile");
	    		}
    		}    		
    	}
    	//Add friend
    	else if (e.getSource() == friendField | e.getSource() == friendButton) {
    		String friend = friendField.getText();
    		//Check to see if user profile is selected
    		if (currentProfile != null) {
    			//check to see if new friend exists
    			if(db.containsProfile(friend)) {
    				boolean isFriends = false;
    				//check to see if they are already a friend
    				while(currentProfile.getFriends().hasNext()) {
    					if(currentProfile.getFriends().next().equals(friend)) {
    						isFriends = true;
    					}
    				}
    				//Display message if users already friends
    				if(isFriends) {
    					canvas.showMessage("Already friends");
    				}
    				//Add friend if they exist and they are not already a friend
    				else{
    					currentProfile.addFriend(friend);
    					db.getProfile(friend).addFriend(currentProfile.getName());
    	    			canvas.displayProfile(currentProfile);
    	    			canvas.showMessage("Friend " + friend + " added.");
    				}    				
    			}
    			//Display message if user does not exist
    			else{
    				canvas.showMessage("User does not exist");
    			}    			
    		}
    		//Display message if no profile selected
    		else {
    			canvas.showMessage("No profile selected, select profile");
    		}   		
    	}
    	
    	
		
	}

    
    /* Interactor private instance variables  */
    //Text from nameField
    private JTextField nameField;
    //Add button
    private JButton addButton;
    //Delete button
    private JButton deleteButton;
    //Lookup button
    private JButton lookupButton;
    //Text from status
    private JTextField statusField;
    //Status button
    private JButton statusButton;
    //text from picture field
    private JTextField pictureField;
    //picture button
    private JButton pictureButton;
    //Text from friend field
    private JTextField	friendField;
    //Add friend button
    private JButton friendButton;
    
    
    //Create new database of names
    FacePamphletDatabase db = new FacePamphletDatabase();
    
    //Stores current profile
    FacePamphletProfile currentProfile;
    
    //Stores canvas
    private FacePamphletCanvas canvas;
}


