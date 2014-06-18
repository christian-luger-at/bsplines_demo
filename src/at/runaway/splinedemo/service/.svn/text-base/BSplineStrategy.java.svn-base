package at.runaway.splinedemo.service;

import java.util.ArrayList;
import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

/**
 * Draws a BSpline-Curve 
 * 
 * @see http://www.ibiblio.org/e-notes/Splines/Basis.htm
 * @author christianluger
 *
 */
public class BSplineStrategy extends AbstractPaintStrategy {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = 4398361827412221014L;

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public List<Node> createCurvePoints(DataModel model) throws AlgorithmException {
		int n = model.getControlPoints().size() - 1;
		int p = model.getDegree();
		if (n > 0) {
			return createPoints(model, n, p);
		}
		return new ArrayList<Node>();
	}
}
