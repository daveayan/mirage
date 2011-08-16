package mirage.comparator.impl;

import mirage.ObjectComparator;
import mirage.ReflectionUtils;
import mirage.comparator.Comparator;

public class LeafIntegerComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ReflectionUtils.objectIsOfType(objectLeft, Integer.class)
				&& ReflectionUtils.objectIsOfType(objectRight, Integer.class);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Integer) objectLeft).equals((Integer) objectRight);
	}	
}