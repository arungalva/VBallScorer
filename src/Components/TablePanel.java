package Components;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Hovercraft Full Of Eels from StackOverFlow.com (Modified by Edisson Flores)
 * 
 * Comments by Edisson Flores
 *
 *	Purpose: Creates a Panel that automatically update its own
 *			 variables and also creates its own scroll pane.
 *
 */

@SuppressWarnings("serial")
public class TablePanel {

	private JPanel mainPanel = new JPanel();
	private DefaultTableModel dm;
	private JTable table = new JTable();
	private JScrollPane scrollpane = new JScrollPane(table);

	/**
	 * Constructor of "TablePanel" with the panel title, data, and column names
	 * @param title
	 * @param data
	 * @param columnNames
	 */
	public TablePanel(String title, Object[][] data, Object[] columnNames) {
		// Creates a model for the table
		dm = new DefaultTableModel(data, columnNames){
			@SuppressWarnings("rawtypes")
			Class[] types = {String.class, Boolean.class}; // Setups the class format.
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int columnIndex) { // Sets the columns to be a certain class according to the above array.
				return types[columnIndex];
			}
		};

		table.setModel(dm);// Sets the model of the table to the  model created above.

		mainPanel.setBorder(BorderFactory.createTitledBorder(title));// Creates a titled boarder
		mainPanel.setLayout(new BorderLayout(5, 5));// Sets up the layout of the panel.
		mainPanel.add(scrollpane, BorderLayout.CENTER);// Inputs and centres the scrollpane
	}

	/**
	 * Changes the model to the new data
	 * @param data
	 * @param columnNames
	 */
	public void setTableModelDataVector(Object[][] data, Object[] columnNames) {
		dm.setDataVector(data, columnNames);
	}

	/**
	 * Updates GUI (Similar to .repaint() and .doLayout())
	 */
	public void fireTableDataChanged() {
		dm.fireTableDataChanged();
	}

	/**
	 * Returns the scroll pane
	 * @return scrollpane
	 */
	public JScrollPane getScrollPane() {
		return scrollpane;
	}

	/**
	 * Returns the main panel
	 * @return mainPanel
	 */
	public JComponent getMainPanel() {
		return mainPanel;
	}
	
	/**
	 * Returns the table
	 * @return table
	 */
	public JTable getTable(){
		return table;
	}
	
	/**
	 * Returns the model
	 * @return
	 */
	public DefaultTableModel getModel(){
		return dm;
	}

}
