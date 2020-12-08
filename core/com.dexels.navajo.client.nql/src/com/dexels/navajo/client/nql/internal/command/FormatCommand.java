/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.nql.internal.command;

import java.io.IOException;

import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.client.nql.OutputCallback;

public class FormatCommand implements NQLCommand {

	private String format = null;
	@Override
	public void execute(NqlContextApi context,String tenant, String username, String password, OutputCallback callback) throws IOException {
		context.format(format,callback);
	}

	@Override
	public void parse(String raw) {
		String[] parts = raw.split(":");
		format = parts[1];
	}

}
