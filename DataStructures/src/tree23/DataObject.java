package tree23;


public class DataObject {
	Object Data;
	String Key;
	int Count;
	
	public DataObject(Object data, String key) {
		Data = data;
		Key = key;
		Count = 1;
	}
	
	public int compare (DataObject d1) {
		return Key.compareTo(d1.Key);
	}
	
	public int compare (String key) {
		return Key.compareTo(key);
	}
}
