package de.xeinfach.kafenio.util;

import java.util.Vector;

/**
 * Description: Base64 Encoder/Decoder class.
 * 
 * @author Howard Kistler
 */
public final class Base64Codec {
	
	private static LeanLogger log = new LeanLogger("Base64Codec.class");

	private static Vector base64Tokens = new Vector(64);

	public static final char BASE64PAD = '=';
	public static final char LINEFEED = (char)10;
	public static final char CARRIAGERETURN = (char)13;
	public static final int  MAXIMUMLINES = 75;

	static {
		base64Tokens.add("A");
		base64Tokens.add("B");
		base64Tokens.add("C");
		base64Tokens.add("D");
		base64Tokens.add("E");
		base64Tokens.add("F");
		base64Tokens.add("G");
		base64Tokens.add("H");
		base64Tokens.add("I");
		base64Tokens.add("J");
		base64Tokens.add("K");
		base64Tokens.add("L");
		base64Tokens.add("M");
		base64Tokens.add("N");
		base64Tokens.add("O");
		base64Tokens.add("P");
		base64Tokens.add("Q");
		base64Tokens.add("R");
		base64Tokens.add("S");
		base64Tokens.add("T");
		base64Tokens.add("U");
		base64Tokens.add("V");
		base64Tokens.add("W");
		base64Tokens.add("X");
		base64Tokens.add("Y");
		base64Tokens.add("Z");
		base64Tokens.add("a");
		base64Tokens.add("b");
		base64Tokens.add("c");
		base64Tokens.add("d");
		base64Tokens.add("e");
		base64Tokens.add("f");
		base64Tokens.add("g");
		base64Tokens.add("h");
		base64Tokens.add("i");
		base64Tokens.add("j");
		base64Tokens.add("k");
		base64Tokens.add("l");
		base64Tokens.add("m");
		base64Tokens.add("n");
		base64Tokens.add("o");
		base64Tokens.add("p");
		base64Tokens.add("q");
		base64Tokens.add("r");
		base64Tokens.add("s");
		base64Tokens.add("t");
		base64Tokens.add("u");
		base64Tokens.add("v");
		base64Tokens.add("w");
		base64Tokens.add("x");
		base64Tokens.add("y");
		base64Tokens.add("z");
		base64Tokens.add("0");
		base64Tokens.add("1");
		base64Tokens.add("2");
		base64Tokens.add("3");
		base64Tokens.add("4");
		base64Tokens.add("5");
		base64Tokens.add("6");
		base64Tokens.add("7");
		base64Tokens.add("8");
		base64Tokens.add("9");
		base64Tokens.add("+");
		base64Tokens.add("/");
	}	

	/**
	 * private constructor for utility class.
	 */
	private Base64Codec() {}

	/**
	 * method used to encode a string into a base64 string.
	 * @param source plain text to encode.
	 * @return returns the base64 encoded version of source.
	 */
	public static String encode(String source)
	{
		byte[] sourceBytes = source.getBytes();
		int byteTriad = sourceBytes.length % 3;
		StringBuffer encoding = new StringBuffer();
		int bitOffset = 7;
		int b64Offset = 5;
		int bytePlace = 0;
		byte tokenValue = (byte)0;
		int lineLength = 0;
		while(bytePlace < sourceBytes.length) {

			tokenValue = (byte)((byte)tokenValue 
								| (byte)((sourceBytes[bytePlace] & (1 << bitOffset)) > 0 ? (1 << b64Offset) : (byte)0));
			bitOffset--;
			if(bitOffset < 0) {
				bitOffset = 7;
				bytePlace++;
			}
			b64Offset--;
			if(b64Offset < 0) {
				b64Offset = 5;
				encoding.append((String)(base64Tokens.elementAt(tokenValue)));
				tokenValue = (byte)0;
				lineLength++;
				if(lineLength > MAXIMUMLINES) {
					encoding.append(CARRIAGERETURN);
					encoding.append(LINEFEED);
					lineLength = 0;
				}
			}
		}
		if(b64Offset != 5) {
			bytePlace--;
			for(int i = b64Offset; i >= 0; i--) {
				if(bitOffset >= 0) {
					tokenValue = (byte)((byte)tokenValue 
										| (byte)((sourceBytes[bytePlace] & (1 << bitOffset)) > 0 ? (1 << i) : (byte)0));
				}
				bitOffset--;
			}
			encoding.append((String)(base64Tokens.elementAt(tokenValue)));
		}	

		if(byteTriad == 2) {
			encoding.append(BASE64PAD);
		} else if(byteTriad == 1) {
			encoding.append(BASE64PAD);
			encoding.append(BASE64PAD);
		}

		return encoding.toString();
	}

	/**
	 * method used to decode a base64 encoded string.
	 * @param source the string to decode.
	 * @return returns the decoded base64 string.
	 */
	public static String decode(String source) {
		StringBuffer decoding = new StringBuffer();
		int bitOffset = 7;
		int b64Offset = 5;
		int bytePlace = 0;
		byte charValue = (byte)0;
		while(bytePlace < source.length())
		{
			if(source.charAt(bytePlace) == BASE64PAD) {
				// end processing when encountering special end-padding character
				break;
			}

			if(source.charAt(bytePlace) == LINEFEED 
				|| source.charAt(bytePlace) == CARRIAGERETURN) 
			{
				// ignore standard line break characters
				bytePlace++;
				continue;
			} else {
				if(!base64Tokens.contains(source.substring(bytePlace, bytePlace + 1))) {
					// ignore unknown characters (mostly implemented to deal with other line break character sequences)
					bytePlace++;
					continue;
				} else {
					byte currentByte = (byte)(base64Tokens.indexOf(source.substring(bytePlace, bytePlace + 1)));
					charValue = (byte)((byte)charValue 
										| (byte)((currentByte & (1 << b64Offset)) > 0 ? (1 << bitOffset) : (byte)0));
					bitOffset--;
					if(bitOffset < 0) {
						bitOffset = 7;
						decoding.append((char)charValue);
						charValue = (byte)0;
					}

					b64Offset--;
					if(b64Offset < 0) {
						b64Offset = 5;
						bytePlace++;
					}
				}
			}
		}
		return decoding.toString();
	}
}