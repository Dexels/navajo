package com.dexels.navajo.adapter.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class BaseLdapAdapter implements Mappable {

	private static final String DEFAULT_LDAP_SERVER = "ldap://iris.dexels.nl:389/";
	private static final String DEFAULT_LDAP_USERNAME = "uid=root,dc=dexels,dc=com";
	private static final String DEFAULT_LDAP_PASSWORD = "secret";

	public String server = DEFAULT_LDAP_SERVER;
	public String username = DEFAULT_LDAP_USERNAME;
	public String password = DEFAULT_LDAP_PASSWORD;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseLdapAdapter.class);

	private InitialDirContext initialDir = null;

	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

	@SuppressWarnings("unused")
	private void checkLdap(String ldapUrl, String principal, String username, String password) throws NamingException {
		Hashtable<String,String> env = new Hashtable<String,String>();

		String usr = "uid=" + username + ",";
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, usr + principal);
		env.put(Context.SECURITY_CREDENTIALS, password);
		// Create the initial context
		initialDir = new InitialDirContext(env);
		Integer i = new Integer(28420);

		logger.debug("Adding " + i + " to directory...");

		initialDir.bind(principal + ",cn=myRandomInt", i);
		// ctx.bind(arg0, i)
		i = new Integer(98765);
		logger.debug("i is now: " + i);

		i = (Integer) initialDir.lookup("cn=myRandomInt");
		logger.debug("Retrieved i from directory with value: " + i);
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

	public BaseLdapAdapter[] list(String base) {
		// "ou=users,dc=dexels,dc=com"
		try {
			if (initialDir == null) {
				startup();
			}
			DirContext dc = (DirContext) initialDir.lookup(base);
			NamingEnumeration e = dc.list("");
			while (e.hasMore()) {
				Object o = e.next();
				logger.info("o: " + o);
			}
		} catch (NamingException e) {
			logger.error("LDAP problem: ", e);
		}

		return null;

	}

	public static void main(String[] args) throws NamingException, ClientException, NavajoException {

		ClientInterface cc = NavajoClientFactory.createDefaultClient();
		cc.setServerUrl("hera1.dexels.com/sportlink/knvb/servlet/Postman");
		cc.setUsername("");
		cc.setPassword("");

		final Navajo init = NavajoClientFactory.getClient().doSimpleSend("club/InitUpdateClub");
		init.getProperty("Club/ClubIdentifier").setValue("BBFW63X");
		init.getProperty("Club/LastName").setValue("");

		// init.write(System.err);
		final Navajo process = NavajoClientFactory.getClient().doSimpleSend(init, "member/ProcessQueryAllMembersPerClub");

		
		
		Message memberdata = process.getMessage("MembersPerClub");
		ArrayList al = memberdata.getAllMessages();
		BaseLdapAdapter bla = new BaseLdapAdapter();
		DirContext context = bla.getContext("ou=clubs,dc=dexels,dc=com");
		Map constants = new HashMap();

		
		
		
		
//	    BasicAttributes myAttrs = new BasicAttributes(true);  //Basic Attributes
//	    Attribute objectClass = new BasicAttribute("objectclass"); //Adding Object Classes
//	    
//	    objectClass.add("inetOrgPerson");
//	    /*objectClass.add("organizationalPerson");
//	    objectClass.add("person");
//	    objectClass.add("top");*/
//	 
//	    Attribute ouSet = new BasicAttribute("ou");
//	    ouSet.add("people");
//	    ouSet.add("aap");
//	    
//	    myAttrs.put(objectClass);
//	    myAttrs.put(ouSet);
//	    myAttrs.put("cn","noot");
//	    myAttrs.put("sn","mies");
//	    myAttrs.put("mail","wim");
//		
		

		final Navajo process2 = NavajoClientFactory.getClient().doSimpleSend(init, "club/ProcessQueryClub");
		process2.getMessage("ClubData");
		process2.write(System.err);
		for (int i = 0; i < al.size(); i++) {
			Message current = (Message) al.get(i);
			bla.insertPerson(context, current,constants);
			bla.addGroupMember("uid", "horr"+i, "member", context);

		}
	}

	public DirContext getContext(String dn) throws NamingException {
		if (initialDir == null) {
			startup();
		}
		DirContext dc = (DirContext) initialDir.lookup(dn);
		return dc;

	}

	public Context insertPerson(DirContext context, Message entity, Map constants) throws NamingException {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("cn", "LastName");
		mapping.put("sn", "LastName");
		mapping.put("gn", "FirstName");
//		mapping.put("uid", "PersonId");
		mapping.put("mail", "EmailData");
		mapping.put("street", "Address");

		mapping.put("userid", "PersonId");
		mapping.put("userPassword", "PersonId");

		// mapping.put("l","City");
		mapping.put("ou", "AgeClass");
//		mapping.put("member", "AgeClass");
//		mapping.put("postalCode", "ZipcodeCity");
		mapping.put("telephoneNumber", "TelephoneData");

		String[] objectClasses = new String[] { "top", "person", "account","organizationalPerson","inetorgperson"};
		return insertEntity("cn", context, entity, mapping, objectClasses, constants);
	}

	public Context insertOrganization(DirContext context, Message entity, Map<String, String> constants) throws NamingException {
		Map<String, String> mapping = new HashMap<String, String>();
		
		
	 	mapping.put("cn", "ClubName");
		mapping.put("o", "ClubName");
//		mapping.put("mail", "EmailData");
		//mapping.put("street", "Address");

		mapping.put("userid", "ClubIdentifier");
		mapping.put("userPassword", "ClubIdentifier");

		//mapping.put("l","City");
//		mapping.put("ou", "AgeClass");
//		mapping.put("member", "AgeClass");
//		mapping.put("postalCode", "ZipCode");
//		mapping.put("telephoneNumber", "TelephoneData");
	//	constants.put("member", "uid=Dummy");
		constants.put("member", "uid=Dummy");
		String[] objectClasses = new String[] { "top","organization","account"};
		return insertEntity("cn", context, entity, mapping, objectClasses, constants);
	}
	
	
	public Context insertEntity(String keyAttribute, DirContext context, Message entity, Map mapping, String[] objectClasses, Map constants)
			throws NamingException {
		
		
		String keyProperty = (String) mapping.get(keyAttribute);
		String keySource = entity.getProperty(keyProperty).getValue();
		BasicAttribute objectclass;
		objectclass = new BasicAttribute("objectclass");
		BasicAttributes mandatory = new BasicAttributes(keyAttribute, "" + entity.getProperty(keyProperty).getValue() + "");
		for (int i = 0; i < objectClasses.length; i++) {
			objectclass.add(objectClasses[i]);
		}
		mandatory.put("objectclass",objectclass);

		logger.info("Insert Entity: "+keyAttribute+" mapping: "+mapping);
		for (int i = 0; i < objectClasses.length; i++) {
			logger.info("CLASS: "+objectClasses[i]);
		}
		
//		String keyProperty = (String) mapping.get(keyAttribute);
//		String keySource = entity.getProperty(keyProperty).getValue();
//		BasicAttributes mandatory = new BasicAttributes(keyAttribute, "" + entity.getProperty(keyProperty).getValue() + "");
//		BasicAttribute objectclass = null;
//		logger.info("Create new objectClass");
//		objectclass = new BasicAttribute("objectClass");
//		for (int i = 0; i < objectClasses.length; i++) {
//			logger.info("Adding: "+objectClasses[i]+" to objectClass");
//			objectclass.add(objectClasses[i]);
//		}
//		mandatory.put("objectClass",objectclass);
		logger.info("Added to objectClass");
		for (Iterator iter = mapping.keySet().iterator(); iter.hasNext();) {
			String currentAttribute = (String) iter.next();
			if (currentAttribute.equals(keyAttribute)) {
				continue;
			}
			String propertyName = (String) mapping.get(currentAttribute);
			if (propertyName != null) {
				Property currentProperty = entity.getProperty(propertyName);
				if (currentProperty != null) {
					String value = currentProperty.getValue();
					if (value==null) {
						continue;
					}
					String propertyValue = "" + value.trim() + "";
					logger.info("AddingProp: >"+currentAttribute+"="+propertyValue+"<");
					BasicAttribute attr = new BasicAttribute(currentAttribute, propertyValue);
					mandatory.put(attr);
				} else {
					logger.info("Missing property: " + propertyName);
				}
			}
		}
		if (constants != null) {
			for (Iterator iter = constants.keySet().iterator(); iter.hasNext();) {
				String currentAttribute = (String) iter.next();
				String value = (String) constants.get(currentAttribute);
				BasicAttribute attr = new BasicAttribute(currentAttribute, value);
				logger.info("Adding:     >"+currentAttribute+"="+value+"<");
				mandatory.put(attr);
			}
		}
		try {
			logger.info("Creating: >"+keyAttribute+"="+keySource+"<");
			Context result = context.createSubcontext(keyAttribute + "=" + keySource, mandatory);
			return result;
		} catch (javax.naming.NameAlreadyBoundException n) {
			logger.info("Exists!");
			context.unbind(keyAttribute + "=" + keySource);
			Context result = context.createSubcontext(keyAttribute + "=" + keySource, mandatory);
			return result;
		}
	}

	public void addGroupMember(String key, String value, String groupMember, DirContext group ) throws NamingException {
		Attributes a = group.getAttributes("");
		Attribute aa =a.get(groupMember);
		aa.add(key+"="+value);
	}
}
