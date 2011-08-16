package mirage.comparator;

import mirage.ObjectComparator;

public interface Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) ;
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) ;
}