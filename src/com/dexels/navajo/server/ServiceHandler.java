package com.dexels.navajo.server;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
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
import com.dexels.navajo.document.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.persistence.*;
import java.util.HashMap;


public abstract class ServiceHandler implements Constructor {

    protected Navajo requestDocument;
    protected Parameters parms;
    protected NavajoConfig properties;
    protected Access access;

    /**
     *
     * @param doc
     * @param access
     * @param parms
     * @param properties
     */
    public final void setInput(Navajo doc, Access access, Parameters parms, NavajoConfig properties) {
        this.requestDocument = doc;
        this.parms = parms;
        this.properties = properties;
        this.access = access;
    }

    /**
     * @return
     * @throws NavajoException
     * @throws UserException
     * @throws SystemException
     */
    public abstract Navajo doService() throws NavajoException, UserException, SystemException, AuthorizationException;

    /**
     *
     * @return
     * @throws Exception
     */
    public final Persistable construct() throws Exception {
        return doService();
    }

}
