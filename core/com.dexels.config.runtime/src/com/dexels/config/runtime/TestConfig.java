package com.dexels.config.runtime;

public enum TestConfig {
	NAVAJO_TEST_USER,NAVAJO_TEST_PASS,NAVAJO_TEST_SERVER,KAFKA_DEVELOP,KAFKA_TEST;
	
    public String getValue() {
        return System.getenv(this.toString());
    }
}
