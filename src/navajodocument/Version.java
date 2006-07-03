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
package navajodocument;

/**
 * VERSION HISTORY
 *
 * 8.2.19 Added setting of property types when calling setAnyValue() method of Property object.
 *
 * 8.2.20 Added pattern matching to ToMoney in case of string argument.
 *
 * 8.2.21 Added StopwatchTime type property
 *
 * 8.2.22 Fixed bug in StopwatchTime type property
 *
 * 8.2.23 ...
 * 
 * 8.2.24 ...
 *
 * 8.2.25 Added request id to header
 * 
 * 9.0.00 Refactored NANO: split the generic functions and the XML-implementation specific
 *        functions.
 *        
 *        Changed the encoding of binaries. Binary data will be added as text node under a
 *        property, instead of an attribute. So it is NOT backwards compatible. If you upgrade
 *        a server, you will need to upgrade the client too.
 *        
 *        Added QDSSSax parser implementation (Quick 'n Dirty Stream Sharing SAX) 
 *        This implementation can handle binaries of any size*
 *        
 * 9.0.1 Binary now supports proper compare function.
 *        
 *     * limited only by diskspace
 */
public class Version extends dexels.Version {

	public static final int MAJOR          = 9;
	public static final int MINOR          = 0;
	public static final int PATCHLEVEL     = 1;
	public static final String VENDOR      = "Dexels";
	public static final String PRODUCTNAME = "Navajo Document";
	public static final String RELEASEDATE = "2006-07-03";

	// Included packages.

	public Version() {
		setReleaseDate(RELEASEDATE);
		String name = System.getProperty("com.dexels.navajo.DocumentImplementation");
		if (name == null || name.equals("com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl"))
			setSpecialVersion("JAXPIMPL");
		else
			setSpecialVersion("NANOIMPL");
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
		System.err.println(v.toString());
	}
}
