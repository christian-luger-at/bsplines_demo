package at.runaway.splinedemo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import at.runaway.splinedemo.service.BSplineStrategy;
import at.runaway.splinedemo.service.BezierStrategy;
import at.runaway.splinedemo.service.CubicSplineInterpolationStrategy;
import at.runaway.splinedemo.service.FittingStrategy;
import at.runaway.splinedemo.service.NURBSStrategy;
import at.runaway.splinedemo.service.PaintStrategy;
import at.runaway.splinedemo.utils.Helper;

public class DataModel implements Serializable {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = 2084720829642895914L;

	public static final int NO_SELECTION = -1;

	private static final PaintStrategy BEZIER_STRATEGY = new BezierStrategy();
	
	private static final PaintStrategy BSPLINES_STRATEGY = new BSplineStrategy();
	
	private static final PaintStrategy NURBS_STRATEGY = new NURBSStrategy();
	
	private static final FittingStrategy GLOB_INTERPOL_CUBIC_STRATEGY = new CubicSplineInterpolationStrategy();

	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	/**
	 * List of curve fitting points
	 */
	private List<Node> curveFittingPoints = new ArrayList<Node>();

	/**
	 * List of derivate points
	 */
	private List<Node> curveFittingDerivates = new ArrayList<Node>();;

	/**
	 * List of control points to calculate curve (Painting mode)
	 */
	private List<Node> controlPoints = new ArrayList<Node>();

	/**
	 * List of knots
	 */
	private double[] knots;

	/**
	 * Selected point
	 */
	private int selectedIndex = NO_SELECTION;
	
	/**
	 * Selected knot index
	 */
	private int selectedKnotIndex = NO_SELECTION;
	
	/**
	 * Degree of curve
	 */
	private int degree = 3;
	
	private PaintingCurveType paintingCurveType = PaintingCurveType.BEZIER;
	
	private FittingCurveType fittingCurveType = FittingCurveType.FITTING_GLOBAL_INTERPOLATION;
		
	private FittingStrategyType fittingStrategyType = FittingStrategyType.EQUALLY_SPACED;
	
	private PaintStrategy paintStrategy = BEZIER_STRATEGY;
	
	private FittingStrategy fittingStrategy = GLOB_INTERPOL_CUBIC_STRATEGY;

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	public void calculateKnots() {
		int n = controlPoints.size() - 1;
		if (n - degree + 2 > 0) {
			knots = new double[n - degree + 2];
		 	for (int j = 0; j < n - degree + 2; j++) {
				knots[j] = (double)j / (n - degree + 1);
			}
		} else {
			knots = new double[0];
		}
	}
	
	public void resetPointList() {
		controlPoints = new ArrayList<Node>();
		curveFittingPoints = new ArrayList<Node>();
		curveFittingDerivates = new ArrayList<Node>();
		knots = new double[0];
		selectedIndex = NO_SELECTION;
		selectedKnotIndex = NO_SELECTION;
	}

	public void replaceKnot(int knotIndex, double newValue) {
		if (knots.length > 0 && Helper.isIndexInRange(knots, knotIndex)) {
			knots[knotIndex] = newValue;
		}
	}
	
	// ------ Getter / Setter
	
	public List<Node> getCurveFittingPoints() {
		return curveFittingPoints;
	}

	public void setCurveFittingPoints(List<Node> curveFittingPoints) {
		this.curveFittingPoints = curveFittingPoints;
	}

	public List<Node> getCurveFittingDerivates() {
		return curveFittingDerivates;
	}

	public void setCurveFittingDerivates(List<Node> curveFittingDerivates) {
		this.curveFittingDerivates = curveFittingDerivates;
	}

	public List<Node> getControlPoints() {
		return controlPoints;
	}

	public void setControlPoints(List<Node> controlPoints) {
		this.controlPoints = controlPoints;
	}

	public double[] getKnots() {
		return knots;
	}

	public void setKnots(double[] knots) {
		this.knots = knots;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public int getSelectedKnotIndex() {
		return selectedKnotIndex;
	}

	public void setSelectedKnotIndex(int selectedKnotIndex) {
		this.selectedKnotIndex = selectedKnotIndex;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		if (degree != this.degree) {
			this.degree = degree;
			calculateKnots();
		}
	}

	public PaintingCurveType getPaintingCurveType() {
		return paintingCurveType;
	}

	public void setPaintingCurveType(PaintingCurveType paintingCurveType) {
		this.paintingCurveType = paintingCurveType;
		setPaintStrategy();
	}

	public FittingCurveType getFittingCurveType() {
		return fittingCurveType;
	}

	public void setFittingCurveType(FittingCurveType fittingCurveType) {
		this.fittingCurveType = fittingCurveType;
	}

	public PaintStrategy getPaintStrategy() {
		return paintStrategy;
	}

	public void setPaintStrategy(PaintStrategy paintStrategy) {
		this.paintStrategy = paintStrategy;
	}

	public FittingStrategy getFittingStrategy() {
		return fittingStrategy;
	}

	public void setFittingStrategy(FittingStrategy fittingStrategy) {
		this.fittingStrategy = fittingStrategy;
	}

	public FittingStrategyType getFittingStrategyType() {
		return fittingStrategyType;
	}

	public void setFittingStrategyType(FittingStrategyType fittingStrategyType) {
		this.fittingStrategyType = fittingStrategyType;
	}
	
	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	private void setPaintStrategy() {
		if (paintingCurveType == PaintingCurveType.BEZIER) {
			int degree = getControlPoints().size() - 1;
			setDegree(degree);
			paintStrategy = BEZIER_STRATEGY;
			calculateKnots();
		} else if (paintingCurveType == PaintingCurveType.BSPLINE) {
			paintStrategy = BSPLINES_STRATEGY;
			calculateKnots();
		} else if (paintingCurveType == PaintingCurveType.NURBS) {
			paintStrategy = NURBS_STRATEGY;
		}
	}


}
