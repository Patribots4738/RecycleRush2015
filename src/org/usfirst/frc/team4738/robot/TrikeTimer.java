package org.usfirst.frc.team4738.robot;

public class TrikeTimer {

	private double startTime,deltaTime =0;
	
	
	public TrikeTimer (){
		
		
	}
	
	public void start(){
		startTime = System.nanoTime();
		
		
	}
	public double get(){
	return	(System.nanoTime()-startTime)/1000000;
	}
	
	public double Lap(){
		double deltaTime=startTime = System.nanoTime();
		reset();
		return deltaTime;
	}
	
	public void reset(){
		startTime = System.nanoTime();
	}
}
