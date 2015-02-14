package org.usfirst.frc.team4738.robot;

public class Utils {

	public static double startTime;
	

	public static double getTime()
	{
		return (System.nanoTime() - startTime)/1000000000;
	}
}
