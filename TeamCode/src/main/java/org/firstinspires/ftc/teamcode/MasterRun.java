package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MasterRun extends OpMode {

    Turret_Main turretMain = new Turret_Main();
    TurretBackgroundCode turretBackgroundCode = new TurretBackgroundCode();

    FieldCentricDrive fieldCentricDrive = new FieldCentricDrive();

    @Override
    public void runOpMode() throws InterruptedException {



    }
}
