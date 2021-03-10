package app;

import lejos.hardware.Button;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;
 
public class FollowLine 
{ 
    public static void main(String[] args) 
    {
        
    	int rotatedTime = 0;
    	int speedMulti = Movement.getSpeedMulti();
    	int turnTime = 60 * speedMulti;
    	int objectsPassed = 0;
    	boolean tunnistettu = false;
    	// boolean previousTurn = true; // true == left, false == right
    	Stopwatch timer = new Stopwatch();
    	
    	Thread color = new ColorDetection();
    	Thread ultrasonic = new ObjectDetection();
    	
    	color.start();
    	ultrasonic.start();
    	
        Button.waitForAnyPress(); 
        Delay.msDelay(5000);
        timer.reset();
    	
    	while (ColorDetection.rgb == "Black") {
			rotatedTime = 0;
    		while (ColorDetection.rgb == "Black") {
    			Movement.Forwards(5);
    			
    			if (ObjectDetection.detected) { 
    				tunnistettu = true;
    				break; 
    			}
    		}
    		
    		if (tunnistettu) {
    			objectsPassed++;
    			if (objectsPassed == 2) {
    				Movement.CloseMotors();
    				System.out.println(timer.elapsed());
    			}
    			Movement.Stop();
    			Movement.TurnRight(200 * speedMulti);
    			Movement.Forwards(300 * speedMulti);
    			Movement.TurnLeft(200 * speedMulti);
    			Movement.Forwards(800 * speedMulti);
    			Movement.TurnLeft(200 * speedMulti);
    			while(ColorDetection.rgb != "Black") {
    				Movement.Forwards(10);
    			}
    			Movement.TurnRight(80 * speedMulti);
    			tunnistettu = false;
    		}
    		while (ColorDetection.rgb != "Black") {
        		Movement.TurnRight(10);
        		/*
    			if (previousTurn) {
        			Movement.TurnRight(10);
    			} else {
    				Movement.TurnLeft(10);
    			}
    			*/
        		rotatedTime += 10;
        		if (rotatedTime >= turnTime) {
        			Movement.Stop();
        			break;
        		}
    		}
    		
    		// if (ColorDetection.rgb == "Black") {previousTurn = !previousTurn;}
    		while (ColorDetection.rgb != "Black") {
				Movement.TurnLeft(10);
				/*
    			if (previousTurn) {
    				Movement.TurnLeft(10);
            		previousTurn = false;
    			} else {
            		Movement.TurnRight(10);
    				previousTurn = true;
    			}
    			*/
    		}
    	}
    	Movement.Stop();
    	Movement.CloseMotors();
    	ColorDetection.ToggleColorChecking();

        Button.LEDPattern(3);
    	Button.waitForAnyPress();
    	Button.waitForAnyPress();
    } 
} 