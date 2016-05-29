import static org.junit.Assert.*;
import org.junit.Test;

public class UUTTest {

	@Test
	public void test() {
		
		UUT tester = new UUT();
		
		assertEquals("10 x 0 must be 0", 0, tester.multiplication(10, 0));	//needs changes
		assertEquals("0 x 10 must be 0", 0, tester.multiplication(0, 10));
		assertEquals("0 x 0 must be 0", 0, tester.multiplication(0 ,0));
		fail("Not yet implemented");
	}

}