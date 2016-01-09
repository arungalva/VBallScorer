/***********************************************************************
 * @author: Arun Galva
   @version: 1.0
   Last Edited on June 12th, 2014
 Purpose: The model for the volleyball scorer 
 * 
 ***********************************************************************/

import java.util.ArrayList;

public class NewGame {

	private Team homeTeam; // Home team
	private Team awayTeam; // Away team
	private int homeTeamScore=0; // Integer for Home team Score
	private int awayTeamScore=0; // Integer for Away team Score
	private int homeTeamSets=0; // Integer for Home team Sets
	private int awayTeamSets=0; // Integer for Away team Sets
	private int overAllSets=0; // Integer for the total number of sets in the game
	private int maxPointsPerSet; // Integer for the max number of points per set
	private TeamRegistry availableTeams; // Team Registry Object for available teams
	private ArrayList <Player> homeTeamPlayersOnCourt = new ArrayList<Player>(); //arrayList of Players containing all the home team players on court
	private ArrayList <Player> homeTeamPlayerSubsAvailable = new ArrayList<Player>();//arrayList of Players containing all the home team substitute players
	private ArrayList <Player> awayTeamPlayersOnCourt = new ArrayList<Player>();//arrayList of Players containing all the away team players on court
	private ArrayList <Player> awayTeamPlayerSubsAvailable = new ArrayList<Player>();//arrayList of Players containing all the away team player substitute players
	private ArrayList <Integer> homeTeamSetScores = new ArrayList<Integer>(); //arrayList of integers containing scores of home team after each set
	private ArrayList <Integer> awayTeamSetScores = new ArrayList<Integer>(); //arrayList of integers containing scores of away team after each set
	private boolean setWon; //boolean for set won
	private boolean gameWon; //boolean for game won
	private Player currentServer; //Player for the current server
	private int homeTeamNumSubsUsed=0; // Integer to keep track of the subs used by the home team
	private int awayTeamNumSubsUsed=0; // Integer to keep track of the subs used by the away team
	private int homeTeamNumTimeoutsUsed=0; // Integer to keep track of the timeouts used by the home team
	private int awayTeamNumTimeoutsUsed=0; // Integer to keep track of the timeouts used by the away team
	private int currentSetNumber=1; // Integer to keep track of the current set Number
	private Player selectedPlayer; // Player for the selected player
	private int originalMaxSetPoints; // default max set points

	/**
	 *  Constructor 
	 * @param homeTeamPlaying Home Team
	 * @param awayTeamPlaying Away Team
	 * @param numPointsPerSet Max points per set
	 * @param numSets Total number of sets in the game
	 */
	public NewGame(Team homeTeamPlaying, Team awayTeamPlaying, int numPointsPerSet, int numSets)
	{
		this.homeTeam=homeTeamPlaying;
		this.awayTeam = awayTeamPlaying;
		this.originalMaxSetPoints=numPointsPerSet;
		this.maxPointsPerSet=numPointsPerSet;
		this.overAllSets=numSets;
	}

	////////////////////////////////////////////////////////////////// SETTING METHODS

	/** Method to set the home team Player as a Player on court 
	 * @param playerNum Number of the Player from the Home Team that You want to add to court
	 * 
	 * 
	 * */
	public void setHomePlayerOnCourt(int playerNum)
	{
		// add the player to the home team players on court and remove player from subs
		this.getHomeTeamPlayersOnCourt().add(this.getHomeTeam().getPlayer(playerNum));
		this.getHomePlayersSubs().remove(this.getHomeTeam().getPlayer(playerNum));

	}

/**
 * Check if the player is on court for the home team
 * @param playerNum number of the player to check
 * @return true if the player is on court
 */
	public boolean checkPlayerCourtHome(int playerNum)
	{
		if (this.getHomeTeamPlayersOnCourt().contains(this.getHomeTeam().getPlayer(playerNum)) )
		{
			return true;

		}else{

			return false;
		}
	}

	/**
	 * Check if the player is on court for the away team
	 * @param playerNum number of the player to check
	 * @return true if the player is on court
	 */
	public boolean checkPlayerCourtAway(int playerNum)
	{
		if (this.getAwayTeamPlayersOnCourt().contains(this.getAwayTeam().getPlayer(playerNum)))
		{
			return true;

		}else{

			return false;
		}
	}


	/**
	 * 
	 * @param playerNum number of the player that is on court
	 */
	// method to set the away team player as a player on court
	public void setAwayPlayerOnCourt(int playerNum)
	{
		// add the player to the away team players on court and remove player from subs
		this.getAwayTeamPlayersOnCourt().add(this.getAwayTeam().getPlayer(playerNum));
		this.getAwayPlayersSubs().remove(this.getAwayTeam().getPlayer(playerNum));
	}

