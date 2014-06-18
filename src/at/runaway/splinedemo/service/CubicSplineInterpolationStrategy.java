package at.runaway.splinedemo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

import static at.runaway.splinedemo.utils.VectorHelper.add;
import static at.runaway.splinedemo.utils.VectorHelper.multiply;
import static at.runaway.splinedemo.utils.VectorHelper.subtract;
import static at.runaway.splinedemo.utils.VectorHelper.divide;

public class CubicSplineInterpolationStrategy extends AbstractFittingStrategy {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = -3111633083270090816L;
	
	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	public List<Node> calculate(DataModel dataModel) {
		List<Node> q = dataModel.getCurveFittingPoints();
		List<Node> d = dataModel.getCurveFittingDerivates();
		int n = q.size() - 1;
		if (n < 3) {
			return new ArrayList<Node>();
		}
		Node dVector;
		double[] uVectorOfPoints = calculateKnotsOfCurve(dataModel, n);
		double[] uVector = calculateKnotsOfCPoints(uVectorOfPoints, n);
		Node[] p = new Node[n+3];
		dVector = subtract(d.get(0), q.get(0));
		p[0] = q.get(0);
		p[1] = add(multiply(dVector, uVector[4] / 3), p[0]); 
		p[n+2] = q.get(n);
		dVector = subtract(d.get(n), q.get(n));
		p[n+1] = subtract(p[n+2], multiply(dVector, (1 - uVector[n+2]) / 3));
		Node[] r = new Node[n+2]; 
		double dd[] = new double[n+2];
		double[] abc = new double[4];
		for (int i = 3; i < n; i++) {
			r[i] = q.get(i-1);
		}
		abc = basisFunctions(4, uVector[4], 3, uVector);
		double den = abc[1];
		p[2] = divide(subtract(q.get(1), multiply(p[1], abc[0])), den);
		for (int i = 3; i < n; i++) {
			dd[i] = abc[2] / den;
			abc = basisFunctions(i + 2, uVector[i+2], 3, uVector);
			den = abc[1] - abc[0] * dd[i];
			p[i] = divide(subtract(r[i], multiply(p[i-1], abc[0])), den);
		}
		dd[n] = abc[2] / den;
		abc = basisFunctions(n + 2, uVector[n+2], 3, uVector);
		den = abc[1] - abc[0] * dd[n];
		p[n] = divide(
				subtract(q.get(n-1), multiply(p[n+1], abc[2]), multiply(p[n-1], abc[0])), 
				den);

		for (int i = n-1; i >= 2; i--) {
			p[i] = subtract(p[i], multiply(p[i+1], dd[i+1]));
		}
		return Arrays.asList(p); 
	}
	
	protected double[] calculateKnotsOfCPoints(double[] uVectorOfPoints, int n) {
		double[] u = new double[n+7];
		for (int i = n+3; i <= n+6; i++) {
			u[i] = 1;
		}
		for (int j = 1; j <= n-1; j++) {
			u[j+3] = uVectorOfPoints[j];
		}
		return u;
	}
}
