package at.runaway.splinedemo.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import at.runaway.splinedemo.controller.Controller;
import at.runaway.splinedemo.controller.ControllerListener;
import at.runaway.splinedemo.model.PaintMode;
import at.runaway.splinedemo.ui.panels.AbstractJPanel;
import at.runaway.splinedemo.ui.panels.BasisFunctionJPanel;
import at.runaway.splinedemo.ui.panels.ButtonJPanel;
import at.runaway.splinedemo.ui.panels.CurveInfoJPanel;
import at.runaway.splinedemo.ui.panels.FittingAreaJPanel;
import at.runaway.splinedemo.ui.panels.PaintingAreaJPanel;
import at.runaway.splinedemo.ui.panels.FittingInfoJPanel;
import at.runaway.splinedemo.ui.panels.KnotInfoJPanel;
import at.runaway.splinedemo.ui.panels.PropertiesJPanel;
import at.runaway.splinedemo.utils.CommonConstants;

/**
 * JFrame of the application. This class manages all Panels and the model
 * 
 * More information can be found on the following websites:
 * http://www.stefan-koegler.de/studienarbeit_online-tutorium/java/approximation/index.html
 * http://i33www.ira.uka.de/applets/mocca/html/noplugin/curves.html
 * http://www.cse.unsw.edu.au/~lambert/splines/source.html
 * http://www.ccs.neu.edu/jpt/jpt_2_4/src/alphaindex.htm
 * http://www.cse.unsw.edu.au/~lambert/splines/
 * http://www.computerbase.de/lexikon/De_Casteljau-Algorithmus
 * http://page.mi.fu-berlin.de/csenger/sv/deCasteljanApplet.html
 * http://wwwcs.uni-paderborn.de/fachbereich/AG/agdomik/studienarbeiten/Kutter/index.html
 * http://www.nar-associates.com/nurbs/c_code.html
 * https://www2.fh-augsburg.de/informatik/professoren/maertin/rechnerarchitekturen/anhang/quellcode/letzter%20Code-Stand/bspline/?C=M;O=A
 * http://www.giancola.com/www.dagonet.com/freena.htm
 * 
 * @author christianluger
 */
public class SplinesJFrame extends JFrame {
		
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = 5344074483407073300L;
			
	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private Controller controller = new Controller();
	
	private AbstractJPanel buttonPanel = new ButtonJPanel(controller); 
	
	private AbstractJPanel paintingAreaPanel = new PaintingAreaJPanel(controller);

	private AbstractJPanel fittingAreaPanel = new FittingAreaJPanel(controller);
	
	private AbstractJPanel basisFunctionPanel = new BasisFunctionJPanel(controller);
	
	private AbstractJPanel curveInfoPanel = new CurveInfoJPanel(controller);
	
	private AbstractJPanel propertiesPanel = new PropertiesJPanel(controller);
	
	private AbstractJPanel knotInfoPanel = new KnotInfoJPanel(controller); 
	
	private AbstractJPanel fittingInfoPanel = new FittingInfoJPanel(controller);
	
	private JRadioButtonMenuItem paintItem;

	private JRadioButtonMenuItem fittingItem;
	
	private Container mainPanel = new JPanel(new BorderLayout());

	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S
	// --------------------------------------------------------------------------

	public SplinesJFrame(String title) {
		super(title);
		initFrame();
		attachDataModel();
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------

	private void initFrame() {
		// set up panels
		Container content = this.getContentPane();
		initButtonPanel(mainPanel);
		initDrawingPanel(mainPanel);
		initKnotInfoPanel(mainPanel);
		Container sidePanel = new JPanel(new BorderLayout());
		sidePanel.setPreferredSize(new Dimension(340, this.getHeight()));
		initBasisFunctionPanel(sidePanel);
		initInfoPanel(sidePanel);
		initPropertiesPanel(sidePanel);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		content.add(mainPanel);
        // set menu bar
		addMenuBar();
		setVisibility();
	}
	
	public void attachDataModel() {
		controller.addModelListener(new ControllerListener() {
			public void modelChanged() {
				setVisibility();
			}
		});
	}
	
	private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar(); 
        // file menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new OpenAction());
        fileMenu.add(new SaveAction());
        fileMenu.addSeparator(); 
        fileMenu.add(new ExitAction()); 
        menuBar.add(fileMenu); 
        // mode menu
        ModeMenuActionListener modeMenuActionListener = new ModeMenuActionListener();
        paintItem = new JRadioButtonMenuItem("Paint curve by control points", true);
        paintItem.addActionListener(modeMenuActionListener);
        fittingItem = new JRadioButtonMenuItem("Curve fitting");
        fittingItem.addActionListener(modeMenuActionListener);
        JMenu modeMenu = new JMenu("Mode");
        modeMenu.add(paintItem);
        modeMenu.add(fittingItem);
        menuBar.add(modeMenu);
        // set button group
        ButtonGroup bg = new ButtonGroup();
        bg.add(paintItem);
        bg.add(fittingItem);
        // set bar
        setJMenuBar(menuBar); 
	}
	
