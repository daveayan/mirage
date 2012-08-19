package mirage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import daveayan.mirage.ObjectComparator;
import daveayan.mirage.ObjectUtils;

import sample.domain.Car;

@RunWith(Parameterized.class)
public class ObjectComparator_compare_Test {
	@Parameters
	public static Collection<Object[]> generateData() {
		List<Object[]> testcases = new ArrayList<Object[]> ();
		addTestCaseInto(testcases, "hello", "hello", true);
		addTestCaseInto(testcases, "hello", "hello", true);
		addTestCaseInto(testcases, "hello", "helloWorld", false);
		addTestCaseInto(testcases, "hello", "HELLO", false);
		addTestCaseInto(testcases, "hello", false, false);
		
		addTestCaseInto(testcases, 123, new Integer(123), true);
		addTestCaseInto(testcases, new Integer(123), new Integer(123), true);
		addTestCaseInto(testcases, 123, 123, true);
		addTestCaseInto(testcases, 123, new Integer(1234), false);
		addTestCaseInto(testcases, 123, "123", false);
		addTestCaseInto(testcases, 123, false, false);
		
		addTestCaseInto(testcases, 123.45, 123.45, true);
		addTestCaseInto(testcases, 123.45, new Double(123.45), true);
		addTestCaseInto(testcases, new Double(123.45), new Double(123.45), true);
		addTestCaseInto(testcases, 123.456, 123.45, false);
		addTestCaseInto(testcases, 123.456, 123, false);
		addTestCaseInto(testcases, 123.456, "123.456", false);
		addTestCaseInto(testcases, 123.456, true, false);
		
		addTestCaseInto(testcases, true, true, true);
		addTestCaseInto(testcases, false, false, true);
		addTestCaseInto(testcases, true, new Boolean(true), true);
		addTestCaseInto(testcases, new Boolean(true), true, true);
		addTestCaseInto(testcases, new Boolean(true), new Boolean(true), true);
		addTestCaseInto(testcases, true, false, false);
		addTestCaseInto(testcases, false, true, false);
		addTestCaseInto(testcases, new Boolean(true), false, false);
		addTestCaseInto(testcases, new Boolean(false), true, false);
		
		addTestCaseInto(testcases, ObjectUtils.asList(123), ObjectUtils.asList(123), true);
		addTestCaseInto(testcases, ObjectUtils.asList(123), ObjectUtils.asList(123, 456), false);
		addTestCaseInto(testcases, ObjectUtils.asList(123, 456), ObjectUtils.asList(123), false);
		addTestCaseInto(testcases, ObjectUtils.asList(123), ObjectUtils.asList(), false);
		
		addTestCaseInto(testcases, new Car(), new Car(), false);
		
		return testcases;
	}
	
	@Test public void testMethod() {
		Assert.assertEquals(cth.objectLeft + " : " + cth.objectRight + " : "
				+ expectedValue, expectedValue,
				oc.compare(cth.objectLeft, cth.objectRight));
	}
	
	private static void addTestCaseInto(List<Object[]> testcases, Object ol, Object or, boolean ev) {
		testcases.add(new Object[] {new CompareTestHolder(ol, or), ev});
	}
	
	public ObjectComparator_compare_Test(CompareTestHolder c, boolean ev) {
		this.cth = c;
		this.expectedValue = ev;
		oc = ObjectComparator.newInstance();
	}
	ObjectComparator oc = null;
	CompareTestHolder cth = null;
	boolean expectedValue;
}

class CompareTestHolder {
	public Object objectLeft, objectRight;
	public CompareTestHolder (Object ol, Object or) {
		this.objectLeft = ol;
		this.objectRight = or;
	}
	public CompareTestHolder() {}
}