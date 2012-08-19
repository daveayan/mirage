package mirage;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import daveayan.mirage.ReflectionUtils;

import sample.domain.Person;

public class ReflectionUtils_getFieldInObject_Test {
	@Test public void whenObjectIsNullReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getFieldInObject(null, "name"));
	}
	@Test (expected=IllegalArgumentException.class) 
	public void whenFieldNameIsNullReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getFieldInObject(Person.getFullyLoadedInstance(), null));
	}
	@Test (expected=IllegalArgumentException.class)
	public void whenFieldNameDoesNotExistReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getFieldInObject(Person.getFullyLoadedInstance(), "name-does-not-exist"));
	}
	@Test (expected=IllegalArgumentException.class)
	public void whenFieldValueIsNull() {
		Assert.assertEquals(null, ReflectionUtils.getFieldInObject(Person.getPartialInstance1(), "address"));
	}
	@Test public void whenFieldValueIsString() {
		Assert.assertEquals("QWERTY", ReflectionUtils.getFieldInObject(Person.getPartialInstance1(), "name"));
	}
	@Test public void whenFieldValueIsList() {
		Object object = ReflectionUtils.getFieldInObject(Person.getFullyLoadedInstance(), "phones");
		Assert.assertNotNull(object);
		Assert.assertEquals(true, object instanceof List<?>);
		Assert.assertEquals(2, ((List<?>) object).size());
	}
	@Test public void whenFieldValueIsMap() {
		Object object = ReflectionUtils.getFieldInObject(Person.getFullyLoadedInstance(), "addresses");
		Assert.assertNotNull(object);
		Assert.assertEquals(true, object instanceof Map<?, ?>);
		Assert.assertEquals(2, ((Map<?, ?>) object).size());
	}
}