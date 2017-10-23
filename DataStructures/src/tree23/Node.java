package tree23;

public class Node {
	Node Parent;
	Node[] Children;
	DataObject[] Data;
	int Size;
	
	
	//////////////// CONSTRUCTOR FUNCTIONS ////////////////////
	public Node() {
		Parent = null;
		Size = 0;
		initChildren();
		initData();
	}
	
	public void initChildren() {
		Children = new Node[4];
		Children[0] = Children[1] = Children[2] = Children[3] = null;
	}
	
	public void initData() {
		Data = new DataObject[3];
		Data[0] = Data[1] = Data[2] = null;	
	}
	
	////////////////// DUPLICATE DATA HANDLING ////////////////////////////////
	
	public int CheckForMatchingKey(String key) {
		int matchingKeyIndex = -1;
		for(int i = 0; i < Size; i++) {
			if(Data[i].compare(key) == 0) {
				matchingKeyIndex = i;
			}
		}
		return matchingKeyIndex;
	}
	
	public int CheckForMatchingKey(DataObject d0) {
		int matchingKeyIndex = -1;
		for(int i = 0; i < Size; i++) {
			if(d0.compare(Data[i]) == 0) {
				matchingKeyIndex = i;
			}
		}
		return matchingKeyIndex;
	}
	
	public void AddDuplicate(int matchingKeyIndex) {
		Data[matchingKeyIndex].Count++;
	}
	
	/////////////////INSERTION FUNCTIONS////////////////////////////////
	
	public void BalanceOverflow() {
		if(Size == 3) {
			if(IsLeaf() && IsRoot()) {
				SplitPromoteLeafRoot();
			} else if (!IsLeaf() && IsRoot()) {
				SplitPromoteNonLeafRoot();
			} else if (IsLeaf() && !IsRoot()) {
				SplitPromoteLeafNonRoot();
			} else {
				SplitPromoteNonLeafNonRoot();
			}
		}
	}
	
	private void SplitPromoteLeafRoot() {
		Node rightNode = new Node();
		rightNode.AddAndSort(DetatchDataObject(2));
		Node newRoot = new Node();
		newRoot.AddAndSort(DetatchDataObject(1));
		newRoot.AddChildAtIndex(this, 0);
		newRoot.AddChildAtIndex(rightNode, 1);
	}

	private void SplitPromoteNonLeafRoot() {
		Node rightNode = new Node();
		rightNode.AddAndSort(DetatchDataObject(2));
		rightNode.AddChildAtIndex(DetatchChild(2), 0);
		rightNode.AddChildAtIndex(DetatchChild(3), 1);
		Node newRoot = new Node();
		newRoot.AddAndSort(DetatchDataObject(1));
		newRoot.AddChildAtIndex(this, 0);
		newRoot.AddChildAtIndex(rightNode, 1);
	}
	
	private void SplitPromoteLeafNonRoot() {
		Node rightNode = new Node();
		rightNode.AddAndSort(DetatchDataObject(2));
		int childNo = WhichChildAmI();
		Parent.MakeRoomForNewChildFromSplit(childNo);
		Parent.AddChildAtIndex(rightNode, childNo + 1);
		Parent.AddAndSort(DetatchDataObject(1));
		Parent.BalanceOverflow();
	}
	
	private void SplitPromoteNonLeafNonRoot(){
		Node rightNode = new Node();
		rightNode.AddAndSort(DetatchDataObject(2));
		rightNode.AddChildAtIndex(DetatchChild(2),0);
		rightNode.AddChildAtIndex(DetatchChild(3),1);
		int childNo = WhichChildAmI();
		Parent.MakeRoomForNewChildFromSplit(childNo);
		Parent.AddChildAtIndex(rightNode, childNo + 1);
		Parent.AddAndSort(DetatchDataObject(1));
		Parent.BalanceOverflow();
	}
	
	///////////// QUERY FUNCTIONS //////////////////
	
	
	///////////// DELETION FUNCTIONS ///////////////
		//rotation functions
	public void RotateDataLeftParentIntoChild(int childNo){
		if(childNo == 0) {
			Children[0].AddAndSort(DetatchDataObject(0));
			DefragDataObjectsLeft();
		}else {
			Children[1].AddAndSort(DetatchDataObject(1));
		}
	}
	
