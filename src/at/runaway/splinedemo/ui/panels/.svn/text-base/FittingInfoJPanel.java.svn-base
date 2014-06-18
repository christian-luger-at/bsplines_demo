package at.runaway.splinedemo.ui.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import at.runaway.splinedemo.controller.Controller;
import at.runaway.splinedemo.controller.ControllerListener;
import at.runaway.splinedemo.model.FittingStrategyType;

public class FittingInfoJPanel extends AbstractJPanel {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = -4288851346750962299L;

 	private static final String NO_POINT = "---";
	
	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------
 	
 	private JComboBox knotVectorCalcBox = new JComboBox();
 	
 	private JLabel knotVectorCalcLabel = new JLabel("Knot Vector Calculation Strategy:");
 	
 	private JLabel pointLabel = new JLabel("Selected point: ");
 	
 	private JLabel pointNameLabel = new JLabel();

	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public FittingInfoJPanel(Controller controller) {
		super(controller);
		initPanel();
		attachDataModel();
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------

	private void attachDataModel() {
		controller.addModelListener(new ControllerListener() {
			public void modelChanged() {
				setValues();
			}
		});
	}

	private void initPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(new TitledBorder("Fitting Parameters"));
		this.add(knotVectorCalcLabel);
		this.add(knotVectorCalcBox);
		this.add(new JLabel("                         "));
		this.add(pointLabel);
		this.add(pointNameLabel);
		
		knotVectorCalcBox.addItem("Equal space method");
		knotVectorCalcBox.addItem("Chord length method");
		knotVectorCalcBox.addItem("Centripedal method");
		knotVectorCalcBox.setMaximumRowCount(3);
		knotVectorCalcBox.addActionListener(new ValueChangedListener());

		setValues();
	}
	
	private void setValues() {
		if (controller.getSelectedIndex() >= 0) {
			int index = controller.getSelectedIndex();
			if (index < controller.getCurveFittingPoints().size()) {
				pointNameLabel.setText(controller.getCurveFittingPoints().get(index).getText());
			}
		}  else {
			pointNameLabel.setText(NO_POINT);
		}
	}
	
	// --------------------------------------------------------------------------
	// I n n e r - C l a s s e s
	// --------------------------------------------------------------------------

	class ValueChangedListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = knotVectorCalcBox.getSelectedIndex();
			FittingStrategyType fittingStrategyType = FittingStrategyType.EQUALLY_SPACED;
			if (index == 1) {
				fittingStrategyType = FittingStrategyType.CORD_LENGTH;
			} else if (index == 2) {
				fittingStrategyType = FittingStrategyType.CENTRIPEDAL;
			}
			controller.setFittingStrategyType(fittingStrategyType);
		}
	}
}
