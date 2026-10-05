package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends OpMode {

    private DcMotor intakemotor;
    private DcMotor intakemotor1;

    @Override
    public void init() {
        intakemotor = hardwareMap.get(DcMotor.class, "intakemotor");
        intakemotor1 = hardwareMap.get(DcMotor.class, "intakemotor1");
        telemetry.addLine("Intake Activated");

    }

    @Override
    public void loop() {
        if (gamepad1.left_trigger_pressed){
            intakemotor.setPower(0.6);
            intakemotor1.setPower(0.6);
        }
        else{
            intakemotor.setPower(0);
            intakemotor1.setPower(0);
        }                           // Intaking

        if (gamepad1.leftBumperWasPressed()){
            intakemotor.setPower(-0.6);
            intakemotor1.setPower(-0.6);

        }
        else {
            intakemotor.setPower(0);
            intakemotor1.setPower(0);
        }

        //Make sure to change values later.

    }
}
