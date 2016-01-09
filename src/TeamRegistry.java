/***********************************************************************
 * @author: Arun Galva
   @version: 1.0
   Last Edited on June 12th, 2014
 Purpose: TeamResgitry class to keep the total teams data
 * 
 ***********************************************************************/
import java.util.*;
import javax.swing.Icon;

public class TeamRegistry {

	private static ArrayList <Team> teamsAvailable = new ArrayList <Team>(); // arraylist of teams 
	private static Team team; // individual team
	private static int numTeams=0; // total number of teams
	
	/***
	 * get the team
	 * @param teamName name of the team you want to get
	 * @return get team
	 */
	public static Team getTeam(String teamName)
	{
		for (int i = 0; i<teamsAvailable.size();i++){
			if (teamsAvailable.get(i).getTeamName().equals(teamName)){
				return teamsAvailable.get(i);
			}
		}
		return null;
	}
	
	/**add the team to the list
	 * @param teamName name of the team you want to add to the list
	 */
	public static void addTeam (String teamName)
	{
		numTeams=numTeams+1; // increase number of teams by 1

		Team newTeam = new Team (teamName); // create the new team
		teamsAvailable.add(newTeam); // add the new team to the teams Available list
	}
	
	public static void addTeam (Team theTeam)
	{
		numTeams=numTeams+1; // increase number of teams by 1

		teamsAvailable.add(theTeam); // add the new team to the teams Available list
	}

	/**
	 * remove the team from the list
	 * @param team team you want to remove
	 */
	public static void removeTeam(Team team)
	{
		teamsAvailable.remove(team);
	}

	public static ArrayList<Team> getTeamsAvailable(){
		
		return teamsAvailable;
		
	}
}
