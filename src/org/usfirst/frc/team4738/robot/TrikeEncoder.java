package org.usfirst.frc.team4738.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrikeEncoder extends Encoder {
	
	public TrikePID PID;
	public int interationCount;
	public double DIAMETER;
	public double CIRCUMFERENCE;
	private double speed, speedSum;
	
	TrikeTimer periodTimer = new TrikeTimer();
	
	public TrikeEncoder(int aChannel, int bChannel, boolean reverseDirection,
			EncodingType encodingType, double diameter,VictorSP motor) {
		super(aChannel, bChannel, reverseDirection, encodingType);
		
		DIAMETER = diameter;
		CIRCUMFERENCE =Math.PI * DIAMETER;
		
		setDistancePerPulse(.001);
		
		PID = new TrikePID( 0.08,50,0,50,this,motor);
		
		periodTimer.start();
	}

	public TrikeEncoder(int aChannel, int bChannel, 
						boolean reverseDirection, 
						EncodingType encodingType, 
						double diameter){
		super(aChannel, bChannel, reverseDirection, encodingType);
		DIAMETER = diameter;
		CIRCUMFERENCE =Math.PI * DIAMETER;
		setDistancePerPulse(.001);
		
	}
	
	//Sets the P constant
	public void setKp(){
		PID.Kp=(double)SmartDashboard.getNumber("Set Kp");	
	}
	
	public void sumRate(){
		
		if(checkInterval()){
			speed=speedSum/interationCount;
			interationCount=0;
			speedSum=0;
		}
		speedSum+=getRate();	
		interationCount++;
	}
	
	private boolean checkInterval() {
		if ((periodTimer.get() > 50)) {
			periodTimer.reset();
			return true;
		}
		return false;
	}
	
	public double getRotations()
	{
		return (double)get() / 2048;
	}
	
	public double getDistance()
	{
		return getRotations() * CIRCUMFERENCE / 12;
	}
	
	public double getAverageSpeed(){
		return speed;
	}
}