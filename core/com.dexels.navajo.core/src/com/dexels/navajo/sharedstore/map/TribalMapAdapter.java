package com.dexels.navajo.sharedstore.map;

import java.util.Collection;
import java.util.Iterator;

public class TribalMapAdapter {

	public int mapCount;
	public TribalMapStatistics [] tribalMaps;
	
	public TribalMapStatistics [] getTribalMaps() {
		Collection<SharedTribalMap> c = SharedTribalMap.getAllTribalMaps();
		TribalMapStatistics [] tribalMaps = new TribalMapStatistics[c.size()];
		Iterator<SharedTribalMap> i = c.iterator();
		int count = 0;
		while ( i.hasNext() ) {
			SharedTribalMap st = i.next();
			tribalMaps[count] = new TribalMapStatistics();
			tribalMaps[count].id = st.getId();
			tribalMaps[count].size = st.getSize();
			String keyType = "-";
			if ( st.keySet() != null && st.keySet().iterator() != null && st.keySet().iterator().hasNext() ) {
				try {
				keyType = st.keySet().iterator().next().getClass().getName();
				} catch (Throwable t) {
					keyType = "-";
				}
			}
			String valueType = "-";
			if ( st.values() != null && st.values().iterator() != null && st.values().iterator().hasNext() ) {
				try {
				valueType = st.values().iterator().next().getClass().getName();
				} catch (Throwable t) {
					valueType = "-";
				}
			}
			tribalMaps[count].keyType = keyType;
			tribalMaps[count].valueType = valueType;
			tribalMaps[count].insertCount = st.insertCount;
			tribalMaps[count].deleteCount = st.deleteCount;
			tribalMaps[count].getCount = st.getCount;
			
			count++;
		}
		return tribalMaps;
	}

	public int getMapCount() {
		return SharedTribalMap.getAllTribalMaps().size();
	}
}
