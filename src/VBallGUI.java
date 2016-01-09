/***********************************************************************
 * @author: Edisson Flores
 * @version: 1.0
 * Last Edited on June 14th, 2014
 * Purpose: Creating the main score board GUI for user.
 * 
 ***********************************************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class VBallGUI extends JPanel {

	//////////// Editable variables //////////////////////////////////////////////////////////////////////////////////////////
	
	// Selects the font for the main score board.
	final private String SCORE_FONT = 		"Arial";
	
	// Selects the font size for the main score board.
	final private int SCORE_FONT_SIZE = 	30;
	
	// Selects the font style for the main score board.
	final private boolean SCORE_FONT_BOLD = true;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////// DO NOT EDIT PAST THIS LINE! /////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private NewGame activeGame;					// The active game
	private LiveTextOutPut sourceUpdate;		// Update resource
	private VBallController controller;			// Controller resource
	private PromptGUI prompts = new PromptGUI();// Prompt resource

	private JLabel[] homeOnCourt = new JLabel[6], awayOnCourt = new JLabel[6];
	private ArrayList<JLabel> homeOffCourt = new ArrayList<JLabel>(),
			awayOffCourt = new ArrayList<JLabel>();
	private ArrayList<JRadioButton> awayPlayersServings = new ArrayList<JRadioButton>(), 
			homePlayersServings = new ArrayList<JRadioButton>();
	private ArrayList<Player> awayPlayersIndex = new ArrayList<Player>(), 
			homePlayersIndex = new ArrayList<Player>();

	////// GUI Components ///////
	
	// JButtons
	private JButton button, buttonAwayAddSet, buttonHomeAddSet, buttonAwayAddScore, 
			buttonHomeAddScore, buttonAwayRemoveSet, buttonHomeRemoveSet, 
			buttonAwayRemoveScore, buttonHomeRemoveScore;
	// JRadioButtons
	private JRadioButton rButton;
	
	// JSeparators
	private JSeparator separator;
	// JLabels
	private JLabel team1ScoreL, team1SetsL, team2ScoreL, team2SetsL, team1NameL, 
			team2NameL, label;
	// JPanels
	private JPanel panelScore, panelScoreFULL, panel, panel2, panelBottom, panelTeamInfo, 
	teamHomePlayerList = new JPanel(), 
	teamAwayPlayerList = new JPanel(), 
	teamCourtHome = new JPanel(), 
	teamCourtAway= new JPanel();
	// JTextFields
	private JTextField subInHome, subOutHome, subInAway, subOutAway;
	// JScrollPanes
	private JScrollPane scrollPane, scrollPane2;
	// ButtonGroups
	private ButtonGroup getData, serving;
	// GridBagConstraints
	private GridBagConstraints c,c2,c3,c4,c5,c6;
	// GridBagLayouts
	private GridBagLayout gridBag;
	// JMenuBars
	private JMenuBar menuBar;
	// JMenus
	private JMenu menu;
	// JMenuItems
	private JMenuItem menuItem;
	
	// Tells the code that it started for the first time
	private boolean firstTimeRun = true;
	
	/**
	 * Constructor for VBallGUI with a NewGame as parameter
	 * @param newGame
	 */
	public VBallGUI(NewGame newGame){
		super();

		// Activates the new active game.
		this.activeGame = newGame;
		
		// Initialises the substitutes for the new game.
		this.activeGame.setHomeTeamPlayersubs();
		this.activeGame.setAwayTeamPlayersubs();

		// Initialises the set points for the new game.
		this.activeGame.InitializeSetPoints();

		// Connects the controller to the current game.
		this.controller = new VBallController(activeGame);

		// Connects the update module to the current game.
		this.sourceUpdate = new LiveTextOutPut(activeGame);
		
		// Generates the GUI
		this.viewLayout();

		// Generates the new game file.
		this.sourceUpdate.createGameFile(activeGame.getHomeTeam(), activeGame.getAwayTeam());
		
		// Sets the first time run boolean to false
		firstTimeRun = false;

	}

	/**
	 * Generates the interface.
	 */
	private void viewLayout() {

		this.setLayout(new BorderLayout()); // Sets frame layout to BorderLayout

		////////////////////////////////////////////////////////////////////////// Player Names

		this.panel = new JPanel(); // Creates Score Board panel

		// Shows the home team's name
		this.team1NameL = new JLabel(activeGame.getHomeTeam().getTeamName());
		this.team1NameL.setFont(new Font(SCORE_FONT,(SCORE_FONT_BOLD)?Font.BOLD:Font.PLAIN,SCORE_FONT_SIZE));
		this.panel.add(this.team1NameL);

		// Adds a separator between the two names
		this.separator = new JSeparator();
		this.separator.setPreferredSize(new Dimension(100,0));
		this.panel.add(this.separator);

		// Shows the away team's name
		this.team2NameL = new JLabel(activeGame.getAwayTeam().getTeamName());
		this.team2NameL.setFont(new Font(SCORE_FONT,(SCORE_FONT_BOLD)?Font.BOLD:Font.PLAIN,SCORE_FONT_SIZE));
		this.panel.add(this.team2NameL);

		this.add(panel,BorderLayout.NORTH);// Adds the above to the frame

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////// Score board //////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// Prepares JPanel for the score board
		this.panelScoreFULL = new JPanel();

		/////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////// Left Side Buttons ///
		/////////////////////////////////////////////////////////////////////
		
		// Creates the "Time Out" button
		this.button = new JButton("Time Out");
		this.button.setPreferredSize(new Dimension(100,25));
		
		/////////////////////////////////////////////////////////////////////////////
		// Action Listener Manual /////////// ALL BUTTONS USE THE SAME METHOD ////////
		///////////////////////////////////////////////////////////////////////////////
		                                                                            ///
		// Every variable method must be finalised to be used in a button.          ///
		final NewGame localGameHome = activeGame;                                   ///
                                                                                    ///
		// Attaches the listener to the button                                      ///
		this.button.addActionListener(                                              ///
                                                                                    ///
				// Creates new listener                                             ///
				new ActionListener(){                                               ///
                                                                                    ///
					// Creates a actionPerformed method for the listener            ///
					public void actionPerformed(ActionEvent evt){                   ///
						                                                            ///
						// Once the button is clicked on. Anything in the method    ///
						// will be activated.                                       ///
						localGameHome.homeTeamTimeout();                            ///
						prompts.timeOutTimerPrompt(localGameHome.getHomeTeam());    ///
					}                                                               ///
				});                                                                 ///
		                                                                            ///
		///////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////
		
		// Adds the above to "panelScoreFull" panel
		this.panelScoreFULL.add(this.button,BorderLayout.WEST);

		// Creates panel for the remove buttons
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		
		// Creates the "Remove Point" button
		this.buttonHomeRemoveScore = new JButton("Remove Point");
		this.buttonHomeRemoveScore.setPreferredSize(new Dimension(125,50));
		this.buttonHomeRemoveScore.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				controller.removeScoreHome();
				updateScores();
				sourceUpdate.updateOutput();
			}
		});
		this.panel.add(this.buttonHomeRemoveScore,BorderLayout.NORTH);

		// Adds separator between the 2 remove buttons
		this.separator = new JSeparator();
		this.separator.setPreferredSize(new Dimension(0,25));
		this.panel.add(this.separator,BorderLayout.CENTER);

		// Creates the "Remove Set" button
		this.buttonHomeRemoveSet = new JButton("Remove Set");
		this.buttonHomeRemoveSet.setPreferredSize(new Dimension(125,50));
		this.buttonHomeRemoveSet.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				activeGame.setHomeTeamSets(activeGame.getHomeTeamSets()-1);
				updateScores();
				sourceUpdate.updateOutput();
			}
		});
		this.panel.add(this.buttonHomeRemoveSet,BorderLayout.SOUTH);

		// Adds the above to "panelScoreFull" panel
		this.panelScoreFULL.add(panel,BorderLayout.WEST);

		// Creates panel for the add buttons
		this.panel = new JPanel();

		// Creates the "Add Point" button
		this.panel.setLayout(new BorderLayout());
		this.buttonHomeAddScore = new JButton("Add Point");
		this.buttonHomeAddScore.setPreferredSize(new Dimension(125,50));
		this.buttonHomeAddScore.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				controller.addScoreHome();
				updateScores();
				sourceUpdate.updateOutput();
			}
		});
		this.panel.add(this.buttonHomeAddScore,BorderLayout.NORTH);

		// Adds separator between the 2 add buttons
		this.separator = new JSeparator();
		this.separator.setPreferredSize(new Dimension(0,25));
		this.panel.add(this.separator,BorderLayout.CENTER);

		// Creates the "Add Set" button
		this.buttonHomeAddSet = new JButton("Add Set");
		this.buttonHomeAddSet.setPreferredSize(new Dimension(125,50));
		this.buttonHomeAddSet.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				controller.addSetHome();
				updateScores();
				sourceUpdate.updateOutput();
			}
		});
		this.panel.add(this.buttonHomeAddSet,BorderLayout.SOUTH);

		// Adds the above to the "panelScoreFull" panel
		this.panelScoreFULL.add(this.panel,BorderLayout.WEST);

		/////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////// Center Score Board ///
		/////////////////////////////////////////////////////////////////////

		// Creates panel for the whole score board
		this.panelScore = new JPanel();
		this.panelScore.setLayout(new BorderLayout());

		// Creates panel for the Home team's scores and sets
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());

		// Creates panel for the Home team's score
		this.panel2 = new JPanel();
		this.panel2.setBorder(BorderFactory.createTitledBorder("Score"));// Generates a title for the score

		String temp; // Used to change single digit numbers to have a zero before them

		// Calculation to see if the digit is lower than 10
		if (activeGame.getHomeTeamScore()<10){
			temp = "0"+Integer.toString(this.activeGame.getHomeTeamScore());
		}else{
			temp = Integer.toString(this.activeGame.getHomeTeamScore());
		}

		// Creates the Home team's score
		this.team1ScoreL = new JLabel(temp);
		this.team1ScoreL.setFont(new Font(SCORE_FONT,(SCORE_FONT_BOLD)?Font.BOLD:Font.PLAIN,SCORE_FONT_SIZE));
		this.panel2.add(this.team1ScoreL);

		// Adds the score
		this.panel.add(this.panel2,BorderLayout.NORTH);

		// Creates panel for the Home team's sets
		this.panel2 = new JPanel();
		this.panel2.setBorder(BorderFactory.createTitledBorder("Sets"));// Generates a title for the sets

		// Calculation to see if the digit is lower than 10
		if (activeGame.getHomeTeamSets()<10){
			temp = "0"+Integer.toString(this.activeGame.getHomeTeamSets());
		}else{
			temp = Integer.toString(this.activeGame.getHomeTeamSets());
		}

		// Creates the Home team's sets
		this.team1SetsL = new JLabel(temp);
		this.team1SetsL.setFont(new Font(SCORE_FONT,(SCORE_FONT_BOLD)?Font.BOLD:Font.PLAIN,SCORE_FONT_SIZE));
		this.panel2.add(this.team1SetsL);

		// Adds the sets
		this.panel.add(this.panel2,BorderLayout.SOUTH);

		// Adds the both the score and sets to the "panelScore"
		this.panelScore.add(this.panel,BorderLayout.WEST);

		// Creates panel for the Away team's scores and sets
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());

		// Creates panel for the Away team's score
		this.panel2 = new JPanel();
		this.panel2.setBorder(BorderFactory.createTitledBorder("Score"));// Generates a title for the score

		// Calculation to see if the digit is lower than 10
		if (activeGame.getAwayTeamScore()<10){
			temp = "0"+Integer.toString(this.activeGame.getAwayTeamScore());
		}else{
			temp = Integer.toString(this.activeGame.getAwayTeamScore());
		}

		// Creates the Away team's score
		this.team2ScoreL = new JLabel(temp);
		this.team2ScoreL.setFont(new Font(SCORE_FONT,(SCORE_FONT_BOLD)?Font.BOLD:Font.PLAIN,SCORE_FONT_SIZE));
		this.panel2.add(team2ScoreL);

		// Adds the score 
		this.panel.add(this.panel2,BorderLayout.NORTH);

		// Creates the "VS" sign on the center of the scores.
		this.label = new JLabel("VS");
		this.label.setFont(new Font(SCORE_FONT,(SCORE_FONT_BOLD)?Font.BOLD:Font.PLAIN,SCORE_FONT_SIZE-10));

		// Adds the "VS" sign to the "panelScore" panel
		this.panelScore.add(this.label,BorderLayout.CENTER);

		// Creates panel for the Away team's sets
		this.panel2 = new JPanel();
		this.panel2.setBorder(BorderFactory.createTitledBorder("Sets"));// Generates a title for the sets

		// Calculation to see if the digit is lower than 10
		if (activeGame.getAwayTeamSets()<10){
			temp = "0"+Integer.toString(activeGame.getAwayTeamSets());
		}else{
			temp = Integer.toString(activeGame.getAwayTeamSets());
		}

		// Creates the Away team's sets
		this.team2SetsL = new JLabel(temp);
		this.team2SetsL.setFont(new Font(SCORE_FONT,(SCORE_FONT_BOLD)?Font.BOLD:Font.PLAIN,SCORE_FONT_SIZE));
		this.panel2.add(team2SetsL);

		// Adds the sets
		this.panel.add(panel2,BorderLayout.SOUTH);

		// Adds the both the score and sets to the "panelScore"
		this.panelScore.add(this.panel,BorderLayout.EAST);

		// Adds the full score board to the center of "panelScoreFULL"
		this.panelScoreFULL.add(this.panelScore, BorderLayout.CENTER);

		/////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////// Right Side Buttons ///
		/////////////////////////////////////////////////////////////////////

		// Creates panel for the add buttons
		this.panel = new JPanel();

		// Creates the "Add Point" button
		this.panel.setLayout(new BorderLayout());
		this.buttonAwayAddScore = new JButton("Add Point");
		this.buttonAwayAddScore.setPreferredSize(new Dimension(125,50));
		this.buttonAwayAddScore.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				controller.addScoreAway();
				updateScores();
				sourceUpdate.updateOutput();
			}
		});
		this.panel.add(this.buttonAwayAddScore,BorderLayout.NORTH);

		// Adds separator between the 2 add buttons
		this.separator = new JSeparator();
		this.separator.setPreferredSize(new Dimension(0,25));
		this.panel.add(this.separator,BorderLayout.CENTER);

		// Creates the "Add Set" button
		this.buttonAwayAddSet = new JButton("Add Set");
		this.buttonAwayAddSet.setPreferredSize(new Dimension(125,50));
		this.buttonAwayAddSet.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				controller.addSetAway();
				updateScores();
				sourceUpdate.updateOutput();
			}
		});
		this.panel.add(this.buttonAwayAddSet,BorderLayout.SOUTH);

		// Adds the above to the "panelScoreFull" panel
		this.panelScoreFULL.add(this.panel,BorderLayout.EAST);

		this.panel = new JPanel();

		// Creates the "Remove Point" button
		this.panel.setLayout(new BorderLayout());
		this.buttonAwayRemoveScore = new JButton("Remove Point");
		this.buttonAwayRemoveScore.setPreferredSize(new Dimension(125,50));
		this.buttonAwayRemoveScore.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				controller.removeScoreAway();
				updateScores();
				sourceUpdate.updateOutput();
			}
		});
		this.panel.add(this.buttonAwayRemoveScore,BorderLayout.NORTH);

		// Adds separator between the 2 add buttons
		this.separator = new JSeparator();
		this.separator.setPreferredSize(new Dimension(0,25));
		this.panel.add(this.separator,BorderLayout.CENTER);

		// Creates the "Remove Set" button
		this.buttonAwayRemoveSet = new JButton("Remove Set");
		this.buttonAwayRemoveSet.setPreferredSize(new Dimension(125,50));
		this.buttonAwayRemoveSet.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				controller.removeSetAway();
				updateScores();
				sourceUpdate.updateOutput();
			}
		});
		this.panel.add(this.buttonAwayRemoveSet,BorderLayout.SOUTH);

		// Adds the above to the "panelScoreFull" panel
		this.panelScoreFULL.add(this.panel,BorderLayout.EAST);

		// Creates the "Time Out" button
		this.button = new JButton("Time Out");
		this.button.setPreferredSize(new Dimension(100,25));
		final NewGame localGameAway = activeGame;
		this.button.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt){
				localGameAway.awayTeamTimeout();
				prompts.timeOutTimerPrompt(localGameAway.getAwayTeam());
			}
		});
		this.panelScoreFULL.add(this.button,BorderLayout.EAST);

		// Adds the "panelScoreFULL" to the frame
		this.add(this.panelScoreFULL,BorderLayout.CENTER);
		
		//////////////////////////////////////////////////////////////////////////////////////////
		this.panelBottom = new JPanel();                 ////// Preparing a panel for the bottom //
		this.panelBottom.setLayout(new GridBagLayout()); ////// half of the program.             //
		//////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////// Team Lists ///////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////// Home & Away Team Lists ///
		/////////////////////////////////////////////////////////////////////
		
		// The update code is identical to the creation of it. To save the hassle
		// of commenting both code and maintaining them, I've decided to combine the
		// two together.
		fullUpdateTeamChart();


		this.c6 = new GridBagConstraints();

		// Creates a scroll pane for the home team player list
		this.scrollPane = new JScrollPane(this.teamHomePlayerList);
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setPreferredSize(new Dimension(286,0));

		// Adds the above scroll pane
		this.c6.fill = GridBagConstraints.BOTH;
		this.c6.ipadx = 20;
		this.c6.ipady = 20;
		this.c6.gridx = 0;
		this.c6.gridy = 0;
		this.panelBottom.add(this.scrollPane,c6);

		// Creates a scroll pane for the away team player list
		this.scrollPane2 = new JScrollPane(this.teamAwayPlayerList);
		this.scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPane2.setPreferredSize(new Dimension(286,0));

		// Adds the above scroll pane
		this.c6.fill = GridBagConstraints.BOTH;
		this.c6.ipadx = 20;
		this.c6.ipady = 20;
		this.c6.gridx = 1;
		this.c6.gridy = 0;
		this.panelBottom.add(scrollPane2,c6);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////// Team Information and Substitutions ///////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////// Home Team Setup ///
		/////////////////////////////////////////////////////////////////////

		this.panelTeamInfo = new JPanel();
		this.panelTeamInfo.setLayout(new GridLayout(2,2,10,10));

		/////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////// Home Team Info InCourt ///
		/////////////////////////////////////////////////////////////////////
		
		this.gridBag = new GridBagLayout();
		this.teamCourtHome.setLayout(this.gridBag);

		this.c3 = new GridBagConstraints();

		for (int i = 0; i < 6; i++){
			this.label = new JLabel(Integer.toString(this.activeGame.getHomeTeamPlayersOnCourt().get(i).getPlayerNumber()), JLabel.CENTER);
			this.label.setFont(new Font(SCORE_FONT,Font.BOLD,15));
			this.label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			this.label.setPreferredSize(new Dimension(30,20));
			this.label.setToolTipText(this.activeGame.getHomeTeamPlayersOnCourt().get(i).getPlayerName());
			this.c3.fill = GridBagConstraints.HORIZONTAL;
			this.c3.gridx = i;
			this.c3.gridy = 3;
			this.gridBag.setConstraints(this.label, this.c3);
			this.homeOnCourt[i] = this.label;
			this.teamCourtHome.add(this.label);
		}

		// Creates a gap in between the the above component and the one below
		this.c3.fill = GridBagConstraints.HORIZONTAL;
		this.c3.gridx = 0;
		this.c3.gridy = 1;
		this.c3.gridwidth = 6;
		this.teamCourtHome.add(Box.createVerticalStrut(5),this.c3);

		this.label = new JLabel(activeGame.getHomeTeam().getTeamName(), JLabel.CENTER);
		this.label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.label.setFont(new Font(SCORE_FONT,Font.BOLD,20));
		this.c3.fill = GridBagConstraints.HORIZONTAL;
		this.c3.gridx = 0;
		this.c3.gridy = 0;
		this.c3.gridwidth = 6;
		this.gridBag.setConstraints(this.label, this.c3);
		this.teamCourtHome.add(this.label);

		this.label = new JLabel("On Court");
		this.label.setFont(new Font(SCORE_FONT,Font.PLAIN,14));
		this.c3.fill = GridBagConstraints.HORIZONTAL;
		this.c3.gridx = 0;
		this.c3.gridy = 2;
		this.c3.gridwidth = 6;
		this.gridBag.setConstraints(this.label, this.c3);
		this.teamCourtHome.add(this.label);

		// Creates a gap in between the the above component and the one below
		this.c3.fill = GridBagConstraints.HORIZONTAL;
		this.c3.gridx = 0;
		this.c3.gridy = 5;
		this.c3.gridwidth = 6;
		this.teamCourtHome.add(Box.createVerticalStrut(5),this.c3);

		this.label = new JLabel("Substitutions");
		this.label.setFont(new Font(SCORE_FONT,Font.PLAIN,14));
		this.c3.fill = GridBagConstraints.HORIZONTAL;
		this.c3.gridx = 0;
		this.c3.gridy = 6;
		this.c3.gridwidth = 6;
		this.gridBag.setConstraints(this.label, this.c3);
		this.teamCourtHome.add(this.label);

		for (int i = 0; i < this.activeGame.getHomePlayersSubs().size(); i++){
			this.label = new JLabel(Integer.toString(this.activeGame.getHomePlayersSubs().get(i).getPlayerNumber()),JLabel.CENTER);
			this.label.setFont(new Font(SCORE_FONT,Font.BOLD,15));
			this.label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			this.label.setPreferredSize(new Dimension(30,20));
			this.label.setToolTipText(this.activeGame.getHomePlayersSubs().get(i).getPlayerName());
			this.c3.gridwidth = 1;
			this.c3.fill = GridBagConstraints.HORIZONTAL;
			this.c3.gridx = i%6;
			this.c3.gridy = 7 + (int)Math.floor(i/6);
			this.gridBag.setConstraints(this.label, this.c3);
			this.homeOffCourt.add(this.label);
			this.teamCourtHome.add(this.label);
		}

		this.panelTeamInfo.add(this.teamCourtHome);

		/////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////// Home Team Sub Centre ///
		/////////////////////////////////////////////////////////////////////

		this.panel = new JPanel();
		this.panel.setLayout(new GridBagLayout());
		this.panel.setBorder(BorderFactory.createTitledBorder("Sub Settings"));

		this.c4 = new GridBagConstraints();

		// Creates the "Sub in:"
		this.label = new JLabel("Sub in:   ");
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridx = 0;
		this.c4.gridy = 0;
		this.panel.add(this.label,this.c4);

		// Creates the sub in TextField
		this.subInHome = new JTextField(3);
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridwidth = 1;
		this.c4.gridx = 1;
		this.c4.gridy = 0;
		this.panel.add(this.subInHome,this.c4);

		// Creates a gap in between the the above component and the one below
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridwidth = 2;
		this.c4.gridx = 0;
		this.c4.gridy = 1;
		this.panel.add(Box.createVerticalStrut(5),this.c4);

		// Creates the "Sub out:"
		this.label = new JLabel("Sub out: ",JLabel.LEFT);
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridx = 0;
		this.c4.gridy = 2;
		this.panel.add(this.label,this.c4);

		// Creates the sub out TextField
		this.subOutHome = new JTextField(3);
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridwidth = 1;
		this.c4.gridx = 1;
		this.c4.gridy = 2;
		this.panel.add(this.subOutHome,this.c4);

		// Creates a gap in between the the above component and the one below
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridwidth = 2;
		this.c4.gridx = 0;
		this.c4.gridy = 3;
		this.panel.add(Box.createVerticalStrut(5),this.c4);

		// Creates a button for "Set substitute
		this.button = new JButton("Set substitute");
		this.button.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				try{
					activeGame.homeTeamAddSub(
							Integer.parseInt(subInHome.getText()), 
							Integer.parseInt(subOutHome.getText()));
					updateTeamChart();
					updateCourtInfo();
					sourceUpdate.updateOutput();
					subInHome.setText("");
					subOutHome.setText("");
				}catch(NumberFormatException | IndexOutOfBoundsException ex){
					prompts.errorPrompt("Invalid Input", "Invalid players.");
					subInHome.setText("");
					subOutHome.setText("");
				}
			}
		});
		this.c4.ipady = 40;
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.weightx = 0.5;
		this.c4.gridwidth = 2;
		this.c4.gridx = 0;
		this.c4.gridy = 4;
		this.panel.add(this.button,this.c4);

		this.panelTeamInfo.add(this.panel);

		/////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////// Away Team Info Court ///
		/////////////////////////////////////////////////////////////////////

		this.gridBag = new GridBagLayout();
		this.teamCourtAway.setLayout(gridBag);

		this.c5 = new GridBagConstraints();

		for (int i = 0; i < 6; i++){
			this.label = new JLabel(Integer.toString(this.activeGame.getAwayTeamPlayersOnCourt().get(i).getPlayerNumber()), JLabel.CENTER);
			this.label.setFont(new Font(SCORE_FONT,Font.BOLD,15));
			this.label.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.label.setPreferredSize(new Dimension(30,20));
			this.label.setToolTipText(this.activeGame.getAwayTeamPlayersOnCourt().get(i).getPlayerName());
			this.c5.fill = GridBagConstraints.HORIZONTAL;
			this.c5.gridx = i;
			this.c5.gridy = 3;
			this.gridBag.setConstraints(this.label, this.c5);
			this.awayOnCourt[i] = this.label;
			this.teamCourtAway.add(this.label);
		}

		// Creates a gap in between the the above component and the one below
		this.c5.fill = GridBagConstraints.HORIZONTAL;
		this.c5.gridx = 0;
		this.c5.gridy = 1;
		this.c5.gridwidth = 6;
		this.teamCourtAway.add(Box.createVerticalStrut(5),this.c5);

		this.label = new JLabel(activeGame.getAwayTeam().getTeamName(), JLabel.CENTER);
		this.label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.label.setFont(new Font(SCORE_FONT,Font.BOLD,20));
		this.c5.fill = GridBagConstraints.HORIZONTAL;
		this.c5.gridx = 0;
		this.c5.gridy = 0;
		this.c5.gridwidth = 6;
		this.gridBag.setConstraints(this.label, this.c5);
		this.teamCourtAway.add(this.label);

		this.label = new JLabel("On Court");
		this.label.setFont(new Font(SCORE_FONT,Font.PLAIN,14));
		this.c5.fill = GridBagConstraints.HORIZONTAL;
		this.c5.gridx = 0;
		this.c5.gridy = 2;
		this.c5.gridwidth = 6;
		this.gridBag.setConstraints(this.label, this.c5);
		this.teamCourtAway.add(this.label);

		// Creates a gap in between the the above component and the one below
		this.c5.fill = GridBagConstraints.HORIZONTAL;
		this.c5.gridx = 0;
		this.c5.gridy = 5;
		this.c5.gridwidth = 6;
		this.teamCourtAway.add(Box.createVerticalStrut(5),this.c5);

		this.label = new JLabel("Substitutions");
		this.label.setFont(new Font(SCORE_FONT,Font.PLAIN,14));
		this.c5.fill = GridBagConstraints.HORIZONTAL;
		this.c5.gridx = 0;
		this.c5.gridy = 6;
		this.c5.gridwidth = 6;
		this.gridBag.setConstraints(this.label, this.c5);
		this.teamCourtAway.add(this.label);

		for (int i = 0; i < this.activeGame.getAwayPlayersSubs().size(); i++){
			this.label = new JLabel(Integer.toString(this.activeGame.getAwayPlayersSubs().get(i).getPlayerNumber()),JLabel.CENTER);
			this.label.setFont(new Font(SCORE_FONT,Font.BOLD,15));
			this.label.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.label.setPreferredSize(new Dimension(30,20));
			this.label.setToolTipText(this.activeGame.getAwayPlayersSubs().get(i).getPlayerName());
			this.c5.gridwidth = 1;
			this.c5.fill = GridBagConstraints.HORIZONTAL;
			this.c5.gridx = i%6;
			this.c5.gridy = 7 + (int)Math.floor(i/6);
			this.gridBag.setConstraints(this.label, this.c5);
			this.awayOffCourt.add(this.label);
			this.teamCourtAway.add(this.label);
		}

		this.panelTeamInfo.add(this.teamCourtAway);

		/////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////// Away Team Sub Centre ///
		/////////////////////////////////////////////////////////////////////

		this.panel = new JPanel();// Creates the Away Sub Centre panel
		this.panel.setLayout(new GridBagLayout());// Sets layout
		this.panel.setBorder(BorderFactory.createTitledBorder("Sub Settings")); // Generates a title for the panel

		this.c4 = new GridBagConstraints();

		// Creates the "Sub in:"
		this.label = new JLabel("Sub in:   ");
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridx = 0;
		this.c4.gridy = 0;
		this.panel.add(this.label,this.c4);

		// Creates the sub in TextField
		this.subInAway = new JTextField(3);
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridwidth = 1;
		this.c4.gridx = 1;
		this.c4.gridy = 0;
		this.panel.add(this.subInAway,this.c4);

		// Creates a gap in between the the above component and the one below
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridwidth = 2;
		this.c4.gridx = 0;
		this.c4.gridy = 1;
		this.panel.add(Box.createVerticalStrut(5),this.c4);

		// Creates the "Sub out:"
		this.label = new JLabel("Sub out: ",JLabel.LEFT);
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridx = 0;
		this.c4.gridy = 2;
		this.panel.add(this.label,this.c4);

		// Creates the sub out TextField
		this.subOutAway = new JTextField(3);
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridwidth = 1;
		this.c4.gridx = 1;
		this.c4.gridy = 2;
		this.panel.add(this.subOutAway,this.c4);

		// Creates a gap in between the the above component and the one below
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.gridwidth = 2;
		this.c4.gridx = 0;
		this.c4.gridy = 3;
		this.panel.add(Box.createVerticalStrut(10),this.c4);

		this.button = new JButton("Set substitute");
		this.button.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
			public void actionPerformed(ActionEvent evt){
				try{
					try{
						activeGame.awayTeamAddSub(
								Integer.parseInt(subInAway.getText()), 
								Integer.parseInt(subOutAway.getText()));
						updateTeamChart();
						updateCourtInfo();
						sourceUpdate.updateOutput();
						subInHome.setText("");
						subOutHome.setText("");
					}catch(NumberFormatException | IndexOutOfBoundsException ex){
						prompts.errorPrompt("Invalid Input", "Invalid players.");
						subInHome.setText("");
						subOutHome.setText("");
					}
				}catch(NumberFormatException ex){
					prompts.errorPrompt("Invalid Input", "Invalid player.");
				}

			}
		});
		this.c4.ipady = 40;
		this.c4.fill = GridBagConstraints.BOTH;
		this.c4.weightx = 0.5;
		this.c4.gridwidth = 2;
		this.c4.gridx = 0;
		this.c4.gridy = 4;
		this.panel.add(this.button,this.c4);

		this.panelTeamInfo.add(this.panel);

		this.c6.fill = GridBagConstraints.HORIZONTAL;
		this.c6.ipadx = 20;
		this.c6.gridx = 2;
		this.c6.gridy = 0;
		this.panelBottom.add(this.panelTeamInfo,this.c6);

		this.add(panelBottom,BorderLayout.SOUTH);

	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////// Update Functions /////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	/**
	 * Updates the player court information.
	 */
	private void updateCourtInfo(){

		// Updates the Home Team players on court chart to be correct.
		for (int i = 0; i < this.activeGame.getAwayTeamPlayersOnCourt().size(); i++){
			this.awayOnCourt[i].setText(Integer.toString(this.activeGame.getAwayTeamPlayersOnCourt().get(i).getPlayerNumber()));
			this.awayOnCourt[i].setToolTipText(this.activeGame.getAwayTeamPlayersOnCourt().get(i).getPlayerName());
			System.out.println(Integer.toString(this.activeGame.getAwayTeamPlayersOnCourt().get(i).getPlayerNumber()));
		}


		// Updates the Home Team substitutes chart to be correct.
		for (int i = 0; i < this.activeGame.getAwayPlayersSubs().size(); i++){
			this.awayOffCourt.get(i).setText(Integer.toString(this.activeGame.getAwayPlayersSubs().get(i).getPlayerNumber()));
			this.awayOffCourt.get(i).setToolTipText(this.activeGame.getAwayPlayersSubs().get(i).getPlayerName());
			System.out.println(Integer.toString(this.activeGame.getAwayPlayersSubs().get(i).getPlayerNumber()));
		}

		// Updates the Away Team players on court chart to be correct.
		for (int i = 0; i < 6; i++){
			this.homeOnCourt[i].setText(Integer.toString(this.activeGame.getHomeTeamPlayersOnCourt().get(i).getPlayerNumber()));
			this.homeOnCourt[i].setToolTipText(this.activeGame.getHomeTeamPlayersOnCourt().get(i).getPlayerName());
			System.out.println(Integer.toString(this.activeGame.getHomeTeamPlayersOnCourt().get(i).getPlayerNumber()));
		}


		// Updates the Away Team substitutes chart to be correct.
		for (int i = 0; i < this.activeGame.getHomePlayersSubs().size(); i++){
			this.homeOffCourt.get(i).setText(Integer.toString(this.activeGame.getHomePlayersSubs().get(i).getPlayerNumber()));
			this.homeOffCourt.get(i).setToolTipText(this.activeGame.getHomePlayersSubs().get(i).getPlayerName());
			System.out.println(Integer.toString(this.activeGame.getHomePlayersSubs().get(i).getPlayerNumber()));
		}
	}
	
	/**
	 * Fully updates the Team Player charts
	 */
	private void fullUpdateTeamChart(){

		/////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////// Home Team ///
		/////////////////////////////////////////////////////////////////////

		this.teamHomePlayerList.removeAll();
		this.homePlayersServings.clear();
		this.homePlayersIndex.clear();
		this.serving = null;
		this.getData = null;
		
		this.c = new GridBagConstraints();

		this.teamHomePlayerList.setLayout(new GridBagLayout());// Setting the layout to GridBagLayout

		this.c = new GridBagConstraints();

		this.c.anchor = GridBagConstraints.NORTHWEST;// Was suppose to move the chart to the top left corner of the frame

		this.getData = new ButtonGroup();// Button groups for
		this.serving = new ButtonGroup();// the serving and get data radio buttons.

		// Creates and places the "Get Info" label
		this.label = new JLabel("Get Info");
		this.c.fill = GridBagConstraints.HORIZONTAL;
		this.c.gridx = 0;
		this.c.gridy = 0;
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.teamHomePlayerList.add(this.label,this.c);
		
		// Creates and places the "Serving" label
		this.label = new JLabel("Serving");
		this.c.fill = GridBagConstraints.HORIZONTAL;
		this.c.gridx = 1;
		this.c.gridy = 0;
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.teamHomePlayerList.add(this.label,this.c);
		
		// Creates and places the "Player #" label
		this.label = new JLabel("Player #");
		this.c.fill = GridBagConstraints.HORIZONTAL;
		this.c.gridx = 2;
		this.c.gridy = 0;
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.teamHomePlayerList.add(this.label,c);
		
		// Creates and places the "Player Name" label
		this.label = new JLabel("Player Name");
		this.c.fill = GridBagConstraints.HORIZONTAL;
		this.c.gridx = 3;
		this.c.gridy = 0;
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.teamHomePlayerList.add(this.label,this.c);
		
		boolean pickedFirstServer = false;
		boolean pickedFirstSelect = false;
		
		// Generates the chart full of all of the player's names, number, serving status, and info toggles.
		for (int i = 0; i < this.activeGame.getHomeTeam().getPlayerList().size(); i++){
			
			// Creating the radio button for "Get Info"
			this.rButton = new JRadioButton();
			this.rButton.setHorizontalAlignment(AbstractButton.CENTER);
			this.getData.add(this.rButton);
			
			if (!pickedFirstSelect){
				if (firstTimeRun){
					activeGame.selectPlayer(activeGame.getHomeTeam().getPlayerList().get(i));
					pickedFirstSelect = true;
				}
			}
			
			final int TEMP_NUM = i;
			this.rButton.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
				public void actionPerformed(ActionEvent evt){
					activeGame.selectPlayer(activeGame.getHomeTeam().getPlayerList().get(TEMP_NUM));
					sourceUpdate.updateOutput();
				}
			});
			this.rButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			
			// Adds "Get Info"
			this.c.fill = GridBagConstraints.HORIZONTAL;
			this.c.gridx = 0;
			this.c.gridy = i + 1;
			this.teamHomePlayerList.add(this.rButton,this.c);

			// Creating the radio button for "Serving"
			this.rButton = new JRadioButton();
			this.rButton.setHorizontalAlignment(AbstractButton.CENTER);
			this.serving.add(this.rButton);
			
			this.homePlayersServings.add(this.rButton);
			this.homePlayersIndex.add(this.activeGame.getHomeTeam().getPlayerList().get(i));
			this.rButton.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
				public void actionPerformed(ActionEvent evt){
					activeGame.setHomeTeamServer(activeGame.getHomeTeam().getPlayerList().get(TEMP_NUM));
					updateTeamChart();
					sourceUpdate.updateOutput();
				}
			});
			this.rButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

			// Adds "Serving"
			this.c.fill = GridBagConstraints.HORIZONTAL;
			this.c.gridx = 1;
			this.c.gridy = i + 1;
			this.teamHomePlayerList.add(this.rButton,this.c);

			// Setups the radio buttons so that only the players in court can serve.
			if (!this.activeGame.checkPlayerCourtHome(activeGame.getHomeTeam().getPlayerList().get(i).getPlayerNumber())){
				this.rButton.setEnabled(false);
			}else if (!pickedFirstServer){
				if (firstTimeRun){
					activeGame.setHomeTeamServer(activeGame.getHomeTeam().getPlayerList().get(i));
					pickedFirstServer = true;
				}
			}

			// Creates the label for the player's number
			this.label = new JLabel(Integer.toString(this.activeGame.getHomeTeam().getPlayerList().get(i).getPlayerNumber()));
			this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

			// Adds the above label
			this.c.fill = GridBagConstraints.HORIZONTAL;
			this.c.gridx = 2;
			this.c.gridy = i + 1;
			this.teamHomePlayerList.add(this.label,this.c);

			// Creates the label for the player's name
			this.label = new JLabel(this.activeGame.getHomeTeam().getPlayerList().get(i).getPlayerName());
			this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

			// Adds the above label
			this.c.fill = GridBagConstraints.HORIZONTAL;
			this.c.gridx = 3;
			this.c.gridy = i + 1;
			this.teamHomePlayerList.add(this.label,this.c);
		}
		
		this.teamHomePlayerList.doLayout();

		/////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////// Away Team ///
		/////////////////////////////////////////////////////////////////////
		
		this.teamAwayPlayerList.removeAll();
		this.awayPlayersServings.clear();
		this.awayPlayersIndex.clear();

		this.teamAwayPlayerList.setLayout(new GridBagLayout());// Setting the layout to GridBagLayout
		
		this.c2 = new GridBagConstraints();

		this.c2.anchor = GridBagConstraints.NORTH;

		// Creates and places the "Get Info" label
		this.label = new JLabel("Get Info");
		this.c2.fill = GridBagConstraints.HORIZONTAL;
		this.c2.gridx = 0;
		this.c2.gridy = 0;
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.teamAwayPlayerList.add(label,c2);
		
		// Creates and places the "Serving" label
		this.label = new JLabel("Serving");
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.c2.fill = GridBagConstraints.HORIZONTAL;
		this.c2.gridx = 1;
		this.c2.gridy = 0;
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.teamAwayPlayerList.add(label,c2);
		
		// Creates and places the "Player #" label
		this.label = new JLabel("Player #");
		this.c2.fill = GridBagConstraints.HORIZONTAL;
		this.c2.gridx = 2;
		this.c2.gridy = 0;
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.teamAwayPlayerList.add(label,c2);
		
		// Creates and places the "Player Name" label
		this.label = new JLabel("Player Name");
		this.c2.fill = GridBagConstraints.HORIZONTAL;
		this.c2.gridx = 3;
		this.c2.gridy = 0;
		this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.teamAwayPlayerList.add(label,c2);

		// Generates the chart full of all of the player's names, number, serving status, and info toggles.
		for (int i = 0; i < activeGame.getAwayTeam().getPlayerList().size(); i++){
			
			// Creating the radio button for "Get Info"
			this.rButton = new JRadioButton();
			this.rButton.setHorizontalAlignment(AbstractButton.CENTER);
			this.getData.add(this.rButton);
			
			final int TEMP_NUM = i;
			this.rButton.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
				public void actionPerformed(ActionEvent evt){
					activeGame.selectPlayer(activeGame.getAwayTeam().getPlayerList().get(TEMP_NUM));
					sourceUpdate.updateOutput();
				}
			});
			this.rButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			
			// Adds "Get Info"
			this.c2.fill = GridBagConstraints.HORIZONTAL;
			this.c2.gridx = 0;
			this.c2.gridy = 1 + i;
			this.teamAwayPlayerList.add(this.rButton,this.c2);

			// Creating the radio button for "Serving"
			this.rButton = new JRadioButton();
			this.rButton.setHorizontalAlignment(AbstractButton.CENTER);
			this.serving.add(this.rButton);
			this.awayPlayersServings.add(this.rButton);
			this.awayPlayersIndex.add(activeGame.getAwayTeam().getPlayerList().get(i));

			this.rButton.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 178 ^
				public void actionPerformed(ActionEvent evt){
					activeGame.setAwayTeamServer(activeGame.getHomeTeam().getPlayerList().get(TEMP_NUM));
					updateTeamChart();
					sourceUpdate.updateOutput();
				}
			});
			this.rButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

			// Adds "Serving"
			this.c.fill = GridBagConstraints.HORIZONTAL;
			this.c.gridx = 1;
			this.c.gridy = i + 1;
			this.teamAwayPlayerList.add(this.rButton,this.c);

			// Setups the radio buttons so that only the players in court can serve.
			if (!(this.activeGame.checkPlayerCourtAway(activeGame.getAwayTeam().getPlayerList().get(i).getPlayerNumber()))){
				this.rButton.setEnabled(false);
			}

			// Creates the label for the player's number
			this.label = new JLabel(Integer.toString(this.activeGame.getAwayTeam().getPlayerList().get(i).getPlayerNumber()));
			this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

			// Adds the above label
			this.c2.fill = GridBagConstraints.HORIZONTAL;
			this.c2.gridx = 2;
			this.c2.gridy = 1 + i;
			this.teamAwayPlayerList.add(this.label,c2);

			// Creates the label for the player's name
			this.label = new JLabel(this.activeGame.getAwayTeam().getPlayerList().get(i).getPlayerName());
			this.label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

			// Adds the above label
			this.c2.fill = GridBagConstraints.HORIZONTAL;
			this.c2.gridx = 3;
			this.c2.gridy = 1 + i;
			this.teamAwayPlayerList.add(this.label,this.c2);
		}
		
		this.teamAwayPlayerList.doLayout();
	}

	
	/**
	 * Updates the team player charts. (RadioButtons only)
	 */
	private void updateTeamChart(){

		// Sets all home serving player radio buttons to false.
		for (int i = 0; i<this.homePlayersServings.size(); i++){
			this.homePlayersServings.get(i).setEnabled(false);
		}

		// Sets all of the player ON COURT radio buttons to true.
		for (int i = 0; i<this.homePlayersServings.size(); i++){
			if (this.activeGame.checkPlayerCourtHome(this.homePlayersIndex.get(i).getPlayerNumber())){
				this.homePlayersServings.get(i).setEnabled(true);
			}
		}

		// Sets all away serving player radio buttons to false.
		for (int i = 0; i<this.awayPlayersServings.size(); i++){
			this.awayPlayersServings.get(i).setEnabled(false);
		}

		// Sets all of the player ON COURT radio buttons to true.
		for (int i = 0; i<this.awayPlayersServings.size(); i++){
			if (this.activeGame.checkPlayerCourtAway(this.awayPlayersIndex.get(i).getPlayerNumber())){
				this.awayPlayersServings.get(i).setEnabled(true);
			}
		}

		/////////////////////////////////////
		this.teamHomePlayerList.validate();//
		this.teamAwayPlayerList.validate();//// Validates and
		this.teamHomePlayerList.repaint();///// repaints the charts.
		this.teamAwayPlayerList.repaint();///
		/////////////////////////////////////
	}

	/**
	 * Updates the score information.
	 */
	public void updateScores(){
		String temp;

		// Updates the Home Score
		if (this.activeGame.getHomeTeamScore()<10){
			temp = "0"+Integer.toString(this.activeGame.getHomeTeamScore());
		}else{
			temp = Integer.toString(this.activeGame.getHomeTeamScore());
		}
		this.team1ScoreL.setText(temp);

		// Updates the Away Score
		if (this.activeGame.getAwayTeamScore()<10){
			temp = "0"+Integer.toString(this.activeGame.getAwayTeamScore());
		}else{
			temp = Integer.toString(this.activeGame.getAwayTeamScore());
		}
		this.team2ScoreL.setText(temp);

		// Updates the Home Sets
		if (this.activeGame.getHomeTeamSets()<10){
			temp = "0"+Integer.toString(this.activeGame.getHomeTeamSets());
		}else{
			temp = Integer.toString(this.activeGame.getHomeTeamSets());
		}
		this.team1SetsL.setText(temp);

		// Updates the Away Sets
		if (this.activeGame.getAwayTeamSets()<10){
			temp = "0"+Integer.toString(this.activeGame.getAwayTeamSets());
		}else{
			temp = Integer.toString(this.activeGame.getAwayTeamSets());
		}
		this.team2SetsL.setText(temp);

		// When a team wins, all of the scoring buttons get disabled.
		if (this.activeGame.getAwayTeamSets()==this.activeGame.getMaxSetsToWin() || 
				this.activeGame.getHomeTeamSets()==this.activeGame.getMaxSetsToWin()){
			this.buttonAwayAddSet.setEnabled(false);
			this.buttonHomeAddSet.setEnabled(false);
			this.buttonAwayAddScore.setEnabled(false);
			this.buttonHomeAddScore.setEnabled(false);
			this.buttonAwayRemoveSet.setEnabled(false);
			this.buttonHomeRemoveSet.setEnabled(false);
			this.buttonAwayRemoveScore.setEnabled(false);
			this.buttonHomeRemoveScore.setEnabled(false);
		}else{
			this.buttonAwayAddSet.setEnabled(true);
			this.buttonHomeAddSet.setEnabled(true);
			this.buttonAwayAddScore.setEnabled(true);
			this.buttonHomeAddScore.setEnabled(true);
			this.buttonAwayRemoveSet.setEnabled(true);
			this.buttonHomeRemoveSet.setEnabled(true);
			this.buttonAwayRemoveScore.setEnabled(true);
			this.buttonHomeRemoveScore.setEnabled(true);
		}

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////// Menu Bar setup/functions /////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return theMenuBar
	 * 
	 * Generates the menu bar for the score board.
	 */
	protected JMenuBar theMenuBar(){

		// Creates the menu bar
		this.menuBar = new JMenuBar();

		////////////////////////////////////////////////////////////////

		// Builds the "File" menu
		this.menu = new JMenu("File");
		this.menuBar.add(this.menu);

		// Creates the Close option within "File"
		this.menuItem = new JMenuItem("Close");
		this.menuItem.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 199 ^
			public void actionPerformed(ActionEvent evt){
				VBallController.closeProgramAsk();
			}
		});
		this.menu.add(menuItem);

		////////////////////////////////////////////////////////////////

		// Builds the "Edit" menu.
		this.menu = new JMenu("Edit");
		this.menuBar.add(this.menu);

		// Creates the Edit Teams option within "Edit"
		this.menuItem = new JMenuItem("Edit Teams/Players");
		this.menuItem.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 199 ^
			public void actionPerformed(ActionEvent evt){
				controller.editTeamInGame();
				fullUpdateTeamChart();
				updateCourtInfo();
			}
		});
		this.menu.add(this.menuItem);

		// Creates a separator
		this.menu.addSeparator();

		// Creates the Reset All option within "Edit"
		this.menuItem = new JMenuItem("Reset All");
		this.menuItem.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 199 ^
			public void actionPerformed(ActionEvent evt){
				activeGame.resetAll();
				fullUpdateTeamChart();
				updateScores();
				updateCourtInfo();
				sourceUpdate.updateOutput();
			}
		});
		this.menu.add(this.menuItem);

		////////////////////////////////////////////////////////////////

		//Builds the "Help" menu.
		this.menu = new JMenu("Help");
		//this.menu.setEnabled(false);
		this.menuBar.add(this.menu);
		
		// Creates the About option within "Help"
		this.menuItem = new JMenuItem("User Manual");
		this.menu.add(this.menuItem);
		this.menuItem.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 199 ^
			public void actionPerformed(ActionEvent evt){
				if (Desktop.isDesktopSupported()) {
				    try {
				        File myFile = new File("resources/pdfs/User Manual.pdf");
				        Desktop.getDesktop().open(myFile);
				    } catch (IOException ex) {
				        // no application registered for PDFs
				    }
				}
			}
		});

		// Creates a separator
		this.menu.addSeparator();

		// Creates the About option within "Help"
		this.menuItem = new JMenuItem("About");
		this.menuItem.addActionListener(new java.awt.event.ActionListener(){// Manual is on line 199 ^
			public void actionPerformed(ActionEvent evt){
				PromptGUI.aboutPrompt();
			}
		});
		this.menu.add(this.menuItem);

		// Returns the whole menu bar to the frame
		return this.menuBar;
	}
}
