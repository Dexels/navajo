package com.dexels.navajo.version;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

public abstract class BaseVersion {

	protected static final Logger logger = LoggerFactory.getLogger("com.dexels.navajo.version");

	private ArrayList<AbstractVersion> includedPackages = new ArrayList<>();
	
	
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
	public boolean checkInclude(String versionClass) {
		
		
		for (int i = 0; i < includedPackages.size(); i++) {
			AbstractVersion v = includedPackages.get(i);
			if (v.getClass().getName().equals(versionClass)) {
				return true;
			}
		}
		return false;
		
	}
	
	private void addInclude(String versionClass) {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends AbstractVersion> c = (Class<? extends AbstractVersion>) Class.forName(versionClass);
			AbstractVersion v =  c.getDeclaredConstructor().newInstance();
			// Check if v is not already included in chain.
			if (!checkInclude(versionClass)) {
				includedPackages.add(v);
			}
		} catch (Exception e) {
			logger.warn("Could not find version class for: " + versionClass,e);
		}
	}
	


	
	protected void buildIncludeTree(Set<AbstractVersion> t) {
		for (int i = 0; i < includedPackages.size(); i++) {
			AbstractVersion child = includedPackages.get(i);
			if (!t.contains(child)) {
				t.add(child);
			}
		}
		// Loop over children.
		for (int i = 0; i < includedPackages.size(); i++) {
			AbstractVersion child = includedPackages.get(i);
			child.buildIncludeTree(t);
		}
	}
	
	/**
	 * Get included packages. Include children includes.
	 * @return
	 */
	public AbstractVersion [] getIncludePackages() {
		
		Set<AbstractVersion> allDeps = new TreeSet<>();
		buildIncludeTree(allDeps);
		AbstractVersion [] v = new AbstractVersion[allDeps.size()];
		v = allDeps.toArray(v);
		return v;
	}

	
}
