package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Action2;

public interface ReactiveScript {
	public void onMessage(String path, Action2<Msg,Subscriber<? super NavajoStreamEvent>> callback);
	public void onElement(String path, Action1<Subscriber<? super NavajoStreamEvent>> before, Action2<Msg,Subscriber<? super NavajoStreamEvent>> callback,Action1<Subscriber<? super NavajoStreamEvent>> after);
	public void onElement(String path, Action2<Msg,Subscriber<? super NavajoStreamEvent>> callback);
	public void onComplete(Action1<Subscriber<? super NavajoStreamEvent>> callback);
	public void onStart(Action1<Subscriber<? super NavajoStreamEvent>> callback);
}
