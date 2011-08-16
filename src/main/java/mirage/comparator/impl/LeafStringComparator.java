package mirage.comparator.impl;

import mirage.ObjectComparator;
import mirage.comparator.Comparator;

import org.apache.commons.lang.StringUtils;

public class LeafStringComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof String) && (objectRight instanceof String);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return StringUtils.equals(objectLeft.toString(), objectRight.toString());
	}	
}