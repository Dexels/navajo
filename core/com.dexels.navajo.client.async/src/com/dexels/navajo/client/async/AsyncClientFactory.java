package com.dexels.navajo.client.async;

public class AsyncClientFactory {

    private static Class<ManualAsyncClient> asyncClientClass = null;

    private AsyncClientFactory() {
        // no instantiation
    }

    public static ManualAsyncClient getManualInstance() {
        synchronized (AsyncClientFactory.class) {
            try {
                return asyncClientClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {

            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static void setInstance(Class<?> aClazz) {
        asyncClientClass = (Class<ManualAsyncClient>) aClazz;
    }

}
