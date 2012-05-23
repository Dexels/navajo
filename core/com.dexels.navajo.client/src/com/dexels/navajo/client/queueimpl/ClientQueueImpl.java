package com.dexels.navajo.client.queueimpl;

import java.io.Serializable;

import com.dexels.navajo.client.NavajoClient;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class ClientQueueImpl extends NavajoClient implements Serializable {

	private static final long serialVersionUID = -5935586380968357878L;
	private transient ThreadPool myPool = null;

	public ClientQueueImpl() {
	}


	public int getQueueSize() {
		if (myPool != null) {
			return myPool.getQueueSize();
		} else {
			return 0;
		}
	}

	public int getActiveThreads() {
		if (myPool != null) {
			return myPool.getActiveThreads();
		} else {
			return 0;
		}
	}

	public void destroy() {
		if (myPool != null) {
			myPool.destroy();
		}
	}

}
