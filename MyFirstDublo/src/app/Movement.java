package app;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

//Täällä halitaan robotin liikettä ja sen kääntymistä eri suuntiin. Metodeista voi jälleen lukea enemmän dokumentaatiosta.

public class Movement {
	
	private static int speed = 80;
	
	private static int speedMulti = 100 / speed;
	
	private static UnregulatedMotor right = new UnregulatedMotor(MotorPort.A);
	private static UnregulatedMotor left = new UnregulatedMotor(MotorPort.B);
	
	public static int getSpeedMulti() {
		return speedMulti;
	}
    
	public static void TurnLeft(int duration) {
		right.setPower(speed);
		left.setPower(-speed);
		Delay.msDelay((long) duration * speedMulti);
	}
	
	public static void TurnRight(int duration) {
		right.setPower(-speed);
		left.setPower(speed);
		Delay.msDelay((long) duration * speedMulti);
	}
	
	public static void Forwards(int duration) {
		right.setPower(speed);
		left.setPower(speed);
		Delay.msDelay((long) duration * speedMulti);
	}

	public static void Backwards(int duration) {
		right.setPower(-speed);
		left.setPower(-speed);
		Delay.msDelay((long) duration * speedMulti);
	}
	
	public static void Stop() {
		right.setPower(0);
		left.setPower(0);
	}
	
	public static void CloseMotors() {
		right.stop();
		left.stop();
		right.close();
		left.close();
	}
}
