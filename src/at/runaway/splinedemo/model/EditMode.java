package at.runaway.splinedemo.model;

import java.io.Serializable;

/**
 * Edit modes for curve and control points
 * 
 * @author christianluger
 */
public enum EditMode implements Serializable {

	/**
	 * Creates new points
	 */
	NEW,
	
	/**
	 * Edits existing points
	 */
	EDIT,
	
	/**
	 * Edits derivate points
	 */
	EDIT_DERVIVATES,
	
	/**
	 * Deletes points
	 */
	DELETE
}
