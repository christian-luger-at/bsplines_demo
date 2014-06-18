package at.runaway.splinedemo.service;

import at.runaway.splinedemo.AbstractTestCase;
import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

public class NURBSStrategyTest extends AbstractTestCase {
	
	public void testCurvePoint() throws AlgorithmException {
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 3, 3};
		double u = 1;
		int p = 2;
		DataModel model = new DataModel();
		model.getControlPoints().add(new Node(0, 0, 1, ""));
		model.getControlPoints().add(new Node(10, 10, 4, ""));
		model.getControlPoints().add(new Node(30, 20, 1, ""));
		model.getControlPoints().add(new Node(40, 10, 1, ""));
		model.getControlPoints().add(new Node(50, -10, 1, ""));
		NURBSStrategy strategy = new NURBSStrategy();
		Node point = strategy.curvePoint(p, uVector, model, u);
		assertEquals("X coordinate is not correct", 14.0, point.getX());
		assertEquals("Y coordinate is not correct", 12.0, point.getY());
	}
}
