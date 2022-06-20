/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;

public class ServiceRequest extends Request {

	private static final long serialVersionUID = 2821719303061929705L;
	
	private final Navajo request;
	private final boolean skipAuthorization;
	private final String tenant;
	
	public ServiceRequest(Navajo request, boolean skipAuthorization, String tenant) {
		super();
		
		this.request = request;
		this.tenant = tenant;
		// Set timout to 25 secs. (arbitrary) to prevent 'hanging' service requests on busy servers.
		setTimeout(25000);
		this.skipAuthorization = skipAuthorization;
	}
	
	@Override
	public Answer getAnswer() {
	    MDC.clear();
		String origin = getRequest().getHeader().getHeaderAttribute("origin");
		if ( origin != null && !origin.equals("")) {
			if ( origin.equals(TribeManagerFactory.getInstance().getMyUniqueId()) ) {
				return null;
			}
		}
		return new ServiceAnswer(this);
	}

	public Navajo getRequest() {
		return request;
	}

	public String getTenant() {
		return tenant;
	}
	public boolean isSkipAuthorization() {
		return skipAuthorization;
	}

}
