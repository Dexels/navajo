package com.dexels.navajo.reactive.transformer.filestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.ReactiveBuildContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

public class FileStoreTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	public FileStoreTransformerFactory() {} 

	@Override
	public ReactiveTransformer build(Type parentType, String relativePath, List<ReactiveParseProblem> problems, ReactiveParameters parameters, Optional<XMLElement> xml, 
			ReactiveBuildContext buildContext) {

		return new FileStoreTransformer(this, parameters,xml,relativePath);
	}
	

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.DATA})) ;
	}

	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"path"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"path"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("path", Property.STRING_PROPERTY);
		return Optional.of(r);
	}

	@Override
	public String name() {
		return "filestore";
	}

}
