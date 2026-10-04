package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TurretBackgroundCode {

    private CRServo turret_shooter;   // servo 1
    private CRServo turret_shooter2;  // servo 2 (1:1 geared to the same turret)
    private double kp = 0.02;
    private double kd = 0.0;
    private double goalx = 0;
    private double lasterror = 0;
    private double max_power = 0.6;
    private double deadband = 1.0; // degrees; prevents jittering

    private final ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hwMap) {
        turret_shooter  = hwMap.get(CRServo.class, "turret");
        turret_shooter2 = hwMap.get(CRServo.class, "turret2"); // <-- must match your config name

        // If the servos face opposite ways, ONE of them needs to be reversed
        // turret_shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        // turret_shooter2.setDirection(DcMotorSimple.Direction.REVERSE); //Uncomment whicheverone needs to be reversed

        timer.reset();

    }

    public void definingkp(double calculatedkp) { kp = calculatedkp; }
    public double recieving_kp() { return kp; }

    public void definingkd(double calculatedkd) { kd = calculatedkd; }
    public double reciving_kd() { return kd; }

    public void reset_timer() {
        timer.reset();
    }

    // One place that powers BOTH servos, so one can never be forgotten.
    private void setTurretPower(double power) {
        turret_shooter.setPower(power);
        turret_shooter2.setPower(power);
    }

    public void update(double tx, boolean tagFound) {
        double dT = timer.seconds();
        reset_timer();

        if (!tagFound) {
            setTurretPower(0);
            lasterror = 0;
            return;
        }

        double error = goalx - tx;

        if (Math.abs(error) < deadband) {
            setTurretPower(0);
            lasterror = error;
            return;
        }

        double derivative = (dT > 0) ? (error - lasterror) / dT : 0;
        double output = kp * error + kd * derivative;

        output = Math.max(-max_power, Math.min(max_power, output));

        setTurretPower(output);
        lasterror = error;
    }
}