package tree23;

public class TwoThreeTree {
	Node Root;
	int Size;
	int Height;
	
	public TwoThreeTree() {
		Root = new Node();
		Size = 0;
		Height = 1;
		System.out.println("LOG: NEW TREE CREATED. HEIGHT 1, SIZE 0");
	}
	
	public void Insert(DataObject d0) {
		System.out.printf("Insert: \"%s\"\n", d0.Key);
		Node n = Root;
		int matchingKeyIndex = -1;
		while(!n.IsLeaf() && matchingKeyIndex == -1){
			if(!n.IsEmpty()){
				matchingKeyIndex = n.CheckForMatch(d0);
			}
			if(matchingKeyIndex == -1){
				n = n.GetNextChild(d0);
			}
		}
		if(matchingKeyIndex != -1){
			n.AddDuplicate(matchingKeyIndex);
		} else {
			n.AddAndSort(d0);
			n.BalanceOverflow();
		}
		Root = n.GetRoot();
	}

	public DataObject Query(String key) {
		System.out.printf("QUERY: \"%s\"\n", key);
		DataObject comp = new DataObject(null, key);
		Node n = Root;
		DataObject d0 = null;
		int matchingKeyIndex = n.CheckForMatch(comp);
		
		while(!n.IsLeaf() && matchingKeyIndex == -1) {
			n = n.GetNextChild(comp);
			matchingKeyIndex = n.CheckForMatch(comp);
		}
		if(matchingKeyIndex != -1) {
			d0 = n.Data[matchingKeyIndex];
			System.out.printf("QUERY SUCCESS: Found DataObject with Key: \"%s\"\n", d0.Key);
		} else {
			System.out.printf("QUERY FAIL: Unable to Locate DataObject with Key: \"%s\"\n", key);
		}
		return d0;
	}
		
	public DataObject Delete(String key) {
		System.out.printf("DELETE: \"%s\"\n", key);
		DataObject comp = new DataObject(null, key);
		Node n = Root;
		DataObject d0 = null;
		//find matching Node
		int duplicate = Root.CheckForMatch(comp);
		while(duplicate == -1 && !n.IsLeaf()) {
			n = n.GetNextChild(comp);
			duplicate = n.CheckForMatch(comp);
		}
		if(duplicate == -1) { // not found
			System.out.printf("DELETE FAIL: DataObject with Key: \"%s\" not found\n", key);
		} else { // found
			if(!n.IsLeaf()) { // non-leaf node. swap value with in order predecessor
				Node inOrderPredecessorNode = n.GetInOrderPredecessorNode(duplicate);
				DataObject temp = n.DetatchDataObject(duplicate);
				n.AddAndSort(inOrderPredecessorNode.DetatchDataObject(inOrderPredecessorNode.Size -1));
				inOrderPredecessorNode.AddAndSort(temp);
				n = inOrderPredecessorNode;
			}
			duplicate = n.CheckForMatch(comp);
			d0 = n.DetatchDataObject(duplicate);
			n.DefragDataObjectsLeft();
			if(n.IsEmpty()) {
				Root = n.BalanceUnderflow();
			}
		}
		if(d0 != null) {
			System.out.printf("DELETE SUCCESS: DataObject with Key: \"%s\" found and removed.\n", key);
		}
		
		Root = n.GetRoot();
		return d0;
	}
}
