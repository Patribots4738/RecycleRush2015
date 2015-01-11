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
	
	CANTalon elevator;
	Trike trike;
	Encoder elevatorEncoder = new Encoder(DIO_PORT[6], DIO_PORT[7], true, EncodingType.k1X);

	
	private int currentPos;
	
	
	
	public Elevator(int port, Trike trike) {
		elevator= new CANTalon(port);
		this.trike = trike;
	}

	
	
	private void setPosition(){
	

		if(elevatorEncoder.get()>trike.armState[currentPos]){
			elevator.set(-1);
		} else if(elevatorEncoder.get()<trike.armState[currentPos]){
			elevator.set(1);

		}else{
			elevator.set(0);
		}
		
	
	}

	public void setPosition(int curPos){
	
		currentPos = curPos;

		setPosition();
	
	}
	
	public void initEncoder(){
		while(!trike.elevatorBottom.get()){
		elevator.set(.5);
		}
		elevatorEncoder.reset();
	}
	
	
	public void Update(){	
		
		setPosition();
	
	}
}
