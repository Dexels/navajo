package com.dexels.navajo.document;

import java.util.Date;
import java.util.List;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.DatePattern;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.document.types.TypeUtils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Operand {

  public final String type;
  public final String option;
  public final Object value;

  /**
   * Store a new Operand.
   * An operand is an internal Navajo representation of a value object.
   * Value contains the Java representation.
   * Type describes the Navajo type of the object.
   *
   * @param value
   * @param type
   * @param option
   */
  public Operand(Object value, String type, String option) {
	  if(value instanceof Operand) {
		  throw new RuntimeException("Should not embed Operands in other Operands");
	  }
      this.value = value;
      this.type = type;
      this.option = option;
  }

  public Operand(Object value, String type) {
	  this(value,type,null);
  }

  public static final Operand FALSE = new Operand(false,Property.BOOLEAN_PROPERTY);
  public static final Operand TRUE = new Operand(true,Property.BOOLEAN_PROPERTY);
  public static final Operand NULL = new Operand(null,"object");
  public static Operand ofBoolean(boolean b) {
	  return b ? TRUE : FALSE;
  }
 
  public static Operand ofString(String string) {
	  return new Operand(string,Property.STRING_PROPERTY);
  }

  public static Operand ofInteger(Integer value) {
	  return new Operand(value,Property.INTEGER_PROPERTY);
  }

  public static Operand ofFloat(Double value) {
	  return new Operand(value,Property.FLOAT_PROPERTY);
  }

public static Operand ofDatePattern(DatePattern value) {
	return new Operand(value,Property.DATE_PATTERN_PROPERTY);
}

public static Operand ofDynamic(Object o) {
	if(o==null) {
		return NULL;
	}
	return new Operand(o,TypeUtils.determineNavajoType(o));
}

public static Operand ofList(List<? extends Object> result) {
	return new Operand(result,Property.LIST_PROPERTY);
}

public static Operand ofLong(long longValue) {
	return new Operand(longValue,Property.LONG_PROPERTY);
}

public static Operand nullOperand(String type) {
	return new Operand(null,type);
}

public static Operand ofCustom(Object value, String type) {
	return new Operand(value,type);
}

public static Operand ofDate(Date value) {
	return new Operand(value,Property.DATE_PROPERTY);
}

public static Operand ofMoney(Money value) {
	return new Operand(value,Property.MONEY_PROPERTY);
}

public static Operand ofPercentage(Percentage value) {
	return  new Operand(value,Property.PERCENTAGE_PROPERTY);
}

public boolean booleanValue() {
	if(value instanceof Boolean) {
		return (boolean) value;
	}
	throw new ClassCastException("Operand does not have the required boolean type but: "+type+" value: "+value);
}

public String stringValue() {
	if(value instanceof String) {
		return (String) value;
	}
	throw new ClassCastException("Operand does not have the required string type but: "+type);
}

public int integerValue() {
	if(value instanceof Integer) {
		return (Integer) value;
	}
	throw new ClassCastException("Operand does not have the required integer type but: "+type);
}

public ImmutableMessage immutableMessageValue() {
	if(value instanceof ImmutableMessage) {
		return (ImmutableMessage) value;
	}
	throw new ClassCastException("Operand does not have the required immutablemessage type but: "+type);
}


public static Operand ofBinary(Binary o) {
	return new Operand(o,Property.BINARY_PROPERTY);
}

public static Operand ofStopwatchTime(StopwatchTime stopwatchTime) {
	return new Operand(stopwatchTime,Property.STOPWATCHTIME_PROPERTY);
}

public static Operand ofClockTime(ClockTime clockTime) {
	return new Operand(clockTime,Property.CLOCKTIME_PROPERTY);
}

public static Operand ofMessage(Message message) {
	return new Operand(message,"message");
}

public static Operand ofNavajo(Navajo createTestNavajo) {
	return new Operand(createTestNavajo,"navajo");
}

public static Operand ofSelectionList(List<Selection> allSelectedSelections) {
	return new Operand(allSelectedSelections,Property.SELECTION_PROPERTY);
}

public static Operand ofProperty(Property property) {
	return new Operand(property,"property");
}

public static Operand ofImmutable(ImmutableMessage rm) {
	return new Operand(rm,"immutable");
}


}
