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
    	int turnTime = 65;
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
    			objectsPassed++;
    			if (objectsPassed == 2) {
    				Movement.CloseMotors();
    				SoundPlay.playEnd = true;
    				System.out.println(timer.elapsed());
    			}
    			Movement.TurnRight(250);
    			Movement.Forwards(900);
    			Movement.TurnLeft(250);
    			Movement.Forwards(1500);
    			Movement.TurnLeft(200);
    			while(ColorDetection.rgb != "Black") {
    				Movement.Forwards(1);
    			}
    			Movement.TurnRight(250);
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
        				Movement.TurnLeft(5);
        			} else {
            			Movement.TurnRight(5);
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
