package com.dexels.navajo.document.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class TMLSerializer  {

	private static final byte MESSAGE_CODE = 1;
	private static final byte ARRAY_MESSAGE_CODE = 2;
	private static final byte ARRAY_MESSAGE_ELT_CODE = 3;
	private static final byte STRING_PROPERTY_CODE = 4;
	private static final byte DATE_PROPERTY_CODE = 5;
	private static final byte INTEGER_PROPERTY_CODE = 6;
	private static final byte BOOLEAN_PROPERTY_CODE = 7;
	private static final byte BINARY_PROPERTY_CODE = 8;
	private static final byte FLOAT_PROPERTY_CODE = 9;
	private static final byte LONG_STRING_PROPERTY_CODE = 10;
	private static final byte MESSAGE_CLOSE_CODE = 99;

	final protected static char[] hexArray = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * types:
	 * message: 1
	 * array message: 2
	 * array message elt: 3
	 * string property: 4
	 * date property: 5
	 * integer property: 6
	 * boolean property 7:
	 * message close: 99
	 * 
	 * <message name="Visitor">
	 *     <property name="RFID" type="string" value="aap"/>
	 *     <property name="FestivalTokens" type="integer" value="30"/>
	 * </message>
	 * 
	 * -->
	 * 
	 * 1[len]Visitor4[len]RFID[len]aap6[len]FestivalTokens[len]3099
	 */
	private volatile Navajo myNavajo;
	private volatile String webservice;
	
	int arrayEltIndex = 0;

	public TMLSerializer() {

	}

	public TMLSerializer(Navajo in, String webservice) {
		myNavajo = in;
		this.webservice = webservice;
	}

	public int getPayloadType() {
		return 32;
	}

	private final byte [] serializeMessageTag(String name, byte type) {
		if ( type != ARRAY_MESSAGE_ELT_CODE) { // For array elements do NOT serialize name (not needed)
			ByteBuffer bBuffer = ByteBuffer.allocate(1 + 2 + name.length());
			bBuffer.put(type);
			bBuffer.putShort((short) name.length());
			bBuffer.put(name.getBytes());
			bBuffer.compact();
			return bBuffer.array();
		} else {
			return new byte[]{type};
		}
	}

	private final byte [] shortToBytes(short x) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.putShort(x);
		bb.compact();
		return bb.array();
	}
	
	private final short bytesToShort(InputStream is) throws Exception {
		byte [] r = new byte[2];
		is.read(r, 0, 2);
		ByteBuffer bb = ByteBuffer.wrap(r);
		return bb.getShort();
	}
	
	private final byte [] intToBytes(int x) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(x);
		bb.compact();
		return bb.array();
	}
	
	private final int bytesToInt(InputStream is) throws Exception {
		byte [] r = new byte[4];
		is.read(r, 0, 4);
		ByteBuffer bb = ByteBuffer.wrap(r);
		return bb.getInt();
	}
	
	private final void serializeProperty(Property p, byte type, boolean hidePropertyName, OutputStream os) throws Exception {
		
		boolean isPartOfArrayElt = ( type == ARRAY_MESSAGE_ELT_CODE && hidePropertyName );
		int lengthOfPropertyValue = 0;
		if ( p.getType().equals(Property.BINARY_PROPERTY )) {
			lengthOfPropertyValue = (int) ((Binary) p.getTypedValue()).getLength();
		} else if ( p.getTypedValue() != null ) {
			lengthOfPropertyValue = p.getValue().length();
		} 
		if ( p.getType().equals(Property.BOOLEAN_PROPERTY )) {
			os.write(BOOLEAN_PROPERTY_CODE);
		} else if ( p.getType().equals(Property.DATE_PROPERTY)) {
			os.write(DATE_PROPERTY_CODE);
		} else if ( p.getType().equals(Property.INTEGER_PROPERTY) ) {
			os.write(INTEGER_PROPERTY_CODE);
		} else if ( p.getType().equals(Property.FLOAT_PROPERTY) ) {
			os.write(FLOAT_PROPERTY_CODE);
		} else if ( p.getType().equals(Property.BINARY_PROPERTY)) {
			os.write(BINARY_PROPERTY_CODE);
		} else {
			if ( lengthOfPropertyValue > Short.MAX_VALUE ) {
				os.write(LONG_STRING_PROPERTY_CODE);
			} else {
				os.write(STRING_PROPERTY_CODE);
			}
		}
		if ( !isPartOfArrayElt ) {
			os.write(shortToBytes(((short) p.getName().length())));
			os.write(p.getName().getBytes());
		}
		if ( p.getType().equals(Property.BINARY_PROPERTY) ) {
			os.write(intToBytes(lengthOfPropertyValue));
			if ( lengthOfPropertyValue > 0 ) {
				((Binary) p.getTypedValue()).write(os);
			}
		} else {
			if ( lengthOfPropertyValue > Short.MAX_VALUE ) {
				os.write(intToBytes(lengthOfPropertyValue));
			} else {
				os.write(shortToBytes((short) lengthOfPropertyValue));
			}
			if ( lengthOfPropertyValue > 0 ) {
				os.write(p.getValue().getBytes());
			} 
		}

	}
	
	private final void serializeMessage(Message m, byte type, boolean hidePropertyName, OutputStream os) throws Exception {
		os.write(serializeMessageTag(m.getName(), type));
		for ( Property p : m.getAllProperties() ) {
			serializeProperty(p, type, hidePropertyName, os);
		}
		int index = 0;
		for ( Message cm : m.getAllMessages() ) {
			byte childType = ( cm.isArrayMessage() ? ARRAY_MESSAGE_CODE : m.isArrayMessage() ? ARRAY_MESSAGE_ELT_CODE : MESSAGE_CODE );
			serializeMessage(cm, childType, ( index > 0 && childType == ARRAY_MESSAGE_ELT_CODE), os);
			index++;
		}
		os.write(MESSAGE_CLOSE_CODE);
	}

	public final void writeBytes(OutputStream os) {
		try {
			// First add payload type (not compressed!)
			os.write((byte) (getPayloadType() & 0xFF));
			// Use compressed outputstream for remainder.
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Serialize webservice name
			baos.write(shortToBytes((short) webservice.getBytes().length));
			baos.write(webservice.getBytes());
			for ( Message m : myNavajo.getAllMessages() ) {
				serializeMessage(m, ( m.isArrayMessage() ? ARRAY_MESSAGE_CODE : MESSAGE_CODE ), false, baos);
			}
			baos.close();
			GZIPOutputStream gzipOut = new GZIPOutputStream(os);
			gzipOut.write(baos.toByteArray());
			gzipOut.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public final byte[] getBytes()  {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writeBytes(baos);
		return baos.toByteArray();
	}

	private final Property parseProperty(String type, boolean partOfArrayElement, int propertyIndex, int arrayEltIndex, InputStream is, boolean longLength) throws Exception {

		String name = propertyIndex + "";
		byte [] array = null;

		if ( arrayEltIndex == 0 || !partOfArrayElement ) {
			array = new byte[bytesToShort(is)];
			is.read(array, 0, array.length);
			name = new String(array);
		}
		int size = ( longLength ? bytesToInt(is) : bytesToShort(is) );
		array = new byte[size];
		is.read(array, 0, array.length);
		if ( type.equals(Property.BINARY_PROPERTY )) {
			Property p = NavajoFactory.getInstance().createProperty(myNavajo, name,type, null, 0, "", "");
			p.setAnyValue(new Binary(array));
			return p;
		} else {
			String value = new String(array);
			Property p = NavajoFactory.getInstance().createProperty(myNavajo, name,type, value, 0, "", "");
			return p;
		}
	}

	private final void fixPropertyNames(Message am) {
		// fix array elements.
		List<Property> definition = new ArrayList<Property>();
		int index = 0;
		for ( Message sub : am.getElements() ) {
			if ( index == 0 ) {
				definition.addAll(sub.getAllProperties());
			} else {
				int c = 0;
				for ( Property cp : sub.getAllProperties() ) {
					cp.setName(definition.get(c).getName());
					c++;
				}
			}
			index++;
		}
	}
	
	private final Message parseMessage(InputStream is, Message parent, String msgType) throws Exception {
		String name = "-";
		if ( !msgType.equals(Message.MSG_TYPE_ARRAY_ELEMENT)) { // array message elements do not have a message name.
			byte [] array = new byte[bytesToShort(is)];
			is.read(array, 0, array.length);
			name = new String(array);
		}
		Message m = NavajoFactory.getInstance().createMessage(myNavajo, name, msgType);
		if ( parent != null ) {
			parent.addMessage(m);
		} else {
			myNavajo.addMessage(m);
		}
		boolean endOfMessage = false;
		int propertyIndex = 0;
		while ( !endOfMessage ) {
			byte tagType = (byte) is.read(); // 
			if ( tagType == MESSAGE_CODE ) {
				parseMessage(is, m, Message.MSG_TYPE_SIMPLE); // a submessage.
			} else if ( tagType == ARRAY_MESSAGE_CODE ) {
				arrayEltIndex = 0;
				Message am = parseMessage(is, m, Message.MSG_TYPE_ARRAY); // an array message.
				fixPropertyNames(am);
			} else if ( tagType == ARRAY_MESSAGE_ELT_CODE ) {
				parseMessage(is, m, Message.MSG_TYPE_ARRAY_ELEMENT); // an array message element.
				arrayEltIndex++;
			} else if ( tagType == MESSAGE_CLOSE_CODE ) {
				propertyIndex = 0;
				endOfMessage = true;
			} else if ( tagType == STRING_PROPERTY_CODE || tagType == LONG_STRING_PROPERTY_CODE ) {
				Property p = parseProperty(Property.STRING_PROPERTY, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT), propertyIndex, arrayEltIndex, is, (tagType == LONG_STRING_PROPERTY_CODE ));
				m.addProperty(p);
				propertyIndex++;
			} else if ( tagType == DATE_PROPERTY_CODE ) {
				Property p = parseProperty(Property.DATE_PROPERTY, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT), propertyIndex, arrayEltIndex, is, false);
				m.addProperty(p);
				propertyIndex++;
			} else if ( tagType == BOOLEAN_PROPERTY_CODE ) {
				Property p = parseProperty(Property.BOOLEAN_PROPERTY, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT), propertyIndex, arrayEltIndex, is, false);
				m.addProperty(p);
				propertyIndex++;
			} else if ( tagType == INTEGER_PROPERTY_CODE ) {
				Property p = parseProperty(Property.INTEGER_PROPERTY, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT), propertyIndex, arrayEltIndex, is, false);
				m.addProperty(p);
				propertyIndex++;
			}  else if ( tagType == FLOAT_PROPERTY_CODE ) {
				Property p = parseProperty(Property.FLOAT_PROPERTY, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT), propertyIndex, arrayEltIndex, is, false);
				m.addProperty(p);
				propertyIndex++;
			} else if ( tagType == BINARY_PROPERTY_CODE ) {
				Property p = parseProperty(Property.BINARY_PROPERTY, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT), propertyIndex, arrayEltIndex, is, true);
				m.addProperty(p);
				propertyIndex++;
			} else {
				//System.err.println("Unknown byte encountered: " + tagType);
			}
		}
		return m;
	}

	public final void constructFromBytes(InputStream is, int length) throws Exception {
		byte [] wsLengthArray = new byte[2];
		is.read(wsLengthArray, 0, 2);
		ByteBuffer bb = ByteBuffer.wrap(wsLengthArray);
		short wsLength = bb.getShort();
		byte [] wsName = new byte[wsLength];
		is.read(wsName, 0, wsName.length);
		webservice = new String(wsName);
		myNavajo = NavajoFactory.getInstance().createNavajo();
		byte tagType;
		while ( ( tagType = (byte) is.read() ) != -1 ) {
			Message m = parseMessage(is, null, ( tagType == 0x01 ? Message.MSG_TYPE_SIMPLE : Message.MSG_TYPE_ARRAY ));
			if ( m.isArrayMessage() ) {
				fixPropertyNames(m);
			}
		}
	}
	
	public final void constructFromBytes(byte[] b) throws Exception {
		// Strip off first byte, it contains the type. Remainder is compressed.
		byte [] result = new byte[b.length -1];
		System.arraycopy(b, 1, result, 0, result.length);
		GZIPInputStream bais = new GZIPInputStream(new ByteArrayInputStream(result));
		constructFromBytes(bais, -1);
		bais.close();	
	}

	public Navajo getNavajo() {
		return myNavajo;
	}

	
	public String getWebservice() {
		return webservice;
	}

	public void setWebservice(String webservice) {
		this.webservice = webservice;
	}

	public static void main(String [] args) throws Exception {

		
//		Binary b = new Binary(new File("/Users/arjenschoneveld/module_sum3.jpg"));
		
		//System.err.println("Binary: " + b.getData().length);
		
		Navajo n = NavajoFactory.getInstance().createNavajo();
		
		
		Message m = NavajoFactory.getInstance().createMessage(n, "Visitor");
		n.addMessage(m);
		Property p1 = NavajoFactory.getInstance().createProperty(n, "RFID", Property.STRING_PROPERTY, "aap", 0, "", "");
		m.addProperty(p1);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "FestivalTokens", Property.INTEGER_PROPERTY, "78", 0, "", "");
		m.addProperty(p2);
