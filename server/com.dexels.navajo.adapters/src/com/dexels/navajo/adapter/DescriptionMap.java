/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @@author 
 * @@version $Id: DescriptionMap.java,v 1.1 2006/12/08 11:21:26 frank Exp $.
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
package com.dexels.navajo.adapter;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;

public class DescriptionMap implements Mappable {
	
	public boolean doFlushCache = false;
	public int cacheSize = 0;

	public Binary content = null;
	public String stringContent = null;
	public boolean debug = false;
	private NavajoConfigInterface myConfig = null;
	
	public void load(Access a) throws MappableException, UserException {
		myConfig = DispatcherFactory.getInstance().getNavajoConfig();
	}
	
	public void store() throws MappableException, UserException {
	}

	public void kill() {
	}

	public int getCacheSize() {
		if (myConfig.getDescriptionProvider()!=null) {
			return myConfig.getDescriptionProvider().getCacheSize();
		}
		return 0;
	}


	/**
	 * @param doFlushCache param required in TSL 
	 */
	public void setDoFlushCache(boolean doFlushCache) {
		if (myConfig.getDescriptionProvider()!=null) {
			myConfig.getDescriptionProvider().flushCache();
		}
	}

}