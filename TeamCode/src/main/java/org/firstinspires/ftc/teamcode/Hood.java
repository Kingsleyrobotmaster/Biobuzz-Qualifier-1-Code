package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Hood extends OpMode {
    private Servo hood;

    @Override
    public void init() {
        hood = hardwareMap.get(Servo.class, "hood");
        telemetry.addLine("Hood Activated");
    }

    @Override
    public void loop() {

    }
}
