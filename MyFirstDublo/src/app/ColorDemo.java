package app;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;
import app.library.ColorSensor;

public class ColorDemo
{
    public static void main(String[] args)
    {
        ColorSensor    color = new ColorSensor(SensorPort.S3);

        System.out.println("Color Demo");
        System.out.println("Press to start");
        
        Button.LEDPattern(4);    // flash green led and
        Sound.beepSequenceUp();    // make sound when ready.

        Button.waitForAnyPress();
        Button.LEDPattern(0);
        
        // run until escape button pressed.
        
        while (Button.ESCAPE.isUp())
        {
            System.out.println(color.getAmbient());
            Delay.msDelay(250);
        }

        Delay.msDelay(1000);

        color.setRedMode();
        color.setFloodLight(Color.RED);
        color.setFloodLight(true);
        
        while (Button.ESCAPE.isUp())
        {
            System.out.println(color.getRed());
            Delay.msDelay(250);
        }

        Delay.msDelay(1000);

        color.setRGBMode();
        color.setFloodLight(Color.WHITE);
        
        Color rgb;
        
        while (Button.ESCAPE.isUp())
        {
            rgb = color.getColor();
            
            System.out.println(rgb.getRed()+ rgb.getGreen()+ rgb.getBlue());
            Delay.msDelay(250);
        }

        Delay.msDelay(1000);

        color.setColorIdMode();
        color.setFloodLight(false);
        
        while (Button.ESCAPE.isUp())
        {
        	System.out.println(ColorSensor.colorName(color.getColorID()));
            Delay.msDelay(250);
        }

        // free up resources.
        color.close();
        
        Sound.beepSequence();    // we are done.

        Button.LEDPattern(4);
        Button.waitForAnyPress();
    }

}