package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot implements SideCarConstants {

	Joystick leftStick = new Joystick(DRIVER_STATION_PORT[0]);
	Joystick rightStick = new Joystick(DRIVER_STATION_PORT[1]);
	ControlBoard controlBoard = new ControlBoard(DRIVER_STATION_PORT[2], 10);

	Joystick xboxJoystick = new Joystick(DRIVER_STATION_PORT[1]);

	TrikeDrive trikeDrive;
	Elevator elevator = new Elevator(RELAY_PORT[0]);

	Compressor compressor = new Compressor(0);

	boolean isOpen = true;

	Camera camera;

	PositionTracking positionTracking = new PositionTracking(new int[] { 18,
			19, 20, 23, 24, 25 });

	public void robotInit() {
		camera = new Camera();
		trikeDrive = new TrikeDrive(PWM_PORT[1], PWM_PORT[2], leftStick, rightStick);

		controlBoard.setOutputs(0);

		// SmartDashboard.putBoolean("Reset encoders", false);
		SmartDashboard.putNumber("Autonomous Speed", 5);
		SmartDashboard.putNumber("Autonomous Distance", 5);
		SmartDashboard.putBoolean("Autonomous 1", false);
		SmartDashboard.putBoolean("Autonomous 2", false);
		SmartDashboard.putBoolean("Autonomous 3", false);
		SmartDashboard.putInt("Elevator Pos", 0);
		SmartDashboard.putBoolean("Override", false);
		trikeDrive.resetEncoders();
	}

	public void teleopInit() {
		// trikeDrive.resetEncoders();
		// trikeDrive.leftEncoder.PID.Kp=.7;
		// trikeDrive.rightEncoder.PID.Kp=.7;

		//while (elevator.elevatorInit()) {}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// System.out.println(Thread.currentThread().getName() + "\n" +
		// Thread.currentThread().toString());
		// compressor.stop();
		// THIS IS DIRECT ELEVSTOR CONTROL
		if (!SmartDashboard.getBoolean("Override")) {
			//if ((controlBoard.getBoardButton(1).get() || xboxJoystick.getRawButton(1)) 	|| leftStick.getRawButton(4) && !elevator.elevatorBottom.get()) {
				// System.out.println("move");
			/*if(leftStick.getRawButton(3)){
				elevator.elevator.set(Relay.Value.kForward);
			} else if ((controlBoard.getBoardButton(4).get() || xboxJoystick.getRawButton(4)) 
					|| leftStick.getRawButton(2) && !elevator.elevatorTop.get()) {
				// System.out.println("move");
				elevator.elevator.set(Relay.Value.kReverse);
			} else {
				elevator.elevator.set(Relay.Value.kOff);

			}*/
			
			if((xboxJoystick.getY() > 0.1 || xboxJoystick.getRawButton(2)) && !elevator.elevatorBottom.get())
			{
				elevator.elevator.set(Relay.Value.kForward);
			}
			else if((xboxJoystick.getY() < -0.1 || xboxJoystick.getRawButton(3)) && !elevator.elevatorTop.get())
			{
				elevator.elevator.set(Relay.Value.kReverse);
			}
			else{
				elevator.elevator.set(Relay.Value.kOff);
			}
		}

		if (SmartDashboard.getBoolean("Override")) {
			elevator.Update();
			elevatorControl();
		}
		//elevator.writeData();
		//setArm(!leftStick.getTrigger());
		setArm(!xboxJoystick.getTrigger());
		//armControl();
		if (elevator.elevatorBottom.get())
			elevator.encoder.reset();
		//positionTracking.UpdatePositionState();
		  trikeDrive.writeEncoderData();
		  trikeDrive.threeStepSpeedController(1f, 0.25f, 0.75f, true);
		 
	}

	public void autonomousInit() {

		timer.reset();
		
		while (elevator.elevatorInit()) {
			if(timer.get() < 15000)
				break;
		}
		trikeDrive.resetEncoders();
		
		if(false)//if(timer.get() < 14000)
		{
			//if (SmartDashboard.getBoolean("Autonomous 1")) {
			
				
				setArm(true);
				trikeDrive.DriveAutonomous(1f, -0.5f);
				setArm(false);
				timer.waitMill(5000);
				trikeDrive.DriveAutonomous(5f, -0.5f);
			//}
	
			/*if (SmartDashboard.getBoolean("Autonomous 2")) {
				elevator.arm.set(Value.kReverse);
				trikeDrive.rotate90right((float) SmartDashboard.getNumber("Autonomous Speed"));
				trikeDrive.DriveAutonomous((float) SmartDashboard.getNumber("Autonomous Distance"),(float) SmartDashboard.getNumber("Autonomous Speed"));
				elevator.arm.set(Value.kForward);
			}
	
			if (SmartDashboard.getBoolean("Autonomous 3")) {
				trikeDrive.DriveAutonomous((float) SmartDashboard.getNumber("Autonomous Distance"),(float) SmartDashboard.getNumber("Autonomous Speed"));
				elevator.arm.set(Value.kReverse);
				trikeDrive.DriveAutonomous((float) SmartDashboard.getNumber("Autonomous Distance"),(float) SmartDashboard.getNumber("Autonomous Speed"));
				elevator.arm.set(Value.kForward);
			}*/
		}
		setArm(false);
	}

	public void testPeriodic() {

	}

	// ******************************************************
	boolean canThrow;
	boolean stopLoop;
	TrikeTimer timer = new TrikeTimer();

	boolean isOpenBump;
	boolean canThrowBump;

	void elevatorControl() {
		for (int i = 1; i < 3; i++) {

			if (controlBoard.getBoardButton(i).get()
					|| xboxJoystick.getRawButton(i) || leftStick.getRawButton(i + 5)) {
				// System.out.println("move");
				elevator.setPosition(i - 1);
			}
		}

		if (controlBoard.getBoardButton(3).get()
				|| xboxJoystick.getRawButton(3) || leftStick.getRawButton(4)) {
			// System.out.println("move");
			elevator.setPosition(2);
		}

		if (controlBoard.getBoardButton(4).get()
				|| xboxJoystick.getRawButton(4) || leftStick.getRawButton(4)) {
			// System.out.println("move");
			elevator.setPosition(3);
		}

		/*
		 * if (( xboxJoystick .getRawButton(10)) && canThrowBump) { if
		 * (isOpenBump) { elevator.bump(); } else { elevator.bump(); }
		 * isOpenBump = !isOpenBump; canThrowBump = false; } else if
		 * (xboxJoystick.getRawButton(10)) { canThrowBump = true; } if
		 * (xboxJoystick.getRawButton(10)) { elevator.bump();
		 * 
		 * }
		 */
	}

	void armControl() {
		if ((xboxJoystick.getTrigger() || leftStick.getTrigger()) && canThrow) {
			if (isOpen) {
				setArm(true);
			} else {
				setArm(false);
			}
			isOpen = !isOpen;
			canThrow = false;
		} else if (!xboxJoystick.getTrigger() ||  leftStick.getTrigger()) {
			canThrow = true;
		}
	}

	void setArm(boolean state) {
		if (state) {
			//if(elevator.arm.get() == Value.kForward)
			//{
				elevator.arm.set(Value.kOff);
				elevator.arm.set(Value.kReverse);
			//}
		} else {
			//if(elevator.arm.get() == Value.kReverse)
			//{
				elevator.arm.set(Value.kOff);
				elevator.arm.set(Value.kForward);
			//}
		}
	}
	// ******************************************************
}