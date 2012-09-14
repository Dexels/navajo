package test;

import org.pushingpixels.trident.Timeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
	private float value;
	
	private final static Logger logger = LoggerFactory
			.getLogger(HelloWorld.class);
	public void setValue(float newValue) {
		logger.info(this.value + " -> " + newValue);
		this.value = newValue;
	}

	public static void main(String[] args) {
		HelloWorld helloWorld = new HelloWorld();
		Timeline timeline = new Timeline(helloWorld);
		timeline.addPropertyToInterpolate("value", 0.0f, 1.0f);
		timeline.play();

		try {
			Thread.sleep(3000);
		} catch (Exception exc) {
		}

	}
}
