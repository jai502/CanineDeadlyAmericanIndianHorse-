
public class UUT {

	static int output;
	
	public static void multiplication(int i, int j) {
		output = i * j;
	}
	
	public static void main(String[] args) {
		multiplication(2,4);
		System.out.println("Your number is: " + output);
	}
	
}