package at.runaway.splinedemo.service;

import java.util.ArrayList;
import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

public abstract class AbstractPaintStrategy extends GenericStrategy implements PaintStrategy {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	protected static final double DELTA_U = 0.01;
	
	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public List<Node> calculateKnotVectorPoints(DataModel model) throws AlgorithmException {
		List<Node> result = new ArrayList<Node>();
		int n = model.getControlPoints().size() - 1;
		int p = model.getDegree();
		double[] knotVector = createKnotVector(model, p, n);
		for (int i = p+1; i < knotVector.length-p-1; i++) {
			Node point = curvePoint(p, knotVector, model, knotVector[i]);
			result.add(point);
		}
		return result;
	}
	
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
		double u = 0;
		Node pp;
		// calculate N-values
		while (u <= knotVector[m-1]) { 
			List<Node> nPoints = new ArrayList<Node>();
			for (int i = 0; i <= n; i++) {
				value = oneBasisFunction(p, m, knotVector, i, u);
				pp = new Node();
				pp.setX(u * xFactor);
				pp.setY(value * 130);
				nPoints.add(pp);
			}
			result.add(nPoints);
			u += dt;
		}
		return result;
	}

	// --------------------------------------------------------------------------
	// P R O T E C T E D  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	protected List<Node> createPoints(DataModel model, int n, int p) throws AlgorithmException {
		if (n < p) {
			return new ArrayList<Node>();
		}
		List<Node> result = new ArrayList<Node>();
		double[] knotVector = createKnotVector(model, p, n);
		int m = knotVector.length;
		double u = 0;
		while (u <= knotVector[m-1]) {
			Node point = curvePoint(p, knotVector, model, u);
			result.add(point);
			u += DELTA_U;
		}
		return result;
	}
		
	/**
	 * Computes the basis function N[i,p] (see page 74, A2.4)
	 * 
	 * @param p degree
	 * @param m High index of uVector 
	 * @param uVector knot vector
	 * @param i index
	 * @param u knot value
	 * 
	 * @return basis function value
	 */
	protected double oneBasisFunction(int p, int m, double[] uVector, int i, double u) {
		// special case
		if ((i == 0 && u == uVector[0]) || (i == m-p-1 && u == uVector[m-1])) { 
			return 1;
		}
		double[] n = new double[p+1];
		// local property
		if (u < uVector[i] || u >= uVector[i+p+1]) {
			return 0;
		}
		// initialize zeroth-degree function
		for (int j = 0; j <= p; j++) {
			if (u >= uVector[i+j] && u < uVector[i+j+1]) {
				n[j] = 1;
			} else {
				n[j] = 0;
			}
		}
		// compute triangualar table
		for (int k = 1; k <= p; k++) {
			double saved = 0;
			if (n[0] != 0) {
				saved = ((u - uVector[i]) * n[0]) / (uVector[i+k] - uVector[i]);
			}
			for (int j = 0; j < p-k+1; j++) {
				double uLeft = uVector[i+j+1];
				double uRight = uVector[i+j+k+1];
				if (n[j+1] == 0) {
					n[j] = saved;
					saved = 0;
				} else {
					double temp = n[j+1] / (uRight - uLeft);
					n[j] = saved + (uRight - u) * temp;
					saved = (u - uLeft) * temp;
				}
			}
		}
		return n[0];
	}

	
	/**
	 * Determine the knot span index (see page 68, A2.1)
	 *
	 * @param n 
	 * @param p degree
	 * @param u given knot position
	 * @param uVector knotVector
	 * @return
	 * @throws AlgorithmException 
	 */
	protected int findSpan(int n, int p, double u, double[] uVector) throws AlgorithmException {
		if (u > uVector[uVector.length - 1]) {
			throw new AlgorithmException("u=" + u + " is greater than last value of uVector");
		}
		if (u == uVector[n+1]) {
			return n;
		}
		int low = p;
		int high = n + 1;
		int mid = (int)((low + high) / 2.0);
		while (u < uVector[mid] || u >= uVector[mid+1]) {
			if (u < uVector[mid]) {
				high = mid;
			} else {
				low = mid;
			}
			mid = (int)((low + high) / 2.0);
		}
		return mid;
	}

	public double[] createKnotVector(DataModel model, int p, int n) throws AlgorithmException {
		if (n < p) {
			return new double[0];
		}
		// calculate knot vector
		int resultIndex = p + 1;
		int vectorLen = n + p + 2; 
		int counter = 1;
		double[] result = new double[vectorLen];
		double[] knots = model.getKnots();
		for (int j = 0; j < n - p; j++) {
			result[resultIndex] = knots[j+1]; 
			resultIndex++;
			counter++;
		}
		for (int j = 0; j <= p; j++) {
			result[resultIndex] = 1; 
			resultIndex++;
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
		double cux = 0;
		double cuy = 0;
		for (int i = 0; i <= p; i++) { 
			index = span - p + i;
			if (index >= pointList.size()) { 
				index = pointList.size() - 1;
			}
			Node point = pointList.get(index);
			cux += nArray[i] * point.getX();
			cuy += nArray[i] * point.getY();
		}
		return new Node(cux, cuy);
	}
}
