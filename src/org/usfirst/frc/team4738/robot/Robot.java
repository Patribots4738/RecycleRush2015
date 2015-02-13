package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
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

	TrikeDrive trikeDrive = new TrikeDrive(PWM_PORT[1], PWM_PORT[2], leftStick,
			rightStick);

	Elevator elevator = new Elevator(PWM_PORT[0], trike);

	boolean isOpen = true;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	Camera camera;

	public void robotInit() {

		controlBoard.setOutputs(0);

		camera = new Camera();
		
		trikeDrive.setDeadZone(0.1f);
		
		SmartDashboard.putBoolean("Reset encoders", false);
		SmartDashboard.putNumber("Set Speed", 1f);
		
		trikeDrive.resetEncoders();
		
		// elevator.initEncoder();

		// TODO: LIMIT SWITCH CONTROLS THIS
		/*
		 * if (trike.arm.get()) { trike.arm.set(false); }
		 */

	}

	DigitalInput limitSwtich = new DigitalInput(DIO_PORT[8]);
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() 
	{
		
		

		trikeDrive.writeEncoderData();
		trikeDrive.threeStepSpeedController(0.25f, 0.5f, 0.75f);
		// elevatorControl();
		// elevator.Update();
		if(SmartDashboard.getBoolean("Reset encoders"))
			trikeDrive.resetEncoders();
		armControl();

		if (limitSwtich.get()) {
			controlBoard.setOutputs(0b0);
		} else {
			controlBoard.setOutputs(0b111111111111);
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
		if (controlBoard.getBoardButton(6).get() && canThrow) {

			if (isOpen) {
				setArm(true);
			} else {

				setArm(false);
			}

			isOpen = !isOpen;
			canThrow = false;

		} else if (!controlBoard.getBoardButton(6).get()) {
			canThrow = true;
		}

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousInit() {
		TrikeEncoder.startTime = System.nanoTime();
		
		trikeDrive.resetEncoders();
		trikeDrive.DriveAutonomous(5f, (float)SmartDashboard.getNumber("Set Speed"));
	}
	
	public void autonomousPeriodic()
	{
		
	}
	
	boolean stopLoop;

	void setArm(boolean state) {
		try {

			if (state) {
				trike.arm.set(Value.kReverse);
				Thread.sleep(500); // won't this stop controlling?
				trike.arm.set(Value.kOff);// Should we make a new Thread?
			} else {
				trike.arm.set(Value.kForward);
				Thread.sleep(500);
				trike.arm.set(Value.kOff);

			}
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	int i = 0;

	public void testPeriodic() 
	{
		
	}

}