	public void RotateDataRightParentIntoChild(int childNo) {
		if(childNo == 1) {
			Children[1].AddAndSort(DetatchDataObject(0));
			DefragDataObjectsLeft();
		} else {
			Children[2].AddAndSort(DetatchDataObject(1));
		}
	}
	
	public void RotateDataLeftChildIntoParent(int childNo) {
		if(childNo == 2) {
			AddAndSort(Children[2].DetatchDataObject(0));
			Children[2].DefragDataObjectsLeft();
		} else {
			AddAndSort(Children[1].DetatchDataObject(0));
		}
	}
	
	public void RotateDataRightChildIntoParent(int childNo) {
		if(childNo == 0) {
			if(Children[0].Size == 2) {
				AddAndSort(Children[0].DetatchDataObject(1));
			} else {
				AddAndSort(Children[0].DetatchDataObject(0));
			}
		} else {
			if(Children[1].Size == 2) {
				AddAndSort(Children[1].DetatchDataObject(1));
			} else {
				AddAndSort(Children[1].DetatchDataObject(0));
			}
		}
	}
	
	private void MergeDataObjectsIntoTargetNode(Node n) {
		while(Size > 0) {
			n.AddAndSort(DetatchDataObject(0));
			this.DefragDataObjectsLeft();
		}
	}
	
	private void MergeChildrenIntoTargetNode(Node n) {
		int source = WhichChildAmI();
		int target = n.WhichChildAmI();
		if((source == 0 && target == 1) || (source == 1 && target == 2)) { //Merging right into Node n
			while(ChildrenCount() > 0) {
				DefragChildNodesRight();
				n.DefragChildNodesRight();
				n.AddChildAtIndex(this.DetatchChild(3), 0);
			}
			n.DefragChildNodesLeft();
			
		} else if((source == 1 && target == 0) || (source == 2 && target == 1)) { //Merging left into Node n
			n.DefragChildNodesLeft();
			while(ChildrenCount() > 0) {
				DefragChildNodesLeft();
				n.AddChildAtIndex(this.DetatchChild(0),n.NextEmptyChildIndex());
			}
		}
	}

	private Node Balance2NP2NS(int whichChild) {
		Node root = null;
		Children[whichChild].AddAndSort(DetatchDataObject(0));
		MergeChildrenLeft(0);
		Children[1] = null;
		root = BalanceUnderflow();
		return root;
	}
	
	private Node Balance3NP2NS(int whichChild) {
		Node root = null;
		if(whichChild == 0) {
			RotateDataRightParentIntoChild(1);
			Children[0].MergeChildrenIntoTargetNode(Children[1]);
			Children[0] = null;
			DefragChildNodesLeft();
		} else if(whichChild == 1) {
			RotateDataLeftParentIntoChild(1);
			Children[2].MergeDataObjectsIntoTargetNode(Children[1]);
			Children[2].MergeChildrenIntoTargetNode(Children[1]);
			Children[2] = null;
		} else {
			RotateDataLeftParentIntoChild(1);
			Children[2].MergeChildrenIntoTargetNode(Children[1]);
			Children[2] = null;
		}
		root = GetRoot();
		return root;
	}
	
	private Node Balance2NP3NS(int whichChild) {
		Node root = null;
		if(whichChild == 0) {
			RotateDataLeftParentIntoChild(0);
			RotateDataLeftChildIntoParent(1);
			Children[0].AddChildAtIndex(Children[1].DetatchChild(0), Children[0].NextEmptyChildIndex());
			Children[1].DefragChildNodesLeft();
			Children[1].DefragDataObjectsLeft();
		} else {
			RotateDataRightParentIntoChild(1);
			RotateDataRightChildIntoParent(0);
			Children[1].DefragChildNodesRight();
			Children[1].AddChildAtIndex(Children[0].DetatchChild(2), 0);
			Children[1].DefragChildNodesLeft();
		}
		root = GetRoot();
		return root;
	}
	
