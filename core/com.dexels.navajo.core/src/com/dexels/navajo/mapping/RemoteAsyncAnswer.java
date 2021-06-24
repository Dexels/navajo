/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

public class RemoteAsyncAnswer extends Answer {

    private static final Logger logger = LoggerFactory.getLogger(RemoteAsyncAnswer.class);

    private static final long serialVersionUID = 489178532753193613L;

    private String ownerOfRefId = null;
    private String hostNameOwnerOfRef = null;
    private boolean acknowledged = false;

    public RemoteAsyncAnswer(RemoteAsyncRequest q) {
        super(q);

        RemoteAsyncRequest rasr = (RemoteAsyncRequest) getMyRequest();
        String ref = rasr.getRef();
        logger.info("Constructing RemoteAsyncAnswer for: {}", ref );
        if (AsyncStore.getInstance().getInstance(ref) != null) {
            logger.info("Found ref {} in my AsyncStore!", ref);
            acknowledged = true;
            ownerOfRefId = TribeManagerFactory.getInstance().getMyMembership().getMemberId();
            hostNameOwnerOfRef = TribeManagerFactory.getInstance().getMyMembership().getMemberName();
        }

    }

    @Override
    public boolean acknowledged() {
        return acknowledged;
    }

    public Object getOwnerOfRef() {
       return TribeManagerFactory.getInstance().getMember(ownerOfRefId);
    }

    public String getHostNameOwnerOfRef() {
        return hostNameOwnerOfRef;
    }

}
