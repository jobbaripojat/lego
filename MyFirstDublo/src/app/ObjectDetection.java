package app;

import app.library.UltraSonicSensor;
import lejos.hardware.port.SensorPort;

public class ObjectDetection extends Thread {

	public static float range;
	
	static UltraSonicSensor ussr = new UltraSonicSensor(SensorPort.S1);
	
	public static boolean detected = false;
	public static boolean toggledObject = true;
	
	public void run() {
		while (toggledObject) {
			range = ussr.getRange();
			detected = false;
			if (range <= .15) {
				detected = true;
			}
		}
	}
	
    public static void ToggleObjectDetection() {
    	if (toggledObject) {
    		toggledObject = false;
    	} else {
    		toggledObject = true;
    	}
    }
}
