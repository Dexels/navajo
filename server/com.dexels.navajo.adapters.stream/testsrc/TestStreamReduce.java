import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

public class TestStreamReduce {


	@Test
	public void testReduce() {
		System.err.println(">>>> "+Flowable.just("aap","noot","mies","wim","zus","jet")
			.reduce("",reducer())
			.blockingGet()
			);
		
	}

	private BiFunction<String,String,String> reducer() {
		return (red,item)->{
			return red+"-"+item;
		};
	}
}
