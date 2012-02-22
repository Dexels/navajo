/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.core.dom.properties.css2;

import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

public interface CSS2FontProperties extends CSSValue {

	public CSSPrimitiveValue getFamily();

	public void setFamily(CSSPrimitiveValue family);

	public CSSPrimitiveValue getSize();

	public void setSize(CSSPrimitiveValue size);

	public CSSPrimitiveValue getSizeAdjust();

	public void setSizeAdjust(CSSPrimitiveValue sizeAdjust);

	public CSSPrimitiveValue getWeight();

	public void setWeight(CSSPrimitiveValue weight);

	public CSSPrimitiveValue getStyle();

	public void setStyle(CSSPrimitiveValue style);

	public CSSPrimitiveValue getVariant();

	public void setVariant(CSSPrimitiveValue variant);

	public CSSPrimitiveValue getStretch();

	public void setStretch(CSSPrimitiveValue stretch);
}
