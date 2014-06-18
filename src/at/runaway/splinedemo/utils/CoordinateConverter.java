package at.runaway.splinedemo.utils;

import at.runaway.splinedemo.model.Node;

/**
 * Converts real coordinates to canvas coordinates
 * 
 * @author christianluger
 */
public class CoordinateConverter {
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	public static final int DIMENSION_X = 0;
	
	public static final int DIMENSION_Y = 1;

	// --------------------------------------------------------------------------
	// M E M B E R  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private int zeroX;
	
	private int zeroY;
	
	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	public void setZeroPoint(int zeroX, int zeroY) {
		this.zeroX = zeroX;
		this.zeroY = zeroY;
	}
	
	public Node convertToScreen(Node p) {
		Node result = new Node();
		result.setText(p.getText());
		result.setX(p.getX() + zeroX);
		result.setY(-p.getY() + zeroY);
		return result;
	}
	
	public Node convertToReal(Node p) {
		Node result = new Node();
		result.setText(p.getText());
		result.setX(p.getX() - zeroX);
		result.setY(-p.getY() + zeroY);
		return result;
	}
	
	public int convertToScreen(int screen, int dimension) {
		if (dimension == DIMENSION_X) {
			return screen + zeroX;
		}
		if (dimension == DIMENSION_Y) {
			return -screen + zeroY;
		}
		return 0;
	}
}
