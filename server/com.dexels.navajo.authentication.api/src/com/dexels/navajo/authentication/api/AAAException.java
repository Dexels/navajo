package com.dexels.navajo.authentication.api;

/**
 * <p>
 * Title:
 * <h3>SportLink Services</h3><br>
 * </p>
 * <p>
 * Description: Web Services for the SportLink Project<br>
 * <br>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002<br>
 * </p>
 * <p>
 * Company: Dexels.com<br>
 * </p>
 * 
 * @author unascribed
 * @version $Id$
 * 
 *          DISCLAIMER
 * 
 *          THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *          WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *          MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *          IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE LIABLE FOR ANY
 *          DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *          DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 *          GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *          INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 *          IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *          OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 *          IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *          ====================================================================
 */

public final class AAAException extends Exception {

	private static final long serialVersionUID = 385711811441748430L;

	public AAAException(String s) {
		super(s);
	}

	public AAAException(String s, Throwable t) {
		super(s, t);
	}

}