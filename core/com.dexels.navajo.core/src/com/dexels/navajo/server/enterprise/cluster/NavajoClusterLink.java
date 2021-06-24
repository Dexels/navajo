/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.cluster;

import java.util.function.Consumer;


public interface NavajoClusterLink {
    public void subscribeNavajoLinkTopic(String navajoLinkConsumerId, String topic, Consumer<NavajoClusterLinkMessage> callback);
    public void unsubscribeNavajoLinkTopic(String navajoLinkConsumerId, String topic);
    
    public void transmit(NavajoClusterLinkMessage message);
}
