/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.nql;

import java.io.IOException;
import java.util.List;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.ClientContext;

public interface NqlContextApi {

	public List<NQLCommand> parseCommand(String nql);

	public void executeCommand(String nql, String tenant, String username, String password, OutputCallback ob) throws IOException;

	public void setNavajoContext(ClientContext nrc);

	public ClientContext getNavajoContext();

    void call(String service, String tenant, String username, String password, boolean force) throws ClientException;

    public void format(String format, OutputCallback callback) throws IOException;

    public void output(String path);

    public void set(String path, String value);

}