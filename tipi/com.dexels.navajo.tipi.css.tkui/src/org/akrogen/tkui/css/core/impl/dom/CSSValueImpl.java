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
package org.akrogen.tkui.css.core.impl.dom;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Vector;

import org.akrogen.tkui.css.core.dom.parsers.CSSParser;
import org.akrogen.tkui.css.core.exceptions.DOMExceptionImpl;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;
import org.w3c.dom.css.Counter;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.css.Rect;

/**
 * The <code>CSSValueImpl</code> class can represent either a
 * <code>CSSPrimitiveValue</code> or a <code>CSSValueList</code> so that the
 * type can successfully change when using <code>setCssText</code>.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSValueImpl extends AbstractCSSNode implements CSSPrimitiveValue,
		CSSValueList, Serializable {

	private Object value = null;

	/**
	 * Constructor
	 */
	public CSSValueImpl(LexicalUnit value, boolean forcePrimitive) {
		if (value.getParameters() != null) {
			if (value.getLexicalUnitType() == LexicalUnit.SAC_RECT_FUNCTION) {
				// Rect
				this.value = new RectImpl(value.getParameters());
			} else if (value.getLexicalUnitType() == LexicalUnit.SAC_RGBCOLOR) {
				// RGBColor
				this.value = new RGBColorImpl(value.getParameters());
			} else if (value.getLexicalUnitType() == LexicalUnit.SAC_COUNTER_FUNCTION) {
				// Counter
				this.value = new CounterImpl(false, value.getParameters());
			} else if (value.getLexicalUnitType() == LexicalUnit.SAC_COUNTERS_FUNCTION) {
				// Counter
				this.value = new CounterImpl(true, value.getParameters());
			} else {
				this.value = value;
			}
		} else if (forcePrimitive || (value.getNextLexicalUnit() == null)) {

			// We need to be a CSSPrimitiveValue
			this.value = value;
		} else {

			// We need to be a CSSValueList
			// Values in an "expr" can be seperated by "operator"s, which are
			// either '/' or ',' - ignore these operators
			Vector v = new Vector();
			LexicalUnit lu = value;
			while (lu != null) {
				if ((lu.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA)
						&& (lu.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_SLASH)) {
					v.addElement(new CSSValueImpl(lu, true));
				}
				lu = lu.getNextLexicalUnit();
			}
			this.value = v;
		}
	}

	public CSSValueImpl(LexicalUnit value) {
		this(value, false);
	}

	public String getCssText() {
		if (getCssValueType() == CSS_VALUE_LIST) {

			// Create the string from the LexicalUnits so we include the correct
			// operators in the string
			StringBuffer sb = new StringBuffer();
			Vector v = (Vector) this.value;
			LexicalUnit lu = (LexicalUnit) ((CSSValueImpl) v.elementAt(0)).value;
			while (lu != null) {
				sb.append(lu.toString());

				// Step to the next lexical unit, determining what spacing we
				// need to put around the operators
				LexicalUnit prev = lu;
				lu = lu.getNextLexicalUnit();
				if ((lu != null)
						&& (lu.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA)
						&& (lu.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_SLASH)
						&& (prev.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_SLASH)) {
					sb.append(" ");
				}
			}
			return sb.toString();
		} else {
			if (this.value instanceof LexicalUnit) {
				LexicalUnit l = (LexicalUnit) this.value;
				int type = getPrimitiveType();
				switch (type) {
				case CSS_IDENT:
					return l.getStringValue();
				case CSS_NUMBER:
					return l.getIntegerValue() + "";
				}
			}
			return this.value.toString();
		}
	}

	public void setCssText(String cssText) throws DOMException {
		try {
			InputSource is = new InputSource(new StringReader(cssText));
			CSSParser parser = getCSSParser();
			CSSValueImpl v2 = (CSSValueImpl) parser.parsePropertyValue(is);
			this.value = v2.value;
		} catch (Exception e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
	}

	public short getCssValueType() {
		return (this.value instanceof Vector) ? CSS_VALUE_LIST
				: CSS_PRIMITIVE_VALUE;
	}

	public short getPrimitiveType() {
		if (this.value instanceof LexicalUnit) {
			LexicalUnit lu = (LexicalUnit) this.value;
			switch (lu.getLexicalUnitType()) {
			case LexicalUnit.SAC_INHERIT:
				return CSS_IDENT;
			case LexicalUnit.SAC_INTEGER:
			case LexicalUnit.SAC_REAL:
				return CSS_NUMBER;
			case LexicalUnit.SAC_EM:
				return CSS_EMS;
			case LexicalUnit.SAC_EX:
				return CSS_EXS;
			case LexicalUnit.SAC_PIXEL:
				return CSS_PX;
			case LexicalUnit.SAC_INCH:
				return CSS_IN;
			case LexicalUnit.SAC_CENTIMETER:
				return CSS_CM;
			case LexicalUnit.SAC_MILLIMETER:
				return CSS_MM;
			case LexicalUnit.SAC_POINT:
				return CSS_PT;
			case LexicalUnit.SAC_PICA:
				return CSS_PC;
			case LexicalUnit.SAC_PERCENTAGE:
				return CSS_PERCENTAGE;
			case LexicalUnit.SAC_URI:
				return CSS_URI;
				// case LexicalUnit.SAC_COUNTER_FUNCTION:
				// case LexicalUnit.SAC_COUNTERS_FUNCTION:
				// return CSS_COUNTER;
				// case LexicalUnit.SAC_RGBCOLOR:
				// return CSS_RGBCOLOR;
			case LexicalUnit.SAC_DEGREE:
				return CSS_DEG;
			case LexicalUnit.SAC_GRADIAN:
				return CSS_GRAD;
			case LexicalUnit.SAC_RADIAN:
				return CSS_RAD;
			case LexicalUnit.SAC_MILLISECOND:
				return CSS_MS;
			case LexicalUnit.SAC_SECOND:
				return CSS_S;
			case LexicalUnit.SAC_HERTZ:
				return CSS_KHZ;
			case LexicalUnit.SAC_KILOHERTZ:
				return CSS_HZ;
			case LexicalUnit.SAC_IDENT:
				return CSS_IDENT;
			case LexicalUnit.SAC_STRING_VALUE:
				return CSS_STRING;
			case LexicalUnit.SAC_ATTR:
				return CSS_ATTR;
				// case LexicalUnit.SAC_RECT_FUNCTION:
				// return CSS_RECT;
			case LexicalUnit.SAC_UNICODERANGE:
			case LexicalUnit.SAC_SUB_EXPRESSION:
			case LexicalUnit.SAC_FUNCTION:
				return CSS_STRING;
			case LexicalUnit.SAC_DIMENSION:
				return CSS_DIMENSION;
			}
		} else if (this.value instanceof RectImpl) {
			return CSS_RECT;
		} else if (this.value instanceof RGBColorImpl) {
			return CSS_RGBCOLOR;
		} else if (this.value instanceof CounterImpl) {
			return CSS_COUNTER;
		}
		return CSS_UNKNOWN;
	}

	public void setFloatValue(short unitType, float floatValue)
			throws DOMException {
		// this.value = LexicalUnitImpl.createNumber(null, floatValue);
	}

	public float getFloatValue(short unitType) throws DOMException {
		if (this.value instanceof LexicalUnit) {
			LexicalUnit lu = (LexicalUnit) this.value;
			if (lu.getLexicalUnitType() == LexicalUnit.SAC_INTEGER)
				return lu.getIntegerValue();
			return lu.getFloatValue();
		}
		throw new DOMExceptionImpl(DOMException.INVALID_ACCESS_ERR,
				DOMExceptionImpl.FLOAT_ERROR);

		// We need to attempt a conversion
		// return 0;
	}

	public void setStringValue(short stringType, String stringValue)
			throws DOMException {
		switch (stringType) {
		case CSS_STRING:
			// this.value = LexicalUnitImpl.createString(null, stringValue);
			break;
		case CSS_URI:
			// this.value = LexicalUnitImpl.createURI(null, stringValue);
			break;
		case CSS_IDENT:
			// this.value = LexicalUnitImpl.createIdent(null, stringValue);
			break;
		case CSS_ATTR:
			// this.value = LexicalUnitImpl.createAttr(null, stringValue);
			// break;
			throw new DOMExceptionImpl(DOMException.NOT_SUPPORTED_ERR,
					DOMExceptionImpl.NOT_IMPLEMENTED);
		default:
			throw new DOMExceptionImpl(DOMException.INVALID_ACCESS_ERR,
					DOMExceptionImpl.STRING_ERROR);
		}
	}

	/**
	 * TODO: return a value for a list type
	 */
	public String getStringValue() throws DOMException {
		if (this.value instanceof LexicalUnit) {
			LexicalUnit lu = (LexicalUnit) this.value;
			if ((lu.getLexicalUnitType() == LexicalUnit.SAC_IDENT)
					|| (lu.getLexicalUnitType() == LexicalUnit.SAC_STRING_VALUE)
					|| (lu.getLexicalUnitType() == LexicalUnit.SAC_URI)
					|| (lu.getLexicalUnitType() == LexicalUnit.SAC_ATTR)) {
				return lu.getStringValue();
			}
		} else if (this.value instanceof Vector) {
			return null;
		}

		throw new DOMExceptionImpl(DOMException.INVALID_ACCESS_ERR,
				DOMExceptionImpl.STRING_ERROR);
	}

	public Counter getCounterValue() throws DOMException {
		if ((this.value instanceof Counter) == false) {
			throw new DOMExceptionImpl(DOMException.INVALID_ACCESS_ERR,
					DOMExceptionImpl.COUNTER_ERROR);
		}
		return (Counter) this.value;
	}

	public Rect getRectValue() throws DOMException {
		if ((this.value instanceof Rect) == false) {
			throw new DOMExceptionImpl(DOMException.INVALID_ACCESS_ERR,
					DOMExceptionImpl.RECT_ERROR);
		}
		return (Rect) this.value;
	}

	public RGBColor getRGBColorValue() throws DOMException {
		if ((this.value instanceof RGBColor) == false) {
			throw new DOMExceptionImpl(DOMException.INVALID_ACCESS_ERR,
					DOMExceptionImpl.RGBCOLOR_ERROR);
		}
		return (RGBColor) this.value;
	}

	public int getLength() {
		return (this.value instanceof Vector) ? ((Vector) this.value).size()
				: 0;
	}

	public CSSValue item(int index) {
		return (this.value instanceof Vector) ? ((CSSValue) ((Vector) this.value)
				.elementAt(index))
				: null;
	}

	public String toString() {
		return getCssText();
	}
}
