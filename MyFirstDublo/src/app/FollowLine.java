package app;

import lejos.hardware.Button;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

/**
 * Tämä on main, jonka sisällä itse ohjelma runnaa.
 */

public class FollowLine 
{ 
    public static void main(String[] args) 
    {
    	/**
    	 * rotatedTime on kuinka kauan ollaan pyöritty
    	 * turnTime on kuinka kauan pyöritään
    	 * objectsPassed on kuinka monta estettä ollaan kierretty
    	 * tunnistettu on onko edessä este
    	 * previousTurn on suunta mihin käännyttiin viimeksi
    	 * nextTurn on suunta johon käännytään seuraavaksi
    	 */
    	int rotatedTime = 0;
    	int turnTime = 75;
    	int objectsPassed = 0;
    	boolean tunnistettu = false;
    	boolean previousTurn = true; // true == left, false == right
    	boolean nextTurn = false; // true == left, false == right
    	
    	Stopwatch timer = new Stopwatch();
    	/**
    	 * color on värintunnistus threadi
    	 * ultrasonic on esteentunnistus threadi
    	 * sound on äänen soitto
    	 */
    	Thread color = new ColorDetection();
    	Thread ultrasonic = new ObjectDetection();
    	Thread sound = new SoundPlay();
    	
    	color.start();
    	ultrasonic.start();
    	sound.start();
    	/**
    	 * Odottaa napin painamista ja soittaa äänen
    	 * Paikallaan 5 sekuntia ja lähtee sitten liikkeelle
    	 */
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
			/**
			 * Kun tunnistettu väri on musta, ajetaan eteenpäin kallistaen sivulle
			 * kallistettava sivu riippuu edellisestä käännöksestä, jos viimeksi
			 * tarkistettiin ensin vasen reuna, kallistetaan vasemmalle. Asetetaan 
			 * seuraava käännös olemaan vastakkaiseen suuntaan. Jos tunnistetaan
			 * este, rikotaan looppi.
			 */
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
        	/**
        	 * Tarkistetaan rikottiinko looppi esteen takia, vai koska ei enää
        	 * tunnistettu mustaa
        	 */
    		if (tunnistettu) {
				Movement.Stop();
				Movement.Backwards(200);
    			objectsPassed++;
    	    	/**
    	    	 * Jos tämä on jo toinen este, pysäytetään robotti, soitetaan
    	    	 * loppumusiikki ja tulostetaan kierrokseen kertynyt aika
    	    	 */
    			if (objectsPassed == 2) {
    				Movement.CloseMotors();
    				SoundPlay.playEnd = true;
    				System.out.println(timer.elapsed());
    			}
    	    	/**
    	    	 * Kierretään este annettujen arvojen mukaisesti. Keskeytetään liike
    	    	 * jos tunnistetaan väri mustaksi.
    	    	 */
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
    	    	/**
    	    	 * Ajetaan eteenpäin kunnes ylitetään musta viiva, sitten käännytään oikealle
    	    	 * ja jatketaan viivan seuraamista
    	    	 */
    			while(ColorDetection.rgb != "Black") {
    				Movement.Forwards(1);
    			}
    			while(ColorDetection.rgb == "Black") {
    				Movement.Forwards(1);
    			}
    			Movement.TurnRight(600);
    			tunnistettu = false;
    		}

        	/**
        	 * Jos eteenpäin ajaminen pysähtyi värin takia, käännetään robottia 
        	 * nextTurnin määräämään suuntaan. Tämän kanssa yritetään minimoida turha liike.
        	 */
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
        	/**
        	 * Jos nextTurnin määräämästä suunnasta ei löydy mustaa, käännetään vastakkaiseen
        	 * suuntaan kunnes mustaa löydetään.
        	 */
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
