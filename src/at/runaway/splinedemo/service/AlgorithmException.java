package at.runaway.splinedemo.service;

/**
 * This exception will be thrown if an error occurs while computing the spline data
 * 
 * @author christianluger
 */
public class AlgorithmException extends Exception {

	private static final long serialVersionUID = 4875872521796172460L;

	public AlgorithmException(String message) {
		super(message);
	}
	
	public AlgorithmException(String message, Throwable cause) {
		super(message, cause);
	}

}
