package mirage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mirage.comparator.Comparator;
import mirage.comparator.impl.IterableComparator;
import mirage.comparator.impl.LeafBooleanComparator;
import mirage.comparator.impl.LeafDoubleComparator;
import mirage.comparator.impl.LeafIntegerComparator;
import mirage.comparator.impl.LeafStringComparator;

public class ObjectComparator {
	private List<Comparator> list_of_comparators = new ArrayList<Comparator>();
	
	public boolean compare(Object objectLeft, Object objectRight) {
		return compareObjects(objectLeft, objectRight);
	}
	
	private boolean compareObjects(Object objectLeft, Object objectRight) {
		Iterator<Comparator> iter = list_of_comparators.iterator();
		while(iter.hasNext()) {
			Comparator c = iter.next();
			if(c.canCompare(objectLeft, objectRight, this)) {
				return c.compare(objectLeft, objectRight, this);
			}
		}
		return false;
	}
	
	public static ObjectComparator newInstance() {
		ObjectComparator oc = new ObjectComparator();
		oc.initialize();
		return oc;
	}
	private void initialize() {
		list_of_comparators.add(new LeafStringComparator());
		list_of_comparators.add(new LeafIntegerComparator());
		list_of_comparators.add(new LeafDoubleComparator());
		list_of_comparators.add(new LeafBooleanComparator());
		list_of_comparators.add(new IterableComparator());
	}
	private ObjectComparator() {}
}