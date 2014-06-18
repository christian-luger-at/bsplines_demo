package at.runaway.splinedemo.service;

import java.util.ArrayList;
import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

public class NURBSStrategy extends AbstractPaintStrategy {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = 9060448629775086062L;

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

	// --------------------------------------------------------------------------
	// P R O T E C T E D  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	public List<List<Node>> createBasisFunctionPoints(DataModel model, int n, int p) throws AlgorithmException {
		List<List<Node>> result = new ArrayList<List<Node>>();
		if (n < p) {
			return result;
		}
		double[] knotVector = createKnotVector(model, p, n);
		int m = knotVector.length;
		// draw N-functions 
		double xFactor = 300 / knotVector[m-1];
		double dt = knotVector[m-1] / 130;  
		double value;
		double r;
		double sumWeightedBaseFuncs;
		double u = 0;
		Node pp;
		// calculate N-values
		while (u <= knotVector[m-1]) { 
			// calculate weighted basis function sum
			sumWeightedBaseFuncs = 0;
			for (int j = 0; j <= n; j++) {
				value = oneBasisFunction(p, m, knotVector, j, u);
				sumWeightedBaseFuncs += value * model.getControlPoints().get(j).getWeight();
			}
			List<Node> nPoints = new ArrayList<Node>();
			for (int i = 0; i <= n; i++) {
				value = oneBasisFunction(p, m, knotVector, i, u);
				value = oneBasisFunction(p, m, knotVector, i, u);
				r = value * model.getControlPoints().get(i).getWeight() / sumWeightedBaseFuncs;
				pp = new Node();
				pp.setX(u * xFactor);
				pp.setY(r * 130);
				nPoints.add(pp);
			}
			u += dt;
			result.add(nPoints);
		}
		return result;
	}

	/**
	 * Computes a curve point
	 *
	 * @param p degree
	 * @param uVector knot vector
	 * @param model Data model
	 * @param u location where point should be computed
	 * 
	 * @return Point object
	 * @throws AlgorithmException 
	 */
	protected Node curvePoint(int p, double[] uVector, DataModel model, double u) throws AlgorithmException {
		List<Node> pointList = model.getControlPoints();
		int index;
		int n = uVector.length - p - 1;
		int span = findSpan(n, p, u, uVector);
		double[] nArray = basisFunctions(span, u, p, uVector);
		double cwx = 0;
		double cwy = 0;
		double cw = 0;
		for (int i = 0; i <= p; i++) { 
			index = span - p + i;
			if (index >= pointList.size()) { 
				index = pointList.size() - 1;
			}
			Node point = pointList.get(index);
			cw += nArray[i] * point.getWeight();
			cwx += point.getX() * nArray[i] * point.getWeight();
			cwy += point.getY() * nArray[i] * point.getWeight();
		}
		return new Node(cwx / cw, cwy / cw);
	}
}
