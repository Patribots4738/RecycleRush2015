package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot implements SideCarConstants {

	Joystick leftStick = new Joystick(DRIVER_STATION_PORT[0]);
	Joystick rightStick = new Joystick(DRIVER_STATION_PORT[1]);
	ControlBoard controlBoard = new ControlBoard(DRIVER_STATION_PORT[2], 10);
	
	Joystick xboxJoystick = new Joystick(DRIVER_STATION_PORT[3]);
	
	TrikeDrive trikeDrive;
	Elevator elevator = new Elevator(PWM_PORT[0]);
	
	Compressor compressor = new Compressor(0);

	boolean isOpen = true;

	Camera camera;

	PositionTracking positionTracking = new PositionTracking(new int[]{18,19,20,23,24,25});
	
	public void robotInit() {
		camera = new Camera();
		trikeDrive = new TrikeDrive(PWM_PORT[1], PWM_PORT[2], leftStick, rightStick);
		
		controlBoard.setOutputs(0);
		
		SmartDashboard.putNumber("Set Kp",0);
		SmartDashboard.putBoolean("Reset encoders", false);
		SmartDashboard.putNumber("Set Speed", 1f);
		SmartDashboard.putNumber("normal speed",0);
		SmartDashboard.putInt("Elevator Pos", 0);
		trikeDrive.resetEncoders();
	}

	public void teleopInit(){
		trikeDrive.resetEncoders();
		//trikeDrive.leftEncoder.PID.Kp=.7;
		//trikeDrive.rightEncoder.PID.Kp=.7;
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() 
	{
		System.out.println(Thread.currentThread().getName() + "\n" + Thread.currentThread().toString());
		compressor.stop();
		elevator.Update();
		trikeDrive.writeEncoderData();
		trikeDrive.threeStepSpeedController(0.5f, 0.75f, 0.3f);
		if(SmartDashboard.getBoolean("Reset encoders"))
			trikeDrive.resetEncoders();
		elevator.setPosition(SmartDashboard.getInt("Elevator Pos"));
		positionTracking.UpdatePositionState();
	}
	
	public void autonomousInit()
	{
		trikeDrive.resetEncoders();
		compressor.stop();
		trikeDrive.DriveAutonomous(15f, .2f);
		System.out.println("auto ended");
	}
	
	public void testPeriodic() 
	{
		
	}

	//******************************************************
	boolean canThrow;
	boolean stopLoop;
	TrikeTimer timer = new TrikeTimer();
	
	void elevatorControl() {
		for (int i = 1; i < 5; i++) {
			if (controlBoard.getBoardButton(i).get() || xboxJoystick.getRawButton(i)) {
				elevator.setPosition(i);
			}
		}
	}

	void armControl() {
		if ((controlBoard.getBoardButton(6).get() || xboxJoystick.getRawButton(6)) && canThrow) {
			if (isOpen) {
				setArm(true);
			} else {
				setArm(false);
			}
			isOpen = !isOpen;
			canThrow = false;
		} else if (!controlBoard.getBoardButton(6).get() || xboxJoystick.getRawButton(6)) {
			canThrow = true;
		}
	}
	
	void setArm(boolean state) {
		if (state) {
			elevator.arm.set(Value.kReverse);
			timer.waitMill(500);
			elevator.arm.set(Value.kOff);
		} else {
			elevator.arm.set(Value.kForward);
			timer.waitMill(500);
			elevator.arm.set(Value.kOff);
		}
		timer.waitMill(1000);
	}
	//******************************************************
}