package at.runaway.splinedemo.service;

import java.io.Serializable;
import java.util.List;

import at.runaway.splinedemo.model.DataModel;
import at.runaway.splinedemo.model.Node;

/**
 * Interface for all bspline strategies
 * 
 * @author christianluger
 */
public interface PaintStrategy extends Serializable {

	/**
	 * Creates curve points
	 * @param model
	 * @return
	 * @throws AlgorithmException
	 */
	List<Node> createCurvePoints(DataModel model) throws AlgorithmException;

	/**
	 * Creates basis function points
	 * 
	 * @param model
	 * @param n
	 * @param p
	 * @return
	 * @throws AlgorithmException
	 */
	List<List<Node>> createBasisFunctionPoints(DataModel model, int n, int p) throws AlgorithmException;

	/**
	 * Calculates knot vector points
	 * 
	 * @param model
	 * @return
	 * @throws AlgorithmException
	 */
	List<Node> calculateKnotVectorPoints(DataModel model) throws AlgorithmException;
	
	/**
	 * Creates a array with knot vector values
	 * 
	 * @param model
	 * @param p
	 * @param n
	 * @return
	 * @throws AlgorithmException
	 */
	double[] createKnotVector(DataModel model, int p, int n) throws AlgorithmException;
}
