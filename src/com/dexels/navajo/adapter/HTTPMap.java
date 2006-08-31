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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;

public class HTTPMap implements Mappable {

	public Binary content = null;
	public String textContent = null;
	public String method = "POST";
	public String contentType = null;
	public String url = null;
	public boolean doSend = false;
	public Binary result = null;
	public String textResult = null;
	
	private static int instances = 0;
	
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
	}

	public void setTextContent(String s) {
		textContent = s;
	}
	
	public void setContent(Binary b) {
		content = b;
	}
	
	public void setUrl(String s) {
		url = s;
	}
	
	public void setContentType(String s) {
		contentType = s;
	}
	
	public void setDoSend(boolean b) throws UserException {
		instances++;
		
		if ( instances > 100 ) {
			AuditLog.log("HTTPMap", "WARNING: More than 100 waiting HTTP requests");
		}
		try {
			URL u = new URL("http://" + url);
			HttpURLConnection con = null;
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod(method);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			if ( contentType != null ) {
				con.setRequestProperty("Content-type", contentType);
			}
			if ( textContent != null ) {
				OutputStreamWriter osw = null;
				osw = new OutputStreamWriter(con.getOutputStream());
				try {
					osw.write(textContent);
				} finally {
					if ( osw != null ) {
						osw.close();
					}
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
				throw new UserException(-1, "Empty content.");
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
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserException(-1, e.getMessage(), e);
		} finally {
			instances--;
		}
		
		
	}
	
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
	
	public void store() throws MappableException, UserException {
	}

	public void kill() {
	}
	
	public static void main(String [] args) throws Exception {
		// 82.94.253.174/dexels_interface?method=pushKNVBData 
		
		String test = "<xml>" +
		"<wedstrijd>" +
		"<district>Zuid</district>" +
		"<wednr>433</wednr>" +
		"<thuisid>2085</thuisid>" +
		"<thuisteam>74013</thuisteam>" +
		"<uitid>234</uitid>" +
		"<uitteam>242542</uitteam>" +
		"<datum>2006-07-08</datum>" +
		"<tijd>16:00</tijd>" +
		"<thuisscore>1</thuisscore>" +
		"<uitscore>4</uitscore>" +
		"<thuisscoreverl>1</thuisscoreverl>" +
		"<uitscoreverl>4</uitscoreverl>" +
		"<thuispen>13</thuispen>" +
		"<uitpen>43</uitpen>" +
		"<status>afg</status>" +
		"</wedstrijd>" +
		"</xml>";
		
		HTTPMap hm = new HTTPMap();
		hm.setUrl("82.94.253.174/dexels_interface?method=pushKNVBData");
		hm.setContentType("text/xml; charset=UTF-8");
		hm.setTextContent(test);
		hm.setDoSend(true);
		String tr = hm.getTextResult();
		System.err.println(tr);
		Binary b = hm.getResult();
		
		FileOutputStream fos = new FileOutputStream("/home/arjen/testje");
		b.write(fos);
		fos.close();
		
		
		
	}

}
