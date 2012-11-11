package com.daveayan.mirage.comparator.impl;

import com.daveayan.mirage.ObjectComparator;
import com.daveayan.mirage.ReflectionUtils;
import com.daveayan.mirage.comparator.Comparator;


public class FieldBasedComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return true;
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return oc.compare(ReflectionUtils.getAllFieldsIn(objectLeft), ReflectionUtils.getAllFieldsIn(objectRight));
	}
}