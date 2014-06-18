package at.runaway.splinedemo.service;

public abstract class GenericStrategy {

	// --------------------------------------------------------------------------
	// P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------

	/**
	 * Compute the non-vanishing basis function (see page 70, A2.2)
	 * 
	 * @param i span number where u can be found
	 * @param u given knot position
	 * @param p degree
	 * @param uVector knot vector
	 * @return N[i,p]-Array
	 */
	protected double[] basisFunctions(int i, double u, int p, double[] uVector) {
		double[] n = new double[p+1];
		double[] left = new double[p+1]; 
		double[] right = new double[p+1]; 
		double saved;
		double temp;
		n[0] = 1.0;
		for (int j = 1; j <= p; j++) {
			left[j] = u - uVector[i+1-j];
			right[j] = uVector[i+j] - u;
			saved = 0;
			for (int r = 0; r < j; r++) {
				temp = n[r] / (right[r+1] + left[j-r]);
				n[r] = saved + right[r+1] * temp;
				saved = left[j-r] * temp;
			}
			n[j] = saved;
		}
		return n;
	}
}