	private void initButtonPanel(Container root) {
		buttonPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		root.add(buttonPanel, BorderLayout.NORTH);
	}
	
	private void initDrawingPanel(Container root) {
		paintingAreaPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		fittingAreaPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		root.add(paintingAreaPanel, BorderLayout.CENTER);
	}

	private void initBasisFunctionPanel(Container root) {
		basisFunctionPanel.setPreferredSize(new Dimension(this.getWidth(), 220));
		basisFunctionPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		root.add(basisFunctionPanel, BorderLayout.NORTH);
	}

	private void initInfoPanel(Container root) {
		curveInfoPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		fittingInfoPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		GridBagConstraints fittingConstraint = new GridBagConstraints();
		fittingConstraint.gridx = 0;
		fittingConstraint.gridy = 0;
		fittingConstraint.weightx = 0.1;
		fittingConstraint.weighty = 0.1;
		fittingConstraint.fill = GridBagConstraints.BOTH;
		GridBagConstraints curveConstraint = new GridBagConstraints();
		curveConstraint.gridx = 0;
		curveConstraint.gridy = 1;
		curveConstraint.weightx = 0.1;
		curveConstraint.weighty = 0.1;
		curveConstraint.fill = GridBagConstraints.BOTH;
		JPanel panel = new JPanel(new GridBagLayout());
		panel.add(fittingInfoPanel, fittingConstraint);
		panel.add(curveInfoPanel, curveConstraint);
		root.add(panel, BorderLayout.CENTER);
	}

	private void initPropertiesPanel(Container root) {
		propertiesPanel.setPreferredSize(new Dimension(this.getWidth(), 150));
		propertiesPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		root.add(propertiesPanel, BorderLayout.SOUTH);
	}
	
	private void initKnotInfoPanel(Container root) {
		knotInfoPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
		knotInfoPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		root.add(knotInfoPanel, BorderLayout.SOUTH);
	}
	
	private void setVisibility() {
		curveInfoPanel.setVisible(controller.getPaintMode() == PaintMode.PAINTING);
		fittingInfoPanel.setVisible(controller.getPaintMode() == PaintMode.FITTING);
		if (controller.getPaintMode() == PaintMode.PAINTING) {
			mainPanel.remove(fittingAreaPanel);
			mainPanel.add(paintingAreaPanel);
		} else {
			mainPanel.remove(paintingAreaPanel);
			mainPanel.add(fittingAreaPanel);
		}
	}
	
	// --------------------------------------------------------------------------
	// I n n e r - C l a s s e s
	// --------------------------------------------------------------------------
	
	@SuppressWarnings("serial")
	class OpenAction extends AbstractAction {
		{ 
			putValue( Action.NAME, "Open" ); 
		} 
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	        	try {
		        	controller.loadData(fileChooser.getSelectedFile());
		    	} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        } 
	        }
		}
	}

	@SuppressWarnings("serial")
	class SaveAction extends AbstractAction {
		{ 
			putValue( Action.NAME, "Save" ); 
		} 
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { 
	    		try {
	    			controller.saveData(fileChooser.getSelectedFile());
	    		} catch (Exception ex) {
	    			ex.printStackTrace();
	    			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    		}
	        }
		}
	}

	@SuppressWarnings("serial")
	class ExitAction extends AbstractAction {
		{ 
			putValue( Action.NAME, "Exit" ); 
		} 
		public void actionPerformed(ActionEvent e) {
	          System.exit(0); 
		}
	}
	
	class ModeMenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fittingItem) {
				controller.setPaintMode(PaintMode.FITTING);
			} else if (e.getSource() == paintItem) {
				controller.setPaintMode(PaintMode.PAINTING);
			}
			controller.resetPointList();
			setVisibility();
		}
	}
}
