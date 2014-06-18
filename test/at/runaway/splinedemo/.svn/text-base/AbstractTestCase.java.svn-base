package at.runaway.splinedemo;

import java.util.List;

import at.runaway.splinedemo.model.Node;
import junit.framework.TestCase;

/**
 * Base test for all tests
 * 
 * @author christianluger
 */
public class AbstractTestCase extends TestCase {

	private static final boolean DEBUG = false;

	protected void compareVectors(double[] a1, double[] a2, int positionsAfterDecimal) {
		assertEquals("Array length does not match", a1.length, a2.length);
		double delta = Math.pow(10, - positionsAfterDecimal);
		for (int i = 0; i < a1.length; i++) {
			if (DEBUG) {
				System.out.println("[" + i + "]: " + a2[i] + "=" + a1[i]);
			}
			assertEquals(a2[i], a1[i], delta);
		}
	}
	
	protected void compareNodeCoords(Node n1, Node n2, int positionsAfterDecimal) {
		compareVectors(n1.getCoordinates(), n2.getCoordinates(), positionsAfterDecimal);
	}
	
	protected void comparePoints(List<Node> l1, List<Node> l2, int positionsAfterDecimal) {
		if (l1.size() != l2.size()) {
			assertTrue("List size is not equal", false);
		}
		for (int i = 0; i < l1.size(); i++) {
			compareNodeCoords(l1.get(i), l2.get(i), positionsAfterDecimal);
		}
	}

}
