package com.dexels.navajo.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

public class RemoteAsyncRequest extends Request {
    private final static Logger logger = LoggerFactory.getLogger(RemoteAsyncRequest.class);
    private static final long serialVersionUID = 10518850435818645L;

    private String ref;

    public RemoteAsyncRequest(String ref) {
        this.ref = ref;
    }

    @Override
    public Answer getAnswer() {
        Answer a = null;
        logger.info("Constructing RemoteAsyncRequest answer for: {}", ref);
        try {
            a = new RemoteAsyncAnswer(this);
        } catch (Exception e) {
            logger.error("Exception on constructing RemoteAsyncAnswer: ", e);
        }
        logger.info("Returning RemoteAsyncRequest answer for: {}", ref);
        return a;
    }

    public String getRef() {
        return ref;
    }

}
