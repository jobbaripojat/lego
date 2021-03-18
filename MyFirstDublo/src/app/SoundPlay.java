package app;

import java.io.File;

import lejos.hardware.Sound;

public class SoundPlay extends Thread {
	/**
	* Täällä hallitaan robotin soittamaa musiikkia.
	* Tarkemmat tiedot metodeista löytyvät dokumentaatiosta.
	*/
	
	File startSound = new File("start.wav");
	File endSound = new File("robontti.wav");
	
	public static boolean playStart = false;
	public static boolean playEnd = false;
	
	public void run() {
		while(true) {
			if (playStart) {
				Sound.playSample(startSound, 100);
				playStart = false;
			} else if (playEnd) {
				Sound.playSample(endSound, 100);
				playEnd = false;
			}
		}
	}
}
