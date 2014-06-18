package at.runaway.splinedemo.model;

import java.io.Serializable;

import at.runaway.splinedemo.utils.CommonConstants;


/**
 * Representation of a 2D-Point
 * 
 * @author christianluger
 */
public class Node implements Serializable {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final long serialVersionUID = 6909772951429257756L;

	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private double x;
	
	private double y;

	private double weight = 1;
	
	private String text;
	
	// --------------------------------------------------------------------------
	// C O N S T R U C T O R S  
	// --------------------------------------------------------------------------
	
	public Node() {
		// do nothing
	}

	public Node(double x, double y) {
		this(x, y, 1, "");
	}
	
	public Node(double x, double y, String text) {
		this(x, y, 1, text); 
	}

	public Node(double x, double y, double weight, String text) {
		super();
		this.x = x;
		this.y = y;
		this.weight = weight;
		this.text = text;
	}

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	public String getScreenText() {
		return text == null ? "": text;
	} 
	
	public String toString() {
		StringBuffer result = new StringBuffer("[Node: ");
		result.append(text);
		result.append(": ");
		result.append(CommonConstants.KNOT_FORMAT.format(x));
		result.append(" ");
		result.append(CommonConstants.KNOT_FORMAT.format(y));
		result.append("]");
		return result.toString();
	}
	
	public double[] getCoordinates() {
		return new double[] {x, y};
	}
	
	public int getIntX() {
		return (int)x;
	}

	public int getIntY() {
		return (int)y;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
