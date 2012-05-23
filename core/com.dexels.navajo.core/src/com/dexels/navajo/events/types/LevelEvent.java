package com.dexels.navajo.events.types;

import java.util.logging.Level;

/**
 * Interface to specify events that contain an 'event logging level' that can be used to
 * discriminate between different logging levels.
 * 
 * @author arjen
 *
 */
public interface LevelEvent {

	public Level getLevel();
	
}