	/**
	 * method to set the Home Team Player Substitutions
	 */
	public void setHomeTeamPlayersubs()
	{	this.getHomePlayersSubs().addAll(this.getHomeTeam().getPlayerList()); // add all Home team Players to subs

	// loop through the player subs list
	for (int idx=0; idx<(this.getHomePlayersSubs().size()-(this.getHomePlayersSubs().size()-this.getHomeTeamPlayersOnCourt().size())); idx++)
	{
		// remove the players on court from player subs 
		this.getHomePlayersSubs().remove(getHomeTeamPlayersOnCourt().get(idx));
	}
	}

	/**
	 * method to set the Away Team Player Substitutions
	 */
	public void setAwayTeamPlayersubs()
	{
		this.getAwayPlayersSubs().addAll(this.getAwayTeam().getPlayerList()); // add all Away team Players to subs
		// loop through the player subs list
		for (int idx=0; idx<(this.getAwayPlayersSubs().size()-(this.getAwayPlayersSubs().size()-this.getAwayTeamPlayersOnCourt().size())); idx++)
		{
			// remove the players on court from player subs 
			this.getAwayPlayersSubs().remove(getAwayTeamPlayersOnCourt().get(idx));
		}

	}

	/**
	 *  Method to set the Home team Score
	 * @param homeTeamPoints number of points you want to add to the home Team Score
	 */
	public void setHomeTeamScore(int homeTeamPoints)
	{
		// set Home team Score as the Integer in the parameter
		this.homeTeamScore = homeTeamPoints;

		// set Home team score to O if the Score is anything less than 0
		if (this.getHomeTeamScore()<0||this.getHomeTeamScore()>this.getMaxPointsPerSet())
		{
			this.resetScore(this.getHomeTeam());
		}

		// if the home team reaches the maximum points per set, add one point to the home team sets
		if (this.getHomeTeamScore()==this.getMaxPointsPerSet())
		{
			this.setHomeTeamSets(this.getHomeTeamSets()+1);
		}

		// if both the home team and away team are tied at one point away from the max points, add one to the max points
		if (this.getHomeTeamScore()==(this.getMaxPointsPerSet()-1))
		{
			if (this.getAwayTeamScore()==this.getMaxPointsPerSet()-1)
			{
				this.maxPointsPerSet= this.getMaxPointsPerSet()+1;
			}
		}

		// check if Home Team Score equals the max points allowed per set
		if (this.getHomeTeamScore()==this.maxPointsPerSet)
		{
			//Set the boolean setWon to true
			this.setWon=true;
			// once the set is one, reset the current max points to the original
			this.maxPointsPerSet=this.originalMaxSetPoints;
		}

	}

	/**
	 *  Method to set Away team Score
	 * @param awayTeamPoints number of points you want to add to the Away Team Score
	 */
	public void setAwayTeamScore(int awayTeamPoints)
	{
		// set Away team Score as the Integer in the parameter
		this.awayTeamScore = awayTeamPoints;

		// set Home Away score to O if the Score is anything less than 0
		if (this.getAwayTeamScore()<0||this.getAwayTeamScore()>this.getMaxPointsPerSet())
		{
			this.resetScore(this.getAwayTeam());
		}

		// if the away team reaches the maximum points per set, add one point to the home team sets
		if (this.getAwayTeamScore()==this.getMaxPointsPerSet()){
			this.setAwayTeamSets(this.getAwayTeamSets()+1);
		}

		// check if Away Team Score equals the max points allowed per set
		if (this.getAwayTeamScore()==this.maxPointsPerSet)
		{
			//Set the boolean setWon to true
			this.setWon=true;
			// once the set is one, reset the current max points to the original
			this.maxPointsPerSet=this.originalMaxSetPoints;
		}


		// if both the home team and away team are tied at one point away from the max points, add one to the max points
		if (this.getAwayTeamScore()==(this.getMaxPointsPerSet()-1))
		{
			if (this.getHomeTeamScore()==this.getMaxPointsPerSet()-1)
			{
				this.maxPointsPerSet= this.getMaxPointsPerSet()+1;
			}
		}
	}

