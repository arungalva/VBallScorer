import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Components.HintTextFieldUI;
import Components.ImageComponent;
import Components.TablePanel;

/***********************************************************************
 * 	@author: Edisson Flores
 *  @version: 1.0
 *  Last Edited on June 14th, 2014
 *	Purpose: Generates the prompts, dialog, and general
 *			 GUIs for different occasions.
 *
 *		E.g:	- Error prompts
 *				- New player GUI
 * 
 * 	FYI: Almost all GUIs use JOptionPanes.
 * 
 ***********************************************************************/
public class PromptGUI {

	// GUI Components
	
	// JLabels
	private JLabel label;

	/**
	 * Generates and opens the "New Team" window.
	 * @return errorCatch
	 */
	public Object[] newTeamGUI(){
		String[] options = {"Back...","Next"}; // User options on the bottom of the screen

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.label = new JLabel("Please fill in the following information.");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label,c);

		JTextField teamName = new JTextField(30);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		teamName.setUI(new HintTextFieldUI("Team Name",true));
		panel.add(teamName,c);

		// Shows the prompt with the settings above. Returns the user's choice via. integer.
		int prompt = JOptionPane.showOptionDialog(
				null,
				panel,
				"LiveText: VolleyBall Scoring - New Team Creator",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[1]);

