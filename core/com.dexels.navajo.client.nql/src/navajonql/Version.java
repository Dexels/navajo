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
package navajonql;

import org.osgi.framework.BundleContext;

/**
 * VERSION HISTORY
 * 
 * 3.0.0  -Added support for Flexible Load Balancing.
 *        - Async Runner is server agnostic. It does not need to send the request
 *        - to the server that actually runs the async thread. This is handled by
 *        - the Dispatcher (TAG: NavajoRelease6_5_5)
 * 2.0.3. -Added more verbose timing information
 * 2.1.0. Added support for setting unique request id.
 * 2.2.0. -Support for text nodes within binary properties
 *        -Support for f#%ckin huge binaries on java 1.5
 *        -HTML client now works with any document version
 *        -HTML client does not waste as much memory as it used to
 *        -The streaming binaries only work well on 1.5, under 1.4
 *         the HTTP implentation will store all data in memory before
 *         sending.
 * 2.2.1. Added debug data.
 * 2.2.2. Closing input stream when creatin Navajo (for SAXP).
 * 2.2.3. Added support for piggybacking of data.
 * 2.2.4. Removed evil keep-alive
 */
public class Version extends com.dexels.navajo.version.AbstractVersion  {

	
	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
	}
	
}