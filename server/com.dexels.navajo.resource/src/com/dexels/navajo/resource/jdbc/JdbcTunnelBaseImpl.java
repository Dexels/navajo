package com.dexels.navajo.resource.jdbc;

import java.util.Map;

import com.dexels.navajo.tunnel.Tunnel;

public abstract class JdbcTunnelBaseImpl {
	public abstract void activate(Map<String,Object> settings);
	private Tunnel tunnel = null;
	
	public void deactivate() {
		
	}
	
	public void setTunnel(Tunnel t) {
		tunnel = t;
	}

	protected Tunnel getTunnel() {
		return tunnel;
	}
	
	public void clearTunnel(Tunnel t) {
		tunnel = null;
	}

}
