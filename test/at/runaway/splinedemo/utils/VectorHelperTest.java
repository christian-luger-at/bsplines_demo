package at.runaway.splinedemo.utils;

import at.runaway.splinedemo.AbstractTestCase;
import at.runaway.splinedemo.model.Node;

public class VectorHelperTest extends AbstractTestCase {

	public void testDotProduct() {
		Node v1 = new Node(10, 10);
		Node v2 = new Node(10, 0);
		double expected = 100;
		double actual = VectorHelper.dotProduct(v1, v2);
		assertEquals(expected, actual);
	}
	
	public void testMultiply() {
		Node v1 = new Node(1, 1);
		Node expected = new Node(0.5, 0.5);
		Node actual = VectorHelper.multiply(v1, 0.5);
		compareNodeCoords(expected, actual, 4);
	}

	public void testDivide() {
		Node v1 = new Node(1, 1);
		Node expected = new Node(2, 2);
		Node actual = VectorHelper.divide(v1, 0.5);
		compareNodeCoords(expected, actual, 4);
	}

	public void testAdd() {
		Node v1 = new Node(1, 1);
		Node v2 = new Node(10, 0);
		Node v3 = new Node(0.5, 0.5);
		Node expected = new Node(11.5, 1.5);
		Node actual = VectorHelper.add(v1, v2, v3);
		compareNodeCoords(expected, actual, 4);
	}

	public void testSubtract() {
		Node v1 = new Node(1, 1);
		Node v2 = new Node(10, 0);
		Node v3 = new Node(0.5, 0.5);
		Node expected = new Node(-9.5, 0.5);
		Node actual = VectorHelper.subtract(v1, v2, v3);
		compareNodeCoords(expected, actual, 4);
	}
	
	public void testNormalize() {
		Node v1 = new Node(10, 10);
		Node expected = new Node(0.70710678, 0.70710678);
		Node actual = VectorHelper.normalize(v1);
		compareNodeCoords(expected, actual, 4);
	}	
	
	public void testLength1() {
		Node a = new Node(10, 10);
		Node b = new Node(100, 100);
		double expected = 127.27922;
		double actual = VectorHelper.length(a, b);
		assertEquals(expected, actual, 4);
	}

	public void testLength2() {
		Node a = new Node(100, 100);
		Node b = new Node(10, 10);
		double expected = 127.27922;
		double actual = VectorHelper.length(a, b);
		assertEquals(expected, actual, 4);
	}
}
