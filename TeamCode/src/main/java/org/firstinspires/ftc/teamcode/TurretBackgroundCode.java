package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class TurretBackgroundCode {

    private CRServo turret_shooter;
    private double kp = 0.02;
    private double kd = 0.0;
    private double goalx = 0;
    private double lasterror = 0;
    private double max_power = 0.6;
    private double deadband = 1.0; // degrees; stop jittering when close enough

    private final ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hwMap) {
        turret_shooter = hwMap.get(CRServo.class, "turret");
        timer.reset();
    }

    public void definingkp(double calculatedkp) { kp = calculatedkp; }
    public double recieving_kp() { return kp; }

    public void definingkd(double calculatedkd) { kd = calculatedkd; }
    public double reciving_kd() { return kd; }

    public void reset_timer() {
        timer.reset();
    }

    public void update(AprilTagDetection curID) {
        double dT = timer.seconds();
        reset_timer();

        if (curID == null || curID.ftcPose == null) {
            turret_shooter.setPower(0);
            lasterror = 0;
            return;
        }

        double error = goalx - curID.ftcPose.bearing;

        if (Math.abs(error) < deadband) {
            turret_shooter.setPower(0);
            lasterror = error;
            return;
        }

        double derivative = (dT > 0) ? (error - lasterror) / dT : 0;
        double output = kp * error + kd * derivative;

        output = Math.max(-max_power, Math.min(max_power, output));

        turret_shooter.setPower(output);
        lasterror = error;
    }
}