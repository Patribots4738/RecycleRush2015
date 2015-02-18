package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author PatriBots4738
 * Subsystem to control the Elevator
 * 
 * NOTE: Made with the assumption that encoder values can go backwards
 */
public class Elevator implements SideCarConstants {
	
	VictorSP elevator; //Motor controller
	
	//Creating an object of the custom encoder class so we can get 
	//ElevatorEncoder encoder = new ElevatorEncoder(DIO_PORT[4], DIO_PORT[5], false, EncodingType.k4X, 3.25);
	
	//Why are we using a new encoding class if this one works perfectly fine?
	TrikeEncoder encoder = new TrikeEncoder(DIO_PORT[4], DIO_PORT[5], false, EncodingType.k1X, 3.25);
	DoubleSolenoid arm = new DoubleSolenoid(PCM_PORT[0],PCM_PORT[1]);

	//These are the limitSwitches 5v returns a true or a 1, normally closed
	DigitalInput elevatorTop = new DigitalInput(DIO_PORT[6]);
	DigitalInput elevatorBottom = new DigitalInput(DIO_PORT[7]);
	
	TrikeTimer timer = new TrikeTimer();
	
	//
	
	final double[] armState = {0, 1, 2, 3, 4, 4.5};//feet
	 private double deadBand = .1;//feet
	
	 private final double MAX_SPEED = 7.656; //feet per second
	private final double SPEED_CAP =  5;//feet per second
	
	private int currentPos; // The position that the bot is currently at
	
	private final double ELEVATOR_HEIGHT=3.83; //feet
	public double Kp= .1;//a number
	
	public Elevator(int port) {
		elevator= new VictorSP(port);
		SmartDashboard.putNumber("Elevator Speed", 0);
	}	
	
	
	private void setPosition(){
		if(Math.abs(SmartDashboard.getNumber("Elevator Pos")-encoder.getDistance())>deadBand)
			 velocityControl();
		
		
		if(elevatorBottom.get()&& elevator.get()<=0||
				elevatorTop.get()&& elevator.get()>=0
				){
			elevator.set(0);
		}
		
		/*else if(Math.abs(getTargetSpeed()) < 0.34 && Math.abs(getTargetSpeed()) > 0.01)
		{
			if(getTargetSpeed()>0)
			elevator.set(0.35);
			else
				elevator.set(-0.35);

		}*/
		
	}

	
	private void velocityControl()
	{
		
		double error =(getTargetSpeed()-(encoder.getRate()/SPEED_CAP));
		
		elevator.set(elevator.get()+(Kp*error));
	}
	
	private double getCorrectedSpeed(){
		elevator.set(getTargetSpeed());
		timer.waitMill(5);
		double error =(getTargetSpeed()-(encoder.getRate()/SPEED_CAP));
		return elevator.get()+(Kp*error);
	}
	
	private double getTargetSpeed(){
	double error = (SmartDashboard.getNumber("Elevator Pos")-encoder.getDistance());
		return (error/ELEVATOR_HEIGHT)*getNormalizeSpeedCap(); //f per s
	}
	
	public void setPosition(int curPos){
		currentPos = curPos;
		setPosition();
	}
	
	public void setDeadBand(double deadBand){
		this.deadBand= deadBand;
	}
	
	//Normalized the speed cap -1 <-> 1
	public double getNormalizeSpeedCap(){
		return SPEED_CAP/MAX_SPEED; //0.26
	}
	
	public void writeData()
	{
		SmartDashboard.putNumber("Elevator Encoder:Raw", encoder.get());
		SmartDashboard.putNumber("Elevator Encoder:Speed", encoder.getRate());
		SmartDashboard.putNumber("Elevator Encoder:Distance", encoder.getDistance());
		SmartDashboard.putNumber("Elevator Encoder:Rotations", encoder.getRotations());
		SmartDashboard.putBoolean("Elevator Top", elevatorTop.get());
		SmartDashboard.putBoolean("Elevator Bottom", elevatorBottom.get());
		SmartDashboard.putNumber("target speed", getTargetSpeed());
	}
	
	//Updates the arm position every 20ms
	public void Update(){	
		writeData();
		setPosition();
	}
}