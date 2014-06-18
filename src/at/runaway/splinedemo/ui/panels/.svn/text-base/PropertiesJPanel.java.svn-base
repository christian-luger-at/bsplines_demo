package at.runaway.splinedemo.ui.panels;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;

import at.runaway.splinedemo.controller.Controller;

public class PropertiesJPanel extends AbstractJPanel {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = -7847149220329414586L;

	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	JCheckBox polygonVisibleButton = new JCheckBox("Show polygon");

	JCheckBox pointsVisibleButton = new JCheckBox("Show points");
	
	JCheckBox curveVisibleButton = new JCheckBox("Show curve");
	
	JCheckBox labelVisibleButton = new JCheckBox("Show labels");

	JCheckBox helperLineVisibleButton = new JCheckBox("Show helper lines");
	
	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public PropertiesJPanel(Controller controller) {
		super(controller);
		initPanel();
		setValues();
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------

	private void initPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new TitledBorder("Display Properties"));
		this.add(polygonVisibleButton);
		this.add(pointsVisibleButton);
		this.add(curveVisibleButton);
		this.add(labelVisibleButton);
		this.add(helperLineVisibleButton);
		setValues();
		PropertiesItemListener listener = new PropertiesItemListener();
		polygonVisibleButton.addItemListener(listener);
		pointsVisibleButton.addItemListener(listener);
		curveVisibleButton.addItemListener(listener);
		labelVisibleButton.addItemListener(listener);
		helperLineVisibleButton.addItemListener(listener);
	}
	
	private void setValues() {
		polygonVisibleButton.setSelected(controller.isPolygonVisible());
		pointsVisibleButton.setSelected(controller.isPointsVisible());
		curveVisibleButton.setSelected(controller.isCurveVisible());
		labelVisibleButton.setSelected(controller.isLabelVisible());
		helperLineVisibleButton.setSelected(controller.isHelperLinesVisible());
	}
	
	// --------------------------------------------------------------------------
	// I n n e r - C l a s s e s
	// --------------------------------------------------------------------------

	class PropertiesItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
		    Object source = e.getItemSelectable();
		    if (source == polygonVisibleButton) {
		    	controller.setPolygonVisible(polygonVisibleButton.isSelected());
		    } else if (source == pointsVisibleButton) {
		    	controller.setPointsVisible(pointsVisibleButton.isSelected());
		    } else if (source == curveVisibleButton) {
		    	controller.setCurveVisible(curveVisibleButton.isSelected());
		    } else if (source == labelVisibleButton) {
		    	controller.setLabelVisible(labelVisibleButton.isSelected());
		    } else if (source == helperLineVisibleButton) {
		    	controller.setHelperLinesVisible(helperLineVisibleButton.isSelected());
		    }
		}
	}
}
