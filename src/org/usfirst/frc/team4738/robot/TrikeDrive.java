package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author PatriBots4738 The Drive Class for the Patribots 2015 Robot
 */
public class TrikeDrive implements SideCarConstants {

	public VictorSP leftMotor, rightMotor;
	public TrikeEncoder leftEncoder;
	public TrikeEncoder rightEncoder;
	Joystick leftStick, rightStick;

	final double ROTATION_DIAMETER = 22.625;
	double old_speed;

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
	TrikeDrive(int leftMotor, int rightMotor, 
				Joystick leftStick, Joystick rightStick) {

		this.leftMotor = new VictorSP(PWM_PORT[leftMotor]);
		this.rightMotor = new VictorSP(PWM_PORT[rightMotor]);

		this.leftStick = leftStick;
		this.rightStick = rightStick;
		
		rightEncoder = new TrikeEncoder(DIO_PORT[1], DIO_PORT[0], false,
					EncodingType.k4X, 7.65, this.rightMotor);
		leftEncoder = new TrikeEncoder(DIO_PORT[3], DIO_PORT[2], false,
					EncodingType.k4X, 7.65, this.leftMotor);
	}

	public void writeEncoderData() {
		SmartDashboard.putNumber("Right Encoder:Feet", rightEncoder.getDistance());
		SmartDashboard.putNumber("Left Encoder:Feet", leftEncoder.getDistance());
		/*SmartDashboard.putNumber("Right Encoder:Rotations", rightEncoder.getRotations());
		SmartDashboard.putNumber("Left Encoder:Rotations", leftEncoder.getRotations());
		SmartDashboard.putNumber("Right Encoder:Raw", rightEncoder.get());
		SmartDashboard.putNumber("Left Encoder:Raw", leftEncoder.get());	*/
		SmartDashboard.putNumber("Right Encoder:Speed", rightEncoder.getRate());
		SmartDashboard.putNumber("Left Encoder:Speed", leftEncoder.getRate());
	}

	/**
	 * Player control for the robot
	 */
	public void player() {
		player(0.75f);
	}
	
	double threshold = .3;

	public void player(float speed) {
		if(Math.abs(leftStick.getY())>.1)
			leftMotor.set(-leftStick.getY() * speed);
		if(Math.abs(rightStick.getY())>.1)
			rightMotor.set(rightStick.getY() * speed);
	}
	
	public void arcadePlayer(double x, double y)
	{
		leftMotor.set(x + y);
		rightMotor.set(-x + y);
	}
	
	public void threeStepSpeedController(float button3Speed, float button2Speed, float speedCap, boolean arcadeDrive)
	{
		if (rightStick.getRawButton(1))
			arcadePlayer(-leftStick.getY() * button3Speed, leftStick.getX() * button3Speed);
		else if (rightStick.getRawButton(3))
			arcadePlayer(-leftStick.getY() * button2Speed, leftStick.getX() * button2Speed);
		else
			arcadePlayer(-leftStick.getY() * speedCap, leftStick.getX() * speedCap);
	}


	public void threeStepSpeedController(float button3Speed, float button2Speed, float speedCap) {
		if (leftStick.getRawButton(3))
			player(button3Speed);
		else if (leftStick.getRawButton(2))
			player(button2Speed);
		else
			player(speedCap);
	}

	public void DriveAutonomous(float feet, float speed) {
		while (leftEncoder.getDistance() < feet) {
			setMotors(speed);
			writeEncoderData();
		}
		//Adding 0 will not stop the motors so setMotors(0) will not work
		leftMotor.set(0);
		rightMotor.set(0);
	}

	public void DriveAutonomous(float feet) {
		DriveAutonomous(feet, 1);
	}

	void setMotors(double speed) {
		writeEncoderData();
		leftEncoder.sumRate();
		rightEncoder.sumRate();
		leftEncoder.PID.setSpeed(-speed);
		rightEncoder.PID.setSpeed(speed);
		old_speed = speed;
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

	//Im sorry but what does this do? It doesn't seem to work?
	void rotate90left(double speed) {
		resetEncoders();
		double distance = (2 * Math.PI * (ROTATION_DIAMETER / 2)) / 4;
		while (leftEncoder.getDistance() < distance) {
			leftMotor.set(-speed);
		}
		leftMotor.set(0);
	}

	//Same with this. What does it do? It doesn't seem to work
	void rotate90right(double speed) {
		resetEncoders();
		double distance = (2 * Math.PI * (ROTATION_DIAMETER / 2)) / 4;
		while (rightEncoder.getDistance() < distance) {
			rightMotor.set(speed);
		}
		rightMotor.set(0);
	}
}
