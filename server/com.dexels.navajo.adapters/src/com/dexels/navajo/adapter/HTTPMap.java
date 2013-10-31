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
package com.dexels.navajo.adapter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.http.HTTPMapInterface;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.enterprise.queue.Queuable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;
import com.dexels.navajo.util.AuditLog;

public class HTTPMap implements Mappable, Queuable, HTTPMapInterface {

	private static final long serialVersionUID = 5398399368623971687L;
	
	public Binary content = null;
	public String textContent = null;
	public String method = "POST";
	public String contentType = null;
	public String url = null;
	public boolean doSend = false;
	public boolean queuedSend = false;
	public boolean catchConnectionTimeOut = true;
	public boolean hasConnectionTimeOut = false;
	public long waitUntil = 0;
	public Binary result = null;
	public String textResult = null;
	public int connectTimeOut = 5000;
	public int readTimeOut = -1;
	public int retries = 0;
	public int maxRetries = 100;
	public String headerKey;
	public String headerValue;
	
	private static int instances = 0;
	private Navajo myNavajo;
	private Access myAccess;

	public static int maxRunningInstances = -1;
	
	private final static Logger logger = LoggerFactory
			.getLogger(HTTPMap.class);
	
	private HashMap<String, String> headers = new HashMap<String, String>();
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		myNavajo = access.getInDoc();
		myAccess = access;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setTextContent(java.lang.String)
	 */
	@Override
	public void setTextContent(String s) {
		textContent = s;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setContent(com.dexels.navajo.document.types.Binary)
	 */
	@Override
	public void setContent(Binary b) {
		content = b;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setUrl(java.lang.String)
	 */
	@Override
	public void setUrl(String s) {
		url = s;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setContentType(java.lang.String)
	 */
	@Override
	public void setContentType(String s) {
		contentType = s;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setConnectTimeOut(int)
	 */
	@Override
	public void setConnectTimeOut(int i) {
		this.connectTimeOut = i;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setReadTimeOut(int)
	 */
	@Override
	public void setReadTimeOut(int i) {
		this.readTimeOut = i;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setDoSend(boolean)
	 */

	@Override
	public void setDoSend(boolean b) throws UserException {
		if ( !queuedSend ) {
			sendOverHTTP();
		}
	}
	
	private void addHeader(String key, String value){
		headers.put(headerKey, headerValue);
		headerKey = null;
		headerValue = null;
	}
	
	public void setHeaderKey(String key){
		headerKey = key;
		if( headerValue != null){
			addHeader(headerKey, headerValue);
		}
	}
	
	public void setHeaderValue(String value){
		headerValue = value;
		if( headerKey != null){
			addHeader(headerKey, headerValue);
		}
	}
	
	private final void sendOverHTTP() throws UserException {
		increaseInstanceCount();

		if ( isBelowInstanceThreshold()  ) {
			AuditLog.log("HTTPMap", "WARNING: More than 100 waiting HTTP requests", Level.WARNING, myAccess.accessID);
		}
		try {

			AuditLog.log("HTTPMap", "About to send to: " + url, Level.INFO, myAccess.accessID);
			URL u = null;
			if(!url.startsWith("http://") && (!url.startsWith("https://"))) {
				AuditLog.log("HTTPMap", "No protocol. Always prepend protocol. Assuming http.",Level.WARNING);
				u = new URL("http://" + url);
			} else {
				u = new URL(url);
			}
			
			
			HttpURLConnection con = null;
			con = (HttpURLConnection) u.openConnection();
			con.setConnectTimeout(connectTimeOut);
			if ( readTimeOut != -1 ) {
				con.setReadTimeout(readTimeOut);
			}
			con.setRequestMethod(method);
			if ( method.equals("POST")) {
				con.setDoOutput(true);
				con.setDoInput(true);
			}
			con.setUseCaches(false);
			if ( contentType != null ) {
				con.setRequestProperty("Content-type", contentType);
			}
			// Add headers
			if(headers.size() > 0){
				Iterator<String> keySet = headers.keySet().iterator();
				while(keySet.hasNext()){
					String key = keySet.next();
					con.setRequestProperty(key, headers.get(key));
				}
			}
			if ( textContent != null ) {
				OutputStreamWriter osw = null;
				osw = new OutputStreamWriter(con.getOutputStream());
				try {
					osw.write(textContent);
				} finally {
					osw.close();
				}
			} else if ( content != null ) {
				OutputStream os = null;
				os = con.getOutputStream();
				try {
					content.write(os);
				} finally {
					if ( os != null ) {
						os.close();
					}
				}
			} else {
				if ( method.equals("POST")) {
					AuditLog.log("HTTPMap", "Empty content.", Level.INFO, myAccess.accessID);
					throw new UserException(-1, "");
				}
			}

			InputStream is = null;
			is = con.getInputStream();
			try {
				result = new Binary(is);
			} finally {
				if ( is != null ) {
					is.close();
				}
			}

		} catch (java.net.SocketTimeoutException sto) {
			// 
			if (!catchConnectionTimeOut) {
				AuditLog.log("HTTPMap", "Got connectiontimeout", Level.WARNING, myAccess.accessID);
				throw new UserException(-1, sto.getMessage(), sto);
			} else {	
				AuditLog.log("HTTPMap", "Got connectiontimeout", Level.WARNING, myAccess.accessID);
				hasConnectionTimeOut = true;
			}
		} catch (Exception e) {
			AuditLog.log("HTTPMap", "Got other exception: " + e.getMessage(), Level.WARNING, myAccess.accessID);
			throw new UserException(-1, e.getMessage(), e);
		} finally {
			decreaseInstanceCount();
		}
	}
	
	
	protected void increaseInstanceCount() {
		instances++;
	}

	protected void decreaseInstanceCount() {
		instances++;
	}
	


	protected boolean isBelowInstanceThreshold() {
		return instances < 100;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#getTextResult()
	 */
	@Override
	public String getTextResult() {
		if ( result != null ) {
			return new String(result.getData());
		} else {
			return null;
		}
	}
	
	public Binary getResult() {
		return result;
	}
	
	@Override
	public void store() throws MappableException, UserException {
		if (queuedSend) {
			try {
				RequestResponseQueueFactory.getInstance().send(this, 100);
			} catch (Exception e) {
				AuditLog.log("HTTPMap", e.getMessage(), Level.WARNING, myAccess.accessID);
			}
		}
	}

	@Override
	public void kill() {
	}
	
	@Override
	public Binary getResponse() {
		return null;
	}

	@Override
	public boolean send() {
		retries++;
		try {
			sendOverHTTP();
		} catch (UserException e) {
			if ( myAccess != null ) {
				myAccess.setException(e);
			}
			logger.warn("Error performing HTTP: " + e.getMessage() + ", for access: " + (myAccess != null ? myAccess.accessID : "unknown" ));
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setRequest(com.dexels.navajo.document.types.Binary)
	 */
	@Override
	public void setRequest(Binary b) {
		setContent(b);
	}

	@Override
	public void setQueuedSend(boolean b) {
		queuedSend = b;
	}
	

	@Override
	public Binary getRequest() {
		if ( textContent != null ) {
			return new Binary(textContent.getBytes());
		} else {
			return content;
		}
	}

	@Override
	public void setWaitUntil(long w) {
		waitUntil = w;
	}
	
	@Override
	public long getWaitUntil() {
		return waitUntil;
	}

	@Override
	public int getRetries() {
		return retries;
	}

	@Override
	public int getMaxRetries() {
		return maxRetries;
	}

	@Override
	public void setMaxRetries(int r) {
		maxRetries = r;
	}

	@Override
	public void resetRetries() {
		retries = 0;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setMaxInstances()
	 */
	@Override
	public void setMaxInstances() {
		
	}

	@Override
	public Access getAccess() {
		return myAccess;
	}

	@Override
	public Navajo getNavajo() {
		return myNavajo;
	}
	
	@Override
	public int getMaxRunningInstances() {
		return maxRunningInstances;
	}

	@Override
	public void setMaxRunningInstances(int maxRunningInstances) {
		setStaticMaxRunningInstances(maxRunningInstances);
	}
	
	private static void setStaticMaxRunningInstances(int maxRunningInstances) {
		HTTPMap.maxRunningInstances = maxRunningInstances;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setMethod(java.lang.String)
	 */
	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.adapter.URLMap#setCatchConnectionTimeOut(boolean)
	 */
	@Override
	public void setCatchConnectionTimeOut(boolean catchConnectionTimeOut) {
		this.catchConnectionTimeOut = catchConnectionTimeOut;
	}
	
}
