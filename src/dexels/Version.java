package dexels;
import java.text.SimpleDateFormat;
import java.util.*;

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

public abstract class Version implements Comparable<Version> {

	public String RELEASEDATE;
	
	/**
	 * Major release increases when actually new funcionality is introduced.
	 * @return
	 */
	public abstract int getMajor();
	/*
	 * Minor release denotes small functional changes.
	 * Odd release numbers denote production candidates (unstable).
	 * Even release numbers denote a production release (stable).
	 */
	public abstract int getMinor();
	/**
	 * Patchlevel denotes increase with each bug fix on major/minor release.
	 * @return
	 */
	public abstract int getPatchLevel();
	public abstract String getVendor();
	
	// List of versions of included packages.
	public ArrayList<Version> includedPackages = new ArrayList<Version>();
	
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
	public boolean checkInclude(String versionClass, Set<Version> previouslyVisited) {
		
		
		for (int i = 0; i < includedPackages.size(); i++) {
			Version v = includedPackages.get(i);
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
			Class<?> c = Class.forName(versionClass);
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
	
	public String versionString() {
		StringBuffer s = new StringBuffer();
		s.append(toString()+"\n");
		dexels.Version [] d = getIncludePackages();
		for (int i = 0; i < d.length; i++) {
			s.append("\t"+d[i].toString()+"\n");
		}
		return s.toString();
	}
	
	private void buildIncludeTree(Set<Version> t) {
		//System.err.println(this.getClass().getName() + ": in buildIncludeTree: " + t.size());
		for (int i = 0; i < includedPackages.size(); i++) {
			Version child = includedPackages.get(i);
			if (!t.contains(child)) {
				//System.err.println("Adding " + child.getClass().getName());
				t.add(child);
			}
		}
		// Loop over children.
		for (int i = 0; i < includedPackages.size(); i++) {
			Version child = includedPackages.get(i);
			child.buildIncludeTree(t);
		}
	}
	
	/**
	 * Get included packages. Include children includes.
	 * @return
	 */
	public Version [] getIncludePackages() {
		
		Set<Version> allDeps = new TreeSet<Version>();
		buildIncludeTree(allDeps);
		Version [] v = new Version[allDeps.size()];
		v = allDeps.toArray(v);
		return v;
	}
	
	public boolean equals(Object o) {
		return o.getClass().getName().equals(this.getClass().getName());
	}
	
	public int compareTo(Version o) {
	    return o.getClass().getName().compareTo(this.getClass().getName());
	}
	
}
