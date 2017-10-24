package tree23;


public class DataObject implements Comparable<DataObject> {
	Object Data;
	String Key;
	int Count;
	
	public DataObject(Object data, String key) {
		Data = data;
		Key = key;
		Count = 1;
	}
	
	@Override
	public int compareTo(DataObject arg0) {
		return Key.compareTo(arg0.Key);
	}
}
