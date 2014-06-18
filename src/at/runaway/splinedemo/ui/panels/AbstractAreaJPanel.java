package at.runaway.splinedemo.ui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;

import at.runaway.splinedemo.controller.Controller;
import at.runaway.splinedemo.controller.ControllerListener;
import at.runaway.splinedemo.model.Node;
import at.runaway.splinedemo.model.PaintMode;
import at.runaway.splinedemo.service.AlgorithmException;
import at.runaway.splinedemo.ui.helper.LineHelper;
import at.runaway.splinedemo.utils.CommonConstants;

public class AbstractAreaJPanel extends AbstractJPanel {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	protected static final Color POINT_COLOR = Color.GRAY;

	protected static final Color TANGENT_COLOR = Color.GRAY;
	
	protected static final Color DERIVATE_COLOR = Color.ORANGE;
	
	protected static final Color CURVE_COLOR = Color.BLUE;

	private static final long serialVersionUID = 6234719608491369944L;
	
	private static final double POINT_CLICK_TOLERANCE = 5.0;
	
	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------
	
	protected int cursorX; 

	protected int cursorY; 
	
	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------

	public AbstractAreaJPanel(Controller controller) {
		super(controller);	
	}

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	public void paintCurve(List<Node> points, Color color, Graphics g) {
		Node lastPoint = null;
		g.setColor(color);
		for (Node point : points) {
			g.drawOval(point.getIntX(), point.getIntY(), 1, 1);
			if (lastPoint != null) {
				g.drawLine(lastPoint.getIntX(), lastPoint.getIntY(), point.getIntX(), point.getIntY());
			} else {
				lastPoint = new Node();
			}
			lastPoint.setX(point.getX());
			lastPoint.setY(point.getY());
		}
	}
		
	// --------------------------------------------------------------------------
	// P R O T E C T E D  -  M e t h o d s
	// --------------------------------------------------------------------------

	protected void paintKnots(List<Node> points, Graphics g) {
		for (Node point : points) {
			LineHelper.drawPoint(g, point.getIntX(), point.getIntY());
		}
	}
	
	protected void paintPoints(Graphics g, List<Node> list, Color c) {
		if (controller.isPointsVisible()) {
			g.setColor(c);
			for (int i = 0; i < list.size(); i++) {
				Node p = list.get(i);
				LineHelper.drawPoint(g, p.getIntX(), p.getIntY());
				if (controller.isLabelVisible()) {
					g.drawString(p.getScreenText(), p.getIntX() + 10, p.getIntY());
				}
			}
		}
	}
	
	protected void paintTangents(Graphics g) {
		if (controller.isPolygonVisible()) {
			g.setColor(TANGENT_COLOR);
			List<Node> pointList = controller.getControlPoints();
			for (int i = 1; i < pointList.size(); i++) {
				Node p0 = pointList.get(i-1);
				Node p1 = pointList.get(i);
				LineHelper.drawDashLine(g, p0.getIntX(), p0.getIntY(), p1.getIntX(), p1.getIntY());
			}
		}
	}

	protected void paintHelperLines(Graphics g) {
		if (controller.isHelperLinesVisible()) {
			g.setColor(CommonConstants.COLOR_HELPER_LINE);
			LineHelper.drawDashLine(g, cursorX, 10, cursorX, this.getHeight() - 4);
			LineHelper.drawDashLine(g, 4, cursorY, this.getWidth() - 4, cursorY);
		}
	}
		
	protected void paintBSplines(Graphics g) {
		try {
			List<Node> points = controller.getCurvePoints();
			paintCurve(points, CURVE_COLOR, g);
			if (controller.isPointsVisible()) {
				List<Node> knotPoints = controller.calculateKnotVectorPoints();
				paintKnots(knotPoints, g);
			}
		} catch (AlgorithmException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
			
	protected void attachDataModel() {
		controller.addModelListener(new ControllerListener() {
			public void modelChanged() {
				repaint();
			}
		});
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
			List<Node> pointList;
			if (controller.getPaintMode() == PaintMode.PAINTING) {
				pointList = controller.getControlPoints();
			} else {
				pointList = controller.getCurveFittingPoints();
			}
			for (int i = 0; i < pointList.size(); i++) {
				Node p = pointList.get(i);
				double dx = Math.abs(e.getX() - p.getX());
				double dy = Math.abs(e.getY() - p.getY());
				if (dx <= POINT_CLICK_TOLERANCE && dy <= POINT_CLICK_TOLERANCE) {
					executeCommand(e, i);
					return true;
				}
			}
			setCursor(Cursor.getDefaultCursor());
			return false;
		}
	}
}
