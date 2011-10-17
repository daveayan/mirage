package mirage;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import sample.domain.Person;
import sample.domain.Results;

public class ReflectionUtils_getDeepFieldInObjectSafely_Test {
	@Test public void whenObjectIsNullReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getDeepFieldInObjectSafely(null, "name"));
	}
	@Test public void whenFieldNameIsNullReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getDeepFieldInObjectSafely(Person.getFullyLoadedInstance(), null));
	}
	@Test public void whenFieldNameDoesNotExistReturnNull() {
		Assert.assertEquals(null, ReflectionUtils.getDeepFieldInObjectSafely(Person.getFullyLoadedInstance(), "name-does-not-exist"));
	}
	@Test public void whenFirstLevelFieldValueIsNull() {
		Assert.assertEquals(null, ReflectionUtils.getDeepFieldInObjectSafely(Person.getPartialInstance1(), "address"));
	}
	@Test public void whenFirstLevelFieldValueIsString() {
		Assert.assertEquals("QWERTY", ReflectionUtils.getDeepFieldInObjectSafely(Person.getPartialInstance1(), "name"));
	}
	@Test public void whenFirstLevelFieldValueIsList() {
		Object object = ReflectionUtils.getDeepFieldInObjectSafely(Person.getFullyLoadedInstance(), "phones");
		Assert.assertNotNull(object);
		Assert.assertEquals(true, object instanceof List<?>);
		Assert.assertEquals(2, ((List<?>) object).size());
	}
	@Test public void whenFirstLevelFieldValueIsMap() {
		Object object = ReflectionUtils.getDeepFieldInObjectSafely(Person.getFullyLoadedInstance(), "addresses");
		Assert.assertNotNull(object);
		Assert.assertEquals(true, object instanceof Map<?, ?>);
		Assert.assertEquals(2, ((Map<?, ?>) object).size());
	}
	@Test public void whenSecondLevelFieldValueIsPrimitiveInt() {
		Assert.assertEquals(345678, ReflectionUtils.getDeepFieldInObjectSafely(Person.getFullyLoadedInstance(), "currentAccount.accountNumber"));
	}
	@Test public void whenAnyNestedFieldIsAList() {
		List<Object> objects = (List<Object>) ReflectionUtils.getDeepFieldInObject(Results.getFullyLoadedObject(), "list_of_persons.currentAccount.accountNumber");
		System.out.println(objects);
	}
}