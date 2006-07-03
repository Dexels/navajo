package navajo;
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

/**
 * VERSION HISTORY
 * 
 * 5.1.2 Change in TslCompiler and NanoTslCompiler: when calling getXYZ() method that returns a mappable Array (Mappable []),
 * assign value to variable and in subsequent calls use this variable instead of calling getXYZ() again. This will fix
 * a common problem in calling InitNavajoStatus which can return -1 in case of frequent User count changes. Furthermore, this
 * new implementation makes more sense and does not require a getXYZ() method that is robust for subsequent calls (e.g.
 * getResultSet() in SQLMap).
 * 
 * 5.1.3 The dispatcher will add 'serverTime' attribute to the header, indicating how much time the server needed to process
 * this transaction
 * 
 * 5.1.4 Removed access set size counter. Totaltime of access object now includes parsetime. 
 * 
 * 5.1.5 Minor fix for global messages. Directclient uses system props. Listeners use application.properties.
 *       direct client works now for both types. 
 * 5.1.6 Compiler fixes. The navajo plugin had trouble loading the javacompiler class, because of different
 *       class loaders. Also removed debug data.
 * 5.1.7 Added some classloader functionality. Now the script classes can get reloaded separately from the jarfiles. Should help performance of the plugin remote runner.
 * 
 * 5.2.0 Added Task Scheduler functionality.
 * 
 * 5.2.1 Change in CompiledScript. Now using outDoc directly from Access object ( getOutDoc() ), 
 *       instead of using local field.
 *      
 * 5.2.2 Task Scheduler extended: multiple weekdays, error logging.
 * 
 * 5.3.0 Added Integrity Worker for allowing multiple retries to already succeeded webservice calls.
 * 
 * 5.4.0 Added Locking Manager for setting maximum instance count of webservices.
 * 
 * 5.4.1 Dispatcher is now true singleton. Implemented kill() methods, should be robuust to servlet engine
 *       destroys now.
 * 
 * 5.4.2 Some performance improvements in ASTTmlNode.
 * 
 * 5.4.3 Document class can be defined in server.xml
 * 
 * 5.4.4 Closing inputstreams when creating new Navajo object (for SAXP).
 * 
 * 5.4.5 Added AdminServlet again. Comparing binaries correctly.
 */
public class Version extends dexels.Version {

	public static final int MAJOR = 5;
	public static final int MINOR = 4;
	public static final int PATCHLEVEL = 5;
	public static final String VENDOR = "Dexels";
	public static final String PRODUCTNAME = "Navajo Kernel";
	public static final String RELEASEDATE = "2006-07-03";
	
//	 Included packages.
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
