package com.dexels.navajo.adapter.ldap;

import java.util.*;

import javax.naming.*;
import javax.naming.directory.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public class TotalLdapAdapter implements Mappable {

	private static final String DEFAULT_LDAP_SERVER = "ldap://iris.dexels.nl:389/";
	private static final String DEFAULT_LDAP_USERNAME = "uid=root,dc=dexels,dc=com";
	private static final String DEFAULT_LDAP_PASSWORD = "secret";

	public String server = DEFAULT_LDAP_SERVER;
	public String username = DEFAULT_LDAP_USERNAME;
	public String password = DEFAULT_LDAP_PASSWORD;

	private InitialDirContext initialDir = null;

	
	private Navajo out = null;
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
		if(access!=null) {
			out = access.getOutputDoc();
		}
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}


	// public static void main(String[] args) throws SystemException,
	// AuthorizationException {
	// LdapRepository lr = new LdapRepository();
	// lr.authorizeUser("root", "secret", null, null, null);
	// }

	public void startup() throws NamingException {
		Properties env = new Properties();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, server);
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);
		initialDir = new InitialDirContext(env);

	}

	public Message list(String base) {
//"ou=users,dc=dexels,dc=com"
		try {
			if (initialDir == null) {
				startup();
			}
			DirContext dc = (DirContext) initialDir.lookup(base);
			NamingEnumeration e = dc.list("");
			while (e.hasMore()) {
				NameClassPair o = (NameClassPair) e.next();
				System.err.println("NAME: "+o.getName());
				System.err.println("NAMESp: "+o.getNameInNamespace());
//				o.getSchema("");
				System.err.println("o: " + o);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return null;
		
		}

	public static void main(String[] args) throws NamingException {

		TotalLdapAdapter tl= new TotalLdapAdapter();
		tl.list("ou=users,dc=dexels,dc=com");
		
		// DirContext users = (DirContext) a.lookup("ou=users");
		// BasicAttributes mandatory = new BasicAttributes("cn", "BillyBob");
		// BasicAttribute objectclass;
		//                
		// objectclass = new BasicAttribute("objectclass", "top");
		// mandatory.put(objectclass);
		//
		// objectclass = new BasicAttribute("objectclass", "person");
		// mandatory.put(objectclass);
		//
		// BasicAttribute surname = new BasicAttribute("surname", "Roberts");
		//
		// mandatory.put(surname);
		//
		// users.createSubcontext("cn=BillyBob", mandatory);
		// a.createSubcontext("cn=Emelius,dc=dexels,dc=com,objectClass=account,objectClass=top");
		// a.bind("uid=Emelius", "Bartholdy");
	}
}
