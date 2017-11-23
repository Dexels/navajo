package com.dexels.config.runtime;

public enum TestConfig {
	NAVAJO_TEST_USER,
	NAVAJO_TEST_PASS,
	NAVAJO_TEST_SERVER,
	KAFKA_DEVELOP,
	KAFKA_TEST,
	ORACLE_DUMMY_URL,
	MONGO_DEV_JUNIT,
	MONGO_TEST_AAA,
	MONGO_TEST_EXTERNALSTORE,
	MONGO_TEST_REPLICATE,
	MONGO_TEST_SPORTLINK,
	MONGO_PROD_AAA,
	MONGO_PROD_EXTERNALSTORE,
	MONGO_PROD_REPLICATE,
	MONGO_PROD_SPORTLINK;
	
	
	
    public String getValue() {
        return System.getenv(this.toString());
    }
}
