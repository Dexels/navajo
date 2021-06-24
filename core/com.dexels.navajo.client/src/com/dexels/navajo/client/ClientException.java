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
package com.dexels.navajo.client;

import java.io.IOException;

public class ClientException extends IOException {

    private static final long serialVersionUID = 75302782005830988L;
    private final int code;
    private final int level;

    public ClientException(int level, int code, String message) {
        super(message);
        this.code = code;
        this.level = level;
    }

    public ClientException(int level, int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.level = level;
    }

    public ClientException(String message) {
		this(-1,-1,message);
	}

	public int getCode() {
        return this.code;
    }

    public int getLevel() {
        return this.level;
    }
}
