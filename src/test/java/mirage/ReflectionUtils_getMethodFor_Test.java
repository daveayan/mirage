package mirage;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Test;

import com.daveayan.mirage.ReflectionUtils;

public class ReflectionUtils_getMethodFor_Test {
	@Test public void can_cast() {
		long pLong = 5l;
		Long oLong = new Long(10l);
		boolean pBoolean = true;
		Boolean oBoolean = Boolean.FALSE;

		Assert.assertTrue(ReflectionUtils.canCast(oLong, pLong));
		Assert.assertTrue(ReflectionUtils.canCast(pLong, oLong));

		Assert.assertTrue(ReflectionUtils.canCast(oBoolean, pBoolean));
		Assert.assertTrue(ReflectionUtils.canCast(pBoolean, oBoolean));

		Assert.assertFalse(ReflectionUtils.canCast(oBoolean, oLong));
		Assert.assertFalse(ReflectionUtils.canCast(pBoolean, pLong));

		Assert.assertFalse(ReflectionUtils.canCast(oLong, pBoolean));
		Assert.assertFalse(ReflectionUtils.canCast(pBoolean, oLong));
	}

	@Test public void between_primitive_and_wrapper_classes_parameters() {
		SomeClass c = new SomeClass();

		long pLong = 5l;
		Long oLong = new Long(10l);
		boolean pBoolean = true;
		Boolean oBoolean = Boolean.FALSE;

		Method actualMethod = null;

		ReflectionUtils.canCast(oLong, pLong);

		actualMethod = ReflectionUtils.getMethodFor(c, "method1", new Object[] { pLong, pBoolean });
		Assert.assertEquals("method1", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method1", new Object[] { oLong, pBoolean });
		Assert.assertEquals("method1", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method1", new Object[] { pLong, oBoolean });
		Assert.assertEquals("method1", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method1", new Object[] { oLong, oBoolean });
		Assert.assertEquals("method1", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method2", new Object[] { pLong, pBoolean });
		Assert.assertEquals("method2", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method2", new Object[] { oLong, pBoolean });
		Assert.assertEquals("method2", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method2", new Object[] { pLong, oBoolean });
		Assert.assertEquals("method2", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method2", new Object[] { oLong, oBoolean });
		Assert.assertEquals("method2", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method3", new Object[] { pLong, pBoolean });
		Assert.assertEquals("method3", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method3", new Object[] { oLong, pBoolean });
		Assert.assertEquals("method3", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method3", new Object[] { pLong, oBoolean });
		Assert.assertEquals("method3", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method3", new Object[] { oLong, oBoolean });
		Assert.assertEquals("method3", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method4", new Object[] { pLong, pBoolean });
		Assert.assertEquals("method4", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method4", new Object[] { oLong, pBoolean });
		Assert.assertEquals("method4", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method4", new Object[] { pLong, oBoolean });
		Assert.assertEquals("method4", actualMethod.getName());

		actualMethod = ReflectionUtils.getMethodFor(c, "method4", new Object[] { oLong, oBoolean });
		Assert.assertEquals("method4", actualMethod.getName());
	}
}

class SomeClass {
	public long method1(long longValue, boolean booleanValue) {
		return booleanValue ? longValue + 10 : longValue - 10;
	}

	public long method2(Long longValue, boolean booleanValue) {
		return booleanValue ? longValue + 10 : longValue - 10;
	}

	public long method3(long longValue, Boolean booleanValue) {
		return booleanValue ? longValue + 10 : longValue - 10;
	}

	public long method4(Long longValue, Boolean booleanValue) {
		return booleanValue ? longValue + 10 : longValue - 10;
	}
}