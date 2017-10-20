package randomGenerators;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException  {
		Generators gen = new Generators(15);
		String word;
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.print(" IN > ");
			word = gen.Transform(in.nextLine().toCharArray());
			System.out.print("OUT > ");
			System.out.println(word);
		}
	}
}