	/**
	 *  Method to set Home Team Sets
	 * @param homeTeamSetsWon number of Sets you want to add to the home Team sets
	 */
	public void setHomeTeamSets(int homeTeamSetsWon)
	{
		// set Home team Sets as the Integer in the parameter

		this.homeTeamSets = homeTeamSetsWon;

		if (this.getHomeTeamSets()==this.getMaxSetsToWin())
		{
			this.gameWon=true;
		}
		// Set Home Team sets to 0 if sets is less than 0 
		if (this.getHomeTeamSets()<0)
		{
			this.setHomeTeamScore(0);
			this.setAwayTeamSets(0);
		}

		// Set the Home team sets to the max set points if it is greater than teh max set points
		if (this.getHomeTeamSets()>this.getMaxSetsToWin())
		{
			this.setHomeTeamSets(this.getMaxSetsToWin());
		}
	}

	/**
	 *  Method to set Away Team Sets
	 * @param awayTeamSetsWon  number of Sets you want to add to the away Team sets
	 */
	public void setAwayTeamSets(int awayTeamSetsWon)
	{
		// set Away team Sets as the Integer in the parameter
		this.awayTeamSets = awayTeamSetsWon;

		if (this.getHomeTeamSets()==this.getMaxSetsToWin())
		{
			this.gameWon=true;
		}

		// Set Away Team sets to 0 if sets is less than 0 
		if (this.getAwayTeamSets()<0)
		{
			this.setAwayTeamScore(0);
			this.setAwayTeamSets(0);
		}

		// Set the Away team sets to the max set points if it is greater than the max set points
		if (this.getAwayTeamSets()>this.getMaxSetsToWin())
		{
			this.setAwayTeamSets(this.getMaxSetsToWin());
		}

	}

	/**
	 *  Method to make a substitution for the home team
	 * @param subIn Player number for the player from the home team that is being subbed in
	 * @param subOut Player number for the player  from the home team that is being subbed out
	 */
	public void homeTeamAddSub (int subIn, int subOut)
	{
		// set the player sub in onto the court
		this.setHomePlayerOnCourt(subIn);
		//Remove the player Sub out from the Players on court
		this.getHomeTeamPlayersOnCourt().remove(this.getHomeTeam().getPlayer(subOut));
		// add the player Sub out to the player subs list
		this.getHomePlayersSubs().add(this.getHomeTeam().getPlayer(subOut));
		// add one to the number of subs used
		this.homeTeamNumSubsUsed=this.getHomeTeamSubsUsed()+1;
	}

	/**
	 *  Method to make a substitution for the away team
	 *@param subIn Player number for the player from the away team that is being subbed in
	 *@param subOut Player number for the player  from the away team that is being subbed out
	 */
	public void awayTeamAddSub (int subIn, int subOut)
	{
		// set the player sub in onto the court
		this.setAwayPlayerOnCourt(subIn);
		//Remove the player Sub out from the Players on court
		this.getAwayTeamPlayersOnCourt().remove(this.getAwayTeam().getPlayer(subOut));
		// add the player Sub out to the player subs list
		this.getAwayPlayersSubs().add(this.getAwayTeam().getPlayer(subOut));
		// add one to the number of subs used
		this.awayTeamNumSubsUsed=this.getAwayTeamSubsUsed()+1;
	}

	/**
	 * Sets team in the parameter as the team that won the set
	 * @param team Team that won the set
	 */

	public void setWinningSet(Team team)
	{
		if (setWon==true)
		{
			int setsWon=0; // initialize setsWon to 0
			if (team.getTeamName()==homeTeam.getTeamName())
			{
				this.setHomeTeamSets(setsWon+1); // Add 1 to the number of sets Won by the home team
				this.getHomeTeamSetPoints().set((this.getCurrentSetNumber()-1) , this.getHomeTeamScore()); // register the score into the array list Home team set scores 

			}

			else if (team.getTeamName()==awayTeam.getTeamName())
			{
				this.setAwayTeamSets(setsWon+1); // Add 1 to the number of sets Won by the away team
				this.awayTeamSetScores.add(this.getAwayTeamScore()); // register the score into the array list Away team set scores 
			}
			this.resetScore(this.getHomeTeam()); // reset the scores of the home team
			this.resetScore(this.getAwayTeam()); // reset the scores of the away team

			this.currentSetNumber++; //
		}
	}

	/**
	 * Set the player in the parameter as the Server for the Home Team
	 * @param currentServer Player from the home team who is serving
	 */
	public void setHomeTeamServer(Player currentServer)
	{
		// loop through the Home team players on court
		for (int idx=0; idx<homeTeamPlayersOnCourt.size(); idx++)
		{
			if (homeTeamPlayersOnCourt.get(idx)==currentServer)
			{
				// if the Player currentServer is found, set him as the current server
				this.currentServer = homeTeamPlayersOnCourt.get(idx);
			}
		}
	}

