package org.usfirst.frc.team4738.robot;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

public class TrikeEncoder extends Encoder{

	public static double startTime;
	
	public TrikeEncoder(int aChannel, int bChannel, boolean reverseDirection,
			EncodingType encodingType, double diameter) {
		super(aChannel, bChannel, reverseDirection, encodingType);
		DIAMETER = diameter;
		CIRCUMFERENCE =Math.PI * DIAMETER;
		setDistancePerPulse(.001);
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
	
	public double getTime()
	{
		return (System.nanoTime() - startTime)/1000000000;
	}
}
