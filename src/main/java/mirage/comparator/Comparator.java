package mirage.comparator;

public interface Comparator {
	public boolean canCompare(Object objectLeft, Object objectRight) ;
	public boolean compare(Object objectLeft, Object objectRight) ;
}