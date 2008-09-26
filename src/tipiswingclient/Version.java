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
package tipiswingclient;
/**
 *
 * 2.0.12: Added a nullcheck in the ConditionErrorParser.
 * 2.0.13: Fixed weeknumber problem in calendar.
 * 2.0.14: Fixed problem with message table filter. When user hits save button the filter is disabled.
 * 2.0.15: Now possible to open excel stuff in Linux.
 * 2.0.16: Added stopwatchtime. Fixed calendar bug.
 * 2.0.17: Refactored MessageTable / MessageTablePanel, changed column saving methods.
 *         needed by SwingTipi
 * 2.0.18: (Probably also a lot of other changes) Added setVisibleRowCount for picklists
 *         
 */
public class Version extends dexels.Version {

	public static final int MAJOR = 2;
	public static final int MINOR = 0;
	public static final int PATCHLEVEL = 1;
	public static final String VENDOR = "Dexels";
	public static final String PRODUCTNAME = "Tipi Swing Client";

	//Included packages.
	String [] includes = {"navajodocument.Version"};

	public Version() {
		addIncludes(includes);
		setReleaseDate("2006-06-29");
	}

	@Override
	public int getMajor() {
		return MAJOR;
	}

	@Override
	public int getMinor() {
		return MINOR;
	}

	@Override
	public int getPatchLevel() {
		return PATCHLEVEL;
	}

	@Override
	public String getVendor() {
		return VENDOR;
	}

	@Override
	public String getProductName() {
		return PRODUCTNAME;
	}

	public static void main(String [] args) {
		Version v = new Version();
		System.err.println(v.toString());
		dexels.Version [] d = v.getIncludePackages();
		for (int i = 0; i < d.length; i++) {
			System.err.println("\t"+d[i].toString());
		}
	}
}
