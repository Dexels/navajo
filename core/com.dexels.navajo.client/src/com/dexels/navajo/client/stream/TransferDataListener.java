package com.dexels.navajo.client.stream;

public interface TransferDataListener {
	public void transferCompleted(String label, long bytes, long duration);
	public void transferFailed(String label, long duration);
}
