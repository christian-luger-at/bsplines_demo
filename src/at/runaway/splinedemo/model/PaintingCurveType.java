package at.runaway.splinedemo.model;

import java.io.Serializable;

/**
 * Type of curve for painting view
 * 
 * @author christianluger
 */
public enum PaintingCurveType implements Serializable {

	/**
	 * Bezier curve
	 */
	BEZIER,
	
	/**
	 * BSplines
	 */
	BSPLINE,
	
	/**
	 * NURBS-Curves
	 */
	NURBS;
}
