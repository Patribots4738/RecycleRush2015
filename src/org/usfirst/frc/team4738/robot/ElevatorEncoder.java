package org.usfirst.frc.team4738.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorEncoder extends Encoder {


	public double DIAMETER;
	public double CIRCUMFERENCE;


	public ElevatorEncoder(int aChannel, int bChannel, boolean reverseDirection,
			EncodingType encodingType, double diameter) {
		super(aChannel, bChannel, reverseDirection, encodingType);
		DIAMETER = diameter;
		CIRCUMFERENCE =Math.PI * DIAMETER;
		setDistancePerPulse(.001);
		
	}


	
	
	public double getRotations()
	{
		return (double)get() / 2048;
	}
	
	public double getDistance()
	{
		return getRotations() * CIRCUMFERENCE / 12;
	}
	
}
