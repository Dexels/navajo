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

import java.io.*;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;
import com.enterprisedt.net.ftp.*;


public class FTPMap  implements Mappable, com.dexels.navajo.server.enterprise.queue.Queuable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3957374732092549725L;
	
	public String filename = "filename_not_specified";
	public Binary content;
	public String server;
	public String username;
	public String password;
	public String path;
	public boolean useBinary  = true;
	public boolean queuedSend = false;
	
	public boolean delete = false;
	public boolean mkdir = false;

	
	private long waitUntil = 0;
	private int retries = 0;
	private int maxRetries = 100;
	private Navajo myNavajo;
	private Access myAccess;

	public static int maxRunningInstances = -1;
	
	private String mode = "put";
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		myNavajo = inMessage;
		myAccess = access;
	}

	public void setContent(Binary b) {
		content = b;
	}
	
	public void setServer(String s) {
		server = s;
	}
	
	public void setUsername(String s) {
		username = s;
	}
	
	public void setPassword(String s) {
		password = s;
	}
	
	public void setPath(String s) {
		path = s;
	}
	
	public void setUseBinary(boolean b) {
		useBinary = b;
	}
	
	public void setFilename(String s) {
		filename = s;
	}
	
	public void store() throws MappableException, UserException {

		if ( queuedSend ) {
			try {
				RequestResponseQueueFactory.getInstance().send(this, 100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if ( server == null ) {
				throw new UserException(-1, "Set server name");
			}

			if ( username == null ) {
				throw new UserException(-1, "Set username");
			}

			if ( password == null ) {
				throw new UserException(-1, "Set password");
			}

			try {
				FTPClient ftpClient = new FTPClient( server );
				ftpClient.login( username, password );
				ftpClient.setType( useBinary ? FTPTransferType.BINARY : FTPTransferType.ASCII );
				if ( path != null ) {
					ftpClient.chdir( path );
				}
				if(delete) {
					ftpClient.delete(filename);
				} else if(mkdir) {
					try {
						ftpClient.mkdir(filename);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (FTPException e) {
						e.printStackTrace();
					}						
				} else {
					InputStream is = content.getDataAsStream();
					ftpClient.put( content.getDataAsStream(), filename );
					is.close();
					ftpClient.quit();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
				throw new UserException( -1, e.getMessage(), e);
			}
		}
	}

	public void kill() {
	}
	
	
	public Binary getRequest() {
		return content;
	}
	
	public Binary getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean send() {
		retries++;
		try {
			queuedSend = false;
			store();
		} catch (Exception e) {
			if ( myAccess != null ) {
				myAccess.setException(e);
			}
			return false;
		} finally {
			queuedSend = true;
		}
		return true;
	}

	/**
	 * Enable queued operation of this adapter.
	 */
	public void setQueuedSend(boolean b) {
		this.queuedSend = b;
	}

	public static void main(String [] args) throws Exception {

		RequestResponseQueueFactory.getInstance().setQueueOnly(true);
		
//		FTPMap f = new FTPMap();
//		Binary b = new Binary(new FileInputStream(new File("/home/arjen/Dexels/logo_zw.eps")));
//		
//		f.setContent(b);
//		f.setServer("ftp.servage.net");
//		f.setUsername("yourcapital");
//		f.setFilename("zipped.zip");
//		f.setPassword("janpaul");
//		//f.setPath("/home/arjen");
//		f.setQueuedSend(true);
//		f.store();
//		System.err.println("Initiated queued send");
		while ( true ) {
			Thread.sleep(10000);
		}
	}

	public void setWaitUntil(long w) {
		waitUntil = w;
	}

	public long getWaitUntil() {
		return waitUntil;
	}

	public int getRetries() {
		return retries;
	}
	
	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int r) {
		maxRetries = r;
	}

	public void resetRetries() {
		retries = 0;
	}
	
	public Access getAccess() {
		return myAccess;
	}

	public Navajo getNavajo() {
		return myNavajo;
	}
	
	public int getMaxRunningInstances() {
		return maxRunningInstances;
	}

	public void setMaxRunningInstances(int maxRunningInstances) {
		this.maxRunningInstances = maxRunningInstances;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isMkdir() {
		return mkdir;
	}

	public void setMkdir(boolean mkdir) {
		this.mkdir = mkdir;
	}

}
