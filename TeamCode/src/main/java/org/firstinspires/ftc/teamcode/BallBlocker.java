package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class BallBlocker extends OpMode {

    private Servo Ballblocker;

    @Override
    public void init() {
        Ballblocker = hardwareMap.get(Servo.class, "ballblockerservo");

    }

    @Override
    public void loop() {
        if (gamepad1.left_trigger_pressed) {
            Ballblocker.setPosition(40);    //Find Position Later
        }

        if (gamepad1.right_trigger_pressed){
            Ballblocker.setPosition(0); // Find Home position
        }






    }
}
