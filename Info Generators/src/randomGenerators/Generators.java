package randomGenerators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Generators {
	private Random Gen;
	private String[] maleFirstNames;
	private String[] femaleFirstNames;
	private String[] lastNames;
	private static final String MALEFIRSTNAMES = "C:\\Users\\cphillips\\Desktop\\Lists\\FirstM.txt";
	private static final String FEMALEFIRSTNAMES = "C:\\Users\\cphillips\\Desktop\\Lists\\FirstF.txt";
	private static final String LASTNAMES = "C:\\Users\\cphillips\\Desktop\\Lists\\Last.txt";
			
	public Generators (int seed) throws IOException {
		Gen = new Random(seed);
		PopulateNames();
	}
	
	
	
	//////THIS IS WHERE THE MAGIC HAPPENS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public String Transform(char[] regex){
		String finalWord = "";	
		
		for(int i = 0; i < regex.length; i++) {
			if(regex[i] == ' ' || regex[i] == '.') {
				finalWord += regex[i];
			} else if(regex[i] != '{') {
				finalWord += GenStringByRegex(regex[i]);
			}else {
				String temp = "";
				int j = i+1;
				while(regex[j] != '}') {
					temp += regex[j];
					j++;
				}
				int jump = 2 + temp.length() - 1;
				int lower = Integer.valueOf(temp.split(":")[0]);
				int upper = Integer.valueOf(temp.split(":")[1]);
				finalWord += String.valueOf(GenNumRange(lower,upper));
				i += jump;
			}
		}
		return finalWord;
	}
	
	
	
	/////////GENERATION METHODS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// Make a methods for:==============
	// 1) Generate dollar value range
	// 2)
	// 3)
	
	
	// [#] generates random numeric values (0-9)
	public char GenNumeric() {
		return (char)(GenNumRange(48,57));
	}
	
	// [A] generates random UPPER-case Alpha values (A-Z)
	public char GenAlphaU() {
		return (char)(GenNumRange(65,90));
	}
	
	// [a] generates random LOWER-case Alpha values (a-z)
	public char GenAlphaL() {
		return (char)(GenNumRange(97,122));
	}
	
	// [&] generates random ANY-case Alpha value (a-z, A-Z)
	public char GenAlpha() {
		if(CoinFlip()) {
			return GenAlphaU();
		} else {
			return GenAlphaL();
		}
	}
	
	// [X] generates random UPPER-case AlphaNumeric values (0-9, A-Z)
	public char GenAlphaUNumeric() {
		if(CoinFlip()) {
			return GenAlphaU();
		}
		else {
			return GenNumeric();
		}
	}
	
	// [x] Generates random LOWER-case AlphaNumeric values (0-9, a-z)
	public char GenAlphaLNumeric() {
		if(CoinFlip()) {
			return GenAlphaL();
		}
		else {
			return GenNumeric();
		}
	}
	
	// [%] Generates random ANY-case AlphaNumeric values (0-9, A-Z, a-z)
	public char GenAlphaNumeric() {
		if(CoinFlip()) {
			return GenAlphaUNumeric();
		} else {
			return GenAlphaLNumeric();
		}
	}
	
	// [F] returns random female first name (from string array)
	public String GenFemaleFirstName() {
		return femaleFirstNames[Gen.nextInt(250)];
	}
	
	// [M] returns random male first name (from string array)
	public String GenMaleFirstName() {
		return maleFirstNames[Gen.nextInt(250)];
	}
	
	// [L] returns random last name (from string array)
	public String GenLastName() {
		return lastNames[Gen.nextInt(250)];
	}
	
	// [N] return random male or female first name (string string array)
	public String GenFirstName() {
		if(CoinFlip()) {
			return GenMaleFirstName();
		} else {
			return GenFemaleFirstName();
		}
	}
		
	// [ {##:##} ] generates integer values within a set range [inclusive]
	public int GenNumRange(int lower, int upper) {
		return (Gen.nextInt(upper - lower + 1) + lower);
	}
		
	//random true or false
	public boolean CoinFlip() {
		if((Gen.nextInt() % 2) == 0) {
			return true;
		} else {
			return false;
		}
	}	

	///////////////////PRIVATE METHODS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	//generates a string of random values, dictated by a set of regular expressions(#,A,a,&,X,x,%)
	public String GenStringByRegex(char regex) {
			String word = "";
			switch(Enum(regex)) {
				case 0:
					word += GenNumeric();
					break;
				case 1:
					word += GenAlphaU();
					break;
				case 2:
					word += GenAlphaL();
					break;
				case 3:
					word += GenAlpha();
					break;
				case 4:
					word += GenAlphaUNumeric();
					break;
				case 5:
					word += GenAlphaLNumeric();
					break;
				case 6:
					word += GenAlphaNumeric();
					break;
				case 7:
					word += GenFemaleFirstName();
					break;
				case 8:
					word += GenMaleFirstName();
					break;
				case 9:
					word += GenLastName();
					break;
				case 10:
					word += GenFirstName();
					break;
				default:
					break;
			}
			return word;
		}
	
	//enumerates Regular Expressions for parsing
	private int Enum(char val) {
		int retVal = -1;
		if(val == '#') {
			retVal = 0;
		} else if (val == 'A') {
			retVal = 1;
		} else if (val == 'a') {
			retVal = 2;
		} else if (val == '&') {
			retVal = 3;
		} else if (val == 'X') {
			retVal = 4;
		} else if (val == 'x') {
			retVal = 5;
		} else if (val == '%') {
			retVal = 6;
		} else if (val == 'F') {
			retVal = 7;
		} else if (val == 'M') {
			retVal = 8;
		} else if (val == 'L') {
			retVal = 9;
		} else if (val == 'N') {
			retVal = 10;
		} else {
			retVal = -1;
		}
		return retVal;
	}

	//populates a list of names, for random generation
	private void PopulateNames() throws IOException{
		maleFirstNames = new String[250];
		femaleFirstNames = new String[250];
		lastNames = new String[250];
		
		FileReader lfr = new FileReader(LASTNAMES);
		FileReader mfr = new FileReader(MALEFIRSTNAMES);
		FileReader ffr = new FileReader(FEMALEFIRSTNAMES);
		BufferedReader lbr = new BufferedReader(lfr);
		BufferedReader mbr = new BufferedReader(mfr);
		BufferedReader fbr = new BufferedReader(ffr);
		
		for(int i = 0; i < 250; i++){
			lastNames[i] = lbr.readLine().trim();
			maleFirstNames[i] = mbr.readLine().trim();
			femaleFirstNames[i] = fbr.readLine().trim();	
		}
		
		lbr.close(); mbr.close(); fbr.close();
		lfr.close(); mfr.close(); ffr.close();	
	}
}
