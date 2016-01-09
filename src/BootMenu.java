import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import Components.ImageComponent;


/********************************************
 * 	@author Edisson Flores
 *	Last Modified: June 14th, 2014
 *	Purpose: Generates the Welcome screen.
 *
 ********************************************/
@SuppressWarnings("serial")
public class BootMenu extends JPanel {

	PromptGUI prompts = new PromptGUI();
	
	/**
	 * 
	 */
	public BootMenu (){
		super();
		viewLayout();
	}

	/**
	 * 
	 */
	private JLabel label;
	/**
	 * 
	 */
	private JButton button;
	/**
	 * 
	 */
	private JSeparator space;
	/**
	 * 
	 */
	private JPanel panel;
	/**
	 * 
	 */
	private ImageComponent image;

	private VBallController controller = new VBallController();

	/**
	 * Generates the Welcome screen.
	 */
	private void viewLayout() {
		this.setLayout(new BorderLayout());

		image = new ImageComponent("resources/images/logo.png");
		image.setPreferredSize(new Dimension(466,176));
		this.add(image, BorderLayout.NORTH);

		panel = new JPanel();

		label = new JLabel("Hello user! Please choose an option.");
		panel.add(label);

		this.add(panel,BorderLayout.CENTER);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(0,50));

		button = new JButton("New Team");
		button.setPreferredSize(new Dimension(150,30));
		button.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt){
				controller.newTeam();
			}
		});
		panel.add(button,BorderLayout.WEST);

		space = new JSeparator();
		space.setPreferredSize(new Dimension(30,0));

		panel.add(space, BorderLayout.CENTER);

		button = new JButton("New Game");
		button.setPreferredSize(new Dimension(150,30));
		button.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if (TeamRegistry.getTeamsAvailable().size()<2){
					prompts.errorPrompt("Error", "There are no teams available. Please create at least more than one (1) team.");
				}else{
					controller.newGame();
				}
			}
		});
		panel.add(button,BorderLayout.EAST);

		this.add(panel,BorderLayout.SOUTH);

	}


}
