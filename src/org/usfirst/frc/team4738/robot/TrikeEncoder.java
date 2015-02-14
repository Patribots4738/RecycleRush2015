package org.usfirst.frc.team4738.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrikeEncoder extends Encoder {


	public TrikePID PID;
	
	public TrikeEncoder(int aChannel, int bChannel, boolean reverseDirection,
			EncodingType encodingType, double diameter,VictorSP motor) {
		super(aChannel, bChannel, reverseDirection, encodingType);
		DIAMETER = diameter;
		CIRCUMFERENCE =Math.PI * DIAMETER;
		setDistancePerPulse(.001);
		
		PID = new TrikePID( 1/PID.TOP_SPEED,1,0,.05,this,motor);
	}

	public TrikeEncoder(int aChannel, int bChannel, boolean reverseDirection,
			EncodingType encodingType, double diameter) {
		super(aChannel, bChannel, reverseDirection, encodingType);
		DIAMETER = diameter;
		CIRCUMFERENCE =Math.PI * DIAMETER;
		setDistancePerPulse(.001);
		
	}


	public void setKp(){
		PID.Kp=(double)SmartDashboard.getNumber("Set Kp");
		
	}
	public double DIAMETER;
	public double CIRCUMFERENCE;
	
	public double getRotations()
	{
		return (double)get() / 2048;
	}
	
	public double getDistance()
	{
		return getRotations() * CIRCUMFERENCE / 12;
	}
	
}
