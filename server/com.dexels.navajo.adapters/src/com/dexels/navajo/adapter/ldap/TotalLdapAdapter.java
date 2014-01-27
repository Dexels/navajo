package com.dexels.navajo.adapter.ldap;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TotalLdapAdapter implements Mappable {

	private static final String DEFAULT_LDAP_SERVER = "ldap://iris.dexels.nl:389/";
	private static final String DEFAULT_LDAP_USERNAME = "uid=root,dc=dexels,dc=com";
	private static final String DEFAULT_LDAP_PASSWORD = "secret";

	public String server = DEFAULT_LDAP_SERVER;
	public String username = DEFAULT_LDAP_USERNAME;
	public String password = DEFAULT_LDAP_PASSWORD;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TotalLdapAdapter.class);
	
	private InitialDirContext initialDir = null;

	
	@Override
	public void kill() {

	}

	@Override
	public void load(Access access) throws MappableException, UserException {
	}

	@Override
	public void store() throws MappableException, UserException {

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
			NamingEnumeration<NameClassPair> e = dc.list("");
			while (e.hasMore()) {
				NameClassPair o = e.next();
				logger.info("NAME: "+o.getName());
				logger.info("NAMESp: "+o.getNameInNamespace());
//				o.getSchema("");
				logger.info("o: " + o);
			}
		} catch (NamingException e) {
			logger.error("LDAP problem: ", e);
		}
		
		return null;
		
		}

	public static void main(String[] args) {

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
