package mirage.comparator.impl;

import mirage.ObjectComparator;
import mirage.ReflectionUtils;
import mirage.comparator.Comparator;

public class FieldBasedComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return true;
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return oc.compare(ReflectionUtils.getAllFieldsIn(objectLeft), ReflectionUtils.getAllFieldsIn(objectRight));
	}
}