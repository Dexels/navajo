package com.dexels.navajo.expression.compiled.topology;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.admin.AdminClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.kafka.streams.remotejoin.ReplicationTopologyParser;
import com.dexels.kafka.streams.remotejoin.TopologyConstructor;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.ReactiveStandalone;
import com.dexels.navajo.reactive.TopologyReactiveFinder;
import com.dexels.navajo.reactive.api.CompiledReactiveScript;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.transformer.api.MessageTransformer;

public class TestReactiveTopology {
	
	@Before
	public void setup() {
		CoreReactiveFinder finder = new TopologyReactiveFinder();
		Reactive.setFinderInstance(finder);
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		
//		Reactive.finderInstance().addReactiveSourceFactory(new MongoReactiveSourceFactory(), "topic");

	}
	
	
	@Test
	public void testMoreStreamsWithPartials() throws ParseException, IOException {

		CompiledReactiveScript crs = ReactiveStandalone.compileReactiveScript(getClass().getResourceAsStream("morestreamswithpartials.rr"));
		Assert.assertEquals(1, crs.pipes.size());
		for (ReactivePipe pipe : crs.pipes) {
			System.err.println("source name: "+pipe.source.getClass().getName());
			AtomicInteger i = new AtomicInteger();
			pipe.transformers.forEach(e->{
				System.err.println("Transformer: "+e);
				if(e instanceof ReactiveTransformer) {
					ReactiveTransformer rt = (ReactiveTransformer)e;
					System.err.println("type: "+rt.metadata().name()+"\n named params:");
					rt.parameters().named.entrySet().forEach(entry->{
						System.err.println("param: "+entry.getKey()+" value: "+entry.getValue()+" type: "+entry.getValue().returnType());
					});
					System.err.println("|< end of named. unnamed:");
					rt.parameters().unnamed.forEach(elt->{
						System.err.println("E: "+elt+" type: "+elt.returnType());
					});
				} else {
					System.err.println("Non transformer: "+e);
				}
				i.incrementAndGet();
			});
			System.err.println("pipe: "+pipe);
			System.err.println(">>> "+i.get());
		}
	}
	



	
	@Test
	public void testSimpleTopic() throws ParseException, IOException {
		CompiledReactiveScript crs = ReactiveStandalone.compileReactiveScript(getClass().getResourceAsStream("simpletopic.rr"));
		Assert.assertEquals(1, crs.pipes.size());
		for (ReactivePipe pipe : crs.pipes) {
			System.err.println("source name: "+pipe.source.getClass().getName());
			pipe.transformers.forEach(e->{
				System.err.println("Transformer: "+e);
				if(e instanceof ReactiveTransformer) {
					ReactiveTransformer rt = (ReactiveTransformer)e;
					System.err.println("type: "+rt.metadata().name());
					if(!rt.parameters().named.isEmpty()) {
						System.err.println("named params:");
						rt.parameters().named.entrySet().forEach(entry->{
							System.err.println("param: "+entry.getKey()+" value: "+entry.getValue()+" type: "+entry.getValue().returnType());
						});
						System.err.println("|< end of named");
						
					}
					if(!rt.parameters().unnamed.isEmpty()) {
						rt.parameters().unnamed.forEach(elt->{
							System.err.println("E: "+elt+" type: "+elt.returnType());
						});
					}
				}
			});
			System.err.println("pipe: "+pipe);
		}
	}

	@Test
	public void testSimpleTopicParse() throws ParseException, IOException {
		CompiledReactiveScript crs = ReactiveStandalone.compileReactiveScript(getClass().getResourceAsStream("simpletopic.rr"));
		parse(crs);

	}
	private void parse(CompiledReactiveScript crs) {
		 Map<String,MessageTransformer> transformerMap = new HashMap<>();
		 Map<String,Object> config = new HashMap<>();
		 config.put("bootstrap.servers", "localhost:9092");
		 AdminClient adminClient = AdminClient.create(config);
	    TopologyConstructor topologyConstructor = new TopologyConstructor(transformerMap, adminClient);
	    int n = 0;
	    for (ReactivePipe pipe : crs.pipes) {
			parsePipe(pipe,topologyConstructor,n);
			n++;
		}
	}


	private void parsePipe(ReactivePipe pipe, TopologyConstructor topologyConstructor, int pipeNr) {
		ReactiveSource source = pipe.source;
		int transformerNumber = 0;
		pipe.transformers.forEach(e->{
			if(e instanceof TopologyTransformer) {
				TopologyTransformer rt = (TopologyTransformer)e;
				rt.addTransformerToTopology(topology, pipeStack)
				System.err.println(">> "+rt.parameters());
			} else {
				System.err.println("Need to recast this into sth else?");
			}
			transformerNumber++;
		});
	}
	
}
