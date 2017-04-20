package com.epam.testschool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class BaseConstructionsTasks {
	static int n = 0;
	static BufferedReader reader;
	static String pattern = "[a-zA-Z]*";
	static String vowels = "aeiouy";

	private static void task1() throws IOException{
		System.out.println("Task 1");
		System.out.println("Enter " + n + " strings:");
		String[] lines = new String[n];
		int sumLength = 0;
		for (int i = 0; i < n; i++){
			lines[i] = reader.readLine();
			sumLength += lines[i].length();
		}
		double avgLength = (double) sumLength / n;
		System.out.println("Strings with length less that average: (" + avgLength + ")");
		int countStrings = 0;
		for (String string : lines) {
			if (string.length() < avgLength){
				countStrings++;
				System.out.println("String: " + string + ", Length: " + string.length());
			}
		}
		if (countStrings == 0){
			System.out.println("No such strings");			
		}
	}


	private static void task2() throws IOException{
		System.out.println("Task 2");
		System.out.println("Enter " + n + " words in one line:");
		String[] words = new String[n];
		String inputString  = reader.readLine();
		String[] inputWords = inputString.split(" ");
		if (inputString.length() == 0 ||  inputWords.length != n){
			throw new IllegalArgumentException("Wrong number of words");
		}
		words = inputWords;
		int countWords = 0;
		int vowelCount = 0;
		for (int j = 0; j < words.length; j++) {
			if (words[j].matches(pattern) && ( words[j].length() % 2 == 0 )){
				vowelCount = 0;
				for (char c : words[j].toLowerCase().toCharArray()) {
					if(vowels.indexOf(c) >= 0){
						vowelCount += 1;
					}
				}
				if (vowelCount == words[j].length() / 2){
					System.out.println(words[j]);
					countWords ++;
				}
			}
		}
		if (countWords == 0){
			System.out.println("No such words");			
		}
	}
	
	private static void task3() throws NumberFormatException, IOException{
		System.out.println("Task 3");
		System.out.println("Enter a month's number");
		int month = Integer.parseInt(reader.readLine().trim());
		switch (month) {
		case 1:
			System.out.println("Januar");
			break;
		case 2:
			System.out.println("Februar");
			break;
		case 3:
			System.out.println("Mart");
			break;
		case 4:
			System.out.println("April");
			break;
		case 5:
			System.out.println("May");
			break;
		case 6:
			System.out.println("Juni");
			break;
		case 7:
			System.out.println("Juli");
			break;
		case 8:
			System.out.println("August");
			break;
		case 9:
			System.out.println("September");
			break;
		case 10:
			System.out.println("October");
			break;
		case 11:
			System.out.println("November");
			break;
		case 12:
			System.out.println("December");
			break;
		default:
			throw new IllegalArgumentException("Wrong number of month!");
		}

	}
	
	private static void task4() throws NumberFormatException, IOException {
		System.out.println("Task 4");
		System.out.println("Enter a column's number:");
		int k = Integer.parseInt(reader.readLine());
		// Human number
		k--;
		if (k >= n || k < 0) {
			throw new IllegalArgumentException("Wrong number of column!");
		}
		Random randomizer = new Random();
		int[][] arr = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				arr[i][j] = randomizer.nextInt(2*n) - n; 
			}
		}
		final int kk = k;
		Arrays.sort(arr, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[kk], o2[kk]);
				
			}
		});
		System.out.println(Arrays.deepToString(arr));
	}

	private static enum Season{
		WINTER("WINTER"){
			public Season getNextSeason(){
				return SPRING;
			}
			public Season getOppositeSeason(){
				return SUMMER;
			}
		},
		SPRING("SPRING"){
			public Season getNextSeason(){
				return SUMMER;
			}
			public Season getOppositeSeason(){
				return AUTUMN;
			}
		},
		SUMMER("SUMMER"){
			public Season getNextSeason(){
				return AUTUMN;
			}
			public Season getOppositeSeason(){
				return WINTER;
			}
		},
		AUTUMN("AUTUMN"){
			public Season getNextSeason(){
				return WINTER;
			}
			public Season getOppositeSeason(){
				return SPRING;
			}
		};
		String season;
		Season(String season){
			this.season = season;
		}
		public String getCurrentSeason(){
			return this.season;
		}
		public abstract Season getNextSeason();
		public abstract Season getOppositeSeason();
		
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length == 0){
			throw new IllegalArgumentException("Argument is missing");
		}else {
			n = Integer.parseInt(args[0]);
			if ( n <= 0 ){
				throw new IllegalArgumentException("Negative argument was provided");
			}
		}
		reader = new BufferedReader(new InputStreamReader(System.in));
		task1();
		task2();
		task3();
		task4();
		reader.close();
		Season mySeason = Season.AUTUMN;
		System.out.println(mySeason.getCurrentSeason());
		mySeason = mySeason.getNextSeason();
		System.out.println(mySeason.getCurrentSeason());
		mySeason = mySeason.getOppositeSeason();
		System.out.println(mySeason.toString());
	}
}
