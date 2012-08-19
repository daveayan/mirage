package mirage;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import daveayan.mirage.ReflectionUtils;

import sample.domain.Person;

public class ReflectionUtils_getFieldInObjectSafely_Test {
	@Test public void whenObjectIsNullReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getFieldInObjectSafely(null, "name"));
	}
	@Test public void whenFieldNameIsNullReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getFieldInObjectSafely(Person.getFullyLoadedInstance(), null));
	}
	@Test public void whenFieldNameDoesNotExistReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getFieldInObjectSafely(Person.getFullyLoadedInstance(), "name-does-not-exist"));
	}
	@Test public void whenFieldValueIsNull() {
		Assert.assertEquals(null, ReflectionUtils.getFieldInObjectSafely(Person.getPartialInstance1(), "address"));
	}
	@Test public void whenFieldValueIsString() {
		Assert.assertEquals("QWERTY", ReflectionUtils.getFieldInObjectSafely(Person.getPartialInstance1(), "name"));
	}
	@Test public void whenFieldValueIsList() {
		Object object = ReflectionUtils.getFieldInObjectSafely(Person.getFullyLoadedInstance(), "phones");
		Assert.assertNotNull(object);
		Assert.assertEquals(true, object instanceof List<?>);
		Assert.assertEquals(2, ((List<?>) object).size());
	}
	@Test public void whenFieldValueIsMap() {
		Object object = ReflectionUtils.getFieldInObjectSafely(Person.getFullyLoadedInstance(), "addresses");
		Assert.assertNotNull(object);
		Assert.assertEquals(true, object instanceof Map<?, ?>);
		Assert.assertEquals(2, ((Map<?, ?>) object).size());
	}
}