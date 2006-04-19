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

import java.io.File;
import java.io.FileInputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPTransferType;


public class FTPMap  implements Mappable {

	public String filename = "filename_not_specified";
	public Binary content;
	public String server;
	public String username;
	public String password;
	public String path;
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
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
	
	public void setFilename(String s) {
		filename = s;
	}
	
	public void store() throws MappableException, UserException {
		
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
			ftpClient.setType(FTPTransferType.BINARY);
			if ( path != null ) {
				ftpClient.chdir( path );
			}
			ftpClient.put( content.getData(), filename );
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new UserException( -1, e.getMessage(), e);
		}
	}

	public void kill() {
	}
	
	public static void main(String [] args) throws Exception {
		
		ZipMap zm = new ZipMap();
		
		FTPMap f = new FTPMap();
		Binary b = new Binary(new FileInputStream(new File("/home/arjen/dexels.gif")));
		zm.setName("dexels_logo.gif");
		zm.setContent(b);
		f.setContent(zm.getZipped());
		
		f.setServer("spiritus");
		f.setUsername("arjen");
		f.setFilename("zipped.zip");
		f.setPassword("xxxxxx");
		//f.setPath("/home/arjen");
		f.store();
	}

}
