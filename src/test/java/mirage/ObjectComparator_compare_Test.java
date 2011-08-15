package mirage;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ObjectComparator_compare_Test {
	@Test public void whenComparingStringsWithSameDataReturnTrue() {
		Assert.assertEquals(true, oc.compare("Hello", "Hello"));
	}
	@Test public void whenComparingStringsWithDifferentDataReturnFalse() {
		Assert.assertEquals(false, oc.compare("Hello", "HelloWorld"));
	}
	@Test public void whenComparingStringsWithSameDataDifferentCaseReturnFalse() {
		Assert.assertEquals(false, oc.compare("Hello", "HELLO"));
	}
	ObjectComparator oc = null;
	@Before public void setup() {
		oc = ObjectComparator.newInstance();
	}
}