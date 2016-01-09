/***********************************************************************
 * @author: Arun Galva
   @version: 1.0
   Last Edited on June 12th, 2014
 Purpose: LiveTextOutPut class that outputs the data to a text file
 * 
 ***********************************************************************/
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.file.Files;

public class LiveTextOutPut {

	private NewGame outPutGame; // instance of the object NewGame
	private File gameFile; // Output file
	private File liveTextFile; // file for live text

	/**
	 * Constructor 
	 * @param game game you want the file for
	 */
	public LiveTextOutPut(NewGame game)
	{
		this.outPutGame = game;

	}

	/**
	 * get the current game
	 * @return the game
	 */
	public NewGame getOutputGame()
	{
		return this.outPutGame;
	}

	/**
	 * get the game data file
	 * @return the file that game data is outputted to
	 */
	public File getGameFile()
	{
		return this.gameFile;
	}

	public File getLiveTextFile()
	{
		return this.liveTextFile;
	}

	/**
	 * Create the actual game file
	 * @param homeTeam the home team in the game
	 * @param awayTeam the away team in the game
	 * @throws IOException 
	 */


	public void createGameFile(Team homeTeam, Team awayTeam)
	{
		String dir="Games";
		String LiveTextdir="C:/Program Files (x86)/NewTek/LiveText/Stats Plugins/Text Input/Games";

		// create the new file with a dynamic name inside of a folder called games
		this.gameFile = new File(dir+"/"+homeTeam.getTeamName() +" vs "+ awayTeam.getTeamName()+".txt");
		try {
			this.getGameFile().getParentFile().mkdirs();
			this.getGameFile().createNewFile();

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());

		}

		// write to the game file using print writer

		PrintWriter basicLabelWriter = null; // initialize the print writer
		try{

			basicLabelWriter= new PrintWriter(this.getGameFile()); //set up the print writer to write to the game file
			//basicLabelWriter= new PrintWriter(this.liveTextFile); //set up the print writer to write to the Live Text file

			basicLabelWriter.println("BASIC HOME TEAM INFORMATION");
			basicLabelWriter.println("Home Team Name="+this.outPutGame.getHomeTeam().getTeamName());
			basicLabelWriter.println("Home Team Logo="+this.outPutGame.getHomeTeam().getTeamLogo());
			basicLabelWriter.println("\n");
			basicLabelWriter.println("BASIC AWAY TEAM INFORMATION");
			basicLabelWriter.println("Away Team Name="+this.outPutGame.getAwayTeam().getTeamName());
			basicLabelWriter.println("Away Team Logo="+this.outPutGame.getAwayTeam().getTeamLogo());


			// print labels for Home Team Player Names
			basicLabelWriter.println("\n");
			basicLabelWriter.println("HOME TEAM PLAYER NAMES");
			for (int idx=0; idx<this.outPutGame.getHomeTeam().getPlayerList().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getHomeTeam().getPlayerList().get(idx).getPlayerName());
			}

			// print out the current set number
			basicLabelWriter.println("\n");
			basicLabelWriter.println("Current Set Number="+this.outPutGame.getCurrentSetNumber());


			// print labels for AWAY Team Player Names
			basicLabelWriter.println("\n");
			basicLabelWriter.println("AWAY TEAM PLAYER NAMES");
			for (int idx=0; idx<this.outPutGame.getAwayTeam().getNumOfPlayers(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getAwayTeam().getPlayerList().get(idx).getPlayerName());
			}


			// print labels for HOME Team Player NUMBERS
			basicLabelWriter.println("\n");
			basicLabelWriter.println("HOME TEAM PLAYER NUMBERS");
			for (int idx=0; idx<this.outPutGame.getHomeTeam().getNumOfPlayers(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getHomeTeam().getPlayerList().get(idx).getPlayerNumber());
			}

			// print labels for AWAY Team Player NUMBERS
			basicLabelWriter.println("\n");
			basicLabelWriter.println("AWAY TEAM PLAYER NUMBERS");
			for (int idx=0; idx<this.outPutGame.getAwayTeam().getNumOfPlayers(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getAwayTeam().getPlayerList().get(idx).getPlayerNumber());
			}

