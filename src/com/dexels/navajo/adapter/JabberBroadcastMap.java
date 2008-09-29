package com.dexels.navajo.adapter;

import java.io.*;

import org.jivesoftware.smack.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.jabber.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public class JabberBroadcastMap implements Mappable {

	public boolean useIn = false;

	public boolean alert = false;

	public String status = null;
	public String nickName = null;
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Access access) throws MappableException, UserException {
		try {
			StringWriter sw = new StringWriter();
			Navajo n = null;
			
			if(useIn) {
				n = access.getInDoc();
			} else {
				n = access.getOutputDoc();
			}
			n.write(sw);
			JabberWorker.getInstance().broadcastIntoRoom(sw.toString());
			
		} catch(XMPPException e) {
			e.printStackTrace();
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public boolean isUseIn() {
		return useIn;
	}

	public void setUseIn(boolean useIn) {
		this.useIn = useIn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		JabberWorker.getInstance().setPresenceStatus(status);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
		JabberWorker.getInstance().setPresenceStatus(nickName);
 
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
		JabberWorker.getInstance().sendAlert();
	}

}
