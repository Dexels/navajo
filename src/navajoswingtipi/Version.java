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
 * 4.2.1   Added updateProperty method to TipiProperty
 */
package navajoswingtipi;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

import tipi.MainApplication;
import tipi.SwingTipiApplicationInstance;
import tipi.TipiSwingExtension;

public class Version extends dexels.Version {

	
	//	 Included packages.
	
	public Version() {
//		addIncludes(includes);
	}
	
	@SuppressWarnings("restriction")
	public void start(BundleContext bc) throws Exception {
		System.err.println("Starting swing tipi");
		super.start(bc);
		
		bc.addFrameworkListener(new FrameworkListener(){

			private SwingTipiApplicationInstance instance;

			@Override
			public void frameworkEvent(FrameworkEvent event) {
				if(FrameworkEvent.STARTED == event.getType()) {
					try {
						TipiSwingExtension tse = new TipiSwingExtension();
						ClassLoader classLoader = tse.getClass().getClassLoader();
						System.err.println("Loader: "+classLoader.hashCode());
						tse.setExtensionClassloader(classLoader);
						System.err.println("goooo!");
						instance = MainApplication.runApp(new String[]{"tipiCodeBase=/Users/frank/Documents/workspace-osgi-temp/TipiExample/tipi/"});
						System.err.println("INstance ready!");
						instance.getCurrentContext().processRequiredIncludes(tse);
						System.err.println("Includes ready!");
					} catch (XMLParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							try {
								instance.getCurrentContext().switchToDefinition(instance.getDefinition());
							} catch (TipiException e) {
								e.printStackTrace();
							}
							System.err.println("Creating test frame");
							JFrame mm = new JFrame("bbbbbb");
							mm.setBounds(100, 100, 200, 200);
							mm.setVisible(true);
							System.err.println("Created test frame");
						}
					});
				}
				System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+event);
			}});
//
//		
	}


	public static void main(String [] args) {
		JFrame mm = new JFrame("aaaa");
		System.err.println("MM>>>>>M: "+mm);
		mm.setBounds(100, 100, 200, 200);
		mm.setVisible(true);
	}
}
