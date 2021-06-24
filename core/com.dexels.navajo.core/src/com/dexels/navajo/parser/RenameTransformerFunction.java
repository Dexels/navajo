/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.parser;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;

public class RenameTransformerFunction implements TransformerFunction {

	@Override
	public Function<ImmutableMessage, ImmutableMessage> create(List<Operand> parameters, List<String> problems) {
		int size = parameters.size();
		if(size!=2) {
			problems.add("Transformer: rename needs 2 parameters");
			return r->r;
		}
		String fromKey = (String) parameters.get(0).value;
		String to = (String) parameters.get(1).value;

		return in->{
			Optional<Object> oldValue = in.value(fromKey);
			if(oldValue.isPresent()) {
				String oldType = in.columnType(fromKey);
				return in.without(fromKey ).with(to,oldValue.get(), oldType);
			} else {
				return in;
			}
		};
	}

}
