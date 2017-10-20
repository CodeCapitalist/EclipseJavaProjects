package tools;

import java.util.Scanner;

public class TrimBlankSpace {

	public static void main(String[] args) {
		String delimiters = " |\t|\n|\r";
		String[] inputArr;
		String output;
		String tempin;
		String tempin2;
		String tempin3;
		String tempin4;
		String tempin5;
		Scanner scanner = new Scanner(System. in); 
		while(true) {
			output = "";
			tempin2 = "";
			while((tempin = scanner.nextLine()).length() > 0) {
				tempin2 += tempin;
			}
			tempin3 = tempin2.trim().replaceAll("\t+"," ");
			tempin4 = tempin3.trim().replaceAll(" +", " ");
			inputArr = tempin4.split(delimiters);
			for(int i = 0; i < inputArr.length; i++) {
				if(!inputArr[i].equals(" ")) {
					output = output + inputArr[i] + " ";
				}
			}
			System.out.println(output);
		}

	}

}
