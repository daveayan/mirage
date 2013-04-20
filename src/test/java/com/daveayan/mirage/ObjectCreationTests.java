package com.daveayan.mirage;

import org.junit.Test;

import static com.daveayan.mirage.ClassReflector.from;
import static com.daveayan.mirage.ClassReflector.clazz;
import static com.daveayan.mirage.ObjectReflector.object;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ObjectCreationTests {
	@Test public void create_objects_from_class() {
		Object actualObject = from(String.class).createObject();
		
	}
}