package at.runaway.splinedemo.ui.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import at.runaway.splinedemo.controller.Controller;
import at.runaway.splinedemo.controller.ControllerListener;
import at.runaway.splinedemo.model.EditMode;
import at.runaway.splinedemo.model.FittingCurveType;
import at.runaway.splinedemo.model.PaintMode;
import at.runaway.splinedemo.model.PaintingCurveType;
import at.runaway.splinedemo.utils.CommonConstants;

/**
 * This panel manages all buttons.
 * 
 * @author christianluger
 */
public class ButtonJPanel extends AbstractJPanel {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = -869974816962580399L;

	private static final Color COLOR_INACTIVE = Color.BLACK; // TODO L&F-Konform

	private static final Color COLOR_ACTIVE = Color.RED;
	
	private static final String CMD_BEZIER = "bezier";
	
	private static final String CMD_BSPLINE = "bspline";
	
	private static final String CMD_NURBS = "nurbs";

	
	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------
	
	private JPanel curveTypePanel = new JPanel(new FlowLayout());
	private JPanel fittingTypePanel = new JPanel(new FlowLayout());

	private JButton clearButton = new JButton("Clear all");
	private JButton editButton = new JButton("Edit");
	private JButton editDerivatesButton = new JButton("Edit derivates");
	private JButton newButton = new JButton("New");
	private JButton deleteButton = new JButton("Delete");
	
	private ButtonGroup bgCurveTypes = new ButtonGroup();
	private JRadioButton typeBezier = new JRadioButton("Bezier");
	private JRadioButton typeBspline = new JRadioButton("B-Spline");
	private JRadioButton typeNURBS = new JRadioButton("NURBS");
	
