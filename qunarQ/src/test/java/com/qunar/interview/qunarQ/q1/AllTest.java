package com.qunar.interview.qunarQ.q1;

import junit.framework.Test;
import junit.framework.TestSuite;



import com.qunar.interview.qunarQ.q2.MapDiffTest;
import com.qunar.interview.qunarQ.q3.UserInfoAPITest;

//@RunWith(Suite.class)
//@SuiteClasses({
// CitySearchTest.class,
// MapDiffTest.class,
//// UserInfoAPITest.class,
//  })
public class AllTest {
	public static void main(String args){
		TestSuite suite=new TestSuite("Test for junit");
		suite.addTestSuite(MapDiffTest.class);
	//	junit.swingui.TestRunner.run(suite);
//	junit.textui.TestRunner.run(AllTest.suite());
		
//	junit.awtui.TesstRunner.run(AllTest.suite());
	}
	
	
}