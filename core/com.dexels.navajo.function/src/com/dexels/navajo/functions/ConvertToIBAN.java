package com.dexels.navajo.functions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.dexels.navajo.parser.FunctionInterface;


/**
 * Example calculation:
 * 
 * Controlegetal
 *
 * Het controlegetal wordt verkregen door
 *
 *  de rekeningidentificatie te nemen
 *   er de landcode achter te plaatsen
 *   alle letters te vervangen door hun positie in het romeinse alfabet, vermeerderd met 9 (A=10, B=11...Z=35)
 *   twee nullen toe te voegen aan het einde
 *   dan de rest te nemen van de deling van het zo bekomen getal door 97.
 *   deze rest van 98 af te trekken om het controlegetal te krijgen
 *
 * Voorbeeld: Voor een imaginair Nederlands ING banknummer 1234567 is het IBAN NLxx INGB 0001 2345 67 (zie hieronder). Het controlegetal wordt als volgt berekend:
 *
 *   INGB0001234567
 *   INGB0001234567NL
 *   1823161100012345672321 (I = 18; N = 23 ...)
 *   182316110001234567232100
 *   182316110001234567232100 mod 97 = 78
 *   98 - 78 = 20
 *
 * Het IBAN zal dus NL20INGB0001234567 zijn.
 *
 * List of all BIC codes for The Netherlands: http://www.theswiftcodes.com/netherlands/
 * 
 * @author Erik Versteeg
 */
public class ConvertToIBAN extends FunctionInterface {
	public ConvertToIBAN() {}

	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
	    long s = (Long) this.getOperands().get(0);
		
	    if (s == 0)
	      return null;
	