	private ButtonGroup bgFittingTypes = new ButtonGroup();
	private JRadioButton fittingGlobalInterpolation = new JRadioButton("Global Spline Interpolation"); 

	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public ButtonJPanel(Controller controller) {
		super(controller);
		initPanel();
		attachDataModel();
	}

	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	private void initPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		addButtonsToPanel();
		initCurveTypePanel();
		initFittingPanel();
		addCurveTypeMouseListeners();
		initCurveTypeRadioButtons();
		initFittingTypeRadioButtons();
		setButtonColors();
		setCurveTypeRadioButtons();
		setFittingTypeRadioButtons();
		setPanelVisibility();
	}

	private void attachDataModel() {
		controller.addModelListener(new ControllerListener() {
			public void modelChanged() {
				setPanelVisibility();
			}
		});
	}

	private void initCurveTypePanel() {
		curveTypePanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		curveTypePanel.add(new JLabel("Spline type:"));
		curveTypePanel.add(typeBezier);
		curveTypePanel.add(typeBspline);
		curveTypePanel.add(typeNURBS);
	}
	
	private void initFittingPanel() {
		fittingTypePanel.setBackground(CommonConstants.BACKGROUND_COLOR);
		fittingTypePanel.add(new JLabel("Fitting type:"));
		fittingTypePanel.add(fittingGlobalInterpolation);
	}

	private void addButtonsToPanel() {
		this.add(new JLabel("Drawing mode:"));
		this.add(newButton);
		this.add(editButton);
		this.add(editDerivatesButton);
		this.add(deleteButton);
		this.add(clearButton);
		this.add(new JLabel("     "));
		this.add(curveTypePanel); 
		this.add(fittingTypePanel); 
	}
	
	private void addCurveTypeMouseListeners() {
		clearButton.addMouseListener(new ClearButtonMouseListener());
		editButton.addMouseListener(new EditButtonMouseListener());
		newButton.addMouseListener(new NewButtonMouseListener());
		deleteButton.addMouseListener(new DeleteButtonMouseListener());
		editDerivatesButton.addMouseListener(new EditDerivatesButtonMouseListener());
	}
	
	private void initCurveTypeRadioButtons() {
		CurveTypeActionListener curveTypeActionListener = new CurveTypeActionListener();
		typeBezier.setActionCommand(CMD_BEZIER);
		typeBezier.addActionListener(curveTypeActionListener);
		typeBspline.setActionCommand(CMD_BSPLINE);
		typeBspline.addActionListener(curveTypeActionListener);
		typeNURBS.setActionCommand(CMD_NURBS);
		typeNURBS.addActionListener(curveTypeActionListener);
		bgCurveTypes.add(typeBezier);
		bgCurveTypes.add(typeBspline);
		bgCurveTypes.add(typeNURBS);
	}

	private void initFittingTypeRadioButtons() {
		FittingTypeActionListener fittingTypeActionListener = new FittingTypeActionListener();
		fittingGlobalInterpolation.addActionListener(fittingTypeActionListener);
		bgFittingTypes.add(fittingGlobalInterpolation);
	}

	private void setButtonColors() {
		newButton.setForeground(COLOR_INACTIVE);
		editButton.setForeground(COLOR_INACTIVE);
		deleteButton.setForeground(COLOR_INACTIVE);
		editDerivatesButton.setForeground(COLOR_INACTIVE);

		EditMode mode = controller.getEditMode();
		if (mode == EditMode.EDIT) {
			editButton.setForeground(COLOR_ACTIVE);
		} else if (mode == EditMode.NEW) {
			newButton.setForeground(COLOR_ACTIVE);
		} else if (mode == EditMode.DELETE) {
			deleteButton.setForeground(COLOR_ACTIVE);
		} else if (mode == EditMode.EDIT_DERVIVATES) {
			editDerivatesButton.setForeground(COLOR_ACTIVE);
		} 
	}
	
	private void setCurveTypeRadioButtons() {
		typeBezier.setSelected(controller.getPaintingCurveType() == PaintingCurveType.BEZIER);
		typeBspline.setSelected(controller.getPaintingCurveType() == PaintingCurveType.BSPLINE);
		typeNURBS.setSelected(controller.getPaintingCurveType() == PaintingCurveType.NURBS);
	}
	
	private void setFittingTypeRadioButtons() {
		fittingGlobalInterpolation.setSelected(controller.getFittingCurveType() == FittingCurveType.FITTING_GLOBAL_INTERPOLATION);
	}
	
	private void setPanelVisibility() {
		curveTypePanel.setVisible(controller.getPaintMode() == PaintMode.PAINTING);
		fittingTypePanel.setVisible(controller.getPaintMode() == PaintMode.FITTING);
		editDerivatesButton.setVisible(controller.getPaintMode() == PaintMode.FITTING);
	}

	// --------------------------------------------------------------------------
	// I n n e r - C l a s s e s
	// --------------------------------------------------------------------------

	class ClearButtonMouseListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			controller.resetPointList();
			setButtonColors();
		}
	}

	class EditButtonMouseListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			controller.setEditMode(EditMode.EDIT);
			setButtonColors();
		}
	}

	class EditDerivatesButtonMouseListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			controller.setEditMode(EditMode.EDIT_DERVIVATES);
			setButtonColors();
		}
	}

	class NewButtonMouseListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			controller.setEditMode(EditMode.NEW);
			setButtonColors();
		}
	}

	class DeleteButtonMouseListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			controller.setEditMode(EditMode.DELETE);
			setButtonColors();
		}
	}
	
	class CurveTypeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String curveType = e.getActionCommand();
			if (CMD_BEZIER.equals(curveType)) {
				controller.setPaintingCurveType(PaintingCurveType.BEZIER);
			} else if (CMD_BSPLINE.equals(curveType)) {
				controller.setPaintingCurveType(PaintingCurveType.BSPLINE);
			} else if (CMD_NURBS.equals(curveType)) {
				controller.setPaintingCurveType(PaintingCurveType.NURBS);
			} 
		}
	}
	
	class FittingTypeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String fittingType = e.getActionCommand();
			if (fittingType.equals("1")) {
				controller.setFittingCurveType(FittingCurveType.FITTING_GLOBAL_INTERPOLATION);
			}
		}
	}
}
