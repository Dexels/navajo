/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.elasticsearch.adapters;

import java.io.IOException;

import com.dexels.navajo.elasticsearch.ElasticSearchQueryFactory;
import com.dexels.navajo.elasticsearch.impl.ElasticSearchResult;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;

public class ElasticSearchQueryAdapter implements Mappable {

	private String keyword = null;

	public ElasticSearchResult[] result; //I will receive the arraylist from search() method and I will turn it into an array.

	@Override
	public void load(Access access) {}

	@Override
	public void store() {}

	@Override
	public void kill() {}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ElasticSearchResult[] getEsrarray() throws MappableException {

	    try {
			result = ElasticSearchQueryFactory.getInstance().search(keyword);
			for (ElasticSearchResult element : result) {
				System.out.println("id: " + element.id);
				System.out.println("score: " + element.score);
				System.out.println("filename: " + element.fileName);
			}
		} catch (IOException e) {
			throw new MappableException("error " + e);
		}

		return result;
	}

}
