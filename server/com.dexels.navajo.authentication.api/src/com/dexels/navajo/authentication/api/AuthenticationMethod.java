/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.authentication.api;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public interface AuthenticationMethod {
    public static final String OAUTH_IDENTIFIER = "Bearer";
    public static final String BASIC_IDENTIFIER = "Basic";
    public static final String DEFAULT_IDENTIFIER = "default";

    public AuthenticationMethod getInstanceForRequest(String header);

    public String getIdentifier();

    public void process(Access access) throws AuthorizationException;
}