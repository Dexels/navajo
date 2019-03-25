package com.dexels.navajo.server.descriptionprovider;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class PropertyDescription implements Mappable {

	private int id;
	private String username;
	private String locale;
	private String subLocale;
	private String name;
	private String context;
	private String description;
	private boolean published;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getSubLocale() {
		return subLocale;
	}
	
	public void setSubLocale(String subLocale) {
		this.subLocale = subLocale;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	@Override
	public void kill() {
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
	}

	@Override
	public void store() throws MappableException, UserException {
	}
	
}
