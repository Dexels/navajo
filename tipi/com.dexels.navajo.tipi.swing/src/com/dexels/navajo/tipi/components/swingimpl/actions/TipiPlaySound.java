/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.components.swingimpl.TipiApplet;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiPlaySound extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4545861721012818582L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.
	 * tipi.internal.TipiEvent)
	 */
	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException {
		Operand url = getEvaluatedParameter("url", event);
		URL urlVal = (URL) url.value;
		playUrl(urlVal);

	}

	/**
	 * @param urlVal
	 */
	private void playUrl(URL urlVal) {
		TipiApplet rr = null;
		if (myContext != null) {
			rr = ((SwingTipiContext) myContext).getAppletRoot();
		}
		if (rr != null) {
			rr.play(urlVal);
		} else {
			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(urlVal);
				playAudioStream(ais);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/** Plays audio from the given audio input stream. */
	public static void playAudioStream(AudioInputStream audioInputStream) {
		// Audio format provides information like sample rate, size, channels.
		AudioFormat audioFormat = audioInputStream.getFormat();
		System.out.println("Play input audio format=" + audioFormat);

		// Open a data line to play our type of sampled audio.
		// Use SourceDataLine for play and TargetDataLine for record.
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		if (!AudioSystem.isLineSupported(info)) {
			System.out
					.println("Play.playAudioStream does not handle this type of audio on this system.");
			return;
		}

		try {
			// Create a SourceDataLine for play back (throws
			// LineUnavailableException).
			SourceDataLine dataLine = (SourceDataLine) AudioSystem
					.getLine(info);
			// System.out.println( "SourceDataLine class=" + dataLine.getClass()
			// );

			// The line acquires system resources (throws
			// LineAvailableException).
			dataLine.open(audioFormat);

			// Adjust the volume on the output line.
			if (dataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl volume = (FloatControl) dataLine
						.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(100.0F);
			}

			// Allows the line to move data in and out to a port.
			dataLine.start();

			// Create a buffer for moving data from the audio stream to the
			// line.
			int bufferSize = (int) audioFormat.getSampleRate()
					* audioFormat.getFrameSize();
			byte[] buffer = new byte[bufferSize];

			// Move the data until done or there is an error.
			try {
				int bytesRead = 0;
				while (bytesRead >= 0) {
					bytesRead = audioInputStream.read(buffer, 0, buffer.length);
					if (bytesRead >= 0) {
						// System.out.println(
						// "Play.playAudioStream bytes read=" + bytesRead +
						// ", frame size=" + audioFormat.getFrameSize() +
						// ", frames read=" + bytesRead /
						// audioFormat.getFrameSize() );
						// Odd sized sounds throw an exception if we don't write
						// the same amount.
//						int framesWritten = dataLine
//								.write(buffer, 0, bytesRead);
					}
				} // while
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Play.playAudioStream draining line.");
			// Continues data line I/O until its buffer is drained.
			dataLine.drain();

			System.out.println("Play.playAudioStream closing line.");
			// Closes the data line, freeing any resources such as the audio
			// device.
			dataLine.close();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	} // playAudioStream

	public static void main(String[] args) throws MalformedURLException,
			InterruptedException {
		// http://www.sound-effect.com/sounds1/animal/Treeanimals/monkeys1.wav//
		new TipiPlaySound()
				.playUrl(new URL(
						"http://www.sound-effect.com/sounds1/animal/Treeanimals/monkeys1.wav"));
		Thread.sleep(10000);
	}

}
