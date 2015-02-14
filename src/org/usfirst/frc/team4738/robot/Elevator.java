package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * @author PatriBots4738
 * Subsystem to control the Elevator
 * 
 * NOTE: Made with the assumption that encoder values can go backwards
 */
public class Elevator implements SideCarConstants {
	
	Talon elevator;
	
	ElevatorEncoder encoder = new ElevatorEncoder(DIO_PORT[4], DIO_PORT[5], true, EncodingType.k1X,3.25);
	DoubleSolenoid arm = new DoubleSolenoid(PCM_PORT[0],PCM_PORT[1]);

	DigitalInput elevatorTop = new DigitalInput(DIO_PORT[7]);
	DigitalInput elevatorBottom = new DigitalInput(DIO_PORT[8]);
	
	final int[] armState = {0};
	
	 private double deadBand = .2;
	
	
	private double speedCap =.75; 
	
	private int currentPos;
	
	private final double elevatorHeight=5;
	
	public double Kp= 1/elevatorHeight;
	
	public Elevator(int port) {
		elevator= new Talon(port);
	
	}	
	
	
	
	private void setPosition(){
	
		if(elevatorBottom.get()){
			encoder.reset();
		}

		if(getEt()>deadBand){
		elevator.set(Kp*getEt()*speedCap);
		}
	
	}

	private double getEt(){
		return armState[currentPos]-encoder.getDistance();
	}
	
	public void setPosition(int curPos){
	
		currentPos = curPos;

		setPosition();
	
	}
	
	
	

	public void setDeadBand(double deadBand){
		this.deadBand= deadBand;
		
	}
	
	public double getNormalizeSpeedCap(){
		return  encoder.CIRCUMFERENCE;
	}
	
	public void Update(){	
		
		setPosition();
	
	}
}
