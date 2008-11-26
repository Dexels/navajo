/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.io.*;
import java.net.*;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;
import com.sun.media.sound.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiPlaySound extends TipiAction {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
    protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
        Operand url  = getEvaluatedParameter("url", event);
        URL urlVal = (URL)url.value;
        playUrl(urlVal);

        
    }

	/**
	 * @param urlVal
	 */
	private void playUrl(URL urlVal) {
		TipiApplet rr = null;
		if(myContext!=null) {
			rr = ((SwingTipiContext)myContext).getAppletRoot();
		}
        if(rr!=null) {
        	rr.play(urlVal);
        } else {
        	try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(urlVal);
				Clip c = AudioSystem.getClip();
				c.open(ais);
				c.start();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
	}

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
//    	http://www.sound-effect.com/sounds1/animal/Treeanimals/monkeys1.wav//
    	new TipiPlaySound().playUrl(new URL("http://www.sound-effect.com/sounds1/animal/Treeanimals/monkeys1.wav"));
    	Thread.sleep(10000);
    }
    
}
