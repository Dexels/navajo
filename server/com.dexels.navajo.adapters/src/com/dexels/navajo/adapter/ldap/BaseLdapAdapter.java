package com.dexels.navajo.adapter.ldap;

import java.util.*;

import javax.naming.*;
import javax.naming.directory.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public class BaseLdapAdapter implements Mappable {

	private static final String DEFAULT_LDAP_SERVER = "ldap://iris.dexels.nl:389/";
	private static final String DEFAULT_LDAP_USERNAME = "uid=root,dc=dexels,dc=com";
	private static final String DEFAULT_LDAP_PASSWORD = "secret";

	public String server = DEFAULT_LDAP_SERVER;
	public String username = DEFAULT_LDAP_USERNAME;
	public String password = DEFAULT_LDAP_PASSWORD;

	private InitialDirContext initialDir = null;

	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	private void checkLdap(String ldapUrl, String principal, String username, String password) throws NamingException {
		Hashtable env = new Hashtable();

		String usr = "uid=" + username + ",";
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, usr + principal);
		env.put(Context.SECURITY_CREDENTIALS, password);
		// Create the initial context
		initialDir = new InitialDirContext(env);
		Integer i = new Integer(28420);

		System.out.println("Adding " + i + " to directory...");

		initialDir.bind(principal + ",cn=myRandomInt", i);
		// ctx.bind(arg0, i)
		i = new Integer(98765);
		System.out.println("i is now: " + i);

		i = (Integer) initialDir.lookup("cn=myRandomInt");
		System.out.println("Retrieved i from directory with value: " + i);
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
				System.err.println("o: " + o);
			}
		} catch (NamingException e) {
			e.printStackTrace();
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
		Message clubD = process2.getMessage("ClubData");
		process2.write(System.err);
//		Con ext organization = bla.insertOrganization(context, clubD,constants);

		//		constants.put("o","MOC");
//		constants.put("member","MOC");
//		
		for (int i = 0; i < al.size(); i++) {
			Message current = (Message) al.get(i);
			Context person = bla.insertPerson(context, current,constants);
			bla.addGroupMember("uid", "horr"+i, "member", context);

		}

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

	public DirContext getContext(String dn) throws NamingException {
		if (initialDir == null) {
			startup();
		}
		DirContext dc = (DirContext) initialDir.lookup(dn);
		return dc;

	}

	public Context insertPerson(DirContext context, Message entity, Map constants) throws NamingException {
		Map mapping = new HashMap<String, String>();
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

	public Context insertOrganization(DirContext context, Message entity, Map constants) throws NamingException {
		Map mapping = new HashMap<String, String>();
		
		
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

		System.err.println("Insert Entity: "+keyAttribute+" mapping: "+mapping);
		for (int i = 0; i < objectClasses.length; i++) {
			System.err.println("CLASS: "+objectClasses[i]);
		}
		
//		String keyProperty = (String) mapping.get(keyAttribute);
//		String keySource = entity.getProperty(keyProperty).getValue();
//		BasicAttributes mandatory = new BasicAttributes(keyAttribute, "" + entity.getProperty(keyProperty).getValue() + "");
//		BasicAttribute objectclass = null;
//		System.err.println("Create new objectClass");
//		objectclass = new BasicAttribute("objectClass");
//		for (int i = 0; i < objectClasses.length; i++) {
//			System.err.println("Adding: "+objectClasses[i]+" to objectClass");
//			objectclass.add(objectClasses[i]);
//		}
//		mandatory.put("objectClass",objectclass);
		System.err.println("Added to objectClass");
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
					System.err.println("AddingProp: >"+currentAttribute+"="+propertyValue+"<");
					BasicAttribute attr = new BasicAttribute(currentAttribute, propertyValue);
					mandatory.put(attr);
				} else {
					System.err.println("Missing property: " + propertyName);
				}
			}
		}
		if (constants != null) {
			for (Iterator iter = constants.keySet().iterator(); iter.hasNext();) {
				String currentAttribute = (String) iter.next();
				String value = (String) constants.get(currentAttribute);
				BasicAttribute attr = new BasicAttribute(currentAttribute, value);
				System.err.println("Adding:     >"+currentAttribute+"="+value+"<");
				mandatory.put(attr);
			}
		}
		try {
			System.err.println("Creating: >"+keyAttribute+"="+keySource+"<");
			Context result = context.createSubcontext(keyAttribute + "=" + keySource, mandatory);
			return result;
		} catch (javax.naming.NameAlreadyBoundException n) {
			System.err.println("Exists!");
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
