package org.usfirst.frc.team4738.robot;

public class Utils {
	
	public static double limit(double value, double high)
	{
		if(Math.abs(value) > high)
		{
			return high;
		}
		else
		{
			return value;
		}
	}

}
