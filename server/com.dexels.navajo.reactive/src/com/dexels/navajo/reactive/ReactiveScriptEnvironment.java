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
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.api.ReactivePipeNode;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactiveFinder;
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;
import com.dexels.navajo.server.NavajoConfigInterface;

import io.reactivex.Flowable;

public class ReactiveScriptEnvironment  implements EventHandler, ReactiveScriptRunner {

	private static final String REACTIVE_FOLDER = "reactive" + File.separator;
//	private ReactiveScriptParser scriptParser = null;
	private ReactiveFinder reactiveFinder;
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
	public String deployment() {
		return navajoConfig.getDeployment();
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
	public void setReactiveFinder(ReactiveFinder reactiveFinder) {
		this.reactiveFinder = reactiveFinder;
	}
	
	public void clearReactiveFinder(ReactiveFinder reactiveFinder) {
		this.reactiveFinder = null;
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
		return resolveFile(service).exists();
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
		File sf = resolveFile(service);
		
		try(InputStream is = new FileInputStream(sf)) {
			rs = installScript(service, is,service+".xml");
//		} catch (IOException ioe) {
//			return Flowable.error(new RuntimeException("Can't seem to find script: "+context.service));
		}
//		if(rs==null) {
//			return Flowable.error(new RuntimeException("Can't seem to find script: "+context.service));
//		}
		return rs;
	}

	private File resolveFile(String serviceName) {
		File root = testRoot!=null?testRoot:new File( navajoConfig.getRootPath());
		File f = new File(root,"reactive");
		return new File(f,serviceName+".rr");
	}
	
	ReactiveScript installScript(String serviceName, InputStream in, String relativeScriptPath) throws IOException {
		// TODO not pretty:
		Reactive.setFinderInstance(this.reactiveFinder);
		CompiledParser cp = new CompiledParser(in);
		try {
			cp.ReactiveScript();
		} catch (ParseException e) {
			throw new IOException("Error parsing script: "+serviceName,e);
		}
		List<String> problems = new ArrayList<>();
		ReactivePipeNode src = (ReactivePipeNode) cp.getJJTree().rootNode().interpretToLambda(problems,"",Reactive.finderInstance().functionClassifier());
		ReactivePipe pipe = (ReactivePipe) src.apply().value;
//		return new Reac
		Type type = pipe.finalType();
		
		return new ReactiveScript() {
			
			@Override
			public boolean streamInput() {
				return false;
			}
			
			@Override
			public List<ReactiveParseProblem> problems() {
				return problems.stream().map(ReactiveParseProblem::of).collect(Collectors.toList());
			}
			
			@Override
			public Flowable<DataItem> execute(StreamScriptContext context) {
				return pipe.execute(context, Optional.empty(), ImmutableFactory.empty());
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

}