			// print labels for HOME Team Player Pictures
			basicLabelWriter.println("\n");
			basicLabelWriter.println("HOME TEAM PLAYER PICTURES");
			for (int idx=0; idx<this.outPutGame.getHomeTeam().getNumOfPlayers(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Picture="+this.outPutGame.getHomeTeam().getPlayerList().get(idx).getPlayerPicture());
			}

			// print labels for AWAY Team Player Pictures
			basicLabelWriter.println("\n");
			basicLabelWriter.println("AWAY TEAM PLAYER PICTURES");
			for (int idx=0; idx<this.outPutGame.getAwayTeam().getNumOfPlayers(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Picture="+this.outPutGame.getAwayTeam().getPlayerList().get(idx).getPlayerPicture());
			}

			// print labels for HOME Team Players on court Names
			basicLabelWriter.println("\n");
			basicLabelWriter.println("HOME TEAM PLAYERS ON COURT NAMES");
			for (int idx=0; idx<this.outPutGame.getHomeTeamPlayersOnCourt().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getHomeTeamPlayersOnCourt().get(idx).getPlayerName());
			}

			// print labels for Away Team Players on court Names
			basicLabelWriter.println("\n");
			basicLabelWriter.println("AWAY TEAM PLAYERS ON COURT NAMES");
			for (int idx=0; idx<this.outPutGame.getAwayTeamPlayersOnCourt().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getAwayTeamPlayersOnCourt().get(idx).getPlayerName());
			}

