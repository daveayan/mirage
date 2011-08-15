package mirage.comparator.impl;

import org.apache.commons.lang.StringUtils;

import mirage.ReflectionUtils;
import mirage.comparator.Comparator;

public class LeafStringComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight) {
		return ReflectionUtils.objectIsOfType(objectLeft, String.class)
				&& ReflectionUtils.objectIsOfType(objectRight, String.class);
	}
	public boolean compare(Object objectLeft, Object objectRight) {
		return StringUtils.equals(objectLeft.toString(), objectRight.toString());
	}	
}