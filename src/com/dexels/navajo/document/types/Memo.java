package com.dexels.navajo.document.types;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version $Id$
 */

public final class Memo
    extends NavajoType {

  public final String contents;

  public Memo(String s) {
    super(Property.MEMO_PROPERTY);
    contents = s;
  }

  public String toString() {
    return contents;
  }

  public int compareTo(Object s) {
	  if (s==null) {
		return 0;
	}
	  if (s instanceof String) {
		return contents.compareTo((String)s);
	}
		return contents.compareTo(s.toString());
	}

public boolean isEmpty() {
    return contents==null || "".equals(contents);
}
  }