			// print labels for HOME Team Players on court Numbers
			basicLabelWriter.println("\n");
			basicLabelWriter.println("HOME TEAM PLAYERS ON COURT NUMBERS");
			for (int idx=0; idx<this.outPutGame.getHomeTeamPlayersOnCourt().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getHomeTeamPlayersOnCourt().get(idx).getPlayerNumber());
			}

			// print labels for Away Team Players on court Numbers
			basicLabelWriter.println("\n");
			basicLabelWriter.println("AWAY TEAM PLAYERS ON COURT NUMBERS");
			for (int idx=0; idx<this.outPutGame.getAwayTeamPlayersOnCourt().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getAwayTeamPlayersOnCourt().get(idx).getPlayerNumber());
			}

			// print labels for HOME Team Subs Names
			basicLabelWriter.println("\n");
			basicLabelWriter.println("HOME TEAM PLAYERS ON BENCH NAMES");
			for (int idx=0; idx<this.outPutGame.getHomePlayersSubs().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getHomePlayersSubs().get(idx).getPlayerName());
			}

			// print labels for Away Team subs Names
			basicLabelWriter.println("\n");
			basicLabelWriter.println("AWAY TEAM PLAYERS ON BENCH NAMES");
			for (int idx=0; idx<this.outPutGame.getAwayPlayersSubs().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getAwayPlayersSubs().get(idx).getPlayerName());
			}


			// print labels for HOME Team Subs NUmbers
			basicLabelWriter.println("\n");
			basicLabelWriter.println("HOME TEAM PLAYERS ON BENCH NUMBERS");
			for (int idx=0; idx<this.outPutGame.getHomePlayersSubs().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getHomePlayersSubs().get(idx).getPlayerNumber());
			}

			// print labels for Away Team Players on court Names
			basicLabelWriter.println("\n");
			basicLabelWriter.println("AWAY TEAM PLAYERS ON BENCH NUMBERS");
			for (int idx=0; idx<this.outPutGame.getAwayPlayersSubs().size(); idx++)
			{
				basicLabelWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getAwayPlayersSubs().get(idx).getPlayerNumber());
			}

			// Print Labels for the server
			basicLabelWriter.println("\n");
			basicLabelWriter.println("CURRENT SERVER INFORMATION");
			basicLabelWriter.println("CURRENT SERVER NAME="+this.outPutGame.getServer().getPlayerName());
			basicLabelWriter.println("CURRENT SERVER NUMBER="+this.outPutGame.getServer().getPlayerNumber());
			basicLabelWriter.println("CURRENT SERVER PICTURE="+this.outPutGame.getServer().getPlayerPicture());

			//Print Labels for the InGame Data
			basicLabelWriter.println("\n");
			basicLabelWriter.println("IN GAME DATA");
			basicLabelWriter.println("Home Team Score="+this.outPutGame.getHomeTeamScore());
			basicLabelWriter.println("Away Team Score="+this.outPutGame.getAwayTeamScore());
			basicLabelWriter.println("Home Team Sets Won="+this.outPutGame.getHomeTeamSets());
			basicLabelWriter.println("Away Team Sets Won="+this.outPutGame.getAwayTeamSets());
			basicLabelWriter.println("Home Team Substitutions Taken="+this.outPutGame.getHomeTeamSubsUsed());
			basicLabelWriter.println("Away Team Substitutions Taken="+this.outPutGame.getAwayTeamSubsUsed());
			basicLabelWriter.println("Home Team Substitutions Left="+(18-this.outPutGame.getHomeTeamSubsUsed()));
			basicLabelWriter.println("Away Team Substitutions Left="+(18-this.outPutGame.getAwayTeamSubsUsed()));
			basicLabelWriter.println("Home Team Time Outs Taken="+this.outPutGame.getHomeTeamTimeoutsUsed());
			basicLabelWriter.println("Away Team Time Outs Taken="+this.outPutGame.getAwayTeamTimeoutsUsed());
			basicLabelWriter.println("Home Team Sets Won="+this.outPutGame.getHomeTeamSets());
			basicLabelWriter.println("Away Team Sets Won="+this.outPutGame.getAwayTeamSets());

			//Print Labels for score in each set

			basicLabelWriter.println("\n");
			basicLabelWriter.println("HOME TEAM INDIVIDUAL SET SCORES INFORMATION");

			for (int idx=0; idx<this.getOutputGame().getHomeTeamSetPoints().size(); idx++)
			{
				basicLabelWriter.println("Home Team Set " +(idx+1)+ " Score="+this.outPutGame.getHomeTeamSetPoints().get(idx));
			}



			basicLabelWriter.println("\n");
			basicLabelWriter.println("AWAY TEAM INDIVIDUAL SET SCORES INFORMATION");

			for (int idx=0; idx<this.getOutputGame().getAwayTeamSetPoints().size(); idx++)
			{
				basicLabelWriter.println("Away Team Set " +(idx+1)+ " Score="+this.outPutGame.getAwayTeamSetPoints().get(idx));
			}			


			//Print Labels for Selected Player
			basicLabelWriter.println("\n");
			basicLabelWriter.println("SELECTED PLAYER INFORMATION");
			basicLabelWriter.println("Player Name= "+this.outPutGame.getSelectedPlayer().getPlayerName());
			basicLabelWriter.println("Player Number= "+this.outPutGame.getSelectedPlayer().getPlayerNumber());			
			basicLabelWriter.println("Player Image= "+this.outPutGame.getSelectedPlayer().getPlayerPicture());
			// close the file


			basicLabelWriter.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());

		}


		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////Code to create and write the data to the live text file///////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		this.liveTextFile = new File(LiveTextdir+"/"+homeTeam.getTeamName() +" vs "+ awayTeam.getTeamName()+".txt");
		try {
			this.liveTextFile.getParentFile().mkdirs();
			this.liveTextFile.createNewFile();

		}
		catch (Exception e)
		{
			System.out.println("The Problem could be one of the following:");
			System.out.println("\n 1) Live text isnt insatlled in Progam files");
			System.out.println("\n 2) You donot hav permission to edit the files");
			System.out.println("\n\n to fix this error, please look at the user manual");

		}

		// write to the game file using print writer

		PrintWriter LiveTextWriter = null; // initialize the print writer
		try{

			LiveTextWriter= new PrintWriter(this.liveTextFile); //set up the print writer to write to the Live Text file

			LiveTextWriter.println("BASIC HOME TEAM INFORMATION");
			LiveTextWriter.println("Home Team Name="+this.outPutGame.getHomeTeam().getTeamName());
			LiveTextWriter.println("Home Team Logo="+this.outPutGame.getHomeTeam().getTeamLogo());
			LiveTextWriter.println("\n");
			LiveTextWriter.println("BASIC AWAY TEAM INFORMATION");
			LiveTextWriter.println("Away Team Name="+this.outPutGame.getAwayTeam().getTeamName());
			LiveTextWriter.println("Away Team Logo="+this.outPutGame.getAwayTeam().getTeamLogo());


			// print labels for Home Team Player Names
			LiveTextWriter.println("\n");
			LiveTextWriter.println("HOME TEAM PLAYER NAMES");
			for (int idx=0; idx<this.outPutGame.getHomeTeam().getPlayerList().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getHomeTeam().getPlayerList().get(idx).getPlayerName());
			}

			// print out the current set number
			LiveTextWriter.println("\n");
			LiveTextWriter.println("Current Set Number="+this.outPutGame.getCurrentSetNumber());


			// print labels for AWAY Team Player Names
			LiveTextWriter.println("\n");
			LiveTextWriter.println("AWAY TEAM PLAYER NAMES");
			for (int idx=0; idx<this.outPutGame.getAwayTeam().getNumOfPlayers(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getAwayTeam().getPlayerList().get(idx).getPlayerName());
			}


			// print labels for HOME Team Player NUMBERS
			LiveTextWriter.println("\n");
			LiveTextWriter.println("HOME TEAM PLAYER NUMBERS");
			for (int idx=0; idx<this.outPutGame.getHomeTeam().getNumOfPlayers(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getHomeTeam().getPlayerList().get(idx).getPlayerNumber());
			}

			// print labels for AWAY Team Player NUMBERS
			LiveTextWriter.println("\n");
			LiveTextWriter.println("AWAY TEAM PLAYER NUMBERS");
			for (int idx=0; idx<this.outPutGame.getAwayTeam().getNumOfPlayers(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getAwayTeam().getPlayerList().get(idx).getPlayerNumber());
			}

			// print labels for HOME Team Player Pictures
			LiveTextWriter.println("\n");
			LiveTextWriter.println("HOME TEAM PLAYER PICTURES");
			for (int idx=0; idx<this.outPutGame.getHomeTeam().getNumOfPlayers(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Picture="+this.outPutGame.getHomeTeam().getPlayerList().get(idx).getPlayerPicture());
			}

			// print labels for AWAY Team Player Pictures
			LiveTextWriter.println("\n");
			LiveTextWriter.println("AWAY TEAM PLAYER PICTURES");
			for (int idx=0; idx<this.outPutGame.getAwayTeam().getNumOfPlayers(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Picture="+this.outPutGame.getAwayTeam().getPlayerList().get(idx).getPlayerPicture());
			}

			// print labels for HOME Team Players on court Names
			LiveTextWriter.println("\n");
			LiveTextWriter.println("HOME TEAM PLAYERS ON COURT NAMES");
			for (int idx=0; idx<this.outPutGame.getHomeTeamPlayersOnCourt().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getHomeTeamPlayersOnCourt().get(idx).getPlayerName());
			}

			// print labels for Away Team Players on court Names
			LiveTextWriter.println("\n");
			LiveTextWriter.println("AWAY TEAM PLAYERS ON COURT NAMES");
			for (int idx=0; idx<this.outPutGame.getAwayTeamPlayersOnCourt().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getAwayTeamPlayersOnCourt().get(idx).getPlayerName());
			}

			// print labels for HOME Team Players on court Numbers
			LiveTextWriter.println("\n");
			LiveTextWriter.println("HOME TEAM PLAYERS ON COURT NUMBERS");
			for (int idx=0; idx<this.outPutGame.getHomeTeamPlayersOnCourt().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getHomeTeamPlayersOnCourt().get(idx).getPlayerNumber());
			}

			// print labels for Away Team Players on court Numbers
			LiveTextWriter.println("\n");
			LiveTextWriter.println("AWAY TEAM PLAYERS ON COURT NUMBERS");
			for (int idx=0; idx<this.outPutGame.getAwayTeamPlayersOnCourt().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getAwayTeamPlayersOnCourt().get(idx).getPlayerNumber());
			}

			// print labels for HOME Team Subs Names
			LiveTextWriter.println("\n");
			LiveTextWriter.println("HOME TEAM PLAYERS ON BENCH NAMES");
			for (int idx=0; idx<this.outPutGame.getHomePlayersSubs().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getHomePlayersSubs().get(idx).getPlayerName());
			}

			// print labels for Away Team subs Names
			LiveTextWriter.println("\n");
			LiveTextWriter.println("AWAY TEAM PLAYERS ON BENCH NAMES");
			for (int idx=0; idx<this.outPutGame.getAwayPlayersSubs().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Name="+this.outPutGame.getAwayPlayersSubs().get(idx).getPlayerName());
			}


			// print labels for HOME Team Subs NUmbers
			LiveTextWriter.println("\n");
			LiveTextWriter.println("HOME TEAM PLAYERS ON BENCH NUMBERS");
			for (int idx=0; idx<this.outPutGame.getHomePlayersSubs().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getHomePlayersSubs().get(idx).getPlayerNumber());
			}

			// print labels for Away Team Players on court Names
			LiveTextWriter.println("\n");
			LiveTextWriter.println("AWAY TEAM PLAYERS ON BENCH NUMBERS");
			for (int idx=0; idx<this.outPutGame.getAwayPlayersSubs().size(); idx++)
			{
				LiveTextWriter.println("Player "+(idx+1)+" Number="+this.outPutGame.getAwayPlayersSubs().get(idx).getPlayerNumber());
			}

			// Print Labels for the server
			LiveTextWriter.println("\n");
			LiveTextWriter.println("CURRENT SERVER INFORMATION");
			LiveTextWriter.println("CURRENT SERVER NAME="+this.outPutGame.getServer().getPlayerName());
			LiveTextWriter.println("CURRENT SERVER NUMBER="+this.outPutGame.getServer().getPlayerNumber());
			LiveTextWriter.println("CURRENT SERVER PICTURE="+this.outPutGame.getServer().getPlayerPicture());

			//Print Labels for the InGame Data
			LiveTextWriter.println("\n");
			LiveTextWriter.println("IN GAME DATA");
			LiveTextWriter.println("Home Team Score="+this.outPutGame.getHomeTeamScore());
			LiveTextWriter.println("Away Team Score="+this.outPutGame.getAwayTeamScore());
			LiveTextWriter.println("Home Team Sets Won="+this.outPutGame.getHomeTeamSets());
			LiveTextWriter.println("Away Team Sets Won="+this.outPutGame.getAwayTeamSets());
			LiveTextWriter.println("Home Team Substitutions Taken="+this.outPutGame.getHomeTeamSubsUsed());
			LiveTextWriter.println("Away Team Substitutions Taken="+this.outPutGame.getAwayTeamSubsUsed());
			LiveTextWriter.println("Home Team Substitutions Left="+(18-this.outPutGame.getHomeTeamSubsUsed()));
			LiveTextWriter.println("Away Team Substitutions Left="+(18-this.outPutGame.getAwayTeamSubsUsed()));
			LiveTextWriter.println("Home Team Time Outs Taken="+this.outPutGame.getHomeTeamTimeoutsUsed());
			LiveTextWriter.println("Away Team Time Outs Taken="+this.outPutGame.getAwayTeamTimeoutsUsed());
			LiveTextWriter.println("Home Team Sets Won="+this.outPutGame.getHomeTeamSets());
			LiveTextWriter.println("Away Team Sets Won="+this.outPutGame.getAwayTeamSets());

			//Print Labels for score in each set

			LiveTextWriter.println("\n");
			LiveTextWriter.println("HOME TEAM INDIVIDUAL SET SCORES INFORMATION");

			for (int idx=0; idx<this.getOutputGame().getHomeTeamSetPoints().size(); idx++)
			{
				LiveTextWriter.println("Home Team Set " +(idx+1)+ " Score="+this.outPutGame.getHomeTeamSetPoints().get(idx));
			}



			LiveTextWriter.println("\n");
			LiveTextWriter.println("AWAY TEAM INDIVIDUAL SET SCORES INFORMATION");

			for (int idx=0; idx<this.getOutputGame().getAwayTeamSetPoints().size(); idx++)
			{
				LiveTextWriter.println("Away Team Set " +(idx+1)+ " Score="+this.outPutGame.getAwayTeamSetPoints().get(idx));
			}			


			//Print Labels for Selected Player
			LiveTextWriter.println("\n");
			LiveTextWriter.println("SELECTED PLAYER INFORMATION");
			LiveTextWriter.println("Player Name= "+this.outPutGame.getSelectedPlayer().getPlayerName());
			LiveTextWriter.println("Player Number= "+this.outPutGame.getSelectedPlayer().getPlayerNumber());			
			LiveTextWriter.println("Player Image= "+this.outPutGame.getSelectedPlayer().getPlayerPicture());
			// close the file


			LiveTextWriter.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());

		}
	}


	/**
	 * update the data file
	 */
	public void updateOutput()
	{
		// delete the game file
		try {
			Files.delete(this.getGameFile().toPath());
		} catch (IOException e) {

			e.printStackTrace();
		}

		// Delete the live text files
		try {
			Files.delete(this.getLiveTextFile().toPath());
		} catch (IOException e) {

			e.printStackTrace();
		}

		// create the game file again
		this.createGameFile(this.getOutputGame().getHomeTeam(), this.getOutputGame().getAwayTeam());

	}

}
