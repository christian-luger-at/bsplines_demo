package at.runaway.splinedemo.service;

import java.util.ArrayList;
import java.util.List;

import at.runaway.splinedemo.AbstractTestCase;
import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.FittingStrategyType;
import at.runaway.splinedemo.model.Node;

public class CubicSplineInterpolationStrategyTest extends AbstractTestCase {

	public void testCalculateKnotsOfCurve() {
		DataModel model = new DataModel();
		double[] expected = new double[] {0.0, 0.2, 0.4, 0.6, 0.8, 1.0};
		CubicSplineInterpolationStrategy s = new CubicSplineInterpolationStrategy();
		double[] result = s.calculateKnotsOfCurve(model, 5);
		compareVectors(expected, result, 4);
	}
	
	public void testCalculateKnotsOfCPoints() {
		double[] uVectorOfPoints = new double[] {0.0, 0.2, 0.4, 0.6, 0.8, 1.0};
		int n = 5;
		double[] expected = new double[] {0.0, 0.0, 0.0, 0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 1.0, 1.0, 1.0};
		CubicSplineInterpolationStrategy s = new CubicSplineInterpolationStrategy();
		double[] result = s.calculateKnotsOfCPoints(uVectorOfPoints, n);
		compareVectors(expected, result, 4);
	}
	
	public void testCalculateEquallySpaced() {
		DataModel model = new DataModel();
		List<Node> points = model.getCurveFittingPoints();
		List<Node> derivates = model.getCurveFittingDerivates();
		points.add(new Node(1, 1));
		derivates.add(new Node(1, 1)); 
		points.add(new Node(2, 2));
		derivates.add(new Node(2, 2)); 
		points.add(new Node(3, 4));
		derivates.add(new Node(3, 4)); 
		points.add(new Node(4, 2));
		derivates.add(new Node(4, 2)); 
		points.add(new Node(5, 1));
		derivates.add(new Node(-1, -1)); 
		model.setFittingStrategyType(FittingStrategyType.EQUALLY_SPACED);

		List<Node> expected = new ArrayList<Node>();
		expected.add(new Node(1, 1, "P0"));
		expected.add(new Node(1, 1, "P1"));
		expected.add(new Node(2.125, 1.49405, "P2"));
		expected.add(new Node(3.0625, 5.27083, "P3"));
		expected.add(new Node(3.625, 1.42262, "P4"));
		expected.add(new Node(5.5, 1.1666666666, "P5"));
		expected.add(new Node(5.0, 1.0, "P6"));
		CubicSplineInterpolationStrategy s = new CubicSplineInterpolationStrategy();
		List<Node> result = s.calculate(model);
		comparePoints(expected, result, 4);
	}
}
