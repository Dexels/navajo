package dexels;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

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

public abstract class Version {

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
		
		HashSet visited = new HashSet();
		if ( previouslyVisited != null ) {
			visited.addAll(previouslyVisited);
		}
		
		for (int i = 0; i < includedPackages.size(); i++) {
			Version v = (Version) includedPackages.get(i);
			if (v.getClass().getName().equals(versionClass) || 
					(!visited.contains(v.getClass().getName()) && v.checkInclude(versionClass, visited))) {
				return true;
			}
			visited.add(v.getClass().getName());
		}
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
		    // Include includes.
			Version [] children = v.getIncludePackages(); 
			//System.err.println("Checking " + children.length + " children");
			for (int i = 0; i < children.length; i++) {
				//System.err.println("Check if " + children[i].getClass().getName() + " needs inclusion");
				if ( !checkInclude(children[i].getClass().getName(), null)) {
					this.addInclude(children[i].getClass().getName());
				}
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
	
	private ArrayList setIncludeList() {
		ArrayList allIncludes = new ArrayList();
		allIncludes.addAll(includedPackages);
		for (int i = 0; i < allIncludes.size(); i++) {
			Version v = (Version) allIncludes.get(i);
			allIncludes.addAll(v.setIncludeList());
		}
		return allIncludes;
	}
	
	/**
	 * Get included packages. Include children includes.
	 * @return
	 */
	public Version [] getIncludePackages() {
		ArrayList all = setIncludeList();
		Version [] v = new Version[all.size()];
		v = (Version []) all.toArray(v);
		return v;
	}
	
}
