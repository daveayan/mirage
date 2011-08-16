package mirage.comparator.impl;

import mirage.ObjectComparator;
import mirage.comparator.Comparator;

public class LeafDoubleComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof Double) & (objectRight instanceof Double);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Double) objectLeft).equals((Double) objectRight);
	}	
}