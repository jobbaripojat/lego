package app;
import lejos.hardware.Button; 
import lejos.hardware.Sound; 
import lejos.hardware.motor.*; 
import lejos.hardware.port.*; 
import lejos.utility.Delay;
import lejos.robotics.Color;
import lejos.robotics.navigation.*;
import lejos.hardware.port.SensorPort;
import app.library.ColorSensor;
import java.io.File;
import java.util.Timer;
 
public class HelloLego 
{ 
    public static void main(String[] args) 
    { 

    	// File file=new File("2tokyodrift.wav");
    	// if(!file.exists())
    	// System.out.println("File does not exist!");
    	
        System.out.println("Drive Forward\nand Stop\n"); 
        System.out.println("Press any key to start"); 
 
        Button.LEDPattern(4);     // flash green led and 
        Sound.beepSequenceUp();   // make sound when ready. 
 
 
        // create two motor objects to control the motors. 
        UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A); 
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B); 
        ColorSensor color = new ColorSensor(SensorPort.S3);
        
 
        // set motors to 50% power. 
        // motorA.setPower(75); 
        // motorB.setPower(75); 

        color.setColorIdMode();
        color.setFloodLight(Color.WHITE);
        
        String rgb = "Black";
        
        Button.waitForAnyPress(); 
        
        while (rgb == "Black") {
        	while (rgb == "Black") {
        		motorA.setPower(30);
        		motorB.setPower(30);
        		rgb = ColorSensor.colorName(color.getColorID());
        		Delay.msDelay(100); 
        	}
        	long startTime = System.currentTimeMillis();
        	int elapsed = 0;
        	while (rgb != "Black") {
        		long currentTime = System.currentTimeMillis();
        		elapsed = (int) currentTime - (int) startTime;
        		motorA.setPower(30);
        		motorB.setPower(0);
        		System.out.println("Moottori A");
        		rgb = ColorSensor.colorName(color.getColorID());
        		if (elapsed >= 2000) {
        			motorA.setPower(-30);
        			motorB.setPower(0);
        			Delay.msDelay(elapsed + 200);
        			break;
        		}
        		System.out.println(elapsed);
        		Delay.msDelay(100); 
        	}
        	if (rgb == "White") {
        		break;
        	}
        	if (rgb == "Black") {
        		elapsed = 0;
        		continue;
        	}
        	while(rgb != "Black") {
        		rgb = ColorSensor.colorName(color.getColorID());
        		motorA.setPower(0);
        		motorB.setPower(50);
        		System.out.println("Moottori B");
        	}
        }
        
//        while (rgb == "Black")
//        {
//        	System.out.println("Loopin alku");
//        	rgb = ColorSensor.colorName(color.getColorID());
//        	if (rgb != "Black") {
//        		while (rgb != "Black") {
//        			motorA.setPower(30);
//        			motorB.setPower(0);
//            		System.out.println("Moottori A");
//        			Delay.msDelay(2000);
//                	rgb = ColorSensor.colorName(color.getColorID());
//                	while (rgb != "Black") {
//                		motorA.setPower(0);
//                		motorB.setPower(30);
//                		System.out.println("Moottori B");
//                		Delay.msDelay(4000);
//                		rgb = ColorSensor.colorName(color.getColorID());
//                		if (rgb != "Black") {
//                			break;
//                		}
//        			}
//                	if (rgb == "White") {
//                		System.out.println("Loop broke");
//                		break;
//                	}
//        		}
//        	}
//        	else {
//        		motorA.setPower(20);
//        		motorB.setPower(20);
//            	Delay.msDelay(200); 
//        	}
//        }
        // wait 2 seconds. 
        // Delay.msDelay(1500); 
 
        // motorA.setPower(60);
        // motorB.setPower(0);
        
        // wait 2 seconds. 
        // Delay.msDelay(800); 
        
        // motorA.setPower(20);
        // motorB.setPower(20);

        // wait 2 seconds. 
        // Delay.msDelay(2000); 
        
        // stop motors with brakes on.  
        motorA.stop(); 
        motorB.stop(); 
        
        // Sound.playSample(file, 100);
 
        // free up motor resources.  
        motorA.close();  
        motorB.close(); 
  
        Sound.beepSequence(); // we are done. 
        Button.waitForAnyPress();
    } 
} 