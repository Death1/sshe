package match.test;

import org.junit.Test;


public class MatchTest
{
	@Test
	public void test(){
		String a="asgdc1";
		System.out.println(a.matches("^[a-zA-Z]*"));
	}
}
