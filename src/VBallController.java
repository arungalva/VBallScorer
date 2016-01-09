/***********************************************************************
 * @author: Edisson Flores
   @version: 1.0
   Last Edited on June 12th, 2014
 Purpose: Controller class for the processes of making new teams and players.
 			Sometimes used for the main score GUI to control points.
 * 
 ***********************************************************************/

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VBallController {

	private NewGame activeGame;
	
	private Team tempTeam,tempHomeTeam,tempAwayTeam;// Temporary Team variables for new team process and other processes.
	private int tempPointPerSet, tempSetsInGame;
	private ArrayList<Player> tempInCourtHome,tempInCourtAway;
	
	private PromptGUI prompts = new PromptGUI();// Prompt GUIs
	
	public VBallController(){
		
	}
	
	public VBallController(NewGame game){
		
		this.activeGame = game;
		
	}
	
	/**
	 * Prompts the user if they want to close the program before closing.
	 */
	public static void closeProgramAsk(){
		Object[] options = {"Yes",
        "No"};
		
		int c = JOptionPane.showOptionDialog(null,
			    "Are you sure you want to close?",
			    "Comfirm Close",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
			    options[0]);
		
		if (c == JOptionPane.YES_OPTION){
			closeProgram();
		}
	}
	
	/**
	 * Prompts the user for new game information.
	 */
	@SuppressWarnings("unchecked")
	public void newGame(){

		Object[] getData = prompts.newGameGUI();
		
		try{
			int errorCatch = (Integer) getData[0];
			
			if (errorCatch==-1 || errorCatch==0){
				int userOption = prompts.questionPromptYESNO("Are you sure?", "Are you sure you want to go back?");
				if (userOption == 1){
					newGame();
				}
			}else if (errorCatch==-2){
				prompts.errorPrompt("Error", "Both teams are the same.");
				newGame();
			}else if (errorCatch==-3){
				prompts.errorPrompt("Error", "Not enough players on court.");
				newGame();
			}else if (errorCatch==-4){
				prompts.errorPrompt("Error", "Too many players on court.");
				newGame();
			}else if (errorCatch==-5){
				prompts.errorPrompt("Error", "Please select teams for both side.");
				newGame();
			}
		}catch(Exception ex){
			tempHomeTeam = (Team) getData[0];
			tempAwayTeam = (Team) getData[1];
			
			tempInCourtHome = new ArrayList<Player>((ArrayList<Player>) getData[2]);
			tempInCourtAway = new ArrayList<Player>((ArrayList<Player>) getData[3]);
			
			newGameScoring();
		}
		
	}
	
	private void newGameScoring(){
		Object[] getData = prompts.getNewGameScoring();
		
		try{
			int errorCatch = (Integer)getData[0];
			
			if (errorCatch == 0 || errorCatch == -1){
				int cancelPrompt = prompts.questionPromptYESNO("Are you sure?", "Are you sure you want to go back?");
				if (cancelPrompt == 1){
					newGameScoring();
				}else{
					newGame();
				}
			}
		}catch(Exception ex){
			try{
				tempPointPerSet = Integer.parseInt((String)getData[0]);
				tempSetsInGame = Integer.parseInt((String)getData[1]);
				
				NewGame newGame = new NewGame(tempHomeTeam,tempAwayTeam,tempPointPerSet,tempSetsInGame);
				
				for (int i = 0; i<tempInCourtHome.size(); i++){
					for (int e = 0; e<newGame.getHomeTeam().getPlayerList().size(); e++){
						if (newGame.getHomeTeam().getPlayerList().get(e).getPlayerName().equals(tempInCourtHome.get(i).getPlayerName())){
							newGame.setHomePlayerOnCourt(newGame.getHomeTeam().getPlayerList().get(e).getPlayerNumber());
						}
					}
				}
				
				for (int i = 0; i<tempInCourtAway.size(); i++){
					for (int e = 0; e<newGame.getAwayTeam().getPlayerList().size(); e++){
						if (newGame.getAwayTeam().getPlayerList().get(e).getPlayerName().equals(tempInCourtAway.get(i).getPlayerName())){
							newGame.setAwayPlayerOnCourt(newGame.getAwayTeam().getPlayerList().get(e).getPlayerNumber());
						}
					}
				}
				
				startNewGame(newGame);
				
			}catch(Exception exp){
				prompts.errorPrompt("Error", "Please input appropriate values.");
				newGameScoring();
			}
		}
	}
	
	private void startNewGame(NewGame game){
     	VBallGUI mainScreen = new VBallGUI(game);
		
     	VBallBoot.activeGameGUI = new JFrame("LiveText Volleyball Scoring");
     	VBallBoot.activeGameGUI.setSize(1040,600);
     	VBallBoot.activeGameGUI.setLocationRelativeTo(null);
     	VBallBoot.activeGameGUI.setJMenuBar(mainScreen.theMenuBar());
     	VBallBoot.activeGameGUI.setContentPane(mainScreen);
     	VBallBoot.activeGameGUI.setResizable(false);
     	VBallBoot.activeGameGUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     	VBallBoot.activeGameGUI.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				VBallController.closeProgramAsk();
			}
		});
     	VBallBoot.activeGameGUI.setVisible(true);
		
		VBallBoot.bootUp.dispose();
		
		// Reset everything just for interference purposes
		tempHomeTeam = null;
		tempAwayTeam = null;
		tempPointPerSet = 0;
		tempSetsInGame = 0;
		tempInCourtHome.clear();
		tempInCourtAway.clear();
	}
	
	/**
	 * Prompts the user for new team information.
	 */
	public void newTeam(){
		Object[] getNewTeamInfo = prompts.newTeamGUI();
		
		try{
			int errorCatch = (Integer) getNewTeamInfo[0];
			
			if (errorCatch == -1 || errorCatch == 0){
				int cancelPrompt = prompts.questionPromptYESNO("Are you sure?", "Are you sure you want to cancel?");
				if (cancelPrompt == 1){
					newTeam();
				}
			}else if(errorCatch == -2){
				prompts.errorPrompt("Invalid Input", "Please input the correct infomation.");
				newTeam();
			}
		}catch(Exception ex){
			if (tempTeam == null){
				tempTeam = (Team) getNewTeamInfo[0];
				System.out.println(tempTeam.getTeamName());
				newPlayerProcess();
			}else{
				Team newInfoOfTeam = (Team) getNewTeamInfo[0];
				tempTeam.setTeamName(newInfoOfTeam.getTeamName());
				newPlayerProcess();
			}
		}
		
	}
	
	/**
	 * Prompts the user for new player information.
	 */
	private void newPlayerProcess(){
		Object getNewPlayerInfo = prompts.newPlayerGUI(tempTeam);
		
		int errorCatch = (Integer) getNewPlayerInfo;

		System.out.println(errorCatch);

		if (errorCatch == -1){
			int cancelPrompt = prompts.questionPromptYESNO("Are you sure?", "Are you sure you want to cancel?");
			if (cancelPrompt == 1){
				newPlayerProcess();
			}
		}else if (errorCatch == 0){
			int cancelPrompt = prompts.questionPromptYESNO("Are you sure?", "Are you sure you want to go back?");
			if (cancelPrompt == 0){
				newTeam();
			}
		}else if(errorCatch == -2){
			prompts.errorPrompt("Invalid Input", "Please input the correct infomation.");
			newPlayerProcess();
		}else if(errorCatch == 2){
			int userOption = prompts.questionPromptYESNO("Are you sure?", "Are you sure you want to stop making players?");
			if (userOption == 0){
				newTeamFinal();
			}else{
				newPlayerProcess();
			}
		}else if(errorCatch == 3){
			newPlayerProcess();
		}
	}
	
	/**
	 * Prompts the user for the final step of the Team Making process
	 */
	private void newTeamFinal(){
		int finalTeamCheck = prompts.newTeamFinal(tempTeam);
		
		if (finalTeamCheck == 0){
			if (tempTeam.getPlayerList().size()<6) {
				prompts.errorPrompt("Error", "Please make a minimum of 6 players.");
				newTeamFinal();
			}else{
				TeamRegistry.addTeam(tempTeam);
				tempTeam = null;
			}
		}else if (finalTeamCheck == 1){
			newPlayerProcess();
		}
	}
	
	public void editTeamInGame(){
		int getUserChoice = prompts.editTeamGUI(this.activeGame.getHomeTeam(), this.activeGame.getAwayTeam());
		
		if (getUserChoice == 0){
			prompts.teamEditor(this.activeGame.getHomeTeam());
		}else if (getUserChoice == 2){
			prompts.teamEditor(this.activeGame.getAwayTeam());
		}
	}
	
	/**
	 * Adds a point to the home team.
	 */
	public void addScoreHome(){
		if (activeGame == null){return;}// Stops the method if there is no game active

		this.activeGame.setHomeTeamScore(this.activeGame.getHomeTeamScore()+1);

	}
	
	/**
	 * Adds a point to the away team.
	 */
	public void addScoreAway(){
		if (activeGame == null){return;}// Stops the method if there is no game active

		this.activeGame.setAwayTeamScore(this.activeGame.getAwayTeamScore()+1);
	}
	
	/**
	 * Adds a winning set to the home team.
	 */
	public void addSetHome(){
		if (activeGame == null){return;}// Stops the method if there is no game active
		
		this.activeGame.setHomeTeamSets(this.activeGame.getHomeTeamSets()+1);
	}
	
	/**
	 * Adds a winning set to the away team.
	 */
	public void addSetAway(){
		if (activeGame == null){return;}// Stops the method if there is no game active
		
		this.activeGame.setAwayTeamSets(this.activeGame.getAwayTeamSets()+1);
	}
	
	/**
	 * Removes a point to the home team.
	 */
	public void removeScoreHome(){
		if (activeGame == null){return;}// Stops the method if there is no game active

		this.activeGame.setHomeTeamScore(this.activeGame.getHomeTeamScore()-1);
	}
	
	/**
	 * Removes a point to the away team.
	 */
	public void removeScoreAway(){
		if (activeGame == null){return;}// Stops the method if there is no game active

		this.activeGame.setAwayTeamScore(this.activeGame.getAwayTeamScore()-1);
	}
	
	/**
	 * Removes a winning set to the home team.
	 */
	public void removeSetHome(){
		if (activeGame == null){return;}// Stops the method if there is no game active
		
		this.activeGame.setHomeTeamSets(this.activeGame.getHomeTeamSets()-1);
	}
	
	/**
	 * Removes a winning set to the away team.
	 */
	public void removeSetAway(){
		if (activeGame == null){return;}// Stops the method if there is no game active
		
		this.activeGame.setAwayTeamSets(this.activeGame.getAwayTeamSets()-1);
	}
	
	/**
	 * Closes the program.
	 */
	public static void closeProgram(){
		System.exit(0);
	}
	
}
