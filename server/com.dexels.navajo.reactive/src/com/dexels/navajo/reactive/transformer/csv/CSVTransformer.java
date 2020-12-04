/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.transformer.csv;

import java.util.Optional;
import java.util.stream.Collectors;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class CSVTransformer implements ReactiveTransformer {

	private ReactiveParameters parameters;
	private final TransformerMetadata metadata;
	
	private FlowableTransformer<DataItem, DataItem> createTransformer(StreamScriptContext context, Optional<ImmutableMessage> current,ImmutableMessage param) {
		return flow -> flow.map(flowableCSV(context,current,param));
	}
	
	public CSVTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

//	private static Function<DataItem,DataItem>
	public Function<DataItem,DataItem> flowableCSV(StreamScriptContext context, Optional<ImmutableMessage> current,ImmutableMessage param) {
		ReactiveResolvedParameters staticResolved = parameters.resolveNamed(context, current,param, metadata);
		String delimiter = staticResolved.paramString("delimiter"); // (String) resolved.get("delimiter").value;						
		return msg->{
			ReactiveResolvedParameters resolved = parameters.resolve(context, Optional.of(msg.message()),msg.stateMessage(), metadata);
			int columnIndex = 0;
			for (Operand o : resolved.unnamedParameters()) {
				if(!o.type.equals("string")) {
					throw new ClassCastException("Column nr "+columnIndex+" is not of string type but: "+o.type);
				}
				columnIndex++;
			} 
			StringBuilder sb = new StringBuilder();
			sb.append(resolved.unnamedParameters().stream().map(e->(String)e.value).collect(Collectors.joining(delimiter)));
			sb.append("\n");
			return DataItem.of(sb.toString().getBytes());
		};
	}
	

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current,ImmutableMessage param) {
		return createTransformer(context,current,param);
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

	@Override
	public Optional<String> mimeType() {
		return Optional.of("text/csv");
	}

	@Override
	public ReactiveParameters parameters() {
		return parameters;
	}


}
