package Components;

import java.awt.*;
import javax.swing.*;

/** 
 *
 * @author Byron Weber Becker (Modified by Edisson Flores) 
 * 
 * Purpose: A component that paints an image stored in a file.
 * 
 * */

@SuppressWarnings("serial")
public class ImageComponent extends JComponent
{
	/**
	 * 
	 */
	private ImageIcon image;


	/** Construct the new component.
	 * @param fileName The file where the image is stored. */
	public ImageComponent(String fileName){  
		super();
		this.image = new ImageIcon(fileName);

	}

	public void setImage(String newImage){
		this.image = new ImageIcon(newImage);
	}

	/** Paint this component, including its image. */
	public void paintComponent(Graphics g){  
		super.paintComponent(g);

		g.drawImage(this.image.getImage(), 0, 0, this.image.getImage().getWidth(null),this.image.getImage().getHeight(null),null);
	}
}
