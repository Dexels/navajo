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

public final class Memo extends NavajoType {

  public final String contents;
  
  public Memo(String s) {
  	super(Property.MEMO_PROPERTY);
  	contents = s;
  }

  public String toString() {
  	return contents;
  }


	public int compareTo(Object s) {
		return contents.compareTo(s);
	}
}