		if (prompt == -1){
			Object[] returns = {-1};
			return returns;
		}else if (prompt == 0){
			Object[] returns = {0};
			return returns;
		}else{

			String newTeamName = teamName.getText();

			Team newTeam = new Team(newTeamName);

			Object[] returns = {newTeam};

			return returns;
		}
	}

	/**
	 * Generates and opens the "New Player" graphic interface.
	 * @return errorCatch
	 */
	public Object newPlayerGUI(Team setupTeam){
		String[] options = {"Back...","Next", "Finish..."}; // User options on the bottom of the screen

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		label = new JLabel("Please fill in the following information.");
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label,c);

		JTextField playerName = new JTextField(30);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		playerName.setUI(new HintTextFieldUI("Player Name",true));
		panel.add(playerName,c);

		JTextField playerNumber = new JTextField(3);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		playerNumber.setUI(new HintTextFieldUI("Player Number",true));
		panel.add(playerNumber,c);

		// Shows the prompt with the settings above. Returns the user's choice via. integer.
		int prompt = JOptionPane.showOptionDialog(
				null,
				panel,
				"LiveText: VolleyBall Scoring - New Player Creator",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[2]);

		System.out.println(prompt);

		if (prompt == -1){
			return -1;
		}else if (prompt == 0){
			return 0;
		}else if (prompt == 2){
			String newPlayerName = playerName.getText();

			try{
				int newPlayerNumber = Integer.parseInt(playerNumber.getText());

				Player newPlayer = new Player(newPlayerNumber,newPlayerName);

				setupTeam.addPlayer(newPlayer);

				return 2;

			}catch(NumberFormatException ex){
				return -2;
			}
		}else{

			String newPlayerName = playerName.getText();

			try{
				int newPlayerNumber = Integer.parseInt(playerNumber.getText());

				Player newPlayer = new Player(newPlayerNumber,newPlayerName);

				setupTeam.addPlayer(newPlayer);

				return 3;

			}catch(NumberFormatException ex){
				return -2;
			}
		}
	}

	/**
	 * Generates and opens the last step of the "New Team" process.
	 * @param finalTeam
	 * @return errorCatch
	 */
	public int newTeamFinal(Team finalTeam){
		String[] options = {"Create team", "Add new player..."}; // User options on the bottom of the screen

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel label = new JLabel("Does everything look correct?");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label,c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(Box.createHorizontalStrut(5),c);

		label = new JLabel("Team name: "+finalTeam.getTeamName(),JLabel.CENTER);
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(label,c);

		JPanel playerList = new JPanel();
		playerList.setLayout(new GridLayout(0,4));

		label = new JLabel("Player Name");
		label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		playerList.add(label);
		label = new JLabel("#",JLabel.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		playerList.add(label);
		playerList.add(Box.createHorizontalStrut(5));
		playerList.add(Box.createHorizontalStrut(5));

		for (int i = 0; i < finalTeam.getPlayerList().size(); i++){
			JLabel playerName = new JLabel(finalTeam.getPlayerList().get(i).getPlayerName());
			playerName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			playerList.add(playerName);
			JLabel playerNumber = new JLabel(Integer.toString(finalTeam.getPlayerList().get(i).getPlayerNumber()),JLabel.CENTER);
			playerNumber.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			playerList.add(playerNumber);

			JButton editButton = new JButton("Edit");
			editButton.setEnabled(false);
			editButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent evt){

				}
			});

			playerList.add(editButton);

			JButton deleteButton = new JButton("Delete");
			final Team LOCAL_TEAM = finalTeam;
			final JLabel LOCAL_PLAYER_NAME = playerName;
			final JLabel LOCAL_PLAYER_NUMBER = playerNumber;
			final JButton LOCAL_PLAYER_EDIT = editButton;
			final JButton LOCAL_PLAYER_DELETE = deleteButton;
			final JPanel THE_PANEL = playerList;
			deleteButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent evt){
					LOCAL_TEAM.removePlayer(LOCAL_PLAYER_NAME.getText());
					THE_PANEL.remove(LOCAL_PLAYER_NAME);
					THE_PANEL.remove(LOCAL_PLAYER_NUMBER);
					THE_PANEL.remove(LOCAL_PLAYER_EDIT);
					THE_PANEL.remove(LOCAL_PLAYER_DELETE);
					THE_PANEL.doLayout();
				}
			});

			playerList.add(deleteButton);

		}

		JScrollPane playerListScroll = new JScrollPane(playerList);

		playerListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		playerListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		c.gridx = 0;
		c.gridy = 3;
		panel.add(playerListScroll,c);

		// Shows the prompt with the settings above. Returns the user's choice via. integer.
		int prompt = JOptionPane.showOptionDialog(
				null,
				panel,
				"LiveText: VolleyBall Scoring - New Player Creator",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);

		System.out.println(prompt);

		return prompt;
	}
	
	public int editTeamGUI(Team homeTeam, Team awayTeam){
		String[] options = {homeTeam.getTeamName(),"Cancel",awayTeam.getTeamName()};
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		label = new JLabel("Please select a team.",JLabel.CENTER);
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label,c);
		
		int prompt = JOptionPane.showOptionDialog(
				null,
				panel,
				"LiveText: VolleyBall Scoring - Team Editor",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[2]);
		
		return prompt;
	}
	
	public int teamEditor(Team teamEdit){
		String[] options = {"Go back"}; // User options on the bottom of the screen

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(Box.createHorizontalStrut(5),c);

		label = new JLabel("Team name: "+teamEdit.getTeamName(),JLabel.CENTER);
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label,c);

		JPanel playerList = new JPanel();
		playerList.setLayout(new GridLayout(0,4));

		label = new JLabel("Player Name");
		label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		playerList.add(label);
		label = new JLabel("#",JLabel.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		playerList.add(label);
		playerList.add(Box.createHorizontalStrut(5));
		playerList.add(Box.createHorizontalStrut(5));

		for (int i = 0; i < teamEdit.getPlayerList().size(); i++){
			JLabel playerName = new JLabel(teamEdit.getPlayerList().get(i).getPlayerName());
			playerName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			playerList.add(playerName);
			JLabel playerNumber = new JLabel(Integer.toString(teamEdit.getPlayerList().get(i).getPlayerNumber()),JLabel.CENTER);
			playerNumber.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			playerList.add(playerNumber);

			JButton editButton = new JButton("Edit");
			editButton.setEnabled(false);
			editButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent evt){

				}
			});

			playerList.add(editButton);

			JButton deleteButton = new JButton("Delete");
			final Team LOCAL_TEAM = teamEdit;
			final JLabel LOCAL_PLAYER_NAME = playerName;
			final JLabel LOCAL_PLAYER_NUMBER = playerNumber;
			final JButton LOCAL_PLAYER_EDIT = editButton;
			final JButton LOCAL_PLAYER_DELETE = deleteButton;
			final JPanel THE_PANEL = playerList;
			deleteButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent evt){
					LOCAL_TEAM.removePlayer(LOCAL_PLAYER_NAME.getText());
					THE_PANEL.remove(LOCAL_PLAYER_NAME);
					THE_PANEL.remove(LOCAL_PLAYER_NUMBER);
					THE_PANEL.remove(LOCAL_PLAYER_EDIT);
					THE_PANEL.remove(LOCAL_PLAYER_DELETE);
					THE_PANEL.doLayout();
				}
			});

			playerList.add(deleteButton);

		}

		JScrollPane playerListScroll = new JScrollPane(playerList);

		playerListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		playerListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		c.gridx = 0;
		c.gridy = 2;
		panel.add(playerListScroll,c);

		// Shows the prompt with the settings above. Returns the user's choice via. integer.
		int prompt = JOptionPane.showOptionDialog(
				null,
				panel,
				"LiveText: VolleyBall Scoring - New Player Creator",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);

		return prompt;
	}

	/**
	 * Populates the Home Team ComboBox list
	 * @return optionReturns
	 */
	private DefaultComboBoxModel<String> populateHomeTeamOptions() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Pick a Team");
		for (int i = 0; i<TeamRegistry.getTeamsAvailable().size();i++){
			options.add(TeamRegistry.getTeamsAvailable().get(i).getTeamName());
		}
		options.add("New Team...");
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		DefaultComboBoxModel<String> optionReturns = new DefaultComboBoxModel(options.toArray());
		return optionReturns;
	}

	/**
	 * Populates the Away Team ComboBox list
	 * @return optionReturns
	 */
	private DefaultComboBoxModel<String> populateAwayTeamOptions() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Pick a Team");
		for (int i = 0; i<TeamRegistry.getTeamsAvailable().size();i++){
			options.add(TeamRegistry.getTeamsAvailable().get(i).getTeamName());
		}
		options.add("New Team...");
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		DefaultComboBoxModel<String> optionReturns = new DefaultComboBoxModel(options.toArray());
		return optionReturns;
	}

	// newGameGUI variables
	private JComboBox<String> homeTeam = new JComboBox<String>();// All available teams for the Home team
	private JComboBox<String> awayTeam = new JComboBox<String>();// All available teams for the Away team
	private ArrayList<Player> homeTeamPlayers = new ArrayList <Player>();
	private ArrayList<Player> awayTeamPlayers = new ArrayList <Player>();
	private String [] selected = {"",""};// 0 = Home Team / 1 = Away Team (Keeps the selected team)
	private Object[] playerListColumnNames = {"Name", "On Court"};
	private TablePanel homePlayers = new TablePanel("Players Serving", null, playerListColumnNames), awayPlayers = new TablePanel("Players Serving", null, playerListColumnNames);

	private void populateHomePlayers(){
		Team selectedTeam = null;
		for (int i = 0; i < TeamRegistry.getTeamsAvailable().size();i++){
			if (TeamRegistry.getTeamsAvailable().get(i).getTeamName().equals(selected[0])){
				selectedTeam = TeamRegistry.getTeamsAvailable().get(i);
			}
		}

		homeTeamPlayers.clear();

		if (selectedTeam!=null){

			for (int i = 0; i<selectedTeam.getPlayerList().size(); i++){
				homeTeamPlayers.add(selectedTeam.getPlayerList().get(i));
			}

		}
	}

	private void populateAwayPlayers(){
		Team selectedTeam = null;
		for (int i = 0; i < TeamRegistry.getTeamsAvailable().size();i++){
			if (TeamRegistry.getTeamsAvailable().get(i).getTeamName().equals(selected[1])){
				selectedTeam = TeamRegistry.getTeamsAvailable().get(i);
			}
		}

		awayTeamPlayers.clear();

		if (selectedTeam!=null){

			for (int i = 0; i<selectedTeam.getPlayerList().size(); i++){
				awayTeamPlayers.add(selectedTeam.getPlayerList().get(i));
			}

		}
	}

	private void updateHomePlayerTables(){
		//DefaultTableModel dataHome = new DefaultTableModel();
		Object[][] dataHome = new Object[homeTeamPlayers.size()][2];

		for (int i = 0; i<homeTeamPlayers.size(); i++){
			dataHome[i][0] = homeTeamPlayers.get(i).getPlayerName();
			dataHome[i][1] = Boolean.FALSE;
		}

		homePlayers.setTableModelDataVector(dataHome,playerListColumnNames);
		homePlayers.fireTableDataChanged();

	}

	/**
	 * 
	 */
	private void updateAwayPlayerTables(){
		//DefaultTableModel dataHome = new DefaultTableModel();
		Object[][] dataAway = new Object[awayTeamPlayers.size()][2];

		for (int i = 0; i<awayTeamPlayers.size(); i++){
			dataAway[i][0] = awayTeamPlayers.get(i).getPlayerName();
			dataAway[i][1] = Boolean.FALSE;
		}

		awayPlayers.setTableModelDataVector(dataAway,playerListColumnNames);
		awayPlayers.fireTableDataChanged();

	}
	
	/**
	 * 
	 * @return
	 */
	private int enoughInCourtHome(){
		DefaultTableModel model = homePlayers.getModel();
		int amountInCourt = 0;
		for (int i = 0; i<model.getRowCount(); i++){
			if((boolean) model.getValueAt(i, 1)){
				amountInCourt = amountInCourt+1;
			}
		}

		if (amountInCourt<6){
			return -1;
		}else if (amountInCourt>6){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private int enoughInCourtAway(){
		DefaultTableModel model = awayPlayers.getModel();
		int amountInCourt = 0;
		for (int i = 0; i<model.getRowCount(); i++){
			if((boolean) model.getValueAt(i, 1)){
				amountInCourt = amountInCourt+1;
			}
		}

		System.out.println(amountInCourt);
		
		if (amountInCourt<6){
			return -1;
		}else if (amountInCourt>6){
			return 1;
		}else{
			return 0;
		}
	}

	/**
	 * Creates and opens the "New Game" window
	 * @return errorCatch
	 */
	public Object[] newGameGUI(){
		String[] options = {"Back...","Create new game"}; // User options on the bottom of the screen

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		selected[0] = "";
		selected[1] = "";
		
		/// Home Team Listener ///

		ItemListener homeListener = new ItemListener(){
			public void itemStateChanged(ItemEvent evt){
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					
					JComboBox<?> selectHome = (JComboBox<?>)evt.getSource();////// Decodes the
					String selectConvert = (String)selectHome.getSelectedItem();// ItemEvent to String
					
					if (selectConvert.equals("New team...")){// Checks if the user selected the "New Team..." option.
						
						////////////////////////////////////////////////////////
						Window w = SwingUtilities.getWindowAncestor(homeTeam);///
						if (w != null) {//////////////////////////////////////////
							w.dispose();///// Closes the new game window ////////
						}///////////////////////////////////////////////////////
						
						awayTeam.removeItemListener(this);// Removes the listener
						homeTeam.removeItemListener(this);// to prevent interference.
						
						VBallController controller = new VBallController();// Importing controller locally
						controller.newTeam();// Makes user create a new team.
						
						newGameGUI();// Automatically sends use back to new game
						
					}else{
						
						selected[0] = selectConvert;// Sets the user's choice for the home team.
						
						///////////////////////////
						populateHomePlayers();////// Sets up the player 
						updateHomePlayerTables();/// list for the Home team.
						///////////////////////////
					}
				}
			}
		};

		homeTeam.addItemListener(homeListener);// Attaching the listener to the ComboBox

		/// Away Team Listener ///

		ItemListener awayListener = new ItemListener(){
			public void itemStateChanged(ItemEvent evt){
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					
					JComboBox<?> selectAway = (JComboBox<?>)evt.getSource();////// Decodes the
					String selectConvert = (String)selectAway.getSelectedItem();// ItemEvent to String
					
					if (selectConvert.equals("New Team...")){// Checks if the user selected the "New Team..." option.
						
						////////////////////////////////////////////////////////
						Window w = SwingUtilities.getWindowAncestor(awayTeam);///
						if (w != null) {//////////////////////////////////////////
							w.dispose();///// Closes the new game window ////////
						}///////////////////////////////////////////////////////
						
						awayTeam.removeItemListener(this);// Removes the listener
						homeTeam.removeItemListener(this);// to prevent interference.
						
						VBallController controller = new VBallController();// Importing controller locally
						controller.newTeam();// Makes user create a new team.
						
						newGameGUI();// Automatically sends use back to new game
						
					}else{
						
						selected[1] = selectConvert;// Sets the user's choice for the away team.
						
						//////////////////////////
						populateAwayPlayers();///// Sets up the player 
						updateAwayPlayerTables();// list for the Home team.
					}   //////////////////////////
				}
			}
		};

		awayTeam.addItemListener(awayListener);// Attaching the listener to the ComboBox

		/// GUI Creation ///

		this.label = new JLabel("Please fill in the following information.",JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 10;
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label,c);

		// Generates a separator
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL); 
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(separator,c);

		// Generates the "Home Team" label
		this.label = new JLabel("Home Team",JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 10;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(label,c);

		// Generates a separator
		separator = new JSeparator(JSeparator.VERTICAL);
		c.fill = GridBagConstraints.VERTICAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridheight = 5;
		c.gridx = 2;
		c.gridy = 2;
		panel.add(separator,c);

		// Generates the "Away Team" label
		this.label = new JLabel("Away Team",JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 10;
		c.gridwidth = 1;
		c.gridx = 4;
		c.gridy = 2;
		panel.add(label,c);

		// Generates the ComboBox for the Home Team
		homeTeam.setModel(this.populateHomeTeamOptions());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 20;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(homeTeam,c);

		// Generates a spacer
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 3;
		panel.add(Box.createHorizontalStrut(5),c);

		// Generates a separator
		separator = new JSeparator(JSeparator.VERTICAL);
		c.fill = GridBagConstraints.VERTICAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridheight = 5;
		c.gridx = 2;
		c.gridy = 3;
		panel.add(separator,c);

		// Generates a spacer
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 3;
		panel.add(Box.createHorizontalStrut(5),c);

		// Generates the ComboBox for the Away Team
		awayTeam.setModel(this.populateAwayTeamOptions());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 20;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 4;
		c.gridy = 3;
		panel.add(awayTeam,c);

		// Updates the information in the charts.
		populateHomePlayers();
		updateHomePlayerTables();
		populateAwayPlayers();
		updateAwayPlayerTables();

		// Generates the Table for the home team players.
		homePlayers.getMainPanel().setPreferredSize(new Dimension(150,200));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 10;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 4;
		panel.add(homePlayers.getMainPanel(),c);

		// Generates a spacer.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 4;
		panel.add(Box.createHorizontalStrut(5),c);

		// Generates a separator.
		separator = new JSeparator(JSeparator.VERTICAL);
		c.fill = GridBagConstraints.VERTICAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridheight = 5;
		c.gridx = 2;
		c.gridy = 4;
		panel.add(separator,c);

		// Generates a spacer.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 4;
		panel.add(Box.createHorizontalStrut(5),c);

		// Generates the Table for the away team players.
		awayPlayers.getMainPanel().setPreferredSize(new Dimension(150,200));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 10;
		c.gridwidth = 1;
		c.gridx = 4;
		c.gridy = 4;
		panel.add(awayPlayers.getMainPanel(),c);

		// Shows the prompt with the settings above. Returns the user's choice via. integer.
		int prompt = JOptionPane.showOptionDialog(
				null,
				panel,
				"LiveText: VolleyBall Scoring - New Game Creator",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);

		////////////////////////////////////////////
		awayTeam.removeItemListener(awayListener);/// Removes the listener.
		homeTeam.removeItemListener(homeListener);/// to prevent interference.
		////////////////////////////////////////////
		
		Object[] optionReturns = new Object[4];
		
		if (prompt == 0){
			
			optionReturns[0] = 0;
			return optionReturns;
			
		}else if (prompt == -1){
			
			optionReturns[0] = -1;
			return optionReturns;
			
		}else if (prompt == 1){
			Team homeTeam = TeamRegistry.getTeam(selected[0]);
			Team awayTeam = TeamRegistry.getTeam(selected[1]);
			
			if(homeTeam == null || awayTeam == null){// Missing Teams
				optionReturns[0] = -5;
				return optionReturns;
				
			}else if (enoughInCourtAway()==-1){// Not enough players in court
				optionReturns[0] = -3;
				return optionReturns; 
				
			}else if (enoughInCourtHome()==-1){// Not enough players in court
				optionReturns[0] = -3;
				return optionReturns; 
				
			}else if (enoughInCourtAway()==1){// Too many players in court
				optionReturns[0] = -4;
				return optionReturns; 
				
			}else if (enoughInCourtHome()==1){// Too many players in court
				optionReturns[0] = -4;
				return optionReturns; 
				
			}else if(homeTeam.getTeamName().equalsIgnoreCase(awayTeam.getTeamName())){// Both teams are the same
				optionReturns[0] = -2;
				return optionReturns;
			}
			
			optionReturns[0] = homeTeam;
			optionReturns[1] = awayTeam;
			
			ArrayList<Player> homeTeamInCourt = new ArrayList<Player>();
			ArrayList<Player> awayTeamInCourt = new ArrayList<Player>();
			
			optionReturns[2] = homeTeamInCourt;
			optionReturns[3] = awayTeamInCourt;
			
			DefaultTableModel model = homePlayers.getModel();
			for (int i = 0; i<model.getRowCount(); i++){
				if((boolean) model.getValueAt(i, 1)){
					homeTeamInCourt.add(homeTeam.getPlayerList().get(i));
				}
			}
			
			model = awayPlayers.getModel();
			for (int i = 0; i<model.getRowCount(); i++){
				if((boolean) model.getValueAt(i, 1)){
					awayTeamInCourt.add(awayTeam.getPlayerList().get(i));
				}
			}

			return optionReturns;
		}
		return optionReturns;
	}
	
	public Object[] getNewGameScoring(){
		String[] options = {"Back...", "Begin game"};
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		label = new JLabel("Pick the amount of points per set: ");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label,c);
		
		JTextField pointsPerSet = new JTextField(3);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(pointsPerSet,c);
		
		label = new JLabel("Pick the amount of sets in the game: ");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label,c);
		
		JTextField setsInGame = new JTextField(3);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(setsInGame,c);

		// Shows the prompt with the settings above. Returns the user's choice via. integer.
		int prompt = JOptionPane.showOptionDialog(
				null,
				panel,
				"LiveText: VolleyBall Scoring - New Game Creator",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		
		Object[] optionReturns = new Object[2];
		
		if (prompt == 0 || prompt == -1){
			optionReturns[0] = prompt;
			return optionReturns;
		}else{
			optionReturns[0] = pointsPerSet.getText();
			optionReturns[1] = setsInGame.getText();
			return optionReturns;
		}
	}

	/**
	 * Creates a default yes/no question prompt with customizable text and titles.
	 * 
	 * answer 
	 * 
	 * @param windTitle
	 * @param msg
	 * @return answer
	 */
	public int questionPromptYESNO(String windTitle,String msg){

		Object[] options = {"Yes",
		"No"};

		// Shows the prompt with the settings above. Returns the user's choice via. integer.
		int c = JOptionPane.showOptionDialog(null,
				msg,
				windTitle,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]);

		System.out.println(c);

		return c;
	}

	/**
	 * Creates a default error prompt with customisable text and titles.
	 * 
	 * @param windTitle
	 * @param error
	 */
	public void errorPrompt(String windTitle, String error){
		JOptionPane.showMessageDialog(null,
				error,
				windTitle,
				JOptionPane.ERROR_MESSAGE);
	}
	
	public void timeOutTimerPrompt(Team timedTeam){
		final JFrame timerGUI = new JFrame();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel label = new JLabel("TIME OUT",JLabel.CENTER);
		label.setFont(new Font("Arial",Font.BOLD,40));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(label,c);
		
		label = new JLabel("Called by "+timedTeam.getTeamName(),JLabel.CENTER);
		label.setFont(new Font("Arial",Font.BOLD,12));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label,c);
		
		JSeparator separator = new JSeparator();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(separator,c);
		
		JLabel timer = new JLabel("30.00",JLabel.CENTER);
		timer.setFont(new Font("Arial",Font.BOLD,30));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(timer,c);
		
		separator = new JSeparator();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 10;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 4;
		panel.add(separator,c);
		
		JButton exit = new JButton("Go back");
		exit.setEnabled(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 5;
		panel.add(exit,c);

		exit.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt){
				timerGUI.dispose();
			}
		});

		/**
		 * 	Internal class for threading
		 *	Created for the timer
		 */
		class CountDown implements Runnable{

			JLabel timer;// Local text GUI for timer
			JButton exit;// Local exit button to exit
			DecimalFormat df = new DecimalFormat("0.00");// The format for the timer

			double timeAmount = 30;// The amount of time in seconds

			/**
			 * Constructor for "CountDown" with the display and button as its parameters
			 * @param display
			 * @param exitButton
			 */
			public CountDown(JLabel display, JButton exitButton){
				timer = display;
				exit = exitButton;

			}

			/**
			 * Runs the timer
			 */
			public void run(){
				
				// Collects the set time
				double count = timeAmount;
				
				// Loops until the count is below or equal to 0
				while (count>=0){
					
					// Removes 0.002 off the count every cycle (0.001 seemed to be too slow)
					count = count - 0.002;
					
					// Sets up the display to show the timer in format
					timer.setText(df.format(count));
					
					// If the count is below 10 seconds, it'll automatically changes the text to red
					// for visual amusement, and alertness as well. 
					if (count <= 10){
						timer.setForeground(Color.red);
					}
					
					// Thead.sleep has to be in a try catch because it can be interrupted. 
					try {
						Thread.sleep(1);// 1 millisecond
					} catch (InterruptedException e) {}
				}
				
				// Sets the end time to remove any imperfections
				timer.setText("0.00");
				
				// Lets the user exit the window
				exit.setEnabled(true);
			}
		}

		// Creates the thread class
		CountDown timeFunction = new CountDown(timer,exit);

		timerGUI.setContentPane(panel); // Adding content
		timerGUI.pack(); // Automatically resizes the window depending on the content
		timerGUI.setResizable(false); // Disables resizing of window
		timerGUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevents the window to close program
		timerGUI.setLocationRelativeTo(null); // Sets the location to a OS based location (Random depending on OS)
		timerGUI.setVisible(true); // Makes the GUI visible

		Thread timeFunctionActive = new Thread(timeFunction);
		timeFunctionActive.start();

	}
	
	public static void aboutPrompt(){
		String[] options = {"OK"};
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		ImageComponent logo = new ImageComponent("resources/images/logo.png");
		logo.setPreferredSize(new Dimension(466,176));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(logo,c);
		
		JLabel label = new JLabel("Version: 1.0",JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(Box.createVerticalStrut(10),c);
		
		label = new JLabel("© Copyright EA Scorers inc. 2014. All rights reserved.",JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(label,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 4;
		panel.add(Box.createVerticalStrut(10),c);
		
		label = new JLabel("This product includes software developed by the",JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 5;
		panel.add(label,c);
		
		label = new JLabel("Apache Software Foundation http://www.apache.org",JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 6;
		panel.add(label,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 7;
		panel.add(Box.createVerticalStrut(10),c);
		
		label = new JLabel("Programmers: Arun Galva & Edisson Flores",JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 8;
		panel.add(label,c);
		
		@SuppressWarnings("unused")
		int prompt = JOptionPane.showOptionDialog(
				null,
				panel,
				"LiveText: VolleyBall Scoring - New Game Creator",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
	}

}

