package org.firstinspires.ftc.teamcode;

import android.graphics.Camera;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp
public class Turret_Main extends OpMode {
    private Limelight3A apriltagcamera;
    private TurretBackgroundCode initialzingturret;

    double targetid = 34;


    @Override
    public void init() {
        initialzingturret = new TurretBackgroundCode();
        apriltagcamera = hardwareMap.get(Limelight3A.class, "limelight_camera");
        initialzingturret.init(hardwareMap);
        apriltagcamera.pipelineSwitch(0);
        telemetry.addLine("Camera has been activated");

        telemetry.addLine("Turret Activated");
        // Tell William or someone to rename this and put the name here
    }

    @Override
    public void start() {
        initialzingturret.reset_timer();
        apriltagcamera.start();


    }

    @Override
    public void loop() {
        LLResult result = apriltagcamera.getLatestResult();

        double txvalue = 0;
        boolean tagidentified = false;

        if (result != null) {
            for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
                if (tag.getFiducialId() == targetid) {
                    txvalue = tag.getTargetXDegrees();
                    tagidentified = true;

                }
            }
        if (gamepad1.aWasPressed()){
            initialzingturret.definingkp(initialzingturret.recieving_kp()+ 0.0005);

        }
        if (gamepad1.bWasPressed()){
            initialzingturret.definingkp(initialzingturret.recieving_kp() - 0.0005);
        }

        if (gamepad1.aWasPressed()){
            initialzingturret.definingkp(initialzingturret.reciving_kd() + 0.0005);

        }
        if (gamepad1.bWasPressed()){
            initialzingturret.definingkp(initialzingturret.reciving_kd() - 0.0005);
        }

        // This part of the code allows me to manually tune values while controlling the bot

        initialzingturret.update(txvalue, tagidentified);
        telemetry.addData("Tag Dectected", tagidentified);
        telemetry.addData("Curret Tx - DEGRESS", txvalue);
        telemetry.addData("kp value", initialzingturret.recieving_kp());
        telemetry.addData("kd value", initialzingturret.reciving_kd());
        telemetry.update();

        // Outputs stuff like Ryan's Driver Station



        }








    }
}
