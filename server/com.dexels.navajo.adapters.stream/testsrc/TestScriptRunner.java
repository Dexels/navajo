import com.dexels.navajo.document.Header;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;
import com.dexels.navajo.listeners.stream.core.ScriptRunner;

public class TestScriptRunner extends ScriptRunner {

	@Override
	protected BaseScriptInstance resolveScript(Header h) throws Exception {
		BaseScriptInstance instance = (BaseScriptInstance) Class.forName(h.getRPCName()).newInstance();
		instance.init();
		return instance;
	}

}
