package com.dexels.navajo.adapter.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameClassPair;
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

public class Base2LdapAdapter implements Mappable {

	private static final int MAX_CLUBS = 20;
	private static final int MAX_INSERTS_PER_CLUB = 20;
	private static final String DEFAULT_LDAP_SERVER = "ldap://iris.dexels.nl:389/";
	private static final String DEFAULT_LDAP_USERNAME = "uid=root,dc=dexels,dc=com";
	private static final String DEFAULT_LDAP_PASSWORD = "secret";

	public String server = DEFAULT_LDAP_SERVER;
	public String username = DEFAULT_LDAP_USERNAME;
	public String password = DEFAULT_LDAP_PASSWORD;

	
	private final static Logger logger = LoggerFactory
			.getLogger(Base2LdapAdapter.class);
	
	private InitialDirContext initialDir = null;

	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}
	
	@SuppressWarnings("unused")
	private DirContext createBranch(String key, String value, DirContext context, String[] objectClasses) throws NamingException {
		BasicAttributes mandatory = new BasicAttributes(key, value);
		BasicAttribute objectclass;
		objectclass = new BasicAttribute("objectclass");
		for (int i = 0; i < objectClasses.length; i++) {
			objectclass.add(objectClasses[i]);
		}
		mandatory.put(objectclass);

		DirContext result = context.createSubcontext(key + "=" + value, mandatory);
		return result;
	}

	public void startup() throws NamingException {
		Properties env = new Properties();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, server);
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);
		initialDir = new InitialDirContext(env);

	}

	public Base2LdapAdapter[] list(String base) {
		// "ou=users,dc=dexels,dc=com"
		try {
			if (initialDir == null) {
				startup();
			}
			DirContext dc = (DirContext) initialDir.lookup(base);
			NamingEnumeration e = dc.list("");
			while (e.hasMore()) {
				Object o = e.next();
				logger.debug("o: " + o);
			}
		} catch (NamingException e) {
			logger.error("Error listing ldab: ", e);
		}

		return null;

	}

	public static void main(String[] args) throws NamingException, ClientException, NavajoException {

		ClientInterface cc = NavajoClientFactory.createDefaultClient();
		cc.setServerUrl("penelope1.dexels.com/sportlink/knvb/servlet/Postman");
		cc.setUsername("ROOT");
		cc.setPassword("R20T");

		Base2LdapAdapter bla = new Base2LdapAdapter();
		if (bla.initialDir == null) {
			bla.startup();
		}
		bla.insertLeague(bla.getContext("ou=clubs,o=knvb,dc=dexels,dc=com"));

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

	private void insertLeague(DirContext root) throws ClientException, NamingException {
		final Navajo init = NavajoClientFactory.getClient().doSimpleSend("club/InitUpdateClub");

		final Navajo initSearch = NavajoClientFactory.getClient().doSimpleSend("club/InitSearchClubs");
		initSearch.getProperty("ClubSearch/SearchName").setValue("a");
		final Navajo process2 = NavajoClientFactory.getClient().doSimpleSend(initSearch, "club/ProcessSearchClubs");
		Message result = process2.getMessage("Club");
		ArrayList ll = result.getAllMessages();

	
		
		int count = ll.size();
		count = Math.min(count, MAX_CLUBS);
		for (int i = 0; i < count; i++) {
			Message m = (Message) ll.get(i);
			String value = m.getProperty("ClubIdentifier").getValue();
			logger.debug("Clubid: " + value);
			insertClub(root, init, value);
			logger.debug("Inserted: " + m.getProperty("ClubName").getValue());
		}
	}

	public void insertClub(DirContext clubsContext,final Navajo init, String clubId) throws NamingException, ClientException {

		init.getProperty("Club/ClubIdentifier").setValue(clubId);
		init.getProperty("Club/LastName").setValue("");


		Map<String,String> constants = new HashMap<String,String>();
		final Navajo process2 = NavajoClientFactory.getClient().doSimpleSend(init, "club/ProcessQueryClub");
		Message clubD = process2.getMessage("ClubData");
		insertOrganization(clubsContext, clubD, constants);
		final Navajo process = NavajoClientFactory.getClient().doSimpleSend(init, "member/ProcessQueryAllMembersPerClub");
		Message memberdata = process.getMessage("MembersPerClub");

		List<Message> al = memberdata.getAllMessages();
		int count = al.size();
		count = Math.min(count, MAX_INSERTS_PER_CLUB);
		for (int i = 0; i < count; i++) {
			Message current = al.get(i);
//			DirContext personContext = getContext("o=persons,dc=dexels,dc=com");
			try {
				String pId = current.getProperty("PersonId").getValue();
				Map<String,String> cn2 = new HashMap<String,String>(constants);
				cn2.put("userid", pId);
				insertPerson(clubsContext, current, cn2);
				DirContext clubMemberContext = getContext(clubsContext,"cn=members_"+clubId);
				// getContext("cn=members,o="+clubId+",o=clubs,dc=dexels,dc=com");
				String uid = current.getProperty("PersonId").getValue();
				addGroupMember("uid", uid, "member", clubMemberContext);
			} catch (Exception e) {
				logger.debug("Adding member failed, continuing",e);
				current.write(System.err);
			}
		}
	}

	public DirContext getContext(String dn) throws NamingException {
		if (initialDir == null) {
			startup();
		}
//		logger.debug("Context lookup: "+dn);
		DirContext dc = (DirContext) initialDir.lookup(dn);
		return dc;

	}
	
	public DirContext getContext(DirContext c, String dn) throws NamingException {
	
		logger.debug("Context lookup: "+dn);
		DirContext dc = (DirContext) c.lookup(dn);
		return dc;

	}

	public Context insertPerson(DirContext context, Message entity, Map<String,String> constants) throws NamingException {
		Map<String,String> mapping = new HashMap<String, String>();
		mapping.put("cn", "LastName");
		mapping.put("sn", "LastName");
		mapping.put("gn", "FirstName");
		// mapping.put("uid", "PersonId");
		mapping.put("mail", "EmailData");
		mapping.put("street", "Address");

//		mapping.put("userid", "PersonId");
		mapping.put("userPassword", "PersonId");

		// mapping.put("l","City");
		mapping.put("ou", "AgeClass");
		// mapping.put("member", "AgeClass");
		// mapping.put("postalCode", "ZipcodeCity");
		mapping.put("telephoneNumber", "TelephoneData");

		String[] objectClasses = new String[] { "top", "person", "organizationalPerson", "inetorgperson" };
		return insertEntity("userid", constants.get("userid"), context, entity, mapping, objectClasses, constants);
	}

	public DirContext insertOrganization(DirContext context, Message entity, Map<String,String> constants) throws NamingException {
		Map<String,String> mapping = new HashMap<String, String>();

		// mapping.put("cn", "ClubName");
		mapping.put("o", "ClubIdentifier");
		mapping.put("description", "ClubName");
		// mapping.put("mail", "EmailData");
		mapping.put("street", "Address");

		// mapping.put("userid", "ClubIdentifier");
		// mapping.put("userPassword", "ClubIdentifier");

		mapping.put("l", "City");
		// mapping.put("ou", "AgeClass");
		// mapping.put("member", "AgeClass");
		mapping.put("postalCode", "ZipCode");
		mapping.put("telephoneNumber", "TelephoneData");
		// constants.put("member", "uid=Dummy");
		String[] objectClasses = new String[] { "top", "organization" };
		DirContext insertEntity = insertEntity("o", null, context, entity, mapping, objectClasses, constants);
		Map<String,String> memberMap = new HashMap<String, String>();
		memberMap.put("member", "cn=dummy");
		
		entity.write(System.err);
		String clubId = entity.getProperty("ClubIdentifier").getValue();
		String clubName = entity.getProperty("ClubName").getValue();
		logger.debug("Owner: "+clubId);
		memberMap.put("owner", "o="+clubId);
		memberMap.put("description", clubName);
		memberMap.put("o", clubId);
		// memberMap.put("o", "MOC");
		// memberMap.put("dn", "MOC");
		insertEntity("cn", "members_"+clubId, context, null, null, new String[] { "top", "groupOfNames" }, memberMap);
		// String[] objectClasses = new String[] {
		// "top","organization","account"};
		// String[] objectClasses = new String[] { "top","organization"};
		return insertEntity;

	}

	public void deleteContext(DirContext dd, String dn) throws NamingException {

		NamingEnumeration e = dd.list("");
		while (e.hasMore()) {
			e.next();

			NamingEnumeration<NameClassPair> sss = dd.list("");
			while (sss.hasMore()) {
				Object oo = sss.next();
				logger.debug("oo: " + oo);
			}

			// deleteContext(oo, oo.getNameInNamespace());
		}

		dd.unbind(dn);
	}

	public DirContext insertEntity(String keyAttribute, String keyValue, DirContext context, Message entity, Map mapping,
			String[] objectClasses, Map constants) throws NamingException {

		String keySource = null;
		String keyProperty = null;
		if (mapping == null) {
			keySource = keyValue;

		} else {
			keyProperty = (String) mapping.get(keyAttribute);
			if(keyProperty==null) {
				keySource = (String) constants.get(keyAttribute);
				
			} else {
				Property property = entity.getProperty(keyProperty);
				if(property==null) {
					// Sort of hack...
					keySource = (String) constants.get(keyAttribute);
				} else {
					keySource = property.getValue();
				}
			}

		}

		BasicAttributes mandatory = new BasicAttributes(keyAttribute, keySource);

		BasicAttribute objectclass;
		objectclass = new BasicAttribute("objectclass");
		for (int i = 0; i < objectClasses.length; i++) {
			objectclass.add(objectClasses[i]);
		}
		mandatory.put(objectclass);
		if (mapping != null) {
			for (Iterator iter = mapping.keySet().iterator(); iter.hasNext();) {
				String currentAttribute = (String) iter.next();
				// if (currentAttribute.equals(keyAttribute)) {
				// continue;
				// }
				String propertyName = (String) mapping.get(currentAttribute);
				if (propertyName != null) {
					Property currentProperty = entity.getProperty(propertyName);
					if (currentProperty != null) {
						String value = currentProperty.getValue();
						if (value == null) {
							continue;
						}
						String propertyValue = "" + value.trim() + "";
						BasicAttribute attr = new BasicAttribute(currentAttribute, propertyValue);
						mandatory.put(attr);
					} else {
						logger.debug("Missing property: " + propertyName);
					}
				}
			}
		}
		if (constants != null) {
			for (Iterator iter = constants.keySet().iterator(); iter.hasNext();) {
				String currentAttribute = (String) iter.next();
				String value = (String) constants.get(currentAttribute);
				BasicAttribute attr = new BasicAttribute(currentAttribute, value);
				mandatory.put(attr);
			}
		}
		try {
//			logger.debug("Key: "+keyAttribute+" value: "+keySource);
//			logger.debug("Parent context: "+context.getNameInNamespace());
//			logger.debug("Adding params: "+mandatory);
			DirContext result = context.createSubcontext(keyAttribute + "=" + keySource, mandatory);
			return result;
		} catch (javax.naming.NameAlreadyBoundException n) {
			// logger.debug("Exists!");
			// todo: Recursive delete
			// deleteContext(context,keyAttribute + "=" + keySource);
			context.unbind(keyAttribute + "=" + keySource);
			DirContext result = context.createSubcontext(keyAttribute + "=" + keySource, mandatory);
			return result;
		}
	}

	public void addGroupMember(String key, String value, String groupMember, DirContext group) throws NamingException {
		
		Attributes a = group.getAttributes("");
		Attribute aa = a.get(groupMember);
		if(aa.size()==1) {
			// does not work
			String first = (String) aa.get(0);
			if(first.equals("dummy")) {
				logger.debug("Dummy found!");
				aa.clear();
			}
			
		}
		aa.add(key + "=" + value);

		group.modifyAttributes("", DirContext.REPLACE_ATTRIBUTE, a);
	}
}
