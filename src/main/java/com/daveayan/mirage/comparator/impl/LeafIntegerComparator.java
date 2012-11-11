package com.daveayan.mirage.comparator.impl;

import com.daveayan.mirage.ObjectComparator;
import com.daveayan.mirage.comparator.Comparator;


public class LeafIntegerComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof Integer) & (objectRight instanceof Integer);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return ((Integer) objectLeft).equals((Integer) objectRight);
	}	
}