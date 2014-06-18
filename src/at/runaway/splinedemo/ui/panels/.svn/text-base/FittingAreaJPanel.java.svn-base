package at.runaway.splinedemo.ui.panels;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import at.runaway.splinedemo.controller.Controller;
import at.runaway.splinedemo.model.EditMode;
import at.runaway.splinedemo.model.Node;
import at.runaway.splinedemo.model.PaintMode;
import at.runaway.splinedemo.ui.helper.LineHelper;
import at.runaway.splinedemo.utils.CommonConstants;
import at.runaway.splinedemo.utils.Helper;

/**
 * Draws the given points and the corresponding curves
 */
public class FittingAreaJPanel extends AbstractAreaJPanel {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------
	
	private static final long serialVersionUID = 303955044067257341L;
		
	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private PointSelectorMouseListener pointSelectorListener = new PointSelectorMouseListener();

	private ExistingPointDraggedMouseMotionListener existingPointListener = new ExistingPointDraggedMouseMotionListener();
	
	private NewPointMouseListener newPointListener = new NewPointMouseListener();
	
	private HelperLineMouseMotionListener helperLineMouseMotionListener = new HelperLineMouseMotionListener();
		
	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public FittingAreaJPanel(Controller controller) {
		super(controller);
		initPanel();
	}

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public void paint(Graphics g) {
		super.paint(g);
		if (controller.getPaintMode() != PaintMode.FITTING) {
			return;
		}
		paintHelperLines(g);
		controller.calculateControlPoints();
		if (controller.isCurveVisible()) {
			paintBSplines(g);
		}
		// paint curve fitting points
		List<Node> points = controller.getCurveFittingPoints();
		paintPoints(g, points, POINT_COLOR);
		// paint control points
		List<Node> controlPoints = controller.getControlPoints();
		paintPoints(g, controlPoints, POINT_COLOR);
		// paint tangents
		paintTangents(g);
		// paint derivates
		paintDerivates(g);
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	private void initPanel() {
		this.setBorder(new TitledBorder("Fitting draw area"));
		// register model listener
		attachDataModel();
	    // register mouse listeners
		addMouseListener(pointSelectorListener);
		addMouseMotionListener(existingPointListener);
		addMouseListener(newPointListener);
		addMouseMotionListener(helperLineMouseMotionListener);
	}
	
	private void paintDerivates(Graphics g) {
		if (controller.getCurveFittingPoints().size() < 2) {
			return;
		}
		g.setColor(DERIVATE_COLOR);
		// paint first point
		paintDerivate(g, 0);
		// paint last point
		paintDerivate(g, controller.getCurveFittingPoints().size() - 1);
	}
	
	private void paintDerivate(Graphics g, int index) {
		Node point = controller.getCurveFittingPoints().get(index);
		Node vector = controller.getCurveFittingDerivates().get(index);
		g.drawLine(point.getIntX(), point.getIntY(), vector.getIntX(), vector.getIntY());
		LineHelper.drawPoint(g, vector.getIntX(), vector.getIntY());
	}
		
	// --------------------------------------------------------------------------
	// I n n e r - C l a s s e s
	// --------------------------------------------------------------------------
	
	/**
	 * Mouse listener for selecting points
	 */
	class PointSelectorMouseListener extends MouseAdapter {
		private MouseCommand cmd = new MouseCommand() {
			public void executeCommand(MouseEvent e, int i) {
				if (controller.getEditMode() != EditMode.NEW) {
					controller.setSelectedIndex(i);
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		};

		public void mousePressed(MouseEvent e) {
			if (controller.getEditMode() != EditMode.NEW) {
				boolean clickedOnPoint = cmd.execute(e);
				if (!clickedOnPoint) {
					controller.setSelectedIndex(Controller.NO_SELECTION);
				}
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			if (controller.getEditMode() != EditMode.NEW) {
				setCursor(Cursor.getDefaultCursor());
			}
		}
	}
	
	/**
	 * Mouse listener that creates a new point 
	 */
	class NewPointMouseListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (controller.getEditMode() == EditMode.NEW) {
				Node p = new Node(e.getX(), e.getY());
				List<Node> pointList = controller.getCurveFittingPoints();
				if (pointList.size() < CommonConstants.MAX_POINTS) {
					p.setText("Q (" + (pointList.size()) + ")");
					controller.addCurveFittingPoint(p, p);
				} else {
					JOptionPane.showMessageDialog(null, "Maximum point count reached", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (controller.getEditMode() == EditMode.DELETE) {
				controller.removeCurveFittingPoint(controller.getSelectedIndex());
			}
		}
	}

	/**
	 * Mouse motion listener that paints helper lines
	 */
	class HelperLineMouseMotionListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) {
			// do nothing
		}
		public void mouseMoved(MouseEvent e) {
			if (controller.isHelperLinesVisible()) {
				cursorX = e.getX();
				cursorY = e.getY();
				repaint();
			}
		}
	}
	
	/**
	 * Mouse motion listener that handles dragging of existing points
	 */
	class ExistingPointDraggedMouseMotionListener implements MouseMotionListener {
		private MouseCommand cmd = new MouseCommand() {
			public void executeCommand(MouseEvent e, int i) {
				if (controller.getEditMode() != EditMode.NEW) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		};

		public void mouseDragged(MouseEvent e) {
			int selectedPointIndex = controller.getSelectedIndex();
			List<Node> list = controller.getCurveFittingPoints();
			if (Helper.isIndexInRange(list, selectedPointIndex)) {
				Node p = list.get(selectedPointIndex);
				Node screenPoint = new Node(e.getX(), e.getY(), p.getWeight(), p.getText());
				if (controller.getEditMode() == EditMode.EDIT) {
					controller.replaceCurveFittingPoint(p, screenPoint); 
					cursorX = e.getX();
					cursorY = e.getY();
				} else if (controller.getEditMode() == EditMode.EDIT_DERVIVATES) {
					Node derivatePoint = new Node(e.getX(), e.getY());
					controller.replaceFittingDerivate(selectedPointIndex, derivatePoint);
				}
			}
		}

		public void mouseMoved(MouseEvent e) {
			cmd.execute(e);
		}
	}
}
