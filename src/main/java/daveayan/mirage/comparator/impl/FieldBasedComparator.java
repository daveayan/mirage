package daveayan.mirage.comparator.impl;

import daveayan.mirage.ObjectComparator;
import daveayan.mirage.ReflectionUtils;
import daveayan.mirage.comparator.Comparator;

public class FieldBasedComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return true;
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return oc.compare(ReflectionUtils.getAllFieldsIn(objectLeft), ReflectionUtils.getAllFieldsIn(objectRight));
	}
}