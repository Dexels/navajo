package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class ValidateAccountNumber extends FunctionInterface {
    private static final String ACCOUNTTYPE_BANK = "BANK";
    private static final String ACCOUNTTYPE_GIRO = "GIRO";
    private static final String ACCOUNTTYPE_IBAN = "IBAN";
    private static final String ACCOUNTTYPE_SAVINGS = "SAVINGS";

    public ValidateAccountNumber() {}
    
    public String remarks() {
        return "Check if the supplied account number is a valid account for the given type.\nUses the functions ElfProef and CheckIBAN.";
    }

    public String usage() {
        return "ValidateAccountNumber(String|Integer, String)";
    }

    @Override
    public Object evaluate() throws TMLExpressionException {
        Object o1 = this.getOperands().get(0);
        Object o2 = null;
        String accounttype = null;
        try {
            o2 = this.getOperands().get(1);
        } catch (Exception e) {
            throw new TMLExpressionException("Wrong number of arguments for function ValidateAccountNumber()");
        }
        
        if (o2 instanceof String) {
            String accounttype_param = (String)o2;
            if (accounttype_param.equalsIgnoreCase("B") || accounttype_param.equalsIgnoreCase("BANK")) {
                accounttype = ACCOUNTTYPE_BANK;
            } else if (accounttype_param.equalsIgnoreCase("G") || accounttype_param.equalsIgnoreCase("GIRO")) {
                accounttype = ACCOUNTTYPE_GIRO;
            } else if (accounttype_param.equalsIgnoreCase("I") || accounttype_param.equalsIgnoreCase("IBAN")) {
                accounttype = ACCOUNTTYPE_IBAN;
            } else if (accounttype_param.equalsIgnoreCase("S") || accounttype_param.equalsIgnoreCase("SPAAR")) {
                accounttype = ACCOUNTTYPE_SAVINGS;
            } else {
                if (o1 != null) {
                    throw new TMLExpressionException("Unknown accounttype for function ValidateAccountNumber(): " + accounttype_param);
                } else {
                    // Feel free to implement something here
                    return Boolean.TRUE;
                }
            }
        } else {
            throw new TMLExpressionException("Illegal argument (2) type for function ValidateAccountNumber(): " + o2.getClass().getName());
        }
        
        if (accounttype.equals(ACCOUNTTYPE_BANK)) {
            ElfProef e = new ElfProef();
            if (o1 instanceof String) {
                return new Boolean(e.elfProef((String) o1));
            } else if (o1 instanceof Integer) {
                return new Boolean(e.elfProef(((Integer) o1).intValue() + ""));
            }
        
        } else if (accounttype.equals(ACCOUNTTYPE_GIRO)) {
            if (((String)o1).length() <= 7) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }

        } else if (accounttype.equals(ACCOUNTTYPE_IBAN)) {
            CheckIBAN c = new CheckIBAN();
            c.reset();
            c.insertOperand(o1);
            return c.evaluate();

        } else if (accounttype.equals(ACCOUNTTYPE_SAVINGS)) {
            String account = null;
            if (o1 instanceof String) {
                account = (String)o1;
            } else if (o1 instanceof Integer) {
                account = ((Integer)o1).toString();
            }
            if (account.length() != 10) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    public static void main(String [] args) {

        String n = "123456789";
        int number = 123456789;
        String type = "B";
        ValidateAccountNumber e = new ValidateAccountNumber();
        e.reset();
        e.insertOperand(number);
        e.insertOperand(type);
        try {
            System.out.println(e.evaluate());
        } catch (TMLExpressionException e1) {
            e1.printStackTrace();
        }
      }
}
