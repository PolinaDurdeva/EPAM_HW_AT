import java.util.Random;

public class Task10 {

	public static void main(String[] args) {
		int maxAge = 0;
		int minAge = 200;
		for (int i= 0; i < args.length; i = i+3){
			System.out.println(args[i+1] + ' ' + args[i] + ' ' + args[i+2]);
			int age = Integer.parseInt(args[i+2]);
			if (age < minAge){
				minAge = age;
			}
			if (age > maxAge){
				maxAge = age;
			}
		}
		double avgAge = (maxAge - minAge)/2;
		avgAge = Math.round(avgAge*100)/100;
		System.out.println(minAge + ' ' + maxAge + ' ' + avgAge);
}

