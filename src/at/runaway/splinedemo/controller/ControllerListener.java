package at.runaway.splinedemo.controller;

/**
 * A generic MVC view, or model listener.
 */
public interface ControllerListener {

	/**
	 * This method will be called if a model change has happened
	 */
	void modelChanged();
}
