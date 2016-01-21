import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.observables.ConnectableObservable;

public class Authorizer implements Observer<NavajoStreamEvent> {

	private final Observable<NavajoStreamEvent> input;
	private final Observer<NavajoStreamEvent> output;
	private final Subscription subscription;

	public Authorizer(Observable<NavajoStreamEvent> authStream,Observable<NavajoStreamEvent> input, Observer<NavajoStreamEvent> output) {
		this.input = input;
		this.output = output;
		this.subscription = authStream.subscribe(this);
	}
	
	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNext(NavajoStreamEvent headerEvent) {
		Header h = (Header)headerEvent.getBody();
		System.err.println("Script: "+h.getRPCName());
		BaseScriptInstance bsi = new BaseScriptInstance(h.getRPCName(), input,output);
		subscription.unsubscribe();
		

	}

}
