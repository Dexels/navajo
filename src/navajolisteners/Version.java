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
package navajolisteners;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

import com.dexels.navajo.server.listener.http.TmlHttpServlet;


public class Version extends dexels.Version {

	public static final int MAJOR = 3;
	public static final int MINOR = 0;
	public static final int PATCHLEVEL = 1;
	public static final String VENDOR = "Dexels";
	public static final String PRODUCTNAME = "Navajo Listeners";
	public static final String RELEASEDATE = "2006-06-30";
	
	//	 Included packages.
	
	public Version() {
//		javax.mail.Address a;
		setReleaseDate(RELEASEDATE);
	}
	
	
	
	
	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		ServiceReference cc =  bc.getServiceReference(HttpService.class.getName());
		HttpService h =  (HttpService) bc.getService(cc);
		System.err.println("h: "+h);
		HttpContext ccc = h.createDefaultHttpContext();
h.registerServlet("/Postman", new TmlHttpServlet(), null, ccc);	
	}




	public int getMajor() {
		return MAJOR;
	}

	public int getMinor() {
		return MINOR;
	}

	public int getPatchLevel() {
		return PATCHLEVEL;
	}

	public String getVendor() {
		return VENDOR;
	}

	public String getProductName() {
		return PRODUCTNAME;
	}

	public static void main(String [] args) {
		Version v = new Version();
		System.err.println(v.versionString());
	}
}

