package mirage.comparator.impl;

import mirage.ObjectComparator;
import mirage.comparator.Comparator;

public class LeafBooleanComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof Boolean) && (objectRight instanceof Boolean);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Boolean) objectLeft).equals((Boolean) objectRight);
	}	
}