//		Property p6 = NavajoFactory.getInstance().createProperty(n, "Picture", Property.BINARY_PROPERTY, null, 0, "", "");
//		p6.setAnyValue(b);
//		m.addProperty(p6);
		Property p7 = NavajoFactory.getInstance().createProperty(n, "StartDate", Property.DATE_PROPERTY, null, 0, "", "");
		p7.setAnyValue(new java.util.Date());
		m.addProperty(p7);
		Property p8 = NavajoFactory.getInstance().createProperty(n, "IsDitWaar", Property.BOOLEAN_PROPERTY, null, 0, "", "");
		p8.setAnyValue(Boolean.TRUE);
		m.addProperty(p8);
		Property p9 = NavajoFactory.getInstance().createProperty(n, "Floating", Property.FLOAT_PROPERTY, null, 0, "", "");
		p9.setAnyValue(66.7);
		m.addProperty(p9);
		
		Message m2 =  NavajoFactory.getInstance().createMessage(n, "Vouchers", Message.MSG_TYPE_ARRAY);
		m.addMessage(m2);
		
		for ( int i = 0; i < 4; i++ ) {
			Message m3 = NavajoFactory.getInstance().createMessage(n, "Vouchers");
			m2.addElement(m3);
			Property p3 = NavajoFactory.getInstance().createProperty(n, "Amount", Property.INTEGER_PROPERTY, 3*i+"", 0, "", "");
			m3.addProperty(p3);
			Property p4 = NavajoFactory.getInstance().createProperty(n, "ProductId", Property.STRING_PROPERTY, "FOOD", 0, "", "");
			m3.addProperty(p4);
			Message m8 =  NavajoFactory.getInstance().createMessage(n, "NogMeer", Message.MSG_TYPE_ARRAY);
			m3.addMessage(m8);
			for ( int j = 0; j < 2; j++ ) {
				Message m4 = NavajoFactory.getInstance().createMessage(n, "NogMeer");
				m8.addElement(m4);
				Property p31 = NavajoFactory.getInstance().createProperty(n, "Kibbeling", Property.INTEGER_PROPERTY, 3*i+"", 0, "", "");
				m4.addProperty(p31);
				Property p41 = NavajoFactory.getInstance().createProperty(n, "Fluitketel", Property.STRING_PROPERTY, "FOOD", 0, "", "");
				m4.addProperty(p41);
			}
		}

		
		//n.write(System.err);
		
		TMLSerializer pl1 = new TMLSerializer(n, "everywear/entity/visitor/ProcessQueryVisitor");
		byte [] data = pl1.getBytes();
		
		//System.err.println(data.length + ":" + bytesToHex(data));

		TMLSerializer pl2 = new TMLSerializer();
		System.err.println("Constructing from bytes...");
		pl2.constructFromBytes(data);
		
//		PayloadFactory.registerPayload(new TMLPayload().getPayloadType(), TMLPayload.class);
//		
//		ByteArrayInputStream bais = new ByteArrayInputStream(data);
//		bais.close();
//		TMLPayload pl2 = (TMLPayload) PayloadFactory.getPayload(bais, data.length);

		StringWriter sw = new StringWriter();
		pl2.getNavajo().write(sw);
//		
		System.err.println(sw.toString());
		int size1 = data.length;
		int size2 = sw.toString().getBytes().length;
		System.err.println("webservice: " + pl2.webservice);
		System.err.println("binary size: " + size1 + ", xml size: " + size2);
		System.err.println((double) size2 / (double) size1);
//
//		FileOutputStream fos = new FileOutputStream(new File("/Users/arjenschoneveld/aap.jpg"));
//		Binary b2 = (Binary) pl2.getNavajo().getProperty("/Visitor/Picture").getTypedValue();
//		b2.write(fos);
//		fos.close();
	}
}
