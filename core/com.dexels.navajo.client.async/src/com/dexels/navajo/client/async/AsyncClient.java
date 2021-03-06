/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.async;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.document.Navajo;


public interface AsyncClient {

    public void callService(Navajo input, String service, NavajoResponseHandler continuation) throws IOException;

    public Navajo callService(Navajo input, String service) throws IOException;

    public String getName();

	public void setName(String name);

	public boolean useHttps();

	public void setHttps(boolean useHttps);

    /**
     * set the SSL socket factory to use whenever an HTTPS call is made.
     *
     * @param algorithm  The algorithm to use, for example: SunX509
     * @param type       Type of the keystore, for example PKCS12 or JKS
     * @param source     InputStream of the client certificate, supply null to reset the socketfactory to default
     * @param password   The keystore password
     */
    public void setClientCertificate(String algorithm, String type, InputStream is, char[] password) throws IOException;

}
