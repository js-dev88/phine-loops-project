package org.bitbucket.jsdev88.projethaddadsaussier.io;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Source :http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 * 
 *
 */
public enum EasterEgg {
	AAA("AAA.wav"),
	FORABDEL("pnl.wav");
	
	private Clip cl;
	
	EasterEgg(String soundName){
		try{
			URL url = this.getClass().getResource(soundName);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
	         cl = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         cl.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	}
	
	
	public void play(){
		 if (cl.isRunning()) cl.stop(); 
		cl.setFramePosition(0); 
        cl.start();
	}
	
	 static void initialize() {
	      values(); 
	 }
}
