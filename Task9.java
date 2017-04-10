import java.util.Random;

public class Task {
	System.out.println("Hello, World!");
	System.out.println("And hi again!");
	Random rand = new Random();
	int randSign = rand.nextInt(50) + 5;
	String str = new String("");
	for (int i = 0; i<randSign; i++ ){
		str += '!';
	}
	System.out.println(str);
	}
}


