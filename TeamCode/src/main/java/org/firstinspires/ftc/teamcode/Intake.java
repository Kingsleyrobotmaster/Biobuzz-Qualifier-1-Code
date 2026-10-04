package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends OpMode {

    private DcMotor intakemotor;

    @Override
    public void init() {
        intakemotor = hardwareMap.get(DcMotor.class, "intakemotor");
        telemetry.addLine("Intake Activated");

    }

    @Override
    public void loop() {
        if (gamepad1.left_trigger_pressed){
            intakemotor.setPower(0.6);
        }
        else{
            intakemotor.setPower(0);
        }                           // Intaking

        if (gamepad1.leftBumperWasPressed()){
            intakemotor.setPower(-0.6);
        }
        else {
            intakemotor.setPower(0);  // Outtaking
        }

    }
}
