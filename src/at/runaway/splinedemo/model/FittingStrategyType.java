package at.runaway.splinedemo.model;

import java.io.Serializable;

public enum FittingStrategyType implements Serializable {

	/**
	 * equally spaced
	 */
	EQUALLY_SPACED,
	
	/**
	 * cord length method
	 */
	CORD_LENGTH,
	
	/**
	 * centripedal method
	 */
	CENTRIPEDAL
}
