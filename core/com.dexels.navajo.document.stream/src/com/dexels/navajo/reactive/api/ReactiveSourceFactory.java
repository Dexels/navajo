/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.api;

public interface ReactiveSourceFactory extends SourceMetadata {
//	public ReactiveSource build(String relativePath, String type, List<ReactiveParseProblem> problems, Optional<XMLElement> x, ReactiveParameters params, List<ReactiveTransformer> transformers, 
//			DataItem.Type finalType,Function<String, ReactiveMerger> reducerSupplier);
//
//	default public ReactiveSource build(String type, ReactiveParameters params, List<ReactiveParseProblem> problems, List<ReactiveTransformer> transformers, DataItem.Type finalType) {
//		return build("",type,problems, Optional.empty(),params,transformers,finalType,ReactiveSource.emptyReducerSupplier);
//	}

	public ReactiveSource build(ReactiveParameters parameters);
//	public ReactiveSource build(Map<String, ContextExpression> namedParams, List<ContextExpression> unnamedParams);

//	ReactiveSource rs = krsf.build("", "kafka",Optional.empty(), ReactiveParameters.empty(), Collections.emptyList(), DataItem.Type.MESSAGE, ReactiveSource.emptyReducerSupplier, ReactiveSource.emptyMapperSupplier);

}
