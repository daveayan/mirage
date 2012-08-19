package daveayan.mirage.comparator.impl;

import daveayan.mirage.ObjectComparator;
import daveayan.mirage.comparator.Comparator;

public class LeafBooleanComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof Boolean) && (objectRight instanceof Boolean);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Boolean) objectLeft).equals((Boolean) objectRight);
	}	
}