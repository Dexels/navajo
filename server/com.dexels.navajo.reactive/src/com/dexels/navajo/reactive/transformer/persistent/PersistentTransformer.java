package com.dexels.navajo.reactive.transformer.persistent;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.RunningReactiveScripts;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.reactive.ReactiveBuildContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PersistentTransformer implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;
	private ReactiveSource reactiveSource;


	public PersistentTransformer(PersistentTransformerFactory metadata,
			String relativePath, List<ReactiveParseProblem> problems, Optional<XMLElement> xmlElement,
			ReactiveParameters parameters,
			ReactiveBuildContext buildContext
			, Binary xmlCode) {
		this.parameters = parameters;
		this.metadata = metadata;
		
		ReactiveScriptParser.parseTransformationsFromChildren("", problems, xmlElement,buildContext);
		
		XMLElement xe = new CaseSensitiveXMLElement();
		try {
			xe.parseFromStream(xmlCode.getDataAsStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.reactiveSource = ReactiveScriptParser.findSubSource(relativePath, xe, problems, buildContext).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		StreamScriptContext cp = context.copyWithNewUUID();
		RunningReactiveScripts rrs = context.runningScripts().get();
		
		return e->{
			Disposable sub = e.map(d->d.message())
				.subscribeOn(Schedulers.io())
//				.observeOn(Schedulers.io())
				.doOnComplete(()->rrs.complete(cp.uuid()))
				.subscribe(c->{
					storeMessage(c);
				}
			);
//			StreamScriptContext withd = cp.withDispose(()->sub.dispose());
			rrs.submit(cp.withDispose(()->sub.dispose()));
			
			return this.reactiveSource.execute(context, Optional.empty());
//			return Flowable.just(createOutputMessage(context))
//					.map(DataItem::of);
		};
		
//		return e->e.filter(item->{
//			ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.of(item.message()), item.stateMessage(), metadata, Optional.empty(), "");
//			return parms.paramBoolean("filter");
//		});
	}

	private void storeMessage(ImmutableMessage c) {
		//
	}

	private ImmutableMessage createOutputMessage(StreamScriptContext context) {
		return ImmutableFactory.empty()
		.with("service", context.service, Property.STRING_PROPERTY)
		.with("uuid", context.uuid(), Property.STRING_PROPERTY)
		.with("username", context.username.orElse(""), Property.STRING_PROPERTY);
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}


}
