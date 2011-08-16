package mirage.comparator.impl;

import mirage.ObjectComparator;
import mirage.ReflectionUtils;
import mirage.comparator.Comparator;

public class LeafDoubleComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ReflectionUtils.objectIsOfType(objectLeft, Double.class)
				&& ReflectionUtils.objectIsOfType(objectRight, Double.class);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Double) objectLeft).equals((Double) objectRight);
	}	
}