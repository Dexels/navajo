package com.dexels.navajo.tipi.components.core.adapter;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.components.core.TipiComponentImpl;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public abstract class BaseAdapter {
	protected TipiComponent myComponent;

	public TipiComponent getComponent() {
		return myComponent;
	}

	public Object getValue(String attribute) {
		return myComponent.getValue(attribute);
	}
	@Override
	public String toString() {
		return myComponent.getClass().getName();
	}

	public String getId() {
		return myComponent.getId();
	}

	public void setComponent(TipiComponent myComponent) {
		this.myComponent = myComponent;
	}

	public BaseAdapter getParent() {
		TipiComponentImpl pp = (TipiComponentImpl) myComponent.getTipiParent();
		if (pp == null) {
			return null;
		}
		return pp.createAdapter(invocation, event);

	}

	public List<BaseAdapter> getChildren() {
		List<BaseAdapter> result = new ArrayList<BaseAdapter>();
		for (TipiComponent tc : myComponent.getChildren()) {
			result.add(((TipiComponentImpl) tc)
					.createAdapter(invocation, event));
		}
		return result;
	}

	public BaseAdapter getChild(String id) {
		TipiComponentImpl rr = (TipiComponentImpl) myComponent
				.getTipiComponent(id);
		if (rr == null) {
			return null;
		}
		return rr.createAdapter(invocation, event);
	}

	public BaseAdapter getComponent(String path) {
		TipiComponentImpl rr = (TipiComponentImpl) myComponent
				.getTipiComponentByPath(path);
		if (rr == null) {
			return null;
		}
		return rr.createAdapter(invocation, event);
	}

	public BaseAdapter find(String id) {
		if (id.equals(myComponent.getId())) {
			return this;
		}
		List<BaseAdapter> ch = getChildren();
		for (BaseAdapter baseAdapter : ch) {
			BaseAdapter bb = baseAdapter.find(id);
			if (bb != null) {
				return bb;
			}
		}
		return null;
	}

	public void setInvocation(TipiAction invocation) {
		this.invocation = invocation;
	}

	public void setEvent(TipiEvent event) {
		this.event = event;
	}

	protected TipiAction invocation;
	protected TipiEvent event;
}
