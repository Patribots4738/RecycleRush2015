package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;;

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
	ControlBoard controlBoard = new ControlBoard(DRIVER_STATION_PORT[2], 10);

	
	TrikeDrive trikeDrive = new TrikeDrive(PWM_PORT[0], PWM_PORT[1], leftStick,
			rightStick);

	Elevator elevator = new Elevator(PWM_PORT[2], trike);
	
	boolean isOpen = true;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	Camera camera;
	public void robotInit() {

		controlBoard.setOutputs(0);
		
	
			camera = new Camera();

				//elevator.initEncoder();

		// TODO: LIMIT SWITCH CONTROLS THIS
		/*
		 * if (trike.arm.get()) { trike.arm.set(false); }
		 */

	}

	
	DigitalInput limitSwtich = new DigitalInput(DIO_PORT[8]);
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		trikeDrive.player();
		// elevatorControl();
		// elevator.Update();
		armControl();

		if(limitSwtich.get())
		{
			controlBoard.setOutput(1, false);
			controlBoard.setOutput(2, false);
			controlBoard.setOutput(3, false);
			controlBoard.setOutput(4, false);
			controlBoard.setOutput(5, false);
			controlBoard.setOutput(6, false);
			controlBoard.setOutput(7, false);
			controlBoard.setOutput(8, false);
			controlBoard.setOutput(9, false);
			controlBoard.setOutput(10, false);
			controlBoard.setOutput(11, false);
			controlBoard.setOutput(12, false);
		}
		else
		{
			controlBoard.setOutput(1, true);
			controlBoard.setOutput(2, true);
			controlBoard.setOutput(3, true);
			controlBoard.setOutput(4, true);
			controlBoard.setOutput(5, true);
			controlBoard.setOutput(6, true);
			controlBoard.setOutput(7, true);
			controlBoard.setOutput(8, true);
			controlBoard.setOutput(9, true);
			controlBoard.setOutput(10, true);
			controlBoard.setOutput(11, true);
			controlBoard.setOutput(12, true);
		}
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
	boolean canThrow;

	void armControl() {
		if (controlBoard.getBoardButton(6).get()&&canThrow) {

			if (isOpen) {
				setArm(true);
			} else {

				setArm(false);
			}

			isOpen = !isOpen;
			canThrow = false;

		} else if(!controlBoard.getBoardButton(6).get()) {
			canThrow = true;
		}
		

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

		int distance = 1;

		setArm(true);
		trikeDrive.moveAuto(2);
		setArm(false);
		trikeDrive.moveAuto(distance);
		setArm(true);
		trikeDrive.moveAuto(-8);
		trikeDrive.turn180();
		trikeDrive.moveAuto(distance - 8);
		trikeDrive.turn90Right();
		trikeDrive.moveAuto(3);
		setArm(false);
		trikeDrive.turn90Right();
		trikeDrive.moveAuto(distance);
		setArm(true);

	}

	boolean stopLoop;
	
	 void setArm(boolean state) {
		try {

			if (state) {
				trike.arm.set(Value.kReverse);
				Thread.sleep(500);
				trike.arm.set(Value.kOff);
			} else {
				trike.arm.set(Value.kForward);
				Thread.sleep(500);
				trike.arm.set(Value.kOff);

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
