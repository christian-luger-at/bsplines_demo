package at.runaway.splinedemo.model;

import java.io.Serializable;

/**
 * Type of gui
 * 
 * @author christianluger
 */
public enum PaintMode implements Serializable {
	
	/**
	 * Painting curve based on control points
	 */
	PAINTING, 
	
	/**
	 * Painting curve based on curve points
	 */
	FITTING
}
