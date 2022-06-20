/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

public class RemoteAsyncRequest extends Request {
    private static final long serialVersionUID = 10518850435818645L;

    private String ref;

    public RemoteAsyncRequest(String ref) {
        this.ref = ref;
    }

    @Override
    public Answer getAnswer() {
    	return new RemoteAsyncAnswer(this);
    }

    public String getRef() {
        return ref;
    }

}
