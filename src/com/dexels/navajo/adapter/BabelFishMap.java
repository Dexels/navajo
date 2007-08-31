package com.dexels.navajo.adapter;

import java.util.HashMap;

import com.dexels.navajo.adapter.html.Browser;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class BabelFishMap implements Mappable {

	public String text;
	public String languageFrom = "en";
	public String languageTo = "nl";
	public String translation;
	
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}
	
	public void setText(String s) {
		this.text = s;
	}
	
	public void setLanguageFrom(String t) {
		this.languageFrom = t;
	}
	
	public void setLanguageTo(String t) {
		this.languageTo = t;
	}
	
	public String getTranslation() throws UserException {

		try {
			Browser b = new Browser("http://babelfish.altavista.com/", "babelfish.altavista.com");
			String result = b.openUrl("http://babelfish.altavista.com/", null, false);	
			HashMap parameters = b.getFormParameters();
			parameters.put("trtext", text);
			parameters.put("lp", languageFrom +"_"+languageTo);
			result = b.openUrl(b.getFormAction(), b.constructFormBody(b.getFormParameters()), true);
			Navajo n = b.getNavajo();
			return(n.getMessage("Form").getProperty("q").getValue());

		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}
	
	public static void main(String [] args) throws Exception {
		BabelFishMap bfm = new BabelFishMap();
		bfm.setText("Middle");
		System.err.println(bfm.getTranslation());
	}

}
