package mirage.comparator.impl;

import java.util.Iterator;

import mirage.ObjectComparator;
import mirage.comparator.Comparator;

public class IterableComparator implements Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		return (objectLeft instanceof Iterable) && (objectRight instanceof Iterable);
	}
	public boolean compare(Object objectLeft, Object objectRight, ObjectComparator oc) {
		Iterator<?> il = ((Iterable<?>) objectLeft).iterator();
		Iterator<?> ir = ((Iterable<?>) objectRight).iterator();
		while(true) {
			boolean hasMoreLeftElements = il.hasNext();
			boolean hasMoreRightElements = ir.hasNext();
			if(! hasMoreLeftElements && ! hasMoreRightElements) break;
			if(hasMoreLeftElements && hasMoreRightElements) {
				if(oc.compare(il.next(), ir.next()) == false) return false;
			}
			if(hasMoreLeftElements != hasMoreRightElements) return false;
		}
		return true;
	}	
}