	    return convertToIBAN(s);
	}
	
	public String usage() {
		return "ConvertToIBAN(long)";
	}
	
	public String remarks() {
		return "Returns the IBAN version of the supplied accountnumber.";
  	}

	/**
	 * Get the bankcode from teh accountnumber
	 * @param accountNumber
	 * @return String
	 * @throws Exception 
	 */
	private static String getIBANBankCode(String accountNumber) {
		String result = "";
		
		long accountNr = Long.parseLong(accountNumber);
		int startingNumbers = Integer.parseInt(Long.toString(accountNr).substring(0, 2));
		int numberPos3 = Integer.parseInt(Long.toString(accountNr).substring(2, 3));
		int numberPos4 = Integer.parseInt(Long.toString(accountNr).substring(3, 4));
		
		if (Long.toString(accountNr).length() < 9) {
			if (startingNumbers != 0) {
				result = "INGB";
			}
		} else {
			if (startingNumbers != 0) {
				// RABO
				if ((startingNumbers >= 10 && startingNumbers <= 19) || 
				    (startingNumbers >= 30 && startingNumbers <= 39)) {
					result = "RABO";
				// Bankgirocentrale BV
				} else if (startingNumbers == 20) {
					result = "XXXX";
				} else if (startingNumbers == 26) {
					// GarantiBank International N.V.
					if (numberPos3 == 5 && numberPos4 == 7) {
						result = "UGBI";
					// Landshot
					} else {
						result = "FVLB";
					}
				// Amsterdam Trade Bank
				} else if (startingNumbers == 27) {
					result = "ATBA"; // also STOL
				// Delta Loyd
				} else if (startingNumbers == 28) {
					result = "DLBK";
				// Friesland bank
				} else if (startingNumbers == 29) {
					result = "FRBK";
				// Triodos bank
				} else if (startingNumbers == 39) {
					result = "TRIO";
				// NIBC Direct
				} else if (startingNumbers == 21 && numberPos3 == 3 && numberPos4 == 2) {
					result = "XXXX";
				// Kas bank (nieuw)
				} else if (startingNumbers == 22 && numberPos3 == 3 && numberPos4 == 6) {
					result = "KASA";
				// Ohra Spaarbank
				} else if (startingNumbers == 25 && numberPos3 == 0 && numberPos4 == 8) {
					result = "OHAA";
				// Fortis
				} else if ((startingNumbers >= 21 && startingNumbers <= 25) || 
						    startingNumbers == 64 || 
						   (startingNumbers >= 80 && startingNumbers <= 81) || 
						   (startingNumbers >= 83 && startingNumbers <= 84) || 
						   (startingNumbers >= 86 && startingNumbers <= 89) || 
						    startingNumbers == 94 || 
						   (startingNumbers >= 97 && startingNumbers <= 99)) {
					result = "FTSB";
				// ING
				} else if ((startingNumbers >= 65 && startingNumbers <= 69) || 
						    startingNumbers == 75 || 
						    startingNumbers == 79) {
					result = "INGB";
				// ABNAMRO
				} else if ((startingNumbers >= 40 && startingNumbers <= 59) || 
						   (startingNumbers >= 61 && startingNumbers <= 62) || 
						    startingNumbers == 71 || 
						    startingNumbers == 76) {
					result ="ABNA";
				// Aegon
				} else if (startingNumbers == 74) {
					result ="AEGO";
				// ASN Bank (nieuw)
				} else if (startingNumbers == 70 && numberPos3 == 7 && numberPos4 == 5) {
					result = "XXXX";
				} else if (startingNumbers == 60) {
					// Bank of Scotland
					if (numberPos3 == 0 && numberPos4 == 0) {
						result = "BOFS";
					// Nederlandse bank (onzeker)
					} else {
						result = "FLOR";
					}
				// Position 3 & 4 are used for something when it comes to certain ranges
				} else if (startingNumbers == 63) {
					// SNS
					if (numberPos3 == 5 && numberPos4 == 0) {
						result = "SNSB";
					// Mizuho Cor. bank
					} else {
						result = "MHCB";
					}
				// Position 3 is used for something when it comes to certain ranges
				} else if (startingNumbers == 72) {
					// Finansbank (Holland) N.V.
					if (numberPos3 == 9 && numberPos4 == 4) {
						result = "XXXX";
					// Aegon
					} else {
						result = "AEGO";
					}
				// Position 3 is used for something when it comes to certain ranges
				} else if (startingNumbers == 73) {
					// Dresdner Bank
					if (numberPos3 == 3 && numberPos4 == 9) {
						result = "XXXX";
					// Ohra Spaarbank (via Kruidvat)
					} else if (numberPos3 == 5 && numberPos4 == 9) {
						result = "OHAA";
					// Ohra Spaarbank
					} else if (numberPos3 == 6 && numberPos4 == 0) {
						result = "OHAA";
					// Aegon
					} else {
						result = "AEGO";
					}
				// Position 3 is used for something when it comes to certain ranges
				} else if (startingNumbers == 77) {
					// DSB Bank B.V.
					if (numberPos3 == 3 && numberPos4 == 1) {
						result = "DSSB";
					// AK Bank N.V. (stopt per mei 2012)
					} else if (numberPos3 == 7 && numberPos4 == 4) {
						result = "AKBK";
					// Aegon
					} else {
						result = "AEGO";
					}
				// Position 3 is used for something when it comes to certain ranges
				} else if (startingNumbers == 78) {
					// Aegon
					if (numberPos3 == 0) {
						result = "AEGO";
					// Argenta Bank
					} else if (numberPos3 == 2 && numberPos4 == 0) {
						result = "ARSN";
					// DSB Bank B.V.
					} else if (numberPos3 == 2 && numberPos4 == 5) {
						result = "DSSB";
					// DSB Bank B.V.
					} else if (numberPos3 == 3) {
						result = "DSSB";
					// TEB Bank (The Economy Bank N.V.)
					} else if (numberPos3 == 4 && numberPos4 == 6) {
						result = "XXXX";
					// Dresdner Bank
					} else if (numberPos3 == 6 && numberPos4 == 4) {
						result = "XXXX";
					// IceSave Bank
					} else if (numberPos3 == 6 && numberPos4 == 5) {
						result = "XXXX";
					// ING
					} else if (numberPos3 == 9) {
						result = "INGB";
					}
				// SNS
				} else if (startingNumbers == 82 ||
						   startingNumbers == 85 ||
						  (startingNumbers >= 90 && startingNumbers <= 93) ||
						  (startingNumbers >= 95 && startingNumbers <= 96)) {
					result = "SNSB";
				// Position 3 is used for something when it comes to certain ranges
				} else if (startingNumbers == 88) {
					// SNS
					if (numberPos3 == 0) {
						result = "SNSB";
					} else {
						result = "XXXX";
					}
				} else {
					result = "XXXX";
				}
			}
		}
		
		if (result.equals("XXXX")) {
			System.out.println("Unknown accountnumber : " + accountNumber);
		}
		
		return result;
	}
	
	private static String getCountryCode() {
		return Locale.getDefault().getCountry();
	}
	
	/**
	 * Converts the given accountnumber to IBAN
	 * @param accountNumber
	 * @return String
	 * @throws Exception 
	 */
	public static String convertToIBAN(long accountNumber) {
		String result = "";
		result = ConvertToIBAN.getIBANBankCode(getNumberWithLeadingZeros(accountNumber, 10)) + getNumberWithLeadingZeros(accountNumber, 10) + ConvertToIBAN.getCountryCode();
		String identificationNumber = getIdentificationNumberForAccount(result, getNumberWithLeadingZeros(accountNumber, 10));
		result = ConvertToIBAN.getCountryCode() + identificationNumber + ConvertToIBAN.getIBANBankCode(getNumberWithLeadingZeros(accountNumber, 10)) + getNumberWithLeadingZeros(accountNumber, 10);
		return result;
	}
	
	/**
	 * Gets the identificationnumber for the given account
	 * @param account
	 * @return String
	 */
	private static String getIdentificationNumberForAccount(String account, String accountNumber) {
		int identificationNumber = 0;
		String numbers = "";
		for (int i = 0; i < 4; i++) {
			numbers += getRomanValueForCharacter(account.substring(i, (i + 1)));
		}
		numbers += accountNumber;
		// Now add the countrycode as numbers
		String countrycode = getCountryCode();
		for (int i = 0; i < countrycode.length(); i++) {
			numbers += getRomanValueForCharacter(countrycode.substring(i, (i + 1)));
		}
		numbers += "00";
		BigInteger number = new BigInteger(numbers).mod(new BigInteger("97"));
		identificationNumber = (98 - number.intValue());
		return getNumberWithLeadingZeros(new Long(identificationNumber), 2);
	}
	
	/**
	 * Gets the right int for the char
	 * @param character
	 * @return String
	 */
	private static String getRomanValueForCharacter(String character) {
		int value = Character.getNumericValue(character.charAt(0));
		return Integer.toString(value);
	}
	
	/**
	 * Add leading zeros to output
	 * @param input
	 * @param length
	 * @return String
	 */
	private static String getNumberWithLeadingZeros(long input, int length) {
		String result = String.format("%" + length + "s", Long.toString(input)).replace(' ', '0');
		return result;
	}

	public static void main(String[] args) {
		ElfProef elf = new ElfProef();
		List<Long> accountNumbers = new ArrayList<Long>();
		accountNumbers.add(new Long(333040945));
		accountNumbers.add(new Long(123456789));
		accountNumbers.add(new Long(4592855));
		accountNumbers.add(new Long(1018401));
		accountNumbers.add(new Long(342830058));
		accountNumbers.add(new Long(665075294));
		accountNumbers.add(new Long(231960));
		accountNumbers.add(new Long(7963522));
		accountNumbers.add(new Long(143661299));
		accountNumbers.add(new Long(595004083));
		accountNumbers.add(new Long(333029372));
		accountNumbers.add(new Long(985347621));
		
		for (Long accountNumber : accountNumbers) {
			boolean elfProefOk = elf.elfProef(Long.toString(accountNumber));
			String ibancode = ConvertToIBAN.convertToIBAN(accountNumber);
			IBAN iban = new IBAN(ibancode);
			if (!iban.isValid()) {
				System.out.println("IBAN: " + accountNumber + " - " + ibancode);
				System.err.println("Cause: " + iban.getInvalidCause());
			} else if (!elfProefOk && Long.toString(accountNumber).length() >= 9) {
				System.out.println("IBAN: " + accountNumber + " - " + ibancode);
				System.err.println("ElfProef fout");
			} else {
				System.out.println("IBAN: " + accountNumber + " - " + ibancode);
			}
		}
	}
}
