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

	private static final long serialVersionUID = 7984829191626051515L;
	public final String contents;

	public Memo(String s) {
		super(Property.MEMO_PROPERTY);
		contents = s;
	}

	@Override
	public String toString() {
		return contents;
	}

	@Override
	public int hashCode() {
		if (contents == null) {
			return 334234;
		}
		return contents.hashCode();
	}

	@Override
	public int compareTo(Memo s) {
		if (s == null) {
			return 0;
		}
		return contents.compareTo(s.toString());
	}

	@Override
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

	@Override
	public boolean isEmpty() {
		return contents == null || "".equals(contents);
	}
}
