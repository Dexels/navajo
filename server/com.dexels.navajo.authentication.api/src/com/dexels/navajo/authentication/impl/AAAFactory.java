package com.dexels.navajo.authentication.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.AAAInterface;
import com.dexels.navajo.authentication.api.AAAFactoryInterface;

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
 * 
 * @author frank
 *
 */
public final class AAAFactory implements AAAFactoryInterface {
    private final static Logger             logger     = LoggerFactory.getLogger(AAAFactory.class);
    private static AAAFactoryInterface    instance   = null;

    private final Set<AAAInterface>   moduleList = new TreeSet<>();
    private final Map<String, AAAInterface> moduleMap  = new HashMap<String, AAAInterface>();

    public void activate() {
        logger.debug("AAA Factory started.");
        instance = this;
    }

    public void deactivate() {
        logger.debug("AAA Factory stopped.");
        instance = null;
    }

    @Override
    public void addAuthenticationModule(AAAInterface a, Map<String, Object> settings) {
        moduleList.add(a);

        if (settings != null) {
            String instance = (String) settings.get("instance");
            if (instance != null) {
                moduleMap.put(instance, a);
            } else {
                logger.warn("Possible problem: AAAInterface found, probably in multitenant mode, but no instance associated: " + a.getClass());
            }
        }
    }

    public void removeAuthenticationModule(AAAInterface a, Map<String, Object> settings) {
        moduleList.remove(a);
        if (settings != null) {
            String instance = (String) settings.get("instance");
            if (instance != null) {
                moduleMap.remove(instance);
            } else {
                logger.warn("Possible problem: Removing AAAInterface, probably in multitenant mode, but no instance associated.");
            }
        }
    }

    @Override
    public AAAInterface getAuthenticationModule(String instance) {
        try {
            return moduleMap.get(instance);
        } catch (Exception e) {
            logger.warn("No AuthenticationModule found. No OSGi?", e);
            return null;
        }
    }

    public static AAAFactoryInterface getInstance() {
        if (instance == null) {
            logger.warn("No AuthenticatonFactory found. No OSGi?");
            instance = new AAAFactory();
        }
        return instance;
    }

}
