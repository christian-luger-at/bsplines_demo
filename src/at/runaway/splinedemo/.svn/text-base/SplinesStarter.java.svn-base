package at.runaway.splinedemo;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import at.runaway.splinedemo.ui.SplinesJFrame;

/**
 * Start class for BSplines-Demo
 * 
 * @author christianluger
 */
public class SplinesStarter implements Runnable {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final int WINDOW_SIZE_X = 1024;

	private static final int WINDOW_SIZE_Y = 768;

	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private JFrame mainFrame = new SplinesJFrame("BSplines Demo Application (created by C. Luger and C. Koechli)");

	// --------------------------------------------------------------------------
	// S T A T I C  -  P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	public static void main(String[] args) {
		Runnable app = new SplinesStarter();
        try {
            SwingUtilities.invokeAndWait(app);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// set main frame
			mainFrame.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
	        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        mainFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}