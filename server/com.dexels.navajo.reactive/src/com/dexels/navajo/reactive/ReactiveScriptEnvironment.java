package com.dexels.navajo.reactive;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.api.ReactiveScriptRunner;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.parser.compiled.ASTReactiveScriptNode;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.TokenMgrError;
import com.dexels.navajo.parser.compiled.api.ReactivePipeNode;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;
import com.dexels.navajo.server.NavajoConfigInterface;

import io.reactivex.Flowable;

public class ReactiveScriptEnvironment  implements EventHandler, ReactiveScriptRunner {

	private static final String REACTIVE_FOLDER = "reactive" + File.separator;
	private final static Logger logger = LoggerFactory.getLogger(ReactiveScriptEnvironment.class);

	private final Map<String,ReactiveScript> scripts = new HashMap<>();


	private NavajoConfigInterface navajoConfig;

	private ReactiveScriptRunner parentRunnerEnvironment;
	private final File testRoot;

	public ReactiveScriptEnvironment() {
		this.testRoot = null;
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
	}

	@Override
	public Optional<String> deployment() {
		return Optional.ofNullable(navajoConfig).map(e->e.getDeployment());
	}

	public ReactiveScriptEnvironment(File testRoot) {
		this.testRoot = testRoot;
	}


	public void setNavajoConfig(NavajoConfigInterface navajoConfig) {
		this.navajoConfig = navajoConfig;
	}

	public void clearNavajoConfig(NavajoConfigInterface navajoConfig) {
		this.navajoConfig = null;
	}

    public void setReactiveScriptEnvironment(ReactiveScriptRunner env) {
		this.parentRunnerEnvironment = env;
	}

	public void clearReactiveScriptEnvironment(ReactiveScriptRunner env) {
		this.parentRunnerEnvironment = null;
	}

	public boolean acceptsScript(String service) {
		ReactiveScript rs = scripts.get(service);
		if(rs!=null) {
			return true;
		}
		// add negative cache if necessary?
		return resolveFile(service).isPresent();
	}


	@Override
	public ReactiveScript build(String service, boolean debug) throws IOException {
		// Do this check first, so we can 'override' scripts for testing
		ReactiveScript rs = scripts.get(service);
		if(rs!=null) {
			return rs;
		}
		if(!acceptsScript(service)) {
			if(parentRunnerEnvironment==null) {
				throw new NullPointerException("This environment does not accept script: "+service+", and there is no parent.");
			}
			return parentRunnerEnvironment.build(service,debug);
		}
		Optional<InputStream> is = resolveFile(service);
		try {
			return ReactiveStandalone.compileReactiveScript(is.get());
		} catch (ParseException|TokenMgrError e) {
			throw new IOException("Error parsing script: "+service, e);
		}
	}

	protected Optional<InputStream> resolveFile(String serviceName) {
		FileInputStream inputStream;
		try {
			File root = testRoot!=null?testRoot:new File( navajoConfig.getRootPath());
			File f = new File(root,"reactive");
			if(!f.exists()) {
				return Optional.empty();
			}
			inputStream = new FileInputStream(new File(f,serviceName+".rr"));
			return Optional.of(inputStream);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	ReactiveScript installScript(String serviceName, InputStream in, String relativeScriptPath) throws IOException {
		// TODO not pretty:
//		Reactive.setFinderInstance(this.reactiveFinder);
		CompiledParser cp = new CompiledParser(in);
		try {
			cp.ReactiveScript();
		} catch (ParseException e) {
			throw new IOException("Error parsing script: "+serviceName,e);
		}
		List<String> problems = new ArrayList<>();
		ASTReactiveScriptNode scriptNode = (ASTReactiveScriptNode) cp.getJJTree().rootNode();
		ContextExpression src = (ContextExpression) scriptNode.interpretToLambda(problems,"",Reactive.finderInstance().functionClassifier(),name->Optional.empty());

		List<ReactivePipeNode> pipes = (List<ReactivePipeNode>) src.apply().value;
		final boolean streamInput = pipes.stream().anyMatch(e->e.isStreamInput());

		List<ReactivePipe> resolvedPipes = pipes.stream().map(node->(ReactivePipe)node.apply().value).collect(Collectors.toList());
		Type type = resolvedPipes.stream().findFirst().map(e->e.finalType()).orElse(Type.ANY);

		return new ReactiveScript() {

			@Override
			public boolean streamInput() {
				return streamInput;
			}

			@Override
			public List<ReactiveParseProblem> problems() {
				return problems.stream().map(ReactiveParseProblem::of).collect(Collectors.toList());
			}

			@Override
			public Flowable<Flowable<DataItem>> execute(StreamScriptContext context) {
				return Flowable.fromIterable(resolvedPipes).map(pipe->pipe.execute(context, Optional.empty(), ImmutableFactory.empty()));
			}

			@Override
			public Type dataType() {
				return type;
			}

			@Override
			public Optional<String> binaryMimeType() {
				// TODO implement again
				return Optional.empty();
			}

			@Override
			public List<String> methods() {
				return scriptNode.methods();
			}
		};
	}

	@Override
	public void handleEvent(Event e) {
		List<String> changed = RepositoryEventParser.filterChanged(e, REACTIVE_FOLDER);
		for (String path : changed) {
			if(path.startsWith(REACTIVE_FOLDER) && path.endsWith(".xml")) {
				String actual = path.substring(REACTIVE_FOLDER.length(), path.length() - ".xml".length());
				ReactiveScript rr =  scripts.get(actual);
				if(rr!=null) {
					logger.info("Flushing changed script: {}",actual);
					scripts.remove(actual);
				}

			}
		}
	}

	@Override
	public Optional<InputStream> sourceForService(String service) {
		return this.resolveFile(service);
	}

	@Override
	public ReactiveScript compiledScript(String service) throws IOException {
		return build(service, false);
//		return ReactiveStandalone.compileReactiveScript(source, binaryMime);
	}

}
