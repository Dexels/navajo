package com.dexels.navajo.server;

import java.util.*;

import javax.naming.*;
import javax.naming.directory.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;

public class LdapRepository extends SimpleRepository {

	private static final String LDAP_SERVER = "ldap://iris.dexels.nl:389/";

	@Override
	public Access authorizeUser(String username, String password, String service, Navajo inMessage, Object certificate) throws SystemException, AuthorizationException {
		// TODO Auto-generated method stub
		try {
			checkLdap(LDAP_SERVER,"o=dexels",username, password);

//			ou=users,dc=dexels,dc=com

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AuthorizationException(true,true,username,"Sorry, LDAP complains.");
		}
		return super.authorizeUser(username, password, service, inMessage, certificate);
	}

	

	private void checkLdap(String ldapUrl, String principal, String username, String password) throws NamingException {
		Hashtable env = new Hashtable();

		String usr = "uid="+username+",";
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, usr+principal);
		env.put(Context.SECURITY_CREDENTIALS,password);
		// Create the initial context
		DirContext ctx = new InitialDirContext(env);
        Integer i = new Integer( 28420 );
        
        System.out.println( "Adding " + i + " to directory..." );
       
        ctx.bind( principal+",cn=myRandomInt", i );
//        ctx.bind(arg0, i)
        i = new Integer( 98765 );
        System.out.println( "i is now: " + i );
        
        i = (Integer) ctx.lookup( "cn=myRandomInt" );
        System.out.println( "Retrieved i from directory with value: " + i );
	}
	
//	public static void main(String[] args) throws SystemException, AuthorizationException {
//		LdapRepository lr = new LdapRepository();
//		lr.authorizeUser("root", "secret", null, null, null);
//	}

    public static void main( String[] args ) throws NamingException {
        // set up environment to access the server
        
        Properties env = new Properties();
        
        env.put( Context.INITIAL_CONTEXT_FACTORY,
                 "com.sun.jndi.ldap.LdapCtxFactory" );
        env.put( Context.PROVIDER_URL, LDAP_SERVER );
        env.put( Context.SECURITY_PRINCIPAL, "uid=root,dc=dexels,dc=com" );
        env.put( Context.SECURITY_CREDENTIALS, "secret");
      // obtain initial directory context using the environment
      DirContext ctx = new InitialDirContext( env );
                
                // now, create the root context, which is just a subcontext
                // of this initial directory context.
//                ctx.createSubcontext( "dc=dexels,dc=com" );
                DirContext a = (DirContext) ctx.lookup( "dc=dexels,dc=com" ); 
                NamingEnumeration e= a.list("ou=users");
                while(e.hasMore()) {
                	Object o = e.next();
                	System.err.println("o: "+o);
                }
                DirContext users = (DirContext) a.lookup("ou=users");
                BasicAttributes mandatory = new BasicAttributes("cn", "BillyBob");
                BasicAttribute objectclass;
                
                objectclass = new BasicAttribute("objectclass", "top");
                mandatory.put(objectclass);

                objectclass = new BasicAttribute("objectclass", "person");
                mandatory.put(objectclass);

                BasicAttribute surname = new BasicAttribute("surname", "Roberts");

                mandatory.put(surname);

                users.createSubcontext("cn=BillyBob", mandatory);
//                a.createSubcontext("cn=Emelius,dc=dexels,dc=com,objectClass=account,objectClass=top");
//                a.bind("uid=Emelius", "Bartholdy");
    }				
}
