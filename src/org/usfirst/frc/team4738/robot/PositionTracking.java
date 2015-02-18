package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositionTracking {

	
	DigitalInput[] switches = new DigitalInput[6];
	
	public PositionTracking(int[] ports){
		
		for(int i =0; i< switches.length; i++){
			switches[i]  = new DigitalInput(ports[i]);
		}
		
	}
	
	public void UpdatePositionState(){
		for(DigitalInput in: switches  ){
			SmartDashboard.putBoolean("Switch Number" + in.getChannel(), in.get());
		}
	}
	
}
