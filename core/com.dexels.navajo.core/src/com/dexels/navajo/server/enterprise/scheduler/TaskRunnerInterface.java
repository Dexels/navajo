package com.dexels.navajo.server.enterprise.scheduler;

import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public interface TaskRunnerInterface {

	public boolean addTask(TaskInterface t);
	public void removeTask(TaskInterface t);
	public Map<String,TaskInterface> getTasks();
	public TaskInterface createTask();
	public void fireAfterTaskEvent(TaskInterface myTask, Navajo response);
	public void addTaskListener(TaskListener taskListener);
	public boolean addTask(String id, TaskInterface myTask, boolean overwrite);
	public void removeTaskListener(TaskListener transition);
	public TaskInterface removeTask(String id, boolean removePersistedTask, String tenant);
	public boolean fireBeforeTaskEvent(TaskInterface task, Navajo request);
	public void removeTaskInput(TaskInterface task);
	public void writeTaskOutput(TaskInterface task, boolean singleEvent, long currentTimeMillis);
	public List<TaskInterface> getFinishedTasks(String username, String fromDate);
	public Navajo getTaskOutput(String id, String tenant);
    public void setNavajoIOConfig(NavajoIOConfig navajoIOConfig);
	public void worker();
	public int getTaskListSize();
	public void kill();
	public void setSharedStoreInterface(SharedStoreInterface ssi);
	public void clearSharedStoreInterface(SharedStoreInterface ssi);
	
}
