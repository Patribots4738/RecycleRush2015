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
	Trike trike;
	TrikeEncoder elevatorEncoder = new TrikeEncoder(DIO_PORT[4], DIO_PORT[5], true, EncodingType.k1X,4);

	
	private int currentPos;
	
	public Elevator(int port, Trike trike) {
		elevator= new Talon(port);
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
	
	/*public void initEncoder(){
		while(!trike.elevatorBottom.get()){
		elevator.set(.5);
		}
		elevatorEncoder.reset();
	}*/
	
	
	public void Update(){	
		
		setPosition();
	
	}
}
