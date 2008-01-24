package com.dexels.navajo.document.types;

import com.dexels.navajo.document.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version $Id$
 */

public final class Memo extends NavajoType implements Comparable<Memo> {

	public final String contents;

	public Memo(String s) {
		super(Property.MEMO_PROPERTY);
		contents = s;
	}

	public String toString() {
		return contents;
	}

	public int hashCode() {
		if (contents == null) {
			return 334234;
		}
		return contents.hashCode();
	}

	public int compareTo(Memo s) {
		if (s == null) {
			return 0;
		}
		return contents.compareTo(s.toString());
	}

	public boolean equals(Object s) {
		if (s == null && contents != null) {
			return false;
		}
		if (contents == null && s != null) {
			return false;
		}
		if (s == null && contents == null) {
			return true;
		}
		if (s == null) {
			return false;
		}
		return contents.equals(s.toString());
	}

	public boolean isEmpty() {
		return contents == null || "".equals(contents);
	}
}
