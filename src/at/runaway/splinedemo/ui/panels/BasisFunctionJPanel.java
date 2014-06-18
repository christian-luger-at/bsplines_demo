package at.runaway.splinedemo.ui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import at.runaway.splinedemo.controller.Controller;
import at.runaway.splinedemo.controller.ControllerListener;
import at.runaway.splinedemo.model.Node;
import at.runaway.splinedemo.model.PaintingCurveType;
import at.runaway.splinedemo.service.AlgorithmException;
import at.runaway.splinedemo.ui.helper.LineHelper;
import at.runaway.splinedemo.utils.CommonConstants;
import at.runaway.splinedemo.utils.CoordinateConverter;
import at.runaway.splinedemo.utils.Helper;

public class BasisFunctionJPanel extends AbstractJPanel {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = 2882408610763778847L;
	
	private static final int POINT_CLICK_TOLERANCE = 6;

	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private PointSelectorMouseListener pointSelectorListener = new PointSelectorMouseListener();

	private ExistingPointDraggedMouseMotionListener existingPointListener = new ExistingPointDraggedMouseMotionListener();

	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public BasisFunctionJPanel(Controller controller) {
		super(controller);
		initPanel();
		attachDataModel();
	}

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public void paint(Graphics g) {
		super.paint(g);
		try {
			paintFunctionBox(g);
			paintHelperLines(g);
			paintBasisFunctions(g);
		} catch (AlgorithmException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void attachDataModel() {
		controller.addModelListener(new ControllerListener() {
			public void modelChanged() {
				repaint();
			}
		});
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------

	private void initPanel() {
		this.setBorder(new TitledBorder("Basis functions"));
		addMouseListener(pointSelectorListener);
		addMouseMotionListener(existingPointListener);
	}
	
	private void paintHelperLines(Graphics g) throws AlgorithmException {
		if (controller.isHelperLinesVisible()) {
			if (controller.getSelectedKnotIndex() >= 0) {
				double[] list = getKnotArray();
				double u = list[controller.getSelectedKnotIndex()];
				int x = mapUToScreen(u);
				g.setColor(CommonConstants.COLOR_HELPER_LINE);
				LineHelper.drawDashLine(g, x, CommonConstants.BASIS_START_Y, x, CommonConstants.BASIS_START_Y + CommonConstants.BASIS_LENGTH_Y);
			}
		}
	}

	private void paintFunctionBox(Graphics g) throws AlgorithmException {
		g.setColor(CommonConstants.COLOR_BASIS_BACKGROUND); 
		g.fillRect(CommonConstants.BASIS_START_X + 1, CommonConstants.BASIS_START_Y + 1, 
				CommonConstants.BASIS_LENGTH_X - 2, CommonConstants.BASIS_LENGTH_Y - 2);
		
		g.setColor(CommonConstants.COLOR_BASIS_BOX); 
		g.drawRect(CommonConstants.BASIS_START_X, CommonConstants.BASIS_START_Y, 
				CommonConstants.BASIS_LENGTH_X, CommonConstants.BASIS_LENGTH_Y);

		boolean showPoints = controller.getPaintingCurveType() == PaintingCurveType.NURBS
						&& controller.getControlPoints().size() - 1 >= controller.getDegree();

		if (showPoints) {
			double[] list = getKnotArray();
			g.setColor(Color.GRAY);
			paintXAxis(g, list);
			for (int i = 0; i < list.length; i++) {
				Polygon p = new Polygon();
				double knotValue = list[i];
				int x = mapUToScreen(knotValue);
				p.addPoint(x - POINT_CLICK_TOLERANCE, CommonConstants.BASIS_START_Y - POINT_CLICK_TOLERANCE);
			    p.addPoint(x + POINT_CLICK_TOLERANCE, CommonConstants.BASIS_START_Y - POINT_CLICK_TOLERANCE);
			    p.addPoint(x , CommonConstants.BASIS_START_Y);
				g.fillPolygon(p);
			}
		}
	}
	
	private void paintBasisFunctions(Graphics g) throws AlgorithmException {
		int n = controller.getControlPoints().size() - 1;
		if (n < 1) {
			return;
		}
		g.setColor(CommonConstants.COLOR_CURVES);
		CoordinateConverter c = new CoordinateConverter();
		c.setZeroPoint(CommonConstants.BASIS_START_X, CommonConstants.BASIS_START_Y + CommonConstants.BASIS_LENGTH_Y);
		List<List<Node>> pointList = controller.createBasisFunctionPoints();
		Node[] lastPoint = new Node[n+1];
		boolean isNotFirstIteration = false;
		for (List<Node> nPoints : pointList) {
			for (int i = 0; i < nPoints.size(); i++) {
				Node aPoint = nPoints.get(i);
				Node sp = c.convertToScreen(aPoint);
				g.drawOval(sp.getIntX(), sp.getIntY(), 1, 1);
				if (isNotFirstIteration) {
					g.drawLine(lastPoint[i].getIntX(), lastPoint[i].getIntY(), sp.getIntX(), sp.getIntY());
				}
				lastPoint[i] = sp;
			}
			if (!isNotFirstIteration) {
				isNotFirstIteration = true;
			}
		}
	}
	
	private void paintXAxis(Graphics g, double[] uVector) {
		int m = uVector.length - 1;
		String firstLabel = CommonConstants.DECIMAL_FORMAT.format(uVector[0]);
		String lastLabel = CommonConstants.DECIMAL_FORMAT.format(uVector[m]);
		
		g.setColor(Color.GRAY); 
		g.drawString(firstLabel, CommonConstants.BASIS_START_X - 5, 
				CommonConstants.BASIS_START_Y + CommonConstants.BASIS_LENGTH_Y + 15); 
		g.drawString(lastLabel, CommonConstants.BASIS_START_X + CommonConstants.BASIS_LENGTH_X - 15, 
				CommonConstants.BASIS_START_Y + CommonConstants.BASIS_LENGTH_Y + 15);
		g.setColor(Color.BLACK);
	}

	
	private int mapUToScreen(double u) throws AlgorithmException {
		double[] list = getKnotArray();
		double lastX = list[list.length - 1];
		return (int)(CommonConstants.BASIS_START_X + CommonConstants.BASIS_LENGTH_X * u / lastX);
	} 
	
	private double[] getKnotArray() throws AlgorithmException {
		double[] raw = controller.calculateKnotVector();
		int p = controller.getDegree();
		int length = raw.length - 2 * p;
		double[] result = new double[length];
		for (int i = 0; i < length; i++) {
			result[i] = raw[i + p];
		}
		return result;
	}
	
	// --------------------------------------------------------------------------
	// I n n e r - C l a s s e s
	// --------------------------------------------------------------------------
	
	/**
	 * Abstract class for mouse commands that deal with mouse clicks
	 */
	abstract class MouseCommand {
		public abstract void executeCommand(MouseEvent e, int i);
		public boolean execute(MouseEvent e) {
			try {
				boolean showPoints = controller.getPaintingCurveType() == PaintingCurveType.NURBS
							&& controller.getControlPoints().size() - 1 >= controller.getDegree();
	
				if (showPoints) {
					int y = CommonConstants.BASIS_START_Y;
					double[] list = getKnotArray();
					// the first and last point are not allowed to be shifted
					for (int i = 1; i < list.length-1; i++) {
						double knotValue = list[i];
						double x = CommonConstants.BASIS_START_X + CommonConstants.BASIS_LENGTH_X * knotValue;
						double dx = Math.abs(e.getX() - x);
						double dy = Math.abs(e.getY() - y);
						if (dx <= POINT_CLICK_TOLERANCE && dy <= POINT_CLICK_TOLERANCE) {
							executeCommand(e, i);
							return true;
						}
					}
				}
			} catch (AlgorithmException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			setCursor(Cursor.getDefaultCursor());
			return false;
		}
	}
	
	
	/**
	 * Mouse listener for selecting points
	 */
	class PointSelectorMouseListener extends MouseAdapter {
		private MouseCommand cmd = new MouseCommand() {
			public void executeCommand(MouseEvent e, int i) {
				controller.setSelectedKnotIndex(i);
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		};

		public void mousePressed(MouseEvent e) {
			cmd.execute(e);
		}
		
		public void mouseReleased(MouseEvent e) {
			controller.setSelectedKnotIndex(Controller.NO_SELECTION);
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	 * Mouse motion listener that handles dragging of existing points
	 */
	class ExistingPointDraggedMouseMotionListener implements MouseMotionListener {
		private MouseCommand cmd = new MouseCommand() {
			public void executeCommand(MouseEvent e, int i) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		};

		public void mouseDragged(MouseEvent e) {
			int selectedKnotIndex = controller.getSelectedKnotIndex();
			try {
				double[] list = getKnotArray();
				if (Helper.isIndexInRange(list, selectedKnotIndex)) { 
					double uPrev = list[selectedKnotIndex - 1];
					double uNext = list[selectedKnotIndex + 1];
					double lastX = list[list.length - 1];
					double u = lastX * (e.getX() - CommonConstants.BASIS_START_X) / CommonConstants.BASIS_LENGTH_X;
					if (u >= uPrev && u <= uNext) {
						controller.replaceKnot(selectedKnotIndex, u);
					}
				}
			} catch (AlgorithmException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		public void mouseMoved(MouseEvent e) {
			cmd.execute(e);
		}
	}
}
