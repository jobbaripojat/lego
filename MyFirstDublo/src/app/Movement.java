package app;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class Movement {
	
	private static int speed = 80;
	
	private static UnregulatedMotor right = new UnregulatedMotor(MotorPort.A);
	private static UnregulatedMotor left = new UnregulatedMotor(MotorPort.B);
	
	public static int getSpeed() {
		return speed;
	}
	
	public static void setSpeed(int x) {
		speed = x;
	}
    
	public static void TurnLeft(int duration) {
		right.setPower(speed);
		left.setPower(-speed);
		Delay.msDelay((long) duration);
	}
	
	public static void TurnRight(int duration) {
		right.setPower(-speed);
		left.setPower(speed);
		Delay.msDelay((long) duration);
	}
	
	public static void TiltLeft(int duration) {
		right.setPower(speed);
		left.setPower((int) (speed * 0.9));
		Delay.msDelay((long) duration);
	}

	public static void TiltRight(int duration) {
		right.setPower((int) (speed * 0.9));
		left.setPower(speed);
		Delay.msDelay((long) duration);
	}
	
	public static void Forwards(int duration) {
		right.setPower(speed);
		left.setPower(speed);
		Delay.msDelay((long) duration);
	}

	public static void Backwards(int duration) {
		right.setPower(-speed);
		left.setPower(-speed);
		Delay.msDelay((long) duration);
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
