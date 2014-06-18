package at.runaway.splinedemo.service;

import java.util.ArrayList;
import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

/**
 * Draws a bezier curve (special case of a bspline).
 * 
 * @author christianluger
 */
public final class BezierStrategy extends AbstractPaintStrategy {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------
	
	private static final long serialVersionUID = -4029707880192469913L;

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	public List<Node> createCurvePoints(DataModel model) throws AlgorithmException {
		int n = model.getControlPoints().size() - 1;
		if (n > 0) {
			return createPoints(model, n, n);
		}
		return new ArrayList<Node>();
	}	
}
