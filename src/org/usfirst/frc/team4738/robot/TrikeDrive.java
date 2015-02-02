package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.*;

/**
 * @author PatriBots4738 The Drive Class for the Patribots 2015 Robot
 */
public class TrikeDrive implements SideCarConstants {

	Talon leftMotor, rightMotor;
	Encoder leftEncoder, rightEncoder;
	Joystick leftStick, rightStick;
	EncoderDecoder encoderDecoder = new EncoderDecoder();

	/**
	 * Robot control for the 2015 Recycle Rush Robot
	 * 
	 * @param leftMotor
	 *            The PWM or CAN port that the left motor is plugged in to
	 * @param rightMotor
	 *            The PWM or CAN port that the right motor is plugged in to
	 * @param leftStick
	 *            The Driver Station port the left Joystick is plugged in to
	 * @param rightStick
	 *            The Driver Station port the right Joystick is plugged in to
	 */
	TrikeDrive(int leftMotor, int rightMotor, Joystick leftStick,
			Joystick rightStick) {

		this.leftMotor = new Talon(PWM_PORT[leftMotor]);
		this.rightMotor = new Talon(PWM_PORT[rightMotor]);
		this.leftStick = leftStick;
		this.rightStick = rightStick;

	}

	private float DEAD_ZONE = .3f;

	/**
	 * Player control for the robot
	 */
	public void player() {

		if (Math.abs(leftStick.getY()) >= DEAD_ZONE) {
			leftMotor.set(-leftStick.getY());
		} else {
			leftMotor.set(0);

		}

		if (Math.abs(rightStick.getY()) >= DEAD_ZONE) {
			rightMotor.set(-rightStick.getY());
		} else {
			rightMotor.set(0);

		}

	}

	/**
	 * The autonomous control for the robot
	 * 
	 * @param leftMotor
	 *            The speed in which the left motor will move until stop is
	 *            called
	 * @param rightMotor
	 *            The speed in which the right motor will move until stop is
	 *            called
	 */
	public void autonomous(float leftMotor, float rightMotor) {
		this.leftMotor.set(leftMotor);
		this.rightMotor.set(rightMotor);
	}

	public void moveAuto(double distance) {
		leftEncoder.reset();
		while (leftEncoder.get() < encoderDecoder.getRotations(distance)) {
			leftMotor.set(1);
			rightMotor.set(1);
		}
		autonomous(false);
	}

	public void turn90Right() {
		double turnDist = (Math.PI * encoderDecoder.RADIUS) / 2;
		while (true) {
			if (rightEncoder.get() < encoderDecoder.getRotations(turnDist)) {
				rightMotor.set(-1);
			} else {
				rightMotor.set(0);
			}
			if (leftEncoder.get() < encoderDecoder.getRotations(turnDist)) {

				leftMotor.set(1);
			} else {
				leftMotor.set(0);
			}
			if (leftEncoder.get() < encoderDecoder.getRotations(turnDist)
					&& rightEncoder.get() < encoderDecoder
							.getRotations(turnDist)) {
				break;
			}
		}
	}

	public void turn90Left() {
		double turnDist = (Math.PI * encoderDecoder.RADIUS) / 2;
		while (true) {
			if (rightEncoder.get() < encoderDecoder.getRotations(turnDist)) {
				rightMotor.set(1);
			} else {
				rightMotor.set(0);
			}
			if (leftEncoder.get() < encoderDecoder.getRotations(turnDist)) {

				leftMotor.set(-1);
			} else {
				leftMotor.set(0);
			}
			if (leftEncoder.get() < encoderDecoder.getRotations(turnDist)
					&& rightEncoder.get() < encoderDecoder
							.getRotations(turnDist)) {
				break;
			}
		}
	}

	public void turn180() {
		double turnDist = (Math.PI * encoderDecoder.RADIUS);
		while (true) {
			if (rightEncoder.get() < encoderDecoder.getRotations(turnDist)) {
				rightMotor.set(-1);
			} else {
				rightMotor.set(0);
			}
			if (leftEncoder.get() < encoderDecoder.getRotations(turnDist)) {

				leftMotor.set(1);
			} else {
				leftMotor.set(0);
			}
			if (leftEncoder.get() < encoderDecoder.getRotations(turnDist)
					&& rightEncoder.get() < encoderDecoder
							.getRotations(turnDist)) {
				break;
			}
		}
	}

	/**
	 * The Overload method for autonomous telling it to stop
	 * 
	 * @param stop
	 *            That is all... Seriously
	 */
	public void autonomous(boolean stop) {
		if (!stop) {
			this.leftMotor.set(0);
			this.rightMotor.set(0);
		}
	}

	/**
	 * Sets the deadzone. Duh! Look at the name Idiot
	 * 
	 * @param deadzone
	 *            Do I seriously have to explain this to you?
	 */
	public void setDeadZone(float deadzone) {
		DEAD_ZONE = deadzone;
	}

}
