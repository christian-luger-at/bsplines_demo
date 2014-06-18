package at.runaway.splinedemo.service;

import java.io.Serializable;
import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

public interface FittingStrategy extends Serializable {

	/**
	 * Calculates the control points of a given curve point list
	 * 
	 * @param dataModel
	 * @return
	 */
	List<Node> calculate(DataModel dataModel);
}
