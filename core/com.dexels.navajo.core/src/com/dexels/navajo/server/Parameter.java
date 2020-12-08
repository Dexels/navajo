/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/


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
