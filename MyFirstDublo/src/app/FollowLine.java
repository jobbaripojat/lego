package app;

import lejos.hardware.Button;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

//Tämä on main, jonka sisällä itse ohjelma runnaa.
 
public class FollowLine 
{ 
    public static void main(String[] args) 
    {
        
    	int rotatedTime = 0;
    	int turnTime = 75;
    	int objectsPassed = 0;
    	boolean tunnistettu = false;
    	boolean previousTurn = true; // true == left, false == right
    	boolean nextTurn = false; // true == left, false == right
    	
    	Stopwatch timer = new Stopwatch();
    	
    	Thread color = new ColorDetection();
    	Thread ultrasonic = new ObjectDetection();
    	Thread sound = new SoundPlay();
    	
    	color.start();
    	ultrasonic.start();
    	sound.start();
    	
        Button.waitForAnyPress(); 
        System.out.println(ObjectDetection.range);
        Delay.msDelay(2000);
        SoundPlay.playStart = true;
        Delay.msDelay(3000);
        timer.reset();
    	
        Movement.Forwards(200);
        
    	while (ColorDetection.rgb == "Black") {
			rotatedTime = 0;
			ObjectDetection.detected = false;
			// ObjectDetection.toggledObject = true;
			
    		while (ColorDetection.rgb == "Black") {
    			if (previousTurn) {
    				Movement.TiltLeft(1);
    				nextTurn = false;
    			} else {
    				Movement.TiltRight(1);
    				nextTurn = true;
    			}
    			
    			if (ObjectDetection.detected) { 
    				tunnistettu = true;
    				break; 
    			}
    		}
    		
    		if (tunnistettu) {
				Movement.Stop();
				Movement.Backwards(200);
    			objectsPassed++;
    			if (objectsPassed == 2) {
    				Movement.CloseMotors();
    				SoundPlay.playEnd = true;
    				System.out.println(timer.elapsed());
    			}
    			for (int i = 1; i <= 1;) {
        			Movement.TurnRight(500);
        			Movement.Forwards(900);
        			if (ColorDetection.rgb == "Black") { break; }
        			Movement.TurnLeft(450);
        			if (ColorDetection.rgb == "Black") { break; }
        			Movement.Forwards(2200);
        			if (ColorDetection.rgb == "Black") { break; }
        			Movement.TurnLeft(500);
        			break;
    	        }
    			while(ColorDetection.rgb != "Black") {
    				Movement.Forwards(1);
    			}
    			while(ColorDetection.rgb == "Black") {
    				Movement.Forwards(1);
    			}
    			Movement.TurnRight(600);
    			tunnistettu = false;
    		}

    		while (ColorDetection.rgb != "Black") {
    			if (nextTurn) {
    				Movement.TurnLeft(1);
    				previousTurn = true;
    			} else {
    				Movement.TurnRight(1);
    				previousTurn = false;
    			}
        		rotatedTime += 1;
        		if (ColorDetection.rgb == "Black") {
        			if (previousTurn) {
        				Movement.TurnLeft(20);
        			} else {
            			Movement.TurnRight(20);
        			}
        		}
        		if (rotatedTime >= turnTime) {
        			Movement.Stop();
        			break;
        		}
    		}
    		
    		while (ColorDetection.rgb != "Black") {
    			if (nextTurn) {
    				Movement.TurnRight(1);
    				previousTurn = true;
    			} else {
    				Movement.TurnLeft(1);
    				previousTurn = false;
    			}
			}
    	}
    } 
} 
