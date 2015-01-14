package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

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

	TrikeDrive trikeDrive = new TrikeDrive(1, 2, leftStick, rightStick);

	Elevator elevator = new Elevator(PWM_PORT[3], trike);

	boolean isOpen = true;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		elevator.initEncoder();

		
		//TODO: LIMIT SWITCH CONTROLS THIS
		/*if (trike.arm.get()) {
			trike.arm.set(false);
		}*/

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
		if (controlBoard.getBoardButton(5).get()) {
			if (isOpen) {
			setArm(true);
			} else {
			
			setArm(false);
			}
			isOpen = !isOpen;
		}
		

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

		int distance =1;
		
		setArm(true);
		trikeDrive.moveAuto(2);
		setArm(false);
		trikeDrive.moveAuto(distance);
		setArm(true);
		trikeDrive.moveAuto(-8);
		trikeDrive.turn180();
		trikeDrive.moveAuto(distance-8);
		trikeDrive.turn90Right();
		trikeDrive.moveAuto(3);
		setArm(false);
		trikeDrive.turn90Right();
		trikeDrive.moveAuto(distance);
		setArm(true);

		
	}

	
	void setArm(boolean state){
		try {

		if(state){
			trike.arm.set(Value.kReverse);
			wait((long) .5f);
			trike.arm.set(Value.kOff);
		}else{
			trike.arm.set(Value.kForward);
			
			wait((long) .5f);
		
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
