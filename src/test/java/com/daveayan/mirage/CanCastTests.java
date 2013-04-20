package com.daveayan.mirage;

import static com.daveayan.mirage.ClassReflector.clazz;
import static com.daveayan.mirage.ObjectReflector.object;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CanCastTests {
	@Test public void casting_with_null_values() {
		assertFalse("with null values nothing can be casted 1", clazz(null).canBeCastedTo(null));
		assertFalse("with null values nothing can be casted 2", clazz(null).canBeCastedTo(String.class));
		assertFalse("with null values nothing can be casted 3", clazz(String.class).canBeCastedTo(null));
		assertFalse("with null values nothing can be casted 2", clazz(null).canBeCastedTo(new String()));
		
		assertFalse("with null values nothing can be casted 1", object(null).canBeCastedTo(null));
		assertFalse("with null values nothing can be casted 2", object(null).canBeCastedTo(String.class));
		assertFalse("with null values nothing can be casted 3", object(String.class).canBeCastedTo(null));
		assertFalse("with null values nothing can be casted 3", object(new String()).canBeCastedTo(null));
	}
	
	@Test public void can_cast_class_to_class() {
		assertFalse("String cannot be casted to Integer", clazz(String.class).canBeCastedTo(Integer.class));
		assertTrue("String can be casted to String", clazz(String.class).canBeCastedTo(String.class));
		assertFalse("Integer cannot be casted to String", clazz(Integer.class).canBeCastedTo(String.class));
		
		assertTrue("Objects can be casted up the object hierarchy", clazz(ArrayList.class).canBeCastedTo(AbstractCollection.class));
		assertFalse("Objects cannot be casted down in the object hierarchy", clazz(AbstractCollection.class).canBeCastedTo(ArrayList.class));
		
		assertTrue("Class can be casted to the interface it implements", clazz(ArrayList.class).canBeCastedTo(List.class));
		assertFalse("Interface cannot be casted to the class it implements", clazz(List.class).canBeCastedTo(ArrayList.class));
	}
	
	@Test public void can_cast_object_to_object() {
		assertFalse("String cannot be casted to Integer", object(new String("AA")).canBeCastedTo(new Integer(1)));
		assertTrue("String can be casted to String", object(new String("AA")).canBeCastedTo(new String("AA")));
		assertFalse("Integer cannot be casted to String", object(new Integer(1)).canBeCastedTo(new String("AA")));
		
		assertTrue("Objects can be casted up the object hierarchy", object(new ArrayList<String>()).canBeCastedTo(AbstractCollection.class));
		assertFalse("Objects cannot be casted down in the object hierarchy", object(AbstractCollection.class).canBeCastedTo(new ArrayList<String>()));
		
		assertTrue("Class can be casted to the interface it implements", object(new ArrayList<String>()).canBeCastedTo(List.class));
		assertFalse("Interface cannot be casted to the class it implements", object(List.class).canBeCastedTo(new ArrayList<String>()));
	}
}