	/**
	 * Set the player in the parameter as the Server for the Away Team
	 * @param currentServer Player from the away team who is serving
	 */
	public void setAwayTeamServer(Player currentServer)
	{
		// loop through the Away team players on court
		for (int idx=0; idx<awayTeamPlayersOnCourt.size(); idx++)
		{
			if (awayTeamPlayersOnCourt.get(idx)==currentServer)
			{
				// if the Player currentServer is found, set him as the current server
				this.currentServer = awayTeamPlayersOnCourt.get(idx);
			}
		}
	}

	/**
	 * Reset the scores of the team in the parameter
	 * @param team Team you want to reset the scores for
	 */
	public void resetScore (Team team)
	{
		if (team.getTeamName()==homeTeam.getTeamName())
		{
			// set home team score to 0 if the parameter is home team
			this.setHomeTeamScore(0);
		}

		else if (team.getTeamName()==awayTeam.getTeamName())
		{
			// set away team score to 0 if the parameter is away team
			this.setAwayTeamScore(0);
		}
	}

	/**
	 * Reset Sets of the team in the parameter
	 * @param team team you want to reset the sets for
	 */
	public void resetSets (Team team)
	{
		if (team.getTeamName()==homeTeam.getTeamName())
		{
			//set home team sets to 0 if the parameter is home team
			this.setHomeTeamSets(0);
		}

		else if (team.getTeamName()==awayTeam.getTeamName())
		{
			// set away team sets to 0 if the parameter is away team
			this.setAwayTeamSets(0);
		}
	}

	/**
	 * Reset Points and sets won for both teams
	 */
	public void resetAll()
	{
		this.resetScore(this.getHomeTeam());
		this.resetScore(this.getAwayTeam());
		this.resetSets(this.getHomeTeam());
		this.resetSets(this.getAwayTeam());
	}

	/**
	 * Home Team Timeout 
	 */
	public void homeTeamTimeout()
	{
		// add 1 to the time outs used by the home team
		this.homeTeamNumTimeoutsUsed= this.homeTeamNumTimeoutsUsed+1;
	}

	/**
	 * Away Team Timeout 
	 */
	public void awayTeamTimeout()
	{
		// add 1 to the time outs used by the away team
		this.awayTeamNumTimeoutsUsed= this.awayTeamNumTimeoutsUsed+1;
	}

	/**
	 * Select a player from any team
	 * @param player you want to select
	 */
	public void selectPlayer(Player player)
	{
		// if the home team has the player, look through the list and compare the player names
		if (this.getHomeTeam().getPlayerList().contains(player))
		{
			for (int idx=0; idx<this.getHomeTeam().getPlayerList().size(); idx++)
			{
				if (player.getPlayerName().equalsIgnoreCase(this.getHomeTeam().getPlayerList().get(idx).getPlayerName()))
				{
					// if player name is found, select that player
					this.selectedPlayer=this.getHomeTeam().getPlayerList().get(idx);
				}
			}
		}
		else{
			// if the away team has the player, look through the list and compare the player names

			for (int idx=0; idx<this.getAwayTeam().getPlayerList().size(); idx++)
			{
				if (player.getPlayerName().equalsIgnoreCase(this.getAwayTeam().getPlayerList().get(idx).getPlayerName()))
				{
					// if player name is found, select that player
					this.selectedPlayer=this.getAwayTeam().getPlayerList().get(idx);
				}
			}
		}
	}

	/**
	 * Initializes the set points and sets all the set points to zero fro both teams
	 */
	public void InitializeSetPoints()
	{
		for (int idx=0; idx<this.getOverAllSets(); idx++)
		{
			this.getHomeTeamSetPoints().add(0); // loop through the arraylist of home team set points and set everything to 0
		}

		for (int idx=0; idx<this.getOverAllSets(); idx++)
		{
			this.getAwayTeamSetPoints().add(0);// loop through the arraylist of away team set points and set everything to 0
		}

	}


	////////////////////////////////////////////////////////// ACCESSOR METHODS

	/**
	 * get the number of sets to win in order to win the game
	 * @return max sets 
	 */
	public int getMaxSetsToWin()
	{
		return ((getOverAllSets()/2)+1);
	}

	/**
	 * get the Home Team
	 * @return the Home Team
	 */
	public Team getHomeTeam()
	{
		return homeTeam;
	}

	/**
	 * Get the Away Team
	 * @return the Away Team
	 */

	public Team getAwayTeam()
	{
		return awayTeam;
	}

