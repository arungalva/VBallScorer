
/***********************************************************************
 * @author: Arun Galva
   @version: 1.0
   Last Edited on June 12th, 2014
 Purpose: Team class containing Team Info
 * 
 ***********************************************************************/
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Team {

	private String teamName;
	private ArrayList <Player> players = new ArrayList<Player>();
	private int numOfPlayers;
	private String teamLogo="resource/images/default_user_picture.png";
	private Player player;
	
	/**
	 * Constructor that has team name as a parameter
	 * @param teamName name of the team
	 */
	public Team (String teamName)
	{
		this.teamName=teamName;
	}
	
	/**
	 * 
	 * @param teamName name of the team
	 * @param numPlayers number of players registered in the team
	 */
	public Team (String teamName, int numPlayers)
	{
		this.teamName=teamName;
		this.numOfPlayers=numPlayers;
	}
	
	/**
	 * 
	 * @param teamName name of the team
	 * @param numPlayers number of players registered in the team
	 * @param 
	 */
	public Team (String teamName, int numPlayers, String teamLogo)
	{
		this.teamName= teamName;
		this.numOfPlayers = numPlayers;
		this.teamLogo=teamLogo;
	}
	
	/**
	 * Change the team Logo
	 * @param TeamLogo location of the team logo
	 */
	public void setTeamLogo(String TeamLogo)
	{
		teamLogo=TeamLogo;
	}
	
	/**
	 * Change the team Name
	 * @param TeamName new Team Name
	 */	
	public void setTeamName(String TeamName)
	{
		teamName=TeamName;
	}
	
	/**
	 * get info about the player in the parameter
	 * @param playerNum the number of the player you want to get
	 * @return player with that number
	 */
	public Player getPlayer(int playerNum)
	{
		Player gotPlayer = null;
		// loop through the current teams players list
		for (int idx=0; idx<this.getPlayerList().size(); idx++)
		{
			if (playerNum==this.getPlayerList().get(idx).getPlayerNumber())
			{
				// if the player number matches the player number of anyone in the list, return that player
				gotPlayer = this.getPlayerList().get(idx);
			}
		}
		return gotPlayer;
		
	}
	
	/**
	 * Get the team name
	 * @return team Name
	 */
	public String getTeamName()
	{
		return teamName;
	}
	
	/**
	 * get the team logo location
	 * @return team logo location
	 */
	public String getTeamLogo()
	{
		return teamLogo;
	}
	
	/**
	 * get the total number of subs 
	 * @return number of subs in the team
	 */
	public int getNumOfPlayers()
	{
		return players.size();
		
	}
	
	/**
	 * Get the list of all the players in the team
	 * @return Players in the team
	 */
	public ArrayList<Player> getPlayerList()
	{
		return players;
	}
	
	/**
	 * add a player to a team
	 * @param newPlayer add this player to the team
	 */
	public void addPlayer(Player newPlayer)
	{
		
		this.getPlayerList().add(newPlayer);
	
	}
	/**
	 * remove a player from a team
	 * @param newPlayer remove this player from the team
	 */
	public void removePlayer(Player playerSelected)
	{
		for (int i = 0; i<this.getPlayerList().size(); i++){
			if (this.getPlayerList().get(i).getPlayerName().equals(playerSelected.getPlayerName())){
				this.getPlayerList().remove(playerSelected);
			}
		}
	}
	
	public void removePlayer(String playerName)
	{
		for (int i = 0; i<this.getPlayerList().size(); i++){
			if (this.getPlayerList().get(i).getPlayerName().equals(playerName)){
				this.getPlayerList().remove(i);
			}
		}
	}
}
