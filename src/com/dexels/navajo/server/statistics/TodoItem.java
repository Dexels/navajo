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
package com.dexels.navajo.server.statistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;

public final class TodoItem {
	
	public TodoItem(final Access a, final AsyncMappable am) throws IOException {
		asyncobject = am;
		File f = DispatcherFactory.getInstance().createTempFile(a.accessID, "log-todoitem");
		persistedFileName = f.getAbsolutePath();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(a);
		oos.close();
	}
	
	public Access getAccessObject()  {
		ObjectInputStream ois = null;
		try {
			File f = new File(persistedFileName);
			ois = new ObjectInputStream(new FileInputStream(f));
			Access a = (Access) ois.readObject();
			return a;
		} catch (Exception e) {
			return null;
		} finally {
			if ( ois != null ) {
				try {
					ois.close();
				} catch (IOException e) {}
			}
		}
	}
	
	public void finalize() {
		if ( persistedFileName != null ) {
			new File(persistedFileName).delete();
		}
	}
	
	private final String persistedFileName;
	public final AsyncMappable asyncobject;
}
