/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.runtime.config;

public enum TestConfig {

    ELASTICSEARCH_INDEX,
    ELASTICSEARCH_SERVER,
    ELASTICSEARCH_TYPE,

    HTTP_TEST_BUCKET,
    HTTP_TEST_SECRET,
    HTTP_TEST_TOKEN,
    HTTP_TEST_URL,

    KAFKA_DEVELOP,
    KAFKA_TEST,

    MONGO_DEV_JUNIT,
    MONGO_PROD_AAA,
    MONGO_PROD_EXTERNALSTORE,
    MONGO_PROD_REPLICATE,
    MONGO_PROD_SPORTLINK,
    MONGO_TEST_AAA,
    MONGO_TEST_EXTERNALSTORE,
    MONGO_TEST_REPLICATE,
    MONGO_TEST_SPORTLINK,

    NAVAJO_TEST_LOCAL,
    NAVAJO_TEST_LOCALREACTIVE,
    NAVAJO_TEST_PASS,
    NAVAJO_TEST_REACTIVE,
	NAVAJO_TEST_SERVER,
    NAVAJO_TEST_USER,

	ORACLE_DUMMY_URL;
	
    public String getValue() {
        return System.getenv(this.toString());
    }

}
