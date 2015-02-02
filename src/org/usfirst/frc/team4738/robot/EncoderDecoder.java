package org.usfirst.frc.team4738.robot;

public class EncoderDecoder {

	public final  double WHEEL_CIRCUMFERENCE = 7.65;
	public final double RADIUS = 1;
	public  int getRotations(double distance){return (int)(distance/ (WHEEL_CIRCUMFERENCE/4));}
	
}
