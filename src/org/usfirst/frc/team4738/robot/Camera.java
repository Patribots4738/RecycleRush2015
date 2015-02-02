package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.CameraServer;


public class Camera 
{
	
	
	CameraServer server;

	
	
Camera(){
	 server = CameraServer.getInstance();
     server.setQuality(7);
     server.startAutomaticCapture("cam0");
     
}
}
