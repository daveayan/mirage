package com.daveayan.mirage.comparator;

import com.daveayan.mirage.ObjectComparator;

public interface Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) ;
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) ;
}