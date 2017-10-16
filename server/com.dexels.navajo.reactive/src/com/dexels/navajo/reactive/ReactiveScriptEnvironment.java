package com.dexels.navajo.reactive;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveScript;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;
import com.dexels.navajo.server.NavajoConfigInterface;

import io.reactivex.Flowable;

public class ReactiveScriptEnvironment  implements EventHandler {

	private static final String REACTIVE_FOLDER = "reactive" + File.separator;
	private ReactiveScriptParser scriptParser = null;
	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveScriptEnvironment.class);

	private final Map<String,ReactiveScript> scripts = new HashMap<>();


	private NavajoConfigInterface navajoConfig;

	private final File testRoot;
	
	public ReactiveScriptEnvironment() {
		this.testRoot = null;
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
	public void setReactiveScriptParser(ReactiveScriptParser reactiveScriptParser) {
		this.scriptParser = reactiveScriptParser;
	}
	
	public void removeReactiveScriptParser(ReactiveScriptParser reactiveScriptParser) {
		this.scriptParser = null;
	}
	
	

	public boolean isReactiveScript(String service) {
		return resolveFile(service).exists();
	}

	public Flowable<NavajoStreamEvent> run(StreamScriptContext context) throws IOException {
		ReactiveScript rs = scripts.get(context.service);
		if(rs!=null) {
			return rs.execute(context);
		}
		File sf = resolveFile(context.service);
		
		try(InputStream is = new FileInputStream(sf)) {
			rs = installScript(context.service, is);
		}
		if(rs==null) {
			return Flowable.error(new RuntimeException("Can't seem to find script: "+context.service));
		}
		return rs.execute(context);
	}

	private File resolveFile(String serviceName) {
		File root = testRoot!=null?testRoot:new File( navajoConfig.getRootPath());
		File f = new File(root,"reactive");
		return new File(f,serviceName+".xml");
	}
	
	private ReactiveScript installScript(String serviceName, InputStream in) throws IOException {
		ReactiveScript parsed = scriptParser.parse(serviceName, in);
		scripts.put(serviceName, parsed);
		return parsed;
	}
	
	@Override
	public void handleEvent(Event e) {
		List<String> changed = RepositoryEventParser.filterChanged(e, REACTIVE_FOLDER);
		System.err.println("Changed: "+changed);
		for (String path : changed) {
			if(path.startsWith(REACTIVE_FOLDER) && path.endsWith(".xml")) {
				System.err.println("Match!");
				String actual = path.substring(REACTIVE_FOLDER.length(), path.length() - ".xml".length());
				System.err.println("Actual: "+actual);
				ReactiveScript rr =  scripts.get(actual);
				if(rr!=null) {
					logger.info("Flushing changed script: {}",actual);
					scripts.remove(actual);
				}
				
			}
		}
	}

}
