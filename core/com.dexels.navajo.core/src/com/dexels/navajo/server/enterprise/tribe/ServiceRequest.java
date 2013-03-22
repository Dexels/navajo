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

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.DispatcherFactory;

public class ServiceRequest extends Request {

	private static final long serialVersionUID = 2821719303061929705L;
	
	private final Navajo request;
	private final boolean skipAuthorization;
	
	public ServiceRequest(Navajo request, boolean skipAuthorization) {
		super();
		this.request = request;
		// Set timout to 25 secs. (arbitrary) to prevent 'hanging' service requests on busy servers.
		setTimeout(25000);
		this.skipAuthorization = skipAuthorization;
	}
	
	public Answer getAnswer() {
		
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

	public boolean isSkipAuthorization() {
		return skipAuthorization;
	}

}
