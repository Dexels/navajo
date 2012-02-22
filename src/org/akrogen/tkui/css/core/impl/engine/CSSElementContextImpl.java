package org.akrogen.tkui.css.core.impl.engine;

import java.util.HashMap;
import java.util.Map;

import org.akrogen.tkui.css.core.dom.CSSStylableElement;
import org.akrogen.tkui.css.core.dom.IElementProvider;
import org.akrogen.tkui.css.core.engine.CSSElementContext;
import org.w3c.dom.Element;

public class CSSElementContextImpl implements CSSElementContext {

	private Element element;

	private Map datas = null;

	private IElementProvider elementProvider;

	public CSSElementContextImpl() {

	}

	public void setData(Object key, Object value) {
		if (datas == null)
			datas = new HashMap();
		datas.put(key, value);
	}

	public Object getData(Object key) {
		if (datas == null)
			return null;
		return datas.get(key);
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element newElement) {
		if (element != null && element instanceof CSSStylableElement
				&& newElement instanceof CSSStylableElement) {
			((CSSStylableElement) newElement)
					.copyDefaultStyleDeclarations(((CSSStylableElement) element));
		}
		this.element = newElement;
	}

	public boolean elementMustBeRefreshed(IElementProvider elementProvider) {
		if (this.elementProvider == null)
			return (elementProvider != null);
		return !this.elementProvider.equals(elementProvider);
	}

	public void setElementProvider(IElementProvider elementProvider) {
		this.elementProvider = elementProvider;
	}
}
