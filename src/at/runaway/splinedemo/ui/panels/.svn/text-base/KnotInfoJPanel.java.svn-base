package at.runaway.splinedemo.ui.panels;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import at.runaway.splinedemo.controller.Controller;
import at.runaway.splinedemo.controller.ControllerListener;
import at.runaway.splinedemo.service.AlgorithmException;
import at.runaway.splinedemo.utils.CommonConstants;

public class KnotInfoJPanel extends AbstractJPanel {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = -9188549357846938271L;
	
	private static final int MAX_LEN = 25;
	
	private static final Font FONT = new Font("Arial", Font.PLAIN, 12);
	
	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------
	
	private JLabel knotLabel = new JLabel();

	private JLabel weightLabel = new JLabel();

	private JLabel pointLabel = new JLabel();
	
	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public KnotInfoJPanel(Controller controller) {
		super(controller);
		initPanel();
		attachDataModel();
		setValues();
	}
	
	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public void attachDataModel() {
		controller.addModelListener(new ControllerListener() {
			public void modelChanged() {
				setValues();
			}
		});
	}

	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------

	private void initPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(new TitledBorder("Knot vector"));
		this.add(knotLabel);
		this.add(weightLabel);
		this.add(pointLabel);
		knotLabel.setFont(FONT);
	}
	
	private void setValues() {
		try {
			double[] knotVector = controller.calculateKnotVector();
			String knotString = printList(knotVector, "U =", MAX_LEN);
			knotLabel.setText(knotString);
		} catch (AlgorithmException e) {
			knotLabel.setText("---");
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String printList(double[] list, String prefix, int maxLength) {
		StringBuffer u = new StringBuffer(prefix);
		u.append(" { ");
		boolean firstTime = true;
		int length = (list.length >= maxLength ? maxLength: list.length);
		for (int i = 0; i < length; i++) {
			if (firstTime) {
				firstTime = false;
			} else {
				u.append(", ");
			}
			u.append(CommonConstants.KNOT_FORMAT.format(list[i]));
		}
		if (list.length > maxLength) {
			u.append(", ...");
		}
		u.append(" }");
		return u.toString();
	}
}
