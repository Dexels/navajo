import java.util.Collections;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class BaseScriptInstance implements Observer<NavajoStreamEvent> {

	private final String name;
	private final Observer<NavajoStreamEvent> output;
	private final Subscription subscription;

	public BaseScriptInstance(String name, Observable<NavajoStreamEvent> input, Observer<NavajoStreamEvent> output) {
		this.name = name;
		this.output = output;
		this.subscription = input.subscribe(this);
		output.onNext(new NavajoStreamEvent(null, NavajoEventTypes.NAVAJO_STARTED, null, Collections.emptyMap()));

	}
	
	@Override
	public void onCompleted() {
		System.err.println("Script: "+name+" done!");
		emitExample();
		output.onNext(new NavajoStreamEvent(null, NavajoEventTypes.NAVAJO_DONE, null, Collections.emptyMap()));
		output.onCompleted();
		subscription.unsubscribe();
		
	}

	@Override
	public void onError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNext(NavajoStreamEvent event) {
		System.err.println("opopopopo: "+event.getPath()+" type: "+event.type());
	}

	public void emitExample() {
		Message example = NavajoFactory.getInstance().createMessage(null, "ExampleMessage", Message.MSG_TYPE_SIMPLE);
		emitMessage("Example/ExampleMessage",example);
	}
	public void emitMessage(String path, Message message) {
		output.onNext(new NavajoStreamEvent(path, NavajoEventTypes.MESSAGE_STARTED, message, Collections.emptyMap()));
		output.onNext(new NavajoStreamEvent(path, NavajoEventTypes.MESSAGE, message, Collections.emptyMap()));
	}
}
