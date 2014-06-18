package at.runaway.splinedemo.service;

import static at.runaway.splinedemo.utils.VectorHelper.length;

import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.FittingStrategyType;
import at.runaway.splinedemo.model.Node;

public abstract class AbstractFittingStrategy extends GenericStrategy implements FittingStrategy {

	protected double[] calculateKnotsOfCurve(DataModel dataModel, int n) {
		double[] result = new double[0];
		FittingStrategyType calcType = dataModel.getFittingStrategyType();
		if (calcType == FittingStrategyType.EQUALLY_SPACED) {
			result = calculateKnotsOfCurveEqually(n);
		}
		if (calcType == FittingStrategyType.CORD_LENGTH) {
			result = calculateKnotsOfCurveChord(dataModel, n);
		}
		if (calcType == FittingStrategyType.CENTRIPEDAL) {
			result = calculateKnotsOfCurveCentripedal(dataModel, n);
		}
		dataModel.setKnots(result);
		return result;
	}

	/**
	 * Calculate knot vector of curve with "Equally spaced" method
	 * 
	 * @param n
	 * @return
	 */

	private double[] calculateKnotsOfCurveEqually(int n) {
		double[] result = new double[n+1];
		for (int k = 1; k < n; k++) {
			result[k] = (double)k / n;
		}
		result[n] = 1;
		return result;
	}

	/**
	 * Calculate knot vector of curve with "chord length" method
	 * 
	 * @param n
	 * @return
	 */
	private double[] calculateKnotsOfCurveChord(DataModel dataModel, int n) {
		List<Node> q = dataModel.getCurveFittingPoints();
		// calculate cord length
		double d = 0; 
		for (int k = 1; k <= n; k++) {
			d += length(q.get(k), q.get(k-1));
		}
		double[] result = new double[n+1];
		for (int k = 1; k < n; k++) {
			result[k] = result[k-1] + length(q.get(k), q.get(k-1)) / d;
		}
		result[n] = 1;
		return result;
	}

	/**
	 * Calculate knot vector of curve with "centripedal" method
	 * 
	 * @param n
	 * @return
	 */
	private double[] calculateKnotsOfCurveCentripedal(DataModel dataModel, int n) {
		List<Node> q = dataModel.getCurveFittingPoints();
		// calculate cord length
		double d = 0; 
		for (int k = 1; k <= n; k++) {
			d += Math.sqrt(length(q.get(k), q.get(k-1)));
		}
		double[] result = new double[n+1];
		for (int k = 1; k < n; k++) {
			result[k] = result[k-1] + Math.sqrt(length(q.get(k), q.get(k-1))) / d;
		}
		result[n] = 1;
		return result;
	}
}
