package mirage.comparator.impl;

import mirage.ObjectComparator;
import mirage.comparator.Comparator;

public class LeafIntegerComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof Integer) & (objectRight instanceof Integer);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Integer) objectLeft).equals((Integer) objectRight);
	}	
}