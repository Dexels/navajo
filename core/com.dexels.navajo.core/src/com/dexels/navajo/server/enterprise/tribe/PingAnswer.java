/**
 * Title:        Navajo<p>
 * Description:  This file is part of the Navajo Service Oriented Application Framework<p>
 * Copyright:    Copyright 2002-2008 (c) Dexels BV<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package com.dexels.navajo.server.enterprise.tribe;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.UserException;

public class PingAnswer extends Answer implements Mappable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7500090213107679662L;
	
	public double cpuLoad;
	public int threadCount;
	public long requestCount;
	public long uptime;
	public boolean busy = false;
	public String statistics;
	public String postmanURL;
	
	public PingAnswer(Request q) {
		super(q);
		refreshPingStatistics();
	}

	private void refreshPingStatistics() {
		cpuLoad = DispatcherFactory.getInstance().getNavajoConfig().getCurrentCPUload();
		threadCount = DispatcherFactory.getInstance().getAccessSet().size();
		requestCount = DispatcherFactory.getInstance().getRequestCount();
		uptime = DispatcherFactory.getInstance().getUptime();
		busy = DispatcherFactory.getInstance().isBusy();
		statistics = TribeManagerFactory.getInstance().getStatistics();
//		postmanURL = JabberWorkerFactory.getInstance().getPostmanURL();
	}
	
	@Override
	public boolean acknowledged() {
		return true;
	}

	public double getCpuLoad() {
		return cpuLoad;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public long getRequestCount() {
		return requestCount;
	}

	public long getUptime() {
		return uptime;
	}

	@Override
	public void kill() {
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
	}

	@Override
	public void store() throws MappableException, UserException {
	}

	public boolean isBusy() {
		return busy;
	}

	public String getStatistics() {
		return statistics;
	}
	
	public String getPostmanURL() {
		return postmanURL;
	}

}
