package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class ControlBoard extends Joystick {

	private JoystickButton[] buttons;
	boolean isPressed[];

	public ControlBoard(int port, int buttonCount) {
		super(port);

		buttons = new JoystickButton[buttonCount];

		for (int i = 0; i < buttonCount; i++) {
			buttons[i] = new JoystickButton(this, i);

		}
	}
	
	public JoystickButton getBoardButton(int buttonNumber){
		return buttons[buttonNumber];
	}
	
	public int getButtonCount(){
		return buttons.length;
	}

}
