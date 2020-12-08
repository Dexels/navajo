/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive;

import java.util.Collections;
import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.api.ReactiveScriptRunner;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.api.ReactiveFinder;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.source.test.EventStreamSourceFactory;
import com.dexels.navajo.reactive.stored.InputStreamSourceFactory;
import com.dexels.navajo.reactive.transformer.call.CallTransformerFactory;
import com.dexels.navajo.reactive.transformer.csv.CSVTransformerFactory;
import com.dexels.navajo.reactive.transformer.filestore.FileStoreTransformerFactory;
import com.dexels.navajo.reactive.transformer.mergesingle.MergeSingleTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FlattenEventStreamFactory;
import com.dexels.navajo.reactive.transformer.other.IntervalTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.SkipTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.TakeTransformerFactory;
import com.dexels.navajo.reactive.transformer.parseevents.ParseEventStreamFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

public class TestSetup {
	
	private TestSetup() {} // no instances

	public static ReactiveFinder setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		ReactiveFinder finder = new CoreReactiveFinder();
		Expression.compileExpressions = true;
		finder.addReactiveSourceFactory(new SQLReactiveSourceFactory(),"sql");
		finder.addReactiveSourceFactory(new SingleSourceFactory(),"single");
		finder.addReactiveSourceFactory(new InputStreamSourceFactory(),"inputstream");
		finder.addReactiveSourceFactory(new EventStreamSourceFactory(),"eventstream");
		finder.addReactiveTransformerFactory(new CSVTransformerFactory(),"csv");
		finder.addReactiveTransformerFactory(new FileStoreTransformerFactory(),"filestore");
		finder.addReactiveTransformerFactory(new MergeSingleTransformerFactory(),"mergeSingle");
		finder.addReactiveTransformerFactory(new CallTransformerFactory(),"call");
		finder.addReactiveTransformerFactory(new StreamMessageTransformerFactory(),"stream");
		finder.addReactiveTransformerFactory(new ReduceTransformerFactory(),"reduce");
		finder.addReactiveTransformerFactory(new FilterTransformerFactory(),"filter");
		finder.addReactiveTransformerFactory(new TakeTransformerFactory(),"take");
		finder.addReactiveTransformerFactory(new SkipTransformerFactory(),"skip");
		finder.addReactiveTransformerFactory(new ParseEventStreamFactory(),"streamtoimmutable");
		finder.addReactiveTransformerFactory(new FlattenEventStreamFactory(),"flatten");
		finder.addReactiveTransformerFactory(new IntervalTransformerFactory(),"interval");
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		return finder;
	}
	
	public static StreamScriptContext createContext(String serviceName, Optional<ReactiveScriptRunner> runner) {
		return new StreamScriptContext("tenant", serviceName
				, Optional.of("username")
				, Optional.of("password")
				, NavajoFactory.getInstance().createNavajo()
				, Collections.emptyMap()
				, Optional.empty()
				, runner,Collections.emptyList(), Optional.empty(),Optional.empty());
	}
	
}
