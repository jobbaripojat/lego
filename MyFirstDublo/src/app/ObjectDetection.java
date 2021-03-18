package app;

import app.library.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;

public class ObjectDetection extends Thread {
	
	/**
	* Täällä on vain run, jonka sisällä haetaan etäisyys objektiin joka saattaa olla edessä.
	* Detected muuttuu riippuen siitä, kuinka lähellä esine on.
	*/
	public static float range;
	
	static UltraSonicSensor ussr = new UltraSonicSensor(SensorPort.S1);
	
	public static boolean detected = false;
	
	
	public void run() {
        Button.waitForAnyPress(); 
		while (true) {
			range = ussr.getRange();
			detected = false;
			if (range < 0.2) {	
				detected = true;
			}
		}
	}
}