	/**
	 * Get the current set number
	 * @return current set number
	 */

	public int getCurrentSetNumber()
	{

		if (this.currentSetNumber<=this.getMaxSetsToWin())
		{
			// return the current set number if it is less than max sets allowed to win
			return this.currentSetNumber;
		}

		else{

			return 0;
		}
	}

	/**
	 * Get the List  of players from the home team that are on court
	 * @return Home team players on court
	 */
	public ArrayList<Player> getHomeTeamPlayersOnCourt()
	{
		return homeTeamPlayersOnCourt;
	}

	/**
	 * Get the List  of players from the away team that are on court
	 * @return Away team players on court
	 */

	public ArrayList<Player> getAwayTeamPlayersOnCourt()
	{
		return awayTeamPlayersOnCourt;
	}

	/**
	 * Get the List  of players from the home team that are on the bench
	 * @return Home team substitute players
	 */
	public ArrayList<Player> getHomePlayersSubs()
	{
		return homeTeamPlayerSubsAvailable;
	}

	/**
	 * Get the List  of players from the away team that are on the bench
	 * @return Away team substitute players
	 */
	public ArrayList<Player> getAwayPlayersSubs()
	{
		return awayTeamPlayerSubsAvailable;
	}

	/**
	 *  Get the Home Team Points in the current set
	 * @return home Team score
	 */
	public int getHomeTeamScore()
	{
		return homeTeamScore;

	}

	/**
	 *  Get the Away Team Points in the current set
	 * @return away Team score
	 */
	public int getAwayTeamScore()
	{
		return awayTeamScore;
	}

	/**
	 *  Get the Number of Sets won by the home team
	 * @return Home Team sets WOn
	 */
	public int getHomeTeamSets()
	{

		return homeTeamSets;

	}

	/**
	 *  Get the Number of Sets won by the away team
	 * @return away Team sets won
	 */
	public int getAwayTeamSets()
	{

		return awayTeamSets;

	}

	/**
	 *  get the scores of the team from each set
	 * @param team Team that you want the Individual Scores For
	 * @param <Integer> List of the individual set scores
	 * @return the individual scores of the team
	 */
	public <Integer> ArrayList getIndividualSetPoints(Team team)
	{

		if (team.getTeamName()==homeTeam.getTeamName())
		{
			// return home team set scores if the team is home team
			return this.homeTeamSetScores;
		}

		else if (team.getTeamName()==awayTeam.getTeamName())
		{
			// return away team set scores if the team is away team
			return this.awayTeamSetScores;
		}

		else{return null;}

	}

	/**
	 * Get the player that is serving Now
	 * @return current server
	 */
	public Player getServer()
	{
		return this.currentServer;
	}

	/**
	 * Get the total number of sets in the game 
	 * @return Over all sets
	 */
	public int getOverAllSets()
	{
		return this.overAllSets;
	}

	/**
	 * Get the number of subs used by the home team
	 * @return homeTeamNumSubsUsed
	 */
	public int getHomeTeamSubsUsed()
	{
		return this.homeTeamNumSubsUsed;
	}

	/**
	 * Get the number of subs used by the away team
	 * @return awayTeamNumSubsUsed
	 */
	public int getAwayTeamSubsUsed()
	{
		return this.awayTeamNumSubsUsed;
	}

	/**
	 * Get the number of Time outs used by the home team
	 * @return homeTeamNumTimeoutsUsed
	 */	
	public int getHomeTeamTimeoutsUsed()
	{
		return this.homeTeamNumTimeoutsUsed;
	}

	/**
	 * Get the number of Time outs used by the away team
	 * @return awayTeamNumTimeoutsUsed
	 */
	public int getAwayTeamTimeoutsUsed()
	{
		return this.awayTeamNumTimeoutsUsed;
	}

	/**
	 * Get the maximum number of points per set
	 * @return maxPointsPerSet
	 */
	public int getMaxPointsPerSet()
	{
		return this.maxPointsPerSet;
	}

	/**
	 * Get the Set scores for the home team
	 * @return Arraylist containing all the scores for the home team
	 */
	public ArrayList<Integer> getHomeTeamSetPoints()
	{
		return this.homeTeamSetScores;
	}

	/**
	 * Get the Set scores for the Away team
	 * @return Arraylist containing all the scores for the away team
	 */
	public ArrayList<Integer> getAwayTeamSetPoints()
	{
		return this.awayTeamSetScores;
	}

	/**
	 * get the player selected 
	 * @return the selected player
	 */
	public Player getSelectedPlayer()
	{
		return this.selectedPlayer;
	}

}
