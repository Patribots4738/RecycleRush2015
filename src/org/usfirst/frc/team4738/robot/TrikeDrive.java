package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * @author PatriBots4738 The Drive Class for the Patribots 2015 Robot
 */
public class TrikeDrive implements SideCarConstants {

	public VictorSP leftMotor, rightMotor;
	public TrikeEncoder leftEncoder;
	public TrikeEncoder rightEncoder ;
	Joystick leftStick, rightStick;

	final double ROTATION_DIAMETER = 22.625;

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

		this.leftMotor = new VictorSP(PWM_PORT[leftMotor]);
		this.rightMotor = new VictorSP(PWM_PORT[rightMotor]);
		this.leftStick = leftStick;
		this.rightStick = rightStick;
		rightEncoder = new TrikeEncoder(DIO_PORT[0],
				DIO_PORT[1], false, EncodingType.k4X, 7.65, this.rightMotor);
		leftEncoder =   new TrikeEncoder(DIO_PORT[3],
				DIO_PORT[2], false, EncodingType.k4X, 7.65, this.leftMotor);
		
	}

	private float DEAD_ZONE = .3f;
	private int i = 0;

	public void writeEncoderData() {
		SmartDashboard.putNumber("Right Encoder:Feet",
				rightEncoder.getDistance());
		SmartDashboard
				.putNumber("Left Encoder:Feet", leftEncoder.getDistance());
		SmartDashboard.putNumber("Right Encoder:Rotations",
				rightEncoder.getRotations());
		SmartDashboard.putNumber("Left Encoder:Rotations",
				leftEncoder.getRotations());
		SmartDashboard.putNumber("Right Encoder:Raw", rightEncoder.get());
		SmartDashboard.putNumber("Left Encoder:Raw", leftEncoder.get());
		SmartDashboard.putNumber("Right Encoder:Speed", rightEncoder.getRate());
		SmartDashboard.putNumber("Left Encoder:Speed", leftEncoder.getRate());
		SmartDashboard.putNumber("Time Elapsed", Utils.getTime());

	}

	/**
	 * Player control for the robot
	 */
	public void player() {
		player(1);
	}

	public void player(float speed) {

		if (Math.abs(leftStick.getY()) >= DEAD_ZONE) {
			leftEncoder.PID.setSpeed(leftStick.getY() * speed);
		} else {
			leftEncoder.PID.setSpeed(0);

		}

		if (Math.abs(rightStick.getY()) >= DEAD_ZONE) {
			rightEncoder.PID.setSpeed(-rightStick.getY() * speed);
		} else {
			rightEncoder.PID.setSpeed(0);
		}
	}
	
	public void playerArcade()
	{
		leftEncoder.PID.setSpeed(-(leftStick.getY() * leftStick.getX()));
		rightEncoder.PID.setSpeed(leftStick.getY() * leftStick.getX());
	}
	
	public void threeStepSpeedController(float button3Speed,
			float button2Speed, float speedCap) {
		if (rightStick.getRawButton(3) || leftStick.getRawButton(3))
			player(button3Speed);
		else if (rightStick.getRawButton(2) || leftStick.getRawButton(2))
			player(button2Speed);
		else
			player(speedCap);
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

	public void DriveAutonomous(float feet, float speed) {
		
		resetEncoders();
		while (rightEncoder.getDistance() < feet) {
			setMotors(speed);
			writeEncoderData();
		}
		setMotors(0);

	}

	public void DriveAutonomous(float feet) {
		DriveAutonomous(feet, 1);
	}

	PIDController PIDc;
	void setMotors(double speed) {
		writeEncoderData();
		
		leftEncoder.PID.setSpeed(-speed);
		rightEncoder.PID.setSpeed(speed);

	}

	// THIS IS IN FEET PER SECOND
	void setMotorsFps(double speed) {
		writeEncoderData();
		leftEncoder.PID.setSpeedFPS(-speed);
		rightEncoder.PID.setSpeedFPS(speed);

	}

	void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	void rotate90left(double speed) {
		resetEncoders();
		double distance = (2 * Math.PI * (ROTATION_DIAMETER / 2)) / 4;

		while (leftEncoder.getDistance() < distance) {
			leftMotor.set(-speed);
		}
		leftMotor.set(0);
	}

	void rotate90right(double speed) {
		resetEncoders();
		double distance = (2 * Math.PI * (ROTATION_DIAMETER / 2)) / 4;

		while (rightEncoder.getDistance() < distance) {
			rightMotor.set(speed);
		}
		rightMotor.set(0);
	}

}
