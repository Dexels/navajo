package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * Title: Navajo Description: Copyright: Copyright (c) 2001 Company: Dexels
 * 
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class StringField extends FunctionInterface {

	public StringField() {
	}

	@Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
		Object a = this.getOperands().get( 0 );
		Object b = this.getOperands().get( 1 );
		Object c = this.getOperands().get( 2 );

		if ( ! ( a instanceof String ) || ! ( b instanceof String ) || ! ( c instanceof Integer ) )
			throw new TMLExpressionException( "StringField(): invalid operand. Usage: " + usage() );

		String text      = ( String ) a;
		String seperator = ( String ) b;

		int field = ( ( Integer ) c ).intValue();

		String[] tokens = text.split( seperator, - 1 );

		if ( field <= tokens.length ) {
			return ( tokens[field - 1] != null ) ? tokens[field - 1].trim() : null;
		}

		return null;
	}

	@Override
	public String remarks() {
		return "This function returns a specified string field given a seperator and an initial string. Eg. StringField('aap, noot, mies', ',', 2) = 'noot'.";
	}

	@Override
	public String usage() {
		return "StringField( text, delimiter, fieldIndex ) :: fieldIndex starts at 1";
	}

}
