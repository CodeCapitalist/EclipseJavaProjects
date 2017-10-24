package sorts;

public class SortableArray<T extends Comparable<T>> {
	T[] GenList;

	
	public SortableArray(T[] theList) {
		GenList = theList;
	}
	
	public static SortableArray<Integer> createSortableArrayFromInts(int[] theList) {
		Integer[] temp = new Integer[theList.length];	
		for(int i = 0; i < theList.length; i++) {
			temp[i] = new Integer(theList[i]);
		}
		return new SortableArray<Integer>(temp);
	}
}
