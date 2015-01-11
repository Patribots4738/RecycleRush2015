package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * @author PatriBots4738
 *	This Initializes the Elevator motor, the solenoid arm, the elevator safety top and bottom switches,
 *	And the 3 encoders
 */
public class Trike implements SideCarConstants {

	Solenoid arm = new Solenoid(PCM_PORT[0]);

	DigitalInput elevatorTop = new DigitalInput(DIO_PORT[0]);
	DigitalInput elevatorBottom = new DigitalInput(DIO_PORT[1]);
	
	Encoder leftEncoder = new Encoder(DIO_PORT[2], DIO_PORT[3], true, EncodingType.k1X);
	Encoder rigtEncoder = new Encoder(DIO_PORT[4], DIO_PORT[5], true, EncodingType.k1X);
	

	//TODO: Load this array with all of the different arm heights.
	int[] armState = {};

}
