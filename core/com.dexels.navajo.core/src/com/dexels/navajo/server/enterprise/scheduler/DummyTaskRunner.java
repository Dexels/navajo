package com.dexels.navajo.server.enterprise.scheduler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public class DummyTaskRunner implements TaskRunnerInterface {

		
		private static final Logger logger = LoggerFactory
				.getLogger(DummyTaskRunner.class);
		
	
	@Override
	public boolean addTask(TaskInterface t) {
		logger.warn("WARNING: Trying to schedule task. This is not supported in the standard version of Navajo");
		return false;
	}

	@Override
	public Map getTasks() {
		return null;
	}

	@Override
	public void removeTask(TaskInterface t) {
		
	}

	@Override
	public TaskInterface createTask() {
		return new DummyTask();
	}

	@Override
	public void fireAfterTaskEvent(TaskInterface myTask, Navajo response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTaskListener(TaskListener taskListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addTask(String id, TaskInterface myTask, boolean overwrite) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTaskListener(TaskListener transition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TaskInterface removeTask(String id, boolean removePersistedTask, String tenant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean fireBeforeTaskEvent(TaskInterface task, Navajo request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTaskInput(TaskInterface task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeTaskOutput(TaskInterface task, boolean singleEvent, long currentTimeMillis) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TaskInterface> getFinishedTasks(String username, String fromDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Navajo getTaskOutput(String id, String tenant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void worker() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTaskListSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSharedStoreInterface(SharedStoreInterface ssi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearSharedStoreInterface(SharedStoreInterface ssi) {
		// TODO Auto-generated method stub
		
	}

}
