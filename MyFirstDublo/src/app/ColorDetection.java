package app;

import app.library.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;

//Luokka koostuu metoideista, joiden avulla värisensori toimii ja palautta dataa. Dokumentaatiossa on tarkemmin tietoa metodeista.

class ColorDetection extends Thread {
	
	public static String rgb = "";
	private static boolean RunningColor = true;
	
	private static ColorSensor color = new ColorSensor(SensorPort.S4);
    
    public static void InitializeColor() {
    	color.setColorIdMode();
        color.setFloodLight(Color.WHITE);
        Button.LEDPattern(4);
        System.out.println("Press any key to start"); 
    }
    
    public static String CheckColor() {
		return ColorSensor.colorName(color.getColorID());
    }
    
    public void run() {
    	InitializeColor();
    	while(RunningColor) {
    		rgb = CheckColor();
    	}
    }
    
    public static void ToggleColorChecking() {
    	if (RunningColor) {
    		RunningColor = false;
    	} else {
    		RunningColor = true;
    	}
    }
}
