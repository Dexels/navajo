package com.dexels.navajo.elasticsearch.adapters;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.dexels.navajo.elasticsearch.ElasticSearchQueryFactory;
import com.dexels.navajo.elasticsearch.FscrawlerFactory;
import com.dexels.navajo.elasticsearch.impl.ElasticSearchResult;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ElasticSearchQueryAdapter implements Mappable {
	
	private String keyword = null;
	public ElasticSearchResult[] result; //I will receive the arraylist from search() method and I will turn it into an array.

	@Override
	public void load(Access access){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store()  {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
	
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
			// TODO Auto-generated catch block
			throw new MappableException("error " + e);
		}
		return result;
	}

}
