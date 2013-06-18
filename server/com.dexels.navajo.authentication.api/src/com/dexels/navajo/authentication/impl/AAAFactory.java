package com.dexels.navajo.authentication.impl;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.AAAInterface;
import com.dexels.navajo.authentication.api.AuthenticationFactory;

/**
 * <p>Title: <h3>SportLink Services</h3><br></p>
 * <p>Description: Web Services for the SportLink Project<br><br></p>
 * <p>Copyright: Copyright (c) 2002<br></p>
 * <p>Company: Dexels.com<br></p>
 * @author unascribed
 * @version $Id$
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
 * exported for now, TODO: Refactor everywhere to use a service reference
 * @author frank
 *
 */
public final class AAAFactory implements AuthenticationFactory {

  private final SortedSet<AAAInterface> moduleList;

  private final static Logger logger = LoggerFactory.getLogger(AAAFactory.class);
  private static AuthenticationFactory instance = null;
  
  public AAAFactory() {
	  moduleList = new TreeSet<AAAInterface>(new Comparator<AAAInterface>() {

		@Override
		public int compare(AAAInterface o1, AAAInterface o2) {
			if(o1.getPriority()< o2.getPriority()) {
				return -1;
			}
			if(o1.getPriority()> o2.getPriority()) {
				return 1;
			}
			return 0;
		}
	});
  }


  @Override
  public void addAuthenticationModule(AAAInterface a, Map<String,Object> settings) {
	  moduleList.add(a);
	  logger.info("# of auth. modules now: "+moduleList.size());
	  if(settings!=null) {
		  logger.info("Multitenant found: "+settings.get("instance"));
	  }
  }
  
  public void removeAuthenticationModule(AAAInterface a) {
	  moduleList.remove(a);
	  logger.info("# of auth. modules now: "+moduleList.size());
  }

  public void activate() {
	  logger.info("AAA Factory started.");
	  instance = this;
  }

  public void deactivate() {
	  logger.info("AAA Factory stopped.");
	  instance = null;
  }

  @Override
  public AAAInterface getAuthenticationModule() {
	  try {
	  return moduleList.first();
	  } catch (Exception  e) {
		  logger.warn("No AuthenticationModule found. No OSGi?", e);
		  return null;
	  }
  }

  public static AuthenticationFactory getInstance() {
	  if ( instance == null ) {
		  logger.warn("No AuthenticatonFactory found. No OSGi?");
		  instance = new AAAFactory();
	  } 
	  return instance;
  }
  
  
  // hmpf. ugly.
  public final static void resetUserCredential(String username) {
	  if ( getInstance().getAuthenticationModule() != null ) {
		  getInstance().getAuthenticationModule().resetUserCredential(username);
	  }
  }

  public final static void clearActionObjects() {
	  if ( getInstance().getAuthenticationModule() != null ) {
		  getInstance().getAuthenticationModule().clearActionObjects();
	  }
  }


}