	private Node Balance3NP3NS(int whichChild) {
		Node root = null;
		int bigSibling = Children[whichChild].GetClosestThreeNodeSiblingIndex();
		switch(whichChild) {
			case 0:
				if(bigSibling == 1) {
					RotateDataLeftParentIntoChild(0);
					RotateDataLeftChildIntoParent(1);
					Children[0].AddChildAtIndex(Children[1].DetatchChild(0), 1);
					Children[1].DefragChildNodesLeft();
				} else {
					RotateDataRightParentIntoChild(1);
					Children[0].MergeChildrenIntoTargetNode(Children[1]);
					Children[0] = null;
					DefragChildNodesLeft();
				}
				break;
			case 1:
				if(bigSibling == 0 ) {
					RotateDataLeftParentIntoChild(1);
					Children[2].MergeDataObjectsIntoTargetNode(Children[1]);
					Children[2].MergeChildrenIntoTargetNode(Children[1]);
					Children[2] = null;
				} else {
					RotateDataRightParentIntoChild(1);
					Children[0].MergeDataObjectsIntoTargetNode(Children[1]);
					Children[0].MergeChildrenIntoTargetNode(Children[1]);
					Children[0] = null;
					DefragChildNodesLeft();
				}
				break;
			case 2:
				if(bigSibling == 1) {
					RotateDataRightParentIntoChild(2);
					RotateDataRightChildIntoParent(1);
					Children[2].DefragChildNodesRight();
					Children[2].AddChildAtIndex(Children[1].DetatchChild(2), 0);
					Children[2].DefragChildNodesLeft();
				} else {
					RotateDataLeftParentIntoChild(1);
					Children[2].MergeChildrenIntoTargetNode(Children[1]);
					Children[2] = null;
				}
				break;
		}
		root = GetRoot();
		return root;
	}
	//returns Root node;
	public Node BalanceUnderflow() {
		Node root = null;
		if(IsRoot()) {
			root = Children[0];
		} else if(!Parent.IsThreeNode() && !HasThreeNodeSibling() ) {
			root = Parent.Balance2NP2NS(WhichChildAmI());
		} else if(!Parent.IsThreeNode() &&  HasThreeNodeSibling() ) {
			root = Parent.Balance2NP3NS(WhichChildAmI());
		} else if( Parent.IsThreeNode() && !HasThreeNodeSibling() ) {
			root = Parent.Balance3NP2NS(WhichChildAmI());
		} else if( Parent.IsThreeNode() &&  HasThreeNodeSibling() ) {
			root = Parent.Balance3NP3NS(WhichChildAmI());
		}
		root.Parent = null;
		return root;
	}
	
	//////////// NODE DATA MANIPULATION ////////////////////
	
	public void MoveChild(int initialIndex, int finalIndex) {
		Children[finalIndex] = Children[initialIndex];
		Children[initialIndex] = null;
	}
	
	private void MakeRoomForNewChildFromSplit(int childNo) {
		if(childNo == 0) {
			MoveChild(2,3);
			MoveChild(1,2);
		} else if (childNo == 1) {
			MoveChild(2, 3);
		} else { } //childNo == 2. Do nothing
	}
	
	public void AddAndSort(DataObject d0) {
		if(Size == 0) {
			Data[0] = d0;
		} else if (Size == 1){
			if(d0.compare(Data[0]) > 0) {
				Data[1] = d0;
			} else {
				MoveDataObject(0, 1);
				Data[0] = d0;
			}
		} else if (Size == 2) {
			if (d0.compare(Data[1]) > 0) {
				Data[2] = d0;
			} else if (d0.compare(Data[0]) > 0) {
				MoveDataObject(1,2);
				Data[1] = d0;
			} else {
				MoveDataObject(1,2);
				MoveDataObject(0,1);
				Data[0] = d0;
			}
		}
		Size++;
	}
	
	public void MoveDataObject(int initialIndex, int finalIndex) {
		Data[finalIndex] = Data[initialIndex];
		Data[initialIndex] = null;
	}
	
