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
package com.dexels.navajo.server.statistics;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class WebserviceAccess implements Mappable {

	public String webservice;
	public int count;
	public int userErrors;
	public int systemErrors;
	public int conditionErrors;
	public double avgProcessingTime;
	
	private long totalProcessTime;
	
	public WebserviceAccess(String w) {
		this.webservice = w;
	}
	
	public void registerError(Exception e) {
		if ( e instanceof UserException ) {
			userErrors++;
		} else if ( e instanceof SystemException ) {
			systemErrors++;
		} else if ( e instanceof ConditionErrorException ) {
			conditionErrors++;
		} else {
			systemErrors++;
		}
	}
	
	public void nextAccess(long pt) {
		count++;
		totalProcessTime += pt;
	}
	
	public int getAccessCount() {
		return count;
	}
	
	public double getAvgProcessingTime() {
		return (double) totalProcessTime / (double) count;
	}
	
	public int getUserErrors() {
		return userErrors;
	}
	
	public int getSystemErrors() {
		return systemErrors;
	}
	
	public int getConditionErrors() {
		return conditionErrors;
	}
	
	public String getWebservice() {
		return this.webservice;
	}

	public int getCount() {
		return this.count;
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
