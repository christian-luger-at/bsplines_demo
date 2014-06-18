package at.runaway.splinedemo.ui.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.runaway.splinedemo.controller.Controller;
import at.runaway.splinedemo.controller.ControllerListener;
import at.runaway.splinedemo.model.Node;
import at.runaway.splinedemo.model.PaintingCurveType;
import at.runaway.splinedemo.utils.CommonConstants;
import at.runaway.splinedemo.utils.Helper;

public class CurveInfoJPanel extends AbstractJPanel {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = -7847149220329414586L;
		
	private static final Integer[] ITEMS = new Integer[CommonConstants.MAX_POINTS];
	
	private static final int WEIGHT_MIN = 0;
	
	private static final int WEIGHT_MAX = 5;
	
	private static final int WEIGHT_INIT = 1;
	
	private static final int WEIGHT_FACTOR = 100;
	
	private static final String NO_POINT = "---";
	
	// --------------------------------------------------------------------------
	// S T A T I C   -  C o d e b l o c k
	// --------------------------------------------------------------------------

	static {
		for (int i = 1; i <= CommonConstants.MAX_POINTS; i++) {
			ITEMS[i-1] = new Integer(i);
		}
	}
	
	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private JComboBox degreeBox = new JComboBox(ITEMS);
	private JLabel degreeLabel = new JLabel("Degree:");
	private JLabel noPropsLabel = new JLabel("No properties to edit");
	private JLabel weightLabel = new JLabel("Weight:");
	private JLabel pointNameLabel = new JLabel("Selected point:");
	private JLabel pointName = new JLabel(NO_POINT);
	
	private JSlider weightSlider = new JSlider(JSlider.HORIZONTAL, 
			WEIGHT_MIN * WEIGHT_FACTOR, 
			WEIGHT_MAX * WEIGHT_FACTOR, 
			WEIGHT_INIT * WEIGHT_FACTOR);
	
	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public CurveInfoJPanel(Controller controller) {
		super(controller);
		initPanel();
		attachDataModel();
	}
	
	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	private void attachDataModel() {
		controller.addModelListener(new ControllerListener() {
			public void modelChanged() {
				setVisibility();
				enableFields();
				setValues();
				setBoxBackground();
			}
		});
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------

	private void initPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(new TitledBorder("Curve Parameters"));
		this.add(noPropsLabel);
		this.add(degreeLabel);
		this.add(degreeBox);
		this.add(new JLabel("                                               "));
		this.add(weightLabel);
		this.add(weightSlider);
		this.add(new JLabel("                                                                "));
		this.add(pointNameLabel);
		this.add(pointName);
		degreeBox.setMaximumRowCount(CommonConstants.MAX_POINTS);
		degreeBox.addActionListener(new ValueChangedListener());
		weightSlider.addChangeListener(new WeightChangeListner());
		weightSlider.setMajorTickSpacing(WEIGHT_FACTOR);
		weightSlider.setMinorTickSpacing(WEIGHT_FACTOR / 2);
		weightSlider.setLabelTable(createWeightLabels());
		weightSlider.setPaintTicks(true);
		weightSlider.setPaintLabels(true);
		setValues();
	}
	
	private void setVisibility() {
		degreeBox.setVisible(controller.getPaintingCurveType() != PaintingCurveType.BEZIER);
		degreeLabel.setVisible(controller.getPaintingCurveType() != PaintingCurveType.BEZIER);
		noPropsLabel.setVisible(controller.getPaintingCurveType() == PaintingCurveType.BEZIER);
		weightLabel.setVisible(controller.getPaintingCurveType() == PaintingCurveType.NURBS);
		weightSlider.setVisible(controller.getPaintingCurveType() == PaintingCurveType.NURBS);
		pointNameLabel.setVisible(controller.getPaintingCurveType() == PaintingCurveType.NURBS);
		pointName.setVisible(controller.getPaintingCurveType() == PaintingCurveType.NURBS);
	}

	private void enableFields() {
		weightSlider.setEnabled(controller.getSelectedIndex() >= 0);
	}
	
	private void setValues() {
		double n = 0;
		int index = controller.getSelectedIndex();
		if (index >= 0) {
			List<Node> points = controller.getControlPoints();
			if (index < points.size()) {
				n = points.get(index).getWeight();
				pointName.setText(points.get(index).getText());
			}
		}  else {
			pointName.setText(NO_POINT);
		}
		weightSlider.setValue((int)(n * WEIGHT_FACTOR));
		if (controller.getDegree() > 1) {
			degreeBox.setSelectedIndex(controller.getDegree() - 1);
		}
	}
	
	private void setBoxBackground() {
		int n = controller.getControlPoints().size() - 1;
		int p = controller.getDegree();
		if (n <= 0 || n >= p) {
			degreeLabel.setForeground(Color.BLACK);
		} else {
			degreeLabel.setForeground(Color.RED);
		}
	}
	
	private Hashtable<Integer, JLabel> createWeightLabels() {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		for (int i = WEIGHT_MIN; i <= WEIGHT_MAX; i++) {
			labelTable.put(i * WEIGHT_FACTOR, new JLabel(Integer.toString(i)));
		}
		return labelTable;		
	}

	// --------------------------------------------------------------------------
	// I n n e r - C l a s s e s
	// --------------------------------------------------------------------------

	class WeightChangeListner implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			if (!source.getValueIsAdjusting()) {
				List<Node> points = controller.getControlPoints(); 
				if (!points.isEmpty()) {
					int index = controller.getSelectedIndex();
					if (Helper.isIndexInRange(points, index)) {
						double weight = source.getValue() / (double)WEIGHT_FACTOR;
						Node oldPoint = points.get(index);
						Node newPoint = oldPoint;
						newPoint.setWeight(weight);
						controller.replacePoint(oldPoint, newPoint);
					}
				}
			}
		}
	}
	
	class ValueChangedListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.setDegree(degreeBox.getSelectedIndex() + 1);
		}
	}
}