	public void MergeChildrenLeft(int intoChild) {
		if(intoChild == 0) {
			Children[1].MergeDataObjectsIntoTargetNode(Children[0]);
			Children[1].MergeChildrenIntoTargetNode(Children[0]);
		} else {
			Children[2].MergeDataObjectsIntoTargetNode(Children[1]);
			Children[2].MergeChildrenIntoTargetNode(Children[1]);
		}
		
	}

	public void DefragDataObjectsLeft() {
		while(Data[0] == null && (Data[1] != null || Data[2] != null)){
			MoveDataObject(1,0);
			MoveDataObject(2,1);
		}
		if(Data[1] == null && Data[2] != null) {
			MoveDataObject(2,1);
		}
	}
	
	public void DefragDataObjectsRight() {
		while(Data[2] == null && (Data[1] != null || Data[0] != null)) {
			MoveDataObject(1,2);
			MoveDataObject(0,1);
		} 
		if(Data[1] == null && Data[0] != null) {
			MoveDataObject(0,1);
		}
	}
	
	public void DefragChildNodesLeft() {
		while(Children[0] == null && (Children[1] != null || Children[2] != null || Children[3] != null)) {
			MoveChild(1,0);
			MoveChild(2,1);
			MoveChild(3,2);
		}
		while(Children[1] == null && (Children[2] != null || Children[3] != null)) {
			MoveChild(2,1);
			MoveChild(3,2);
		}
		if(Children[2] == null && Children[3] != null) {
			MoveChild(3,2);
		}
	}
	
	public void DefragChildNodesRight() {
		while(Children[3] == null && (Children[2] != null || Children [1] != null || Children[0] != null)) {
			MoveChild(2,3);
			MoveChild(1,2);
			MoveChild(0,1);
		}
		while(Children[2] == null && (Children[1] != null || Children[0] != null)) {
			MoveChild(1,2);
			MoveChild(0,1);
		}
		if(Children[1] == null && Children[0] != null) {
			MoveChild(0,1);
		}
	}
	
	public void AddChildAtIndex(Node newChild, int index) {
		Children[index] = newChild;
		newChild.Parent = this;
	}
	
	public DataObject DetatchDataObject(int index) {
		DataObject detatchedData = Data[index];
		Data[index] = null;
		Size--;
		return detatchedData;
		
	}
	
	public Node DetatchChild(int index) {
		Node detatchedChild = Children[index];
		Children[index] = null;
		return detatchedChild;
	}
	
	//////////// GETTERS /////////////////////////////
	
	public int GetIndexOfChildToInsertDataObject(DataObject d0) {
		int nextChild = -1;
		if(d0.compare(Data[0]) < 0) {
			nextChild = 0;
		} else {
			if(Size == 1) {
				nextChild = 1;
			} else { //Size == 2
				if(d0.compare(Data[1]) > 0) {
					nextChild = 2;
				} else {
					nextChild = 1;
				}
			}
		}
		return nextChild;
	}

	public Node GetInOrderPredecessorNode(int dataObjectIndex) {
		Node inOrderPredecessor = Children[dataObjectIndex];
		while(!inOrderPredecessor.IsLeaf()) {
			inOrderPredecessor = inOrderPredecessor.Children[inOrderPredecessor.Size];
		}
		return inOrderPredecessor;
	}
	
	public int GetNextChildIndexByKey(String key) {
		int nextChild = -1;
		if(Data[0].compare(key) > 0) {
			nextChild = 0;
		} else if(IsTwoNode()) {
			nextChild = 1;
		} else if(Data[1].compare(key) > 0) {
			nextChild = 1;
		} else {
			nextChild = 2;
		}
			
		return nextChild;
	}
	
	public Node GetNextChildNodeByKey(String key) {
		Node child = null;
		if(Data[0].compare(key) > 0) {
			child = Children[0];	
		} else if(IsTwoNode()) {
			child = Children[1];
		} else if(Data[1].compare(key) > 0) {
			child = Children[1];
		} else {
			child = Children[2];
		}
		return child;
	}
	
	public int GetClosestThreeNodeSiblingIndex() {
		int closestThreeNodeSibling = -1;
		if(Parent.IsThreeNode()) {
			switch(WhichChildAmI()) {
				case 0:
					if(Parent.Children[1].IsThreeNode()) {
						closestThreeNodeSibling = 1;
					} else if(Parent.Children[2].IsThreeNode()) {
						closestThreeNodeSibling = 2;
					}
					break;
				case 1:
					if(Parent.Children[2].IsThreeNode()) {
						closestThreeNodeSibling = 2;
					} else if(Parent.Children[0].IsThreeNode()) {
						closestThreeNodeSibling = 0;
					}
					break;
				case 2:
					if(Parent.Children[1].IsThreeNode()) {
						closestThreeNodeSibling = 1;
					} else if(Parent.Children[0].IsThreeNode()) {
						closestThreeNodeSibling = 0;
					}
					break;
			}
		} else {
			switch(WhichChildAmI()) {
				case 0:
					if(Parent.Children[1].IsThreeNode()) {
						closestThreeNodeSibling = 1;
					}
					break;
				case 1:
					if(Parent.Children[1].IsThreeNode()) {
						closestThreeNodeSibling = 0;
					}
					break;
			}
		}
		
		return closestThreeNodeSibling;
	}
	
	public Node GetRoot() {
		Node root = this;
		if(!IsRoot()) {
			root = Parent.GetRoot();
		}
		return root;
	}
	
	///////////// NODE CONTEXT FUNCTIONS ////////////////
	
	public boolean IsRoot() {
		boolean isRoot = false;
		if (Parent == null) {
			isRoot = true;
		}
		return isRoot;
	}

	public boolean IsLeaf() {
		boolean isLeaf = false;
		if(Children[0] ==  null && Children[1] == null && Children[2] == null && Children[3] == null) {
			isLeaf = true;
		}
		return isLeaf;
	}

	public boolean IsEmpty() {
		boolean isEmpty = false;
		if(Size == 0) {
			isEmpty = true;
		}
		return isEmpty;
	}
	
	public boolean IsTwoNode() {
		boolean isTwoNode = false;
		if(Size == 1) {
			isTwoNode = true;
		}
		return isTwoNode;
	}
	
	public boolean IsThreeNode() {
		boolean isThreeNode = false;
		if(Size == 2) {
			isThreeNode = true;
		}
		return isThreeNode;
	}
	
	public int WhichChildAmI() {
		int childNo = -1;
		if(!IsRoot()) {
			for(int i = 0; i <= Parent.Size + 1; i++) {
				if(Parent.Children[i] == this) {
					childNo = i;
				}
			}
		}
		return childNo;
	}

	public int ChildrenCount() {
		int count = 0;
		for(int i = 0; i < 4; i++) {
			if(Children[i] != null) {
				count++;
			}
		}
		return count;
	}
	
	public int NextEmptyChildIndex() {
		int nextEmptyChild = -1;
		for(int i = 0; i < Children.length; i++) {
			if(Children[i] == null) {
				nextEmptyChild = i;
				i = Children.length;
			}
		}
		return nextEmptyChild;
	}
	
	public boolean HasThreeNodeSibling() {
		boolean hasThreeNodeSibling = false;
		if(Parent.IsThreeNode()) { //has two siblings
			switch(WhichChildAmI()) { 
				case 0:
					if(Parent.Children[1].IsThreeNode() || Parent.Children[2].IsThreeNode()) {
						hasThreeNodeSibling = true;
					}
					break;
				case 1:
					if(Parent.Children[0].IsThreeNode() || Parent.Children[2].IsThreeNode()) {
						hasThreeNodeSibling = true;
					}
					break;
				case 2:
					if(Parent.Children[0].IsThreeNode() || Parent.Children[1].IsThreeNode()) {
						hasThreeNodeSibling = true;
					}
					break;
			}
		} else { // has one sibling
			switch(WhichChildAmI()) {
				case 0:
					hasThreeNodeSibling = Parent.Children[1].IsThreeNode();
					break;
				case 1:
					hasThreeNodeSibling = Parent.Children[0].IsThreeNode();
					break;
			}
		}
		
		return hasThreeNodeSibling;
	}
}
