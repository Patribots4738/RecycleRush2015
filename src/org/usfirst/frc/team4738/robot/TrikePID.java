package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrikePID {

	double Kp, Ki, Kd, period;
	private double oldErrors, last_Et;
	
	public final static double TOP_SPEED = 14.1386111;

	TrikeDrive trikeDrive;
	private TrikeEncoder encoder;
	private VictorSP motor;

	TrikeTimer periodTimer = new TrikeTimer();
	TrikeTimer totalTimer = new TrikeTimer();

	public TrikePID(double Kp, double Ki, double kd,
					double period, TrikeEncoder encoder, VictorSP motor) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.period = period;
		this.encoder = encoder;
		this.motor = motor;

		totalTimer.start();
		periodTimer.start();
	}

	public double CalculateP(double Et) {
		return Kp * Et;
	}

	public double CalculateI(double curError) {
		double i = Ki * (oldErrors + curError)* totalTimer.get(); //Math is correct
		oldErrors += curError;
		return i;
	}

	public double CalculateD(double Et, double Et_minus_1) {
		return Kd * ((Et - Et_minus_1));//The Math look correct
	}

	public void setSpeedFPS(double speed) {
		//ummmm...?
	}

	public void setSpeed(double speed) {
		if (checkInterval()) {
			motor.set(motor.get()
					+ ( Kp * normalizeSpeed(getEt(speed*TOP_SPEED)) )//never subtracting

						//+ (normalizeSpeed( getEt(denormalizeSpeed(speed)))
							//	+ (normalizeSpeed(CalculateI(getEt(denormalizeSpeed(speed)))))
				);
		}
		last_Et = getEt(denormalizeSpeed(speed));
	}

	private double getEt(double speed) {
		SmartDashboard.putNumber("normal speed",encoder.getRate());//normalizeSpeed( encoder.getAverageSpeed()));
		return (speed) + encoder.getRate();
	}

	private boolean checkInterval() {
		if ((periodTimer.get() > period)) {
			periodTimer.reset();
			
			return true;
		}
		return false;
	}

	public static double normalizeSpeed(double speedin) {
		return speedin / TOP_SPEED;
	}

	public static double denormalizeSpeed(double speedin) {
		return speedin * TOP_SPEED;
	}
	
	public void setPeriod(double period){
		this.period=period;
	}
}