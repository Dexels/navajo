/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
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
package com.dexels.navajo.lockguard;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

/**
 * LockDefinition is supplied as follows:
 * 
 * <message name="LockDefinition">
 *   <property name="Webservice" type="string"/>
 *   <property name="MatchUsername" type="boolean"/>
 *   <property name="MatchRequest" type="boolean"/>
 *   <property name="ExcludeProperties" type="string"/>
 *   <property name="ExcludeMessages" type="string"/>
 *   <property name="MaxInstanceCount" type="integer"/>
 *   <property name="WaitTimeOut" type="integer"/>
 * </message>
 * 
 * @author arjen
 *
 */
public class LockDefinition implements Mappable {
	
	public static final String VERSION = "$Id$";
	
	public static int sequence = 0;
	
	/**
	 * Matching parameters.
	 */
	public int id;                 // generated unique id.
	public String webservice;      // regular expression.
	public boolean matchUsername;  // if true, username is matched in order to determine existing lock.
	public boolean matchRequest;   // if true, request Navajo is matched in order to determine existing lock.
	public HashMap excludeProperties;  // list of properties that are excluded for request matching
	public int timeOut;            // set timeout for retrying request, if 0, don't wait at all, if -1, wait forever...
	
	/**
	 * Number of allowed maximum instances in case of match.
	 */
	public int maxInstanceCount;
	
	public int getId() {
		return this.id;
	}
	
	public String getWebservice() {
		return this.webservice;
	}
	
	public boolean getMatchUsername() {
		return this.matchUsername;
	}
	
	public boolean getMatchRequest() {
		return this.matchRequest;
	}
	
	public int getMaxInstanceCount() {
		return this.maxInstanceCount;
	}
	
	public LockDefinition(
				String webservice,
				boolean matchUsername,
				boolean matchRequest,
				HashMap excludeProperties,
				int timeOut,
				int maxInstanceCount
				) {
	
		this.id = sequence++;
		this.webservice = webservice;
		this.matchUsername = matchUsername;
		this.matchRequest = matchRequest;
		this.excludeProperties = excludeProperties;
		this.timeOut = timeOut;
		this.maxInstanceCount = maxInstanceCount;
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	public void kill() {
		// TODO Auto-generated method stub
		
	}
	
	
}
