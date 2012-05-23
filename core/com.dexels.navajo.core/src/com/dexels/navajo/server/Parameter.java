

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;


public class Parameter implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4327023666866787383L;
	
	public int id = -1;
    public String name;
    public String type;
    public String expression;
    public Object value;
    public String condition = "";
    public int def_id = 0;

}
