/***********************************************************************
 * @author: Arun Galva
   @version: 1.0
   Last Edited on June 12th, 2014
 Purpose: Player class containing Player Info
 * 
 ***********************************************************************/

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;


public class Player {
	NewGame game;

	private static Scanner in = null; // Scanner to read the stats file

	private int playerNumber; // variable to store Player Number
	private String playerName; // variable to store Player Name
	private String playerPicture = "resource/images/default_user_picture.png"; // Variable that stores the location of the player image
	private static int lines; // number of lines in the text files
	private boolean isOnCourt=false; // Boolean to check if the player is on the court
	
	/**
	 * Constructor with only player Name and Number as parameters
	 * Sets the default player picture
	 * @param pName Name of the Player
	 * @param pNum Number for the player
	 */

	public Player(int pNum, String pName)
	{
		this.playerNumber = pNum;
		this.playerName = pName;
	}
	/**
	 * Constructor with player Name, number and Picture as parameters
	 * @param pNum Name of the Player
	 * @param pName Number for the player
	 * @param playerLogo Location of the player Picture
	 */
	public Player(int pNum, String pName, String playerLogo)
	{
		this.playerNumber = pNum;
		this.playerName = pName;
		
		this.playerPicture=playerLogo;
	}

/**
 * Set custom player picture
 * @param pLogo location for the playerPicture
 */
	public void setPlayerIcon(String pLogo)
	{

		playerPicture=pLogo;
	}
/**
 * Change the player Number
 * @param pNumber Number for the Player
 */
	public void setPlayerNumber(int pNumber)
	{
		playerNumber=pNumber;
	}
/**
 * Change the player Name
 * @param pName new Name for the player
 */
	public void setPlayerName(String pName)
	{
		playerName=pName;
	}

	/**
	 * Get the name of the player
	 * @return player Name
	 */
	public  String getPlayerName()
	{
		return this.playerName;

	}
	/**
	 * Get the number for the player
	 * @return player Number
	 */

	public int getPlayerNumber()
	{
		return this.playerNumber;

	}
	
	/**
	 * Get the location of the picture for the player
	 * @return player Picture location
	 */

	public String getPlayerPicture()
	{
		return this.playerPicture;

	}

	

}
