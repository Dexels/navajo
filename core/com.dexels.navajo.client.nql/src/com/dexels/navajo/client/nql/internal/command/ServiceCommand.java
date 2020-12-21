/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.nql.internal.command;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.client.nql.OutputCallback;

public class ServiceCommand implements NQLCommand {

	private String service = null;
	@Override
	public void execute(NqlContextApi context,String tenant, String username, String password, OutputCallback callback) throws ClientException {
		context.call(service,tenant,username, password, false);
	}

	@Override
	public void parse(String raw) {
		String[] parts = raw.split(":");
		service = parts[1];
	}

}
