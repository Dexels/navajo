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
package navajobirt;

/**
 * VERSION HISTORY
 * 
 * 2.0.3. -Added more verbose timing information
 * 
 */
public class Version extends dexels.Version {

	public static final int MAJOR = 1;
	public static final int MINOR = 0;
	public static final int PATCHLEVEL = 0;
	public static final String VENDOR = "Dexels";
	public static final String PRODUCTNAME = "Navajo BIRT Adapter";
	public static final String RELEASEDATE = "2006-04-09";
	
	//Included packages.
	String [] includes = {"navajodocument.Version"};
	
	public Version() {
		addIncludes(includes);
		setReleaseDate(RELEASEDATE);
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
		dexels.Version [] d = (dexels.Version [] ) v.getIncludePackages();
		for (int i = 0; i < d.length; i++) {
			System.err.println("\t"+d[i].toString());
		}
	}
}