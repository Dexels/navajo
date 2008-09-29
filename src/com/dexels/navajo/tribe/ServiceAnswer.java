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
package com.dexels.navajo.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.server.enterprise.tribe.Answer;

public class ServiceAnswer extends Answer {

	private static final long serialVersionUID = 2022715399366921183L;
	
	private Navajo response;
	private boolean hasError;
	private String errorMessage;
	
	public ServiceAnswer(ServiceRequest q) {
		super(q);
		try {
			response = DispatcherFactory.getInstance().handle(q.getRequest(), q.isSkipAuthorization());
		} catch (FatalException e) {
			hasError = true;
			errorMessage = e.getMessage();
		}
	}

	public boolean acknowledged() {
		return true;
	}

	public Navajo getResponse() {
		return response;
	}

	public void setResponse(Navajo response) {
		this.response = response;
	}

	public boolean isHasError() {
		return hasError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
