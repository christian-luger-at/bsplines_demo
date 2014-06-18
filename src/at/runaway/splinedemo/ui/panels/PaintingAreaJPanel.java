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
import at.runaway.splinedemo.utils.CommonConstants;
import at.runaway.splinedemo.utils.Helper;

/**
 * Draws the given points and the corresponding curves
 */
public class PaintingAreaJPanel extends AbstractAreaJPanel {
	
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

	public PaintingAreaJPanel(Controller controller) {
		super(controller);
		initPanel();
		attachDataModel();
	}

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public void paint(Graphics g) {
		super.paint(g);
		if (controller.getPaintMode() != PaintMode.PAINTING) {
			return;
		}
		// do all the painting
		paintHelperLines(g);
		if (controller.isModelEmpty()) {
			return;
		}
		if (controller.isCurveVisible()) {
			paintBSplines(g);
		}
		// paint control points
		List<Node> points = controller.getControlPoints();
		paintPoints(g, points, POINT_COLOR);
		// paint curve point connections
		paintTangents(g);
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	private void initPanel() {
		this.setBorder(new TitledBorder("Drawing area"));
	    // register mouse listeners
		addMouseListener(pointSelectorListener);
		addMouseMotionListener(existingPointListener);
		addMouseListener(newPointListener);
		addMouseMotionListener(helperLineMouseMotionListener);
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
				if (controller.getPaintMode() == PaintMode.PAINTING) {
					List<Node> pointList = controller.getControlPoints();
					if (pointList.size() < CommonConstants.MAX_POINTS) {
						p.setText("P (" + (pointList.size()) + ")");
						controller.addPoint(p);
					} else {
						JOptionPane.showMessageDialog(null, "Maximum point count reached", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else if (controller.getEditMode() == EditMode.DELETE) {
				controller.removePoint(controller.getSelectedIndex());
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
			List<Node> list = controller.getControlPoints();
			if (Helper.isIndexInRange(list, selectedPointIndex)) {
				Node p = list.get(selectedPointIndex);
				Node screenPoint = new Node(e.getX(), e.getY(), p.getWeight(), p.getText());
				if (controller.getEditMode() == EditMode.EDIT) {
					controller.replacePoint(p, screenPoint);
					cursorX = e.getX();
					cursorY = e.getY();
				}
			}
		}

		public void mouseMoved(MouseEvent e) {
			cmd.execute(e);
		}
	}
}
