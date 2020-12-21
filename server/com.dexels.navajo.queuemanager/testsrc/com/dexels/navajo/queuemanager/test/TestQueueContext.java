/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.queuemanager.test;

import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.listener.http.queuemanager.api.QueueContext;


public class TestQueueContext implements QueueContext {

	private final static String QUEUE1 = "NormalQueue";
	private final static double QUEUE1_HEALTH = 0.4;
	private final static int QUEUE1_QUEUE = 3;

	private final static String QUEUE2 = "PriorityQueue";
	private final static double QUEUE2_HEALTH = 0.9;
	private final static int QUEUE2_QUEUE = 3;

	@Override
	public double getQueueHealth(String queueName) {
		if(queueName.equals(QUEUE1)) {
			return QUEUE1_HEALTH;
		}
		if(queueName.equals(QUEUE2)) {
			return QUEUE2_HEALTH;
		}
		throw new IllegalArgumentException("Unknown queue: "+queueName);
	}

	@Override
	public int getQueueSize(String queueName) {
		if(queueName.equals(QUEUE1)) {
			return QUEUE1_QUEUE;
		}
		if(queueName.equals(QUEUE2)) {
			return QUEUE2_QUEUE;
		}
		throw new IllegalArgumentException("Unknown queue: "+queueName);
	}

	@Override
	public Set<String> getQueueNames() {
		Set<String> queues = new HashSet<String>();
		queues.add(QUEUE1);
		queues.add(QUEUE2);
		return queues;
	}

}
