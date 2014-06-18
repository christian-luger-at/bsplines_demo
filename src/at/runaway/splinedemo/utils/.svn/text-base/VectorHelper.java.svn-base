package at.runaway.splinedemo.utils;

import at.runaway.splinedemo.model.Node;

public class VectorHelper {
		
	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	/**
	 * Berechnet das Skalarprodukt zweier Vektoren
	 * 
	 * @param v1 Vektor 1
	 * @param v2 Vektor 2
	 * 
	 * @return Skalarprodukt
	 */
	public static double dotProduct(Node v1, Node v2) {
		return v1.getX() * v2.getX() + v1.getY() * v2.getY() ; 
	}

	/**
	 * Multipliziert einen Vektor mit einer Konstanten
	 * 
	 * @param vector
	 * @param factor
	 * @return
	 */
	public static Node multiply(Node vector, double factor) {
		Node result = new Node();
		result.setX(factor * vector.getX());
		result.setY(factor * vector.getY());
		return result;
	}
	
	
	/**
	 * Dividiert einen Vektor durch eine Konstante
	 * 
	 * @param vector
	 * @param factor
	 * @return
	 */
	public static Node divide(Node vector, double factor) {
		return multiply(vector, 1 / factor);
	}

	/**
	 * Summiert zwei Vectoren
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Node add(Node ...nodes) {
		return add(1, nodes);
	}
	
	/**
	 * Subtrahiert zwei Vectoren
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Node subtract(Node ...nodes) {
		return add(-1, nodes);
	}
	
	/**
	 * Normalisiert einen Vektor
	 * 
	 * @param v
	 * @return normalisierter Vektor
	 */
	public static Node normalize(Node v) {
		double length = Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2));
		return VectorHelper.multiply(v, 1 / length);
	}
	
	/**
	 * Berechnet die Laenge zwischen 2 Vektoren
	 * 
	 * @param a Vektor
	 * @param b Vektor
	 * @return Laenge
	 */
	public static double length(Node a, Node b) {
		double x = Math.abs(a.getX() - b.getX());
		double y = Math.abs(a.getY() - b.getY());
		return Math.sqrt(x*x + y*y);
	}

	// --------------------------------------------------------------------------
	// P R I V A T E  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	private static Node add(double factor, Node ...nodes) {
		Node result = new Node(nodes[0].getX(), nodes[0].getY());
		for (int i = 1; i < nodes.length; i++) {
			result.setX(result.getX() + nodes[i].getX() * factor);
			result.setY(result.getY() + nodes[i].getY() * factor);
		}
		return result;
	}

}
