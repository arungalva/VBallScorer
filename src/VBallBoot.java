import java.awt.event.*;
import javax.swing.*;


/**
 * @author Edisson Flores
 * @version 1.0
 * Last modified: June 14th, 2014
 * Purpose: Boots up the program.
 * 
 */
public class VBallBoot {

	/**
	 * Lets other code access the bootUp frame.
	 */
	public static JFrame activeGameGUI,bootUp;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Generates the welcome screen
		BootMenu welcomeBoot = new BootMenu();
		
		bootUp = new JFrame("Welcome");
		bootUp.setContentPane(welcomeBoot); // Adding content
		bootUp.pack(); // Automatically resizes the window depending on the content
		bootUp.setSize(bootUp.getWidth()-10,bootUp.getHeight()); // Adjusting minor sizing problems
		bootUp.setResizable(false); // Disables resizing of window
		bootUp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevents the window to close program
		bootUp.setLocationRelativeTo(null); // Sets the location to a OS based location (Random depending on OS)
		
		// Enables the close button to activate the closing prompt
		bootUp.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				VBallController.closeProgramAsk();
			}
		});
		bootUp.setVisible(true); // Makes the GUI visible
		
		Team homeTeam = new Team("TEST_Home Team");
		Team awayTeam = new Team("TEST_Away Team");
		
		Player player1= new Player(1, "Home Team Player 1");
		Player player2= new Player(2, "Home Team Player 2");
		Player player3= new Player(3, "Home Team Player 3");
		Player player4= new Player(4, "Home Team Player 4");
		Player player5= new Player(5, "Home Team Player 5");
		Player player6= new Player(6, "Home Team Player 6");
		Player player7= new Player(7, "Home Team Player 7");
		Player player8= new Player(8, "Home Team Player 8");
		Player player9= new Player(9, "Home Team Player 9");
		Player player10= new Player(10, "Home Team Player 10");
		
		
		Player player11= new Player(1, "Away Team Player 1");
		Player player12= new Player(2, "Away Team Player 2");
		Player player13= new Player(3, "Away Team Player 3");
		Player player14= new Player(4, "Away Team Player 4");
		Player player15= new Player(5, "Away Team Player 5");
		Player player16= new Player(6, "Away Team Player 6");
		Player player17= new Player(7, "Away Team Player 7");
		Player player18= new Player(8, "Away Team Player 8");
		Player player19= new Player(9, "Away Team Player 9");
		Player player20= new Player(10, "Away Team Player 10");
		Player player21= new Player(11, "Away Team Player 11");
		Player player22= new Player(12, "Away Team Player 12");
		Player player23= new Player(13, "Away Team Player 13");
		Player player24= new Player(14, "Away Team Player 14");
		Player player25= new Player(15, "Away Team Player 15");
		Player player26= new Player(16, "Away Team Player 16");
		Player player27= new Player(17, "Away Team Player 17");
		Player player28= new Player(18, "Away Team Player 18");
		Player player29= new Player(19, "Away Team Player 19");
		Player player30= new Player(20, "Away Team Player 20");
		
		
		homeTeam.addPlayer(player1);
		homeTeam.addPlayer(player2);
		homeTeam.addPlayer(player3);
		homeTeam.addPlayer(player4);
		homeTeam.addPlayer(player5);
		homeTeam.addPlayer(player6);
		homeTeam.addPlayer(player7);
		homeTeam.addPlayer(player8);
		homeTeam.addPlayer(player9);
		homeTeam.addPlayer(player10);
		
	    awayTeam.addPlayer(player11);
		awayTeam.addPlayer(player12);
		awayTeam.addPlayer(player13);
		awayTeam.addPlayer(player14);
		awayTeam.addPlayer(player15);
		awayTeam.addPlayer(player16);
		awayTeam.addPlayer(player17);
		awayTeam.addPlayer(player18);
		awayTeam.addPlayer(player19);
     	awayTeam.addPlayer(player20);
     	awayTeam.addPlayer(player21);
 		awayTeam.addPlayer(player22);
 		awayTeam.addPlayer(player23);
 		awayTeam.addPlayer(player24);
 		awayTeam.addPlayer(player25);
 		awayTeam.addPlayer(player26);
 		awayTeam.addPlayer(player27);
 		awayTeam.addPlayer(player28);
 		awayTeam.addPlayer(player29);
      	awayTeam.addPlayer(player30);
		
		TeamRegistry.addTeam(awayTeam);
		TeamRegistry.addTeam(homeTeam);
		

	}

}
