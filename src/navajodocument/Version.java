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

import org.osgi.framework.BundleContext;

import com.dexels.navajo.document.NavajoFactory;

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
 * 
 * 9.0.2 Fixed problem with not setting mimetype in Binary when using reader.
 * 
 * 9.0.3 Fixed problem with getting not existing array message child by index in SAXP/NANO.
 * 
 * 9.0.4 Fixed problem with async webservice callback.
 * 
 * 9.1.0 Support for orderby construct in array messages. Message implements Comparable now.
 * 
 * 9.1.1 Order by of messages now support java comparator functions
 * 
 * 9.2.0 Changed Navajo merged semantics.
 * 
 */
public class Version extends dexels.Version {

	@Override
	public void shutdown() {
		super.shutdown();
		NavajoFactory.terminate();
	}

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		super.stop(bc);
	}

	


}
