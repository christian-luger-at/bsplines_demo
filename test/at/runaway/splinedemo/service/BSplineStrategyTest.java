package at.runaway.splinedemo.service;

import at.runaway.splinedemo.AbstractTestCase;
import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

public class BSplineStrategyTest extends AbstractTestCase {
	
	public void testFindSpan00() throws AlgorithmException {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 2;
		double u = 0;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		int n = uVector.length - p - 1;
		int i = strategy.findSpan(n, p, u, uVector);
		assertEquals("Knot span index not correct", 2, i);
	}

	public void testFindSpan01() throws AlgorithmException {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 2;
		double u = 2.5;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		int n = uVector.length - p - 1;
		int i = strategy.findSpan(n, p, u, uVector);
		assertEquals("Knot span index not correct", 4, i);
	}

	public void testFindSpan02() throws AlgorithmException {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 2;
		double u = 5.0;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		int n = uVector.length - p - 1;
		int i = strategy.findSpan(n, p, u, uVector);
		assertEquals("Knot span index not correct", 8, i);
	}

	public void testFindSpanError() {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 2;
		double u = 6;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		int n = uVector.length - p - 1;
		try {
			strategy.findSpan(n, p, u, uVector);
		} catch (AlgorithmException e) {
			return;
		}
		assertTrue("Algorithm-Exception expected", false);
	}

	public void testBasisFunction04() {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 0;
		int i = 4;
		double u = 2.5;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		double[] n = strategy.basisFunctions(i, u, p, uVector);
		compareVectors(n, new double[] {1.0}, 3);
	}
	
	public void testBasisFunction14() {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 1;
		int i = 4;
		double u = 2.5;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		double[] n = strategy.basisFunctions(i, u, p, uVector);
		compareVectors(n, new double[] {0.5, 0.5}, 3);
	}

	public void testBasisFunction24() {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 2;
		int i = 4;
		double u = 2.5;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		double[] n = strategy.basisFunctions(i, u, p, uVector);
		compareVectors(n, new double[] {0.125, 0.75, 0.125}, 3);
	}

	public void testOneBasisFunctionN42() {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 2;
		int i = 4;
		int m = 10; 
		double u = 2.5;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		double value = strategy.oneBasisFunction(p, m, uVector, i, u);
		assertEquals("N4,2(5/2) is not correct", 0.125, value);
	}

	public void testOneBasisFunctionN32() {
		BSplineStrategy strategy = new BSplineStrategy();
		int p = 2;
		int i = 3;
		int m = 10; 
		double u = 2.5;
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		double value = strategy.oneBasisFunction(p, m, uVector, i, u);
		assertEquals("N3,2(5/2) is not correct", 0.75, value);
	}

	public void testCreateKnotVectorP3() throws AlgorithmException {
		DataModel model = new DataModel();
		BSplineStrategy strategy = new BSplineStrategy();
		model.getControlPoints().add(new Node(3,2));
		model.getControlPoints().add(new Node(1,1));
		model.getControlPoints().add(new Node(8,8));
		model.getControlPoints().add(new Node(2,4));
		model.getControlPoints().add(new Node(4,8));
		model.getControlPoints().add(new Node(10,10));
		model.getControlPoints().add(new Node(10,10));
		int degree = 3;
		int n = model.getControlPoints().size() - 1;
		double[] result = new double[] {0.00, 0.00, 0.00, 0.00, 0.25, 0.5, 0.75, 1.00, 1.00, 1.00, 1.00};
		model.setKnots(new double[] {0, 0.25, 0.5, 0.75, 1});
		double[] knotVector = strategy.createKnotVector(model, degree, n);
		compareVectors(result, knotVector, 4);
	}

