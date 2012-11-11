package com.daveayan.mirage.comparator.impl;

import com.daveayan.mirage.ObjectComparator;
import com.daveayan.mirage.comparator.Comparator;


public class LeafBooleanComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof Boolean) && (objectRight instanceof Boolean);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Boolean) objectLeft).equals((Boolean) objectRight);
	}	
}