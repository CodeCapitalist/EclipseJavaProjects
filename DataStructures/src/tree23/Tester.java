package tree23;

public class Tester {

	public static void main(String[] args) {
		TwoThreeTree myTree = new TwoThreeTree();
		String[] myInserts = new String[11];
		String[] myQueries = new String[5];
		DataObject[] myData = new DataObject[11];
		myInserts[0] = "cat";
		myInserts[1] = "dog";
		myInserts[2] = "linx";
		myInserts[3] = "bear";
		myInserts[4] = "racoon";
		myInserts[5] = "muskrat";
		myInserts[6] = "bird";
		myInserts[7] = "zebra";
		myInserts[8] = "ferret";
		myInserts[9] = "porpus";
		myInserts[10]= "fox";
	
		myQueries[0] = "fox";
		myQueries[1] = "FOX";
		myQueries[2] = "bear";
		myQueries[3] = "bird";
		myQueries[4] = "dog";
				
		
		
		for(int i = 0; i < 11; i++) {
			myData[i] = new DataObject(myInserts[i], myInserts[i]);
		}
		
		for(int i = 0; i < 11; i++) {
			myTree.Insert(myData[i]);
			System.out.print("");
		}
		
		for(int i = 0; i < 11; i++) {
			myTree.Query(myInserts[i]);
			System.out.print("");
		}
		System.out.print("");

		myTree.Delete("zebra");
		myTree.Delete("linx");
		myTree.Delete("ferret");
		myTree.Delete("racoon");
		myTree.Delete("fox");
		myTree.Delete("bear");
		System.out.print("");
	}
}
