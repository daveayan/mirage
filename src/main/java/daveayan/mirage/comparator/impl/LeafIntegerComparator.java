package daveayan.mirage.comparator.impl;

import daveayan.mirage.ObjectComparator;
import daveayan.mirage.comparator.Comparator;

public class LeafIntegerComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof Integer) & (objectRight instanceof Integer);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Integer) objectLeft).equals((Integer) objectRight);
	}	
}