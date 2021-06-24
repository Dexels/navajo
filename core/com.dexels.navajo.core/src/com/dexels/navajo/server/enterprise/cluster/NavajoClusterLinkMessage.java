/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.cluster;


public class NavajoClusterLinkMessage {
    public static final String TOPIC_SEPARATOR = "-";

    private final String key;
    private final String msg;
    private final String topic;
    
    /** Topic will be identical to message! */
    public NavajoClusterLinkMessage(String topic, String msg) {
        this.key = msg;
        this.msg = msg;
        this.topic = topic;
    }
    
    public NavajoClusterLinkMessage(String topic, String key, String msg) {
        this.key = key;
        this.msg = msg;
        this.topic = topic;
    }

    public String getKey() {
        return key;
    }
    public String getPublicKey() {
        return getTopic() + TOPIC_SEPARATOR + key;
    }

    public String getMsg() {
        return msg;
    }

    public String getTopic() {
        return topic;
    }

}
