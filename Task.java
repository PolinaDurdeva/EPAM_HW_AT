package Javatask;

import java.text.DecimalFormat;
import java.util.Random;

public class Task {

	private static DecimalFormat format = new DecimalFormat("#.##");

	public static void main(String[] args) {
		int maxAge = 0;
		int minAge = 200;
		int sumAge = 0;
		for (int i = 0; i < args.length; i = i + 3) {
			System.out.println(args[i + 1] + " " + args[i] + " " + args[i + 2]);
			int age = Integer.parseInt(args[i + 2]);
			if (age < minAge) {
				minAge = age;
			}
			if (age > maxAge) {
				maxAge = age;
			}
			sumAge += age;
		}
		int leng = args.length / 3;
		String avgAge = format.format(sumAge / leng);
		System.out.println(minAge + " " + maxAge + " " + avgAge);
	}

	public static char[] charUni(int a) {
		char c = '\\';
		int codeChar = (int) c;
		char[] res = Character.toChars(codeChar + a);
		System.out.println(res);
		return res;
	}

	public static void hello() {
		System.out.println("Hello, World!");
		System.out.println("And hi again!");
		Random rand = new Random();
		int randSign = rand.nextInt(50) + 5;
		String str = new String("");
		for (int i = 0; i < randSign; i++) {
			str += '!';
		}
		System.out.println(str);
	}
}

