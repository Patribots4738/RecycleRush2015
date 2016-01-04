package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author PatriBots4738 Subsystem to control the Elevator
 * 
 *         NOTE: Made with the assumption that encoder values can go backwards
 */
public class Elevator implements SideCarConstants {

	Relay elevator; // Motor controller

	// Creating an object of the custom encoder class so we can get
	// ElevatorEncoder encoder = new ElevatorEncoder(DIO_PORT[4], DIO_PORT[5],
	// false, EncodingType.k4X, 3.25);

	// Why are we using a new encoding class if this one works perfectly fine?
	TrikeEncoder encoder = new TrikeEncoder(DIO_PORT[4], DIO_PORT[5], false,
			EncodingType.k1X, 3.25);
	DoubleSolenoid arm = new DoubleSolenoid(PCM_PORT[0], PCM_PORT[1]);

	// These are the limitSwitches 5v returns a true or a 1, normally closed
	DigitalInput elevatorTop = new DigitalInput(DIO_PORT[6]);
	DigitalInput elevatorBottom = new DigitalInput(DIO_PORT[7]);

	//TrikeTimer timer = new TrikeTimer();

	//

	final double[] armState = { .2, -1.13, -2.53, -3.83 };// feet
	private double deadBand = .05;// feet

	private final double MAX_SPEED = 7.656; // feet per second
	private final double SPEED_CAP = 3;// feet per second

	private int currentPos = 0; // The position that the bot is currently at

	private final double ELEVATOR_HEIGHT = 3.83; // feet

	public double speed = .3;

	public double offset = 0;

	public Elevator(int port) {
		elevator = new Relay(port);

	}

	public boolean elevatorInit() {

		elevator.set(Value.kForward);
		if (elevatorBottom.get()) {
			elevator.set(Value.kOff);
			encoder.reset();
			return false;

		}
		return true;
	}

	private void setPosition() {
		double diff = (armState[currentPos] - offset) - encoder.getDistance();
		if (Math.abs(diff) > deadBand) {
			System.out.println("passed deadband");
			if (diff < 0 && !elevatorTop.get())
				elevator.set(Value.kReverse);
			else if (diff > 0 && !elevatorBottom.get())
				elevator.set(Value.kForward);
			else
				elevator.set(Value.kOff);
		} else {
			elevator.set(Value.kOff);
		}

	}

	public void setPosition(int curPos) {
		currentPos = curPos;
		setPosition();
	}

	public void setDeadBand(double deadBand) {
		this.deadBand = deadBand;
	}

	// Normalized the speed cap -1 <-> 1
	public double getNormalizeSpeedCap() {
		return SPEED_CAP / MAX_SPEED; // 0.26
	}

	public void writeData() {
		SmartDashboard.putNumber("Elevator Encoder:Raw", encoder.get());
		SmartDashboard.putNumber("Elevator Encoder:Speed", encoder.getRate());
		SmartDashboard.putNumber("Elevator Encoder:Distance",
				encoder.getDistance());
		SmartDashboard.putNumber("Elevator Encoder:Rotations",
				encoder.getRotations());
		SmartDashboard.putBoolean("Elevator Top", elevatorTop.get());
		SmartDashboard.putBoolean("Elevator Bottom", elevatorBottom.get());
		SmartDashboard.putNumber("Elevator Speed", 0);
		SmartDashboard.putNumber("Elevator Position", currentPos);
	}

	// Updates the arm position every 20ms
	public void Update() {
		setPosition();
	}

	public void bump() {
		if (offset > 0) {
			offset = 0;
		} else {
			offset = .16666;
		}
	}
}