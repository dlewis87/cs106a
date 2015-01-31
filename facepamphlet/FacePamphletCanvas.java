/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		message.setLabel(msg);
		message.setFont(MESSAGE_FONT);
		message.setLocation((APPLICATION_WIDTH - message.getWidth()) / 2, APPLICATION_HEIGHT - BOTTOM_MESSAGE_MARGIN);
		add(message);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if (profile != null) {
			addUserName(profile.getName());
			addImage(profile.getImage());
			addStatus(profile.getStatus(), profile.getName());
			addFriends(profile.getFriends());	
		}
	}
	
	/** Takes a profile name and adds it to the canvas */
	private void addUserName(String name) {
		userName = new GLabel(name);
		userName.setLocation(LEFT_MARGIN, TOP_MARGIN + userName.getHeight());
		userName.setFont(PROFILE_NAME_FONT);
		userName.setColor(Color.BLUE);
		add(userName);	
	}
	
	
	/** 
	 * Creates frame for image
	 * if image not null then adds adds it to the canvas */
	private void addImage (GImage img) {
		
		//Create frame for image
		pictureFrame = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
		pictureFrame.setLocation(LEFT_MARGIN, (userName.getY() + userName.getHeight() + IMAGE_MARGIN));	
		add(pictureFrame);
		
		
		//Add image if not null or display text
		if(img != null) {
			profileImage = img;
			profileImage.setBounds(pictureFrame.getX(), pictureFrame.getY(), IMAGE_WIDTH, IMAGE_HEIGHT);
			add(profileImage);			
		}
		else{
			GLabel noImage = new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			noImage.setLocation((pictureFrame.getX() + IMAGE_WIDTH - noImage.getWidth()) / 2, pictureFrame.getY() + IMAGE_HEIGHT / 2);
			add(noImage);
		}
		
	}
	
	
	/** Takes user status and adds it to the canvas */
	private void addStatus (String status, String name) {
		if (status != "") {
			userStatus = new GLabel(name + " is " + status);
			userStatus.setLocation(LEFT_MARGIN, pictureFrame.getY() + IMAGE_HEIGHT + STATUS_MARGIN + userStatus.getHeight());
			userStatus.setFont(PROFILE_STATUS_FONT);
			add(userStatus);
		}
		
	}
	
	
	/** Adds Friends banner then Iterates through friends and adds to canvas*/
	private void addFriends (Iterator<String> friends) {
		//Create banner
		GLabel friendsBanner = new GLabel("Friends: ");
		friendsBanner.setLocation(getWidth() / 2, TOP_MARGIN + friendsBanner.getHeight());
		friendsBanner.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsBanner);
		double y = friendsBanner.getY() + friendsBanner.getHeight();
		//Add friends
		while (friends.hasNext()) {
			GLabel friend = new GLabel("" + friends.next(), getWidth() / 2, y);
			friend.setFont(PROFILE_STATUS_FONT);
			add(friend);
			y += friendsBanner.getHeight();
		}
		
	}
	
	
	//Store username label
	private GLabel userName;
	//Store profile message
	private GLabel message = new GLabel(EMPTY_LABEL_TEXT);
	//Store picture frame GRect
	private GRect pictureFrame;
	//Store profile image
	private GImage profileImage = null;
	//Store status
	private GLabel userStatus;
}
