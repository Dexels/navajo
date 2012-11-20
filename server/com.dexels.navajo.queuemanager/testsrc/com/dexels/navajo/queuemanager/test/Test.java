package com.dexels.navajo.queuemanager.test;

import java.io.FileReader;
import java.io.IOException;

import javax.script.ScriptException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.listener.http.queuemanager.api.InputContext;
import com.dexels.navajo.listener.http.queuemanager.api.NavajoSchedulingException;
import com.dexels.navajo.listener.http.queuemanager.api.QueueManager;
import com.dexels.navajo.listener.http.queuemanager.api.QueueManagerFactory;

public class Test {

	/**
	 * @param args
	 * @throws ScriptException 
	 * @throws IOException 
	 * @throws NavajoSchedulingException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws NavajoSchedulingException, IOException, InterruptedException {
		QueueManager qm = QueueManagerFactory.getInstance();
		qm.setQueueContext(new TestQueueContext());
		String result = qm.resolve(getInputContext(), "testsrc/chooseQueue.js");
		System.err.println("result: "+result);
		result = qm.resolve(getInputContext(), "testsrc/chooseQueue.js");
		Thread.sleep(1100);
		result = qm.resolve(getInputContext(), "testsrc/chooseQueue.js");
		Thread.sleep(1100);
		result = qm.resolve(getInputContext(), "testsrc/chooseQueue.js");
		Thread.sleep(1100);
		result = qm.resolve(getInputContext(), "testsrc/chooseQueue.js");
	}


	@SuppressWarnings("unused")
	private static InputContext getInputContext() throws IOException {
        FileReader fr = new FileReader("testsrc/testinput.tml");
		Navajo n = NavajoFactory.getInstance().createNavajo(fr);
		fr.close();
		return null;
//		return new NavajoInputContext(n,null);
	}

	
}
