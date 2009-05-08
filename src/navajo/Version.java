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
 * 
 * VERSION HISTORY
 * 
 * 8.1.3 - Fixed problem with carriage return under Windows with expression between <value> tags.
 *       - Added printwriter console to Access object for debugging purposes.
 *       - Added support for firing AccessLogEvent to notify of 1 or more written full access logs.
 *       - Log ServerTooBusyEvent, HealthCheckEvent, QueuableFailureEvent and TribeMemberDownEvent to auditlog table. 
 *  
 * 8.1.2 - Fixes for script inheritance, fixed 'order' problem with scripts using inheritance.
 *       - Exposing postmanURL via TribeMember class.
 *       
 * 8.1.1 - Fixes for script inheritance. Now supports 'inject' semantics within other tags.
 *       - Added support for AgentId. Can be used to monitor requests from specific agents (client applications).
 *       - Needsrecompile in Dispatcher is only done after it has been established that we are dealing
 *         with a cached service to avoid double checking.
 *       
 * 8.1.0 Added support for keeping track of Script Dependencies, i.e. dependencies on other scripts (includes, navajomap),
 *       Dependencies on Adapters, dependencies on specific adapter fields, i.e. server field in MailMap, query field
 *       in SQLMap. Created specific AdapterField depencency classes, like SQLFieldDependency.
 *       
 *       Fixed bug problem in Navascript.
 * 
 * 8.0.0 Added support for Multi Lingual scripts. Added Java support for scripts as first example.
 * 
 * 7.7.4 Release Candidate
 *       - Now using Service lookup mechanism for adapters as well. Also added utility to generate navascript XSD file.
 *       - Fixed support for multiple include script that contain validations.
 * 
 * 7.7.3 Beta release with 
 *       - support for type checking functions and using functions via Service lookup mechanism.
 *       - Refactored Jabber support to Navajo Listeners.
 * 
 * 7.7.2 Added support for blocking inherited messages/properties.
 * 
 * 7.7.1 Implemented extend/replace inheritance semantics.
 * 
 * 7.7.0 Added support for Script Inheritance via <inject> semantics.
 * 
 * 7.0.4 Minor Changes. Do not persist access objects anymore. Clone access and do not clone indoc and outdoc if
 *       not needed. This should reduce the burden on the GC.
 *       
 * 7.0.3 Removed NanoTslCompiler. Now using TslCompiler also for plug in.
 * 
 * 7.0.2 Delete "Temp Space" files when starting Dispatcher to clean up remains of files after crash/kill/stop of Dispatcher.
 * 
 * 7.0.1 Statistics Runner is now more GC friendly: Persists access object and restore objecstream instead of
         putting Access object in Map.

 * 7.0.0 Lot's of refactoring.
 * 	     Changed interface of Mappable, now load(Access) instead of redundant load(Parameter, Navajo, Access, NavajoConfig)
 *       NavajoConfigInterface/TestNavajoConfig and DispatcherInterface/TestDispatcher/DispatcherFactory created for better testability.
 *       Moved lot's of functionality away from NavajoConfig initialization to prevent weird Dispatcher loops.
 *       Created testsrc structure and started working on unit tests, especially focusing on threading issues.
 *       Fixed problem with NavajoEventTrigger being evaluated each Tribal Member in case of workflow initialization.
 *       Added NavajoExceptionEvent 
 *       Publish AuditLog Event in case of Authentication/Authorization exception. 
 *       
 * 6.5.6 Some restructuring of Dispatcher/NavajoConfig. Better deadlock prevention for waiting for the chief in TribeManager.
 * 
 * 6.5.4-5 Added support for enabling/disabling and shutting down Navajo Server. Added support for forwarding
 *       Async Request to proper Tribal Member. 
 * 
 * 6.5.3 Fixed proper initialization of Navajo instance under heavy load. 
 * 		 Minor refactorings and additional code comments added.
 * 
 * 6.5.2 Improved versioning information
 * 
 * 6.5.1 Added support for <defines> section in scripts.
 * 		 Added support for groupInstance specific scripts (using _[groupinstance name] postfix in script name).
 * 
 * 6.0.0 Strict separation of standard (free) and enterprise edition functionality
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
 *       
 * 5.1.6 Compiler fixes. The navajo plugin had trouble loading the javacompiler class, because of different
 *       class loaders. Also removed debug data.
 *       
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
 * 5.4.2 Field tags can now address parent maps
 * 
 * 5.4.3 Bug fix in the compiler
 * 
 * 5.4.2 Some performance improvements in ASTTmlNode.
 * 
 * 5.4.3 Document class can be defined in server.xml
 * 
 * 5.4.4 Closing inputstreams when creating new Navajo object (for SAXP).
 * 
 * 5.4.5 Added AdminServlet again. Comparing binaries correctly.
 * 
 * 5.4.5 Support for long in TslCompiler/NanoTslCompiler.
 * 
 * 5.4.6 Some minor changes in Worker.
 * 
 * 5.4.7 Added support for logging AsyncMappable object access when finished or killed
 * 
 * 5.4.8 Added support in JAXP TslCompiler for CDATA section under <expression> tag
 * 
 * 5.4.9 Fixed match request functionality for Locks and improved lock error messages.
 * 
 * 5.5.0 Added clienttokens.
 * 
 * 5.5.1 Better memory usage for adapter jars
 * 
 * 5.5.2 Support for mappable adapter statistics
 * 
 * 5.5.3 Support for orderby construct in array messages.
 * 
 * 5.6.0 Restructured Navajo Classloader.
 * 
 * 5.6.1 Removed some possibly harmful synchronized waiting blocks
 * 
 * 5.6.2 Added support for <value> tag
 * 
 * 5.6.3 Added JMX support
 * 
 */

public class Version extends dexels.Version {

	public static final int MAJOR = 8;
	public static final int MINOR = 1;
	public static final int PATCHLEVEL = 2;
	public static final String SPECIAL_VERSION = "Patchlevel 3 Candidate";
	
	public static final String VENDOR = "Dexels";
	public static final String PRODUCTNAME = "Navajo Kernel";
	public static final String RELEASEDATE = "2009-05-05";
	
//	 Included packages.
	String [] includes = {"navajodocument.Version"};
	
	public Version() {
		addIncludes(includes);
		setReleaseDate(RELEASEDATE);
		setSpecialVersion(SPECIAL_VERSION);
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
	
	public static String getDescription() {
		return MAJOR + "." + MINOR + "." + PATCHLEVEL;
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
