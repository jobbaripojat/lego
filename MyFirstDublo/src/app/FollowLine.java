package app;

import lejos.hardware.Button;
 
public class FollowLine 
{ 
    public static void main(String[] args) 
    {
    	Thread color = new ColorDetection();
    	color.start();

        Button.waitForAnyPress(); 
        
    	int rotatedTime = 0;
    	int speedMulti = Movement.getSpeedMulti();
    	int turnTime = 100 * speedMulti;
    	boolean previousTurn = true; // true == left, false == right
    	
    	while (ColorDetection.rgb == "Black") {
			rotatedTime = 0;
    		while (ColorDetection.rgb == "Black") {
    			Movement.Forwards(5);
    		}
    		while (ColorDetection.rgb != "Black") {
    			if (previousTurn) {
            		Movement.TurnRight(10);
    			} else {
    				Movement.TurnLeft(10);
    			}
        		rotatedTime += 10;
        		if (rotatedTime >= turnTime) {
        			Movement.Stop();
        			break;
        		}
    		}
    		if (ColorDetection.rgb == "Black") {previousTurn = !previousTurn;}
    		while (ColorDetection.rgb != "Black") {
    			if (previousTurn) {
    				Movement.TurnLeft(10);
            		previousTurn = false;
    			} else {
            		Movement.TurnRight(10);
    				previousTurn = true;
    			}
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