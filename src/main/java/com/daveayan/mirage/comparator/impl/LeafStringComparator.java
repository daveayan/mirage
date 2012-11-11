package com.daveayan.mirage.comparator.impl;


import org.apache.commons.lang.StringUtils;

import com.daveayan.mirage.ObjectComparator;
import com.daveayan.mirage.comparator.Comparator;


public class LeafStringComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof String) && (objectRight instanceof String);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return StringUtils.equals(objectLeft.toString(), objectRight.toString());
	}	
}