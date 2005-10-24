package dexels;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

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

public abstract class Version implements Comparable {

	public String RELEASEDATE;
	
	public abstract int getMajor();
	public abstract int getMinor();
	public abstract int getPatchLevel();
	public abstract String getVendor();
	
	// List of versions of included packages.
	public ArrayList includedPackages = new ArrayList();
	
	private String specialVersion = null;
	
	public void addIncludes(String [] versionClasses) {
		for (int i = 0; i < versionClasses.length; i++) {
			addInclude(versionClasses[i]);
		}
	}
	
	/**
	 * Check include package in this Version class or any of its included Versions.
	 * Contains circular dependency check.
	 * 
	 * @param versionClass
	 * @return
	 */
	public boolean checkInclude(String versionClass, HashSet previouslyVisited) {
		
		
		for (int i = 0; i < includedPackages.size(); i++) {
			Version v = (Version) includedPackages.get(i);
//			System.err.print(this.getClass().getName() +
//					": Checking if " + versionClass + " is already included...");
			if (v.getClass().getName().equals(versionClass)) {
				//System.err.println("...yes it is!");
				return true;
			}
		}
		//System.err.println("...no it isn't!");
		return false;
		
	}
	
	public void addInclude(String versionClass) {
		try {
			Class c = Class.forName(versionClass);
			Version v = (Version) c.newInstance();
			// Check if v is not already included in chain.
			if (!checkInclude(versionClass, null)) {
				//System.err.println(this.getClass().getName() + ": Adding " + versionClass);
				includedPackages.add(v);
			}
		} catch (Exception e) {
			System.err.println("Could not find version class for: " + versionClass);
		}
	}
	
	public void setReleaseDate(String s) {
		this.RELEASEDATE = s;
	}
	
	public void setSpecialVersion(String v) {
		specialVersion = v;
	}
	
	public Date getReleaseDate() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(RELEASEDATE);
		} catch (Exception e) {
			return null;
		}
	}
	
	public abstract String getProductName();
	
	public String getSpecialVersion() {
		return this.specialVersion;
	}
	
	public String toString() {
		return getProductName() + (specialVersion != null ? " (" + specialVersion + ") " : "") + " " + getMajor() + "." + getMinor() + "." + getPatchLevel() + "/" + getVendor() + " (" + getReleaseDate() + ")";
	}
	
	public String getVersion() {
		return getMajor() + "." + getMinor() + "." + getPatchLevel();
	}
	
	private void buildIncludeTree(TreeSet t) {
		for (int i = 0; i < includedPackages.size(); i++) {
			Version child = (Version) includedPackages.get(i);
			if (!t.contains(child)) {
				t.add(child);
			}
			child.buildIncludeTree(t);
		}
	}
	
	/**
	 * Get included packages. Include children includes.
	 * @return
	 */
	public Version [] getIncludePackages() {
		
		TreeSet allDeps = new TreeSet();
		buildIncludeTree(allDeps);
		Version [] v = new Version[allDeps.size()];
		v = (Version []) allDeps.toArray(v);
		return v;
	}
	
	public boolean equals(Object o) {
		System.err.println("Checking equals..");
		return o.getClass().getName().equals(this.getClass().getName());
	}
	
	public int compareTo(Object o) {
		if ( o.getClass().getName().equals(this.getClass().getName()) )
			return 0;
		else
			return -1;
	}
	
}
