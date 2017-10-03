import org.junit.Assert;
import org.junit.Test;

import io.reactivex.Maybe;

public class TestMaybe {

	public TestMaybe() {
	}

	@Test
	public void testMaybe() {
		Maybe<String> s = Maybe.just("aap");
		String a = s.blockingGet();
		System.err.println("a: "+a);
		String b = s.blockingGet();
		System.err.println("b: "+b);
		Assert.assertEquals(a, b);
	}
}
