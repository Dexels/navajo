package com.dexels.navajo.server.soap;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * The purpose of this class is to handle soap requests.
 *
 * A Navajo SOAP request is structured as follows:
 *
 * <SOAP-ENV:Envelope>
 * <SOAP-ENV:Header>
 *   <a:authentication xmlns:a="http://www.dexels.com/xsd/authentication">
 *     <a:username>NAVAJOUSER</a:username>
 *     <a:password>NAVAJOPASSWORD</a:password>
 *     <a:service>NAME_OF_THE_WS</a:service>
 *   </a:authentication>
 * </SOAP-ENV:Header>
 * <SOAP-ENV:Body>
 *  <ZipCodeQueryMembers>
 *    <ZipCode xsi:type="xsd:string">1621AB</ZipCode>
 *    <HouseNumber xsi:type="xsd:int">3</HouseNumber>
 *  </ZipCodeQueryMembers>
 * </SOAP-ENV:Body>
 * </SOAP-ENV:Envelope>
 *
 * This gets translated to:
 *
 * <tml>
 *   <header>
 *     <transaction rpc_name="NAME_OF_THE_WS" rpc_usr="NAVAJOUSER" rpc_pwd="NAVAJOPASSWORD"/>
 *   </header>
 *   <message name="ZipCodeQueryMembers">
 *     <property name="ZipCode" type="string" value="1621AB" direction="in"/>
 *     <property name="HouseNumber" type="intgeger" value="3" direction="in"/>
 *   </message>
 * </tml>
 *
 */

public class Dispatcher {

  public Dispatcher() {
  }
}