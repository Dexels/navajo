package workflow.test;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.workflow.State;
import com.dexels.navajo.workflow.Transition;
import com.dexels.navajo.workflow.Workflow;
import com.dexels.navajo.workflow.WorkflowFactory;

import junit.framework.TestCase;

public class TestEmfModel extends TestCase {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testEmfModel() {
		Workflow w = WorkflowFactory.eINSTANCE.createWorkflow();
		State s1 = WorkflowFactory.eINSTANCE.createState();
		w.getStates().add(s1);
		State s2 = WorkflowFactory.eINSTANCE.createState();
		w.getStates().add(s2);
		Transition t = WorkflowFactory.eINSTANCE.createTransition();
		t.setFrom(s1);
		t.setTo(s2);
	}
}
