package com.dexels.navajo.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

public class RemoteAsyncAnswer extends Answer {

    private final static Logger logger = LoggerFactory.getLogger(RemoteAsyncAnswer.class);

    private static final long serialVersionUID = 489178532753193613L;

    private Object ownerOfRef = null;
    private Object hostNameOwnerOfRef = null;
    private boolean acknowledged = false;

    public RemoteAsyncAnswer(RemoteAsyncRequest q) {
        super(q);

        RemoteAsyncRequest rasr = (RemoteAsyncRequest) getMyRequest();
        String ref = rasr.getRef();
        logger.info("Constructing RemoteAsyncAnswer for: {}", ref );
        if (AsyncStore.getInstance().getInstance(ref) != null) {
            logger.info("Found ref {} in my AsyncStore!", ref);
            acknowledged = true;
            ownerOfRef = TribeManagerFactory.getInstance().getMyMembership().getAddress();
            hostNameOwnerOfRef = TribeManagerFactory.getInstance().getMyMembership().getMemberName();
        }

    }

    @Override
    public boolean acknowledged() {
        return acknowledged;
    }

    public Object getOwnerOfRef() {
        return ownerOfRef;
    }

    public Object getHostNameOwnerOfRef() {
        return hostNameOwnerOfRef;
    }

}
