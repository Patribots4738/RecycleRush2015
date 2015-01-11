package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements SideCarConstants {

	Trike trike = new Trike();

	Joystick leftStick = new Joystick(DRIVER_STATION_PORT[0]);
	Joystick rightStick = new Joystick(DRIVER_STATION_PORT[1]);

	Joystick xbox = new Joystick(DRIVER_STATION_PORT[2]);

	ControlBoard controlBoard = new ControlBoard(DRIVER_STATION_PORT[3], 10);

	// declare xbox "a" button
	JoystickButton aButton = new JoystickButton(xbox, XboxButton.a.ordinal());

	// declare xbox "x" button
	JoystickButton bButton = new JoystickButton(xbox, XboxButton.b.ordinal());

	JoystickButton xButton = new JoystickButton(xbox, XboxButton.x.ordinal());

	JoystickButton yButton = new JoystickButton(xbox, XboxButton.y.ordinal());

	TrikeDrive trikeDrive = new TrikeDrive(1, 2, leftStick, rightStick);

	Elevator elevator = new Elevator(PWM_PORT[2], trike);

	boolean isOpen = true;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		elevator.initEncoder();

		if (trike.arm.get()) {
			trike.arm.set(false);
		}

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		trikeDrive.player();
		elevatorControl();
		elevator.Update();
		armControl();
	}

	void elevatorControl() {
		for (int i = 0; i < 4; i++) {
			if (controlBoard.getBoardButton(i).get()) {
				elevator.setPosition(i);
			}
		}

	}

	/**
	 * Opens or closes the arm each time the X button is pressed
	 */
	void armControl() {
		if (xButton.get()) {
			if (isOpen) {
				trike.arm.set(false);
			} else {
				trike.arm.set(true);

			}
			isOpen = !isOpen;
		}

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