	public void testCreateKnotVectorP4() throws AlgorithmException {
		DataModel model = new DataModel();
		model.getControlPoints().add(new Node(3,2));
		model.getControlPoints().add(new Node(1,1));
		model.getControlPoints().add(new Node(8,8));
		model.getControlPoints().add(new Node(2,4));
		model.getControlPoints().add(new Node(4,8));
		model.getControlPoints().add(new Node(10,10));
		model.getControlPoints().add(new Node(10,10));
		BSplineStrategy strategy = new BSplineStrategy();
		int degree = 4;
		int n = model.getControlPoints().size() - 1;
		double[] result = new double[] {0, 0, 0, 0, 0, 0.333333, 0.66666, 1, 1, 1, 1, 1};
		model.setKnots(new double[] {0, 0.333333, 0.66666, 1});
		double[] knotVector = strategy.createKnotVector(model, degree, n);
		compareVectors(result, knotVector, 4);
	}

	public void testCurvePointP2U25() throws AlgorithmException {
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		int p = 2;
		double u = 2.5;
		DataModel model = new DataModel();
		model.getControlPoints().add(new Node(3,2));
		model.getControlPoints().add(new Node(1,1));
		model.getControlPoints().add(new Node(8,8));
		model.getControlPoints().add(new Node(2,4));
		model.getControlPoints().add(new Node(4,8));
		model.getControlPoints().add(new Node(10,10));
		BSplineStrategy strategy = new BSplineStrategy();
		Node point = strategy.curvePoint(p, uVector, model, u);
		assertEquals("X coordinate is not correct", 3.0, point.getX());
		assertEquals("Y coordinate is not correct", 5.0, point.getY());
	}

	public void testCurvePointP2U0() throws AlgorithmException {
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		int p = 2;
		double u = 0;
		DataModel model = new DataModel();
		model.getControlPoints().add(new Node(3,2));
		model.getControlPoints().add(new Node(1,1));
		model.getControlPoints().add(new Node(8,8));
		model.getControlPoints().add(new Node(2,4));
		model.getControlPoints().add(new Node(4,8));
		model.getControlPoints().add(new Node(10,10));
		BSplineStrategy strategy = new BSplineStrategy();
		Node point = strategy.curvePoint(p, uVector, model, u);
		assertEquals("X coordinate is not correct", 3.0, point.getX());
		assertEquals("Y coordinate is not correct", 2.0, point.getY());
	}

	public void testCurvePointP2U5() throws AlgorithmException {
		double[] uVector = new double[] {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
		int p = 2;
		double u = 4.99;
		DataModel model = new DataModel();
		model.getControlPoints().add(new Node(3,2));
		model.getControlPoints().add(new Node(1,1));
		model.getControlPoints().add(new Node(8,8));
		model.getControlPoints().add(new Node(2,4));
		model.getControlPoints().add(new Node(4,8));
		model.getControlPoints().add(new Node(10,10));
		BSplineStrategy strategy = new BSplineStrategy();
		Node point = strategy.curvePoint(p, uVector, model, u);
		assertEquals("X coordinate is not correct", 10.0, point.getX());
		assertEquals("Y coordinate is not correct", 10.0, point.getY());
	}

	public void testCurvePointP3U0() throws AlgorithmException {
		double[] uVector = new double[] {0, 0, 0, 0, 1, 1, 1, 1};
		int p = 3;
		double u = 0;
		DataModel model = new DataModel();
		model.getControlPoints().add(new Node(10, 10));
		model.getControlPoints().add(new Node(20, 20));
		model.getControlPoints().add(new Node(30, 30));
		model.getControlPoints().add(new Node(40, 40));
		BSplineStrategy strategy = new BSplineStrategy();
		Node point = strategy.curvePoint(p, uVector, model, u);
		assertEquals("X coordinate is not correct", 10.0, point.getX());
		assertEquals("Y coordinate is not correct", 10.0, point.getY());
	}

	public void testCurvePointP1U0() throws AlgorithmException {
		double[] uVector = new double[] {0, 0, 1, 1};
		int p = 1;
		double u = 0;
		DataModel model = new DataModel();
		model.getControlPoints().add(new Node(312, 211));
		model.getControlPoints().add(new Node(414, 320));
		BSplineStrategy strategy = new BSplineStrategy();
		Node point = strategy.curvePoint(p, uVector, model, u);
		assertEquals("X coordinate is not correct", 312.0, point.getX());
		assertEquals("Y coordinate is not correct", 211.0, point.getY());
	}
}
