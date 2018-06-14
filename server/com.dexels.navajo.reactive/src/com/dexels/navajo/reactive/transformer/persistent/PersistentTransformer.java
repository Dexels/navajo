package com.dexels.navajo.reactive.transformer.persistent;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.reactivestreams.Subscriber;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.RunningReactiveScripts;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.reactive.ReactiveBuildContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.pubsub.rx2.api.PubSubMessage;
import com.dexels.pubsub.rx2.api.TopicPublisher;
import com.dexels.pubsub.rx2.factory.PubSubTools;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.FlowableTransformer;
import io.reactivex.subscribers.DisposableSubscriber;

public class PersistentTransformer implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final PersistentTransformerFactory metadata;
	private ReactiveSource reactiveSource;
	private final TopicPublisher topicPublisher;
	private final Optional<XMLElement> xml;
	private final String relativePath;

	public PersistentTransformer(PersistentTransformerFactory metadata,
			String relativePath, List<ReactiveParseProblem> problems, Optional<XMLElement> xmlElement,
			ReactiveParameters parameters,
			ReactiveBuildContext buildContext
			, TopicPublisher topicPublisher,Binary xmlCode) {
		this.parameters = parameters;
		this.metadata = metadata;
		this.topicPublisher = topicPublisher;
		this.xml = xmlElement;
		this.relativePath = relativePath;
		
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
			
			DisposableSubscriber<PubSubMessage> subscriber = createPublishEndPoint("BLE");
			e.doOnComplete(()->rrs.complete(cp.uuid()))
				.map(item->{
					ReactiveResolvedParameters resParams = this.parameters.resolveNamed(context, Optional.of(item.message()), item.stateMessage(), this.metadata, xml, relativePath);
					String topic = resParams.paramString("topic");
					String key = resParams.paramString("key");
					return new PersistentMessage(topic,key,item.message()).toPubSub();
				}).subscribe(subscriber);

			return this.reactiveSource.execute(context, Optional.empty());
		};
	}

	public DisposableSubscriber<PubSubMessage> createPublishEndPoint(String topic) {
		Subscriber<PubSubMessage> subscriber = this.topicPublisher.backpressurePublisher(Optional.of(topic), 500);
		this.topicPublisher.create(topic);
		return (DisposableSubscriber<PubSubMessage>) subscriber;
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
	
	private class PersistentMessage {
		public final String topic;
		public final String key;
		public final ImmutableMessage message;
		
		public PersistentMessage(String topic, String key, ImmutableMessage message) {
			this.topic = topic;
			this.key = key;
			this.message = message;
		}
		
		public PubSubMessage toPubSub() {
			ReplicationMessage rmsg = ReplicationFactory.empty().withImmutableMessage(message);
			byte[] value = rmsg.toBytes(ReplicationFactory.getInstance());
			return PubSubTools.create(this.key, value, System.currentTimeMillis(), Optional.of(this.topic));
		}
		
	}


}
