package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrikePID {

	double Ki, Kd, period;

	TrikeDrive trikeDrive;
	private double oldErrors, last_Et;
	private TrikeEncoder encoder;
	private VictorSP motor;
	public final static double TOP_SPEED = 14.1386111;
	double Kp = (1 / TrikePID.TOP_SPEED);

	TrikeTimer periodTimer = new TrikeTimer();

	TrikeTimer totalTimer = new TrikeTimer();

	public TrikePID(double Kp, double Ki, double kd, double period,
			TrikeEncoder encoder, VictorSP motor) {
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
		double i = Ki * (oldErrors + curError)* totalTimer.get();
		oldErrors += curError;
		return i;
	}

	public double CalculateD(double Et, double Et_minus_1) {
		return Kd * ((Et - Et_minus_1));
	}

	public void setSpeedFPS(double speed) {

	}

	public void setSpeed(double speed) {
		if (checkInterval()) {
			motor.set(motor.get()
					+ (Kp * (getEt(denormalizeSpeed(speed)))
							+ (normalizeSpeed(CalculateI(getEt(denormalizeSpeed(speed)))))
				));
		}

		SmartDashboard.putNumber("distance error",
				CalculateI(getEt(denormalizeSpeed(speed))));

		last_Et = getEt(denormalizeSpeed(speed));

	}

	private double getEt(double speed) {
		return (speed) - encoder.getRate();
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

}
