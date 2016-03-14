	package example;
	
	import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
	
	public class ParallelTest {
	
	    public static void main(String[] args) {
	        System.out.println("    ------------ flatMapExampleSync");
	        flatMapExampleSync();
	        System.out.println("    ------------ flatMapExampleAsync");
	        flatMapExampleAsync();
	        System.out.println("    ------------concatMapExampleAsync");
	        concatMapExampleAsync();
	        System.out.println("    ------------");
	    }
	
	
	    private static void flatMapExampleAsync() {
	        Observable.range(0, 5).flatMap(i -> {
	            return getDataAsync(i);
	        }).toBlocking().forEach(System.out::println);
	    }
	
	    private static void concatMapExampleAsync() {
	        Observable.range(0, 5).concatMap(i -> {
	            return getDataAsync(i);
	        }).toBlocking().forEach(System.out::println);
	    }
	
	    private static void flatMapExampleSync() {
	        Observable.range(0, 5).flatMap(i -> {
	            return getDataSync(i);
	        }).toBlocking().forEach(System.out::println);
	    }
	
	    // artificial representations of IO work
	    static Observable<String> getDataAsync(int i) {
	        return getDataSync(i).subscribeOn(Schedulers.io());
	    }
	
	    static Observable<String> getDataSync(int i) {
	        return Observable.create((Subscriber<? super String> s) -> {
	            // simulate latency
	            try {
	                Thread.sleep(1000);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            s.onNext("    First"+i);
	            try {
	                Thread.sleep(50);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	                s.onNext("    Second"+i);
	                s.onCompleted();
	            });
	    }
	}