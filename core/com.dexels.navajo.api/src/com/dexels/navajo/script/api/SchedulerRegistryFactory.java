package com.dexels.navajo.script.api;

public class SchedulerRegistryFactory {
    private static SchedulerRegistry instance;

	private static void setInstance(SchedulerRegistry sched) {
    	instance = sched;
    }
}
