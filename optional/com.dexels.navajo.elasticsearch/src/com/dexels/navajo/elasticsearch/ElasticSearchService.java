/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.elasticsearch;

import java.io.IOException;

import com.dexels.navajo.document.Message;

public interface ElasticSearchService {
	public void insert(Message m) throws IOException;
	public void insertJson(String jsonString) throws IOException;
}
