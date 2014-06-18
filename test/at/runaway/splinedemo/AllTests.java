package at.runaway.splinedemo;

import at.runaway.splinedemo.service.BSplineStrategyTest;
import at.runaway.splinedemo.service.CubicSplineInterpolationStrategyTest;
import at.runaway.splinedemo.service.NURBSStrategyTest;
import at.runaway.splinedemo.utils.VectorHelperTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("All tests");
		suite.addTestSuite(VectorHelperTest.class);
		suite.addTestSuite(BSplineStrategyTest.class);
		suite.addTestSuite(NURBSStrategyTest.class);
		suite.addTestSuite(CubicSplineInterpolationStrategyTest.class);
		return suite;
	}

}
