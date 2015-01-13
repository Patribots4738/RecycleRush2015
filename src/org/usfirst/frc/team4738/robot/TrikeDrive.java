package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.*;

/**
 * @author PatriBots4738
 * The Drive Class for the Patribots 2015 Robot
 */
public class TrikeDrive implements SideCarConstants {

	Talon leftMotor, rightMotor;
	Joystick leftStick, rightStick;
	/**
	 * Robot control for the 2015 Recycle Rush Robot
	 * @param leftMotor The PWM or CAN port that the left motor is plugged in to
	 * @param rightMotor The PWM or CAN port that the right motor is plugged in to
	 * @param leftStick The Driver Station port the left Joystick is plugged in to
	 * @param rightStick The Driver Station port the right Joystick is plugged in to 
	 */
	TrikeDrive(int leftMotor, int rightMotor, Joystick leftStick, Joystick rightStick) {

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

		if (Math.abs(leftStick.getAxis(Joystick.AxisType.kX)) <= DEAD_ZONE) {
			leftMotor.set(leftStick.getAxis(Joystick.AxisType.kX));
		} else {
			leftMotor.set(0);

		}

		if (Math.abs(rightStick.getAxis(Joystick.AxisType.kX)) <= DEAD_ZONE) {
			rightMotor.set(rightStick.getAxis(Joystick.AxisType.kX));
		} else {
			rightMotor.set(0);

		}

	}

	/**
	 * The autonomous control for the robot
	 * @param leftMotor The speed in which the left motor will move until stop is called
	 * @param rightMotor The speed in which the right motor will move until stop is called
	 */
	public void autonomous(float leftMotor, float rightMotor) {
		this.leftMotor.set(leftMotor);
		this.rightMotor.set(rightMotor);
	}

	/**
	 * The Overload method for autonomous telling it to stop
	 * @param stop That is all... Seriously
	 */
	public void autonomous(boolean stop) {
		if (!stop) {
			this.leftMotor.set(0);
			this.rightMotor.set(0);
		}
	}

	/**
	 * Sets the deadzone. Duh! Look at the name Idiot
	 * @param deadzone Do I seriously have to explain this to you?
	 */
	public void setDeadZone(float deadzone) {
		DEAD_ZONE = deadzone;
	}
	
}
