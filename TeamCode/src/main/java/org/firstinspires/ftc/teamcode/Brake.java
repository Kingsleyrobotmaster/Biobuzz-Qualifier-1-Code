package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Brake extends OpMode {
    private Servo Brake_right;
    private Servo Brake_left;

    boolean sticksIdle = Math.abs(gamepad1.left_stick_x) < 0.05
                && Math.abs(gamepad1.left_stick_y) < 0.05
                && Math.abs(gamepad1.right_stick_x) < 0.05
                && Math.abs(gamepad1.right_stick_y) < 0.05;
                //
    @Override
    public void init() {
        Brake_left = hardwareMap.get(Servo.class, "brake_left");
        Brake_right = hardwareMap.get(Servo.class, "brake_right");


    }

    @Override
    public void loop() {

        if (sticksIdle){
            Brake_left.setPosition(40); //Change to Disired Position Later after building
            Brake_right.setPosition(40);
        }


    }
}
