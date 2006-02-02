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

import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class WebserviceAccess {

	private String webservice;
	private int count;
	private long totalProcessTime;
	private int userErrors;
	private int systemErrors;
	private int conditionErrors;
	
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
}
