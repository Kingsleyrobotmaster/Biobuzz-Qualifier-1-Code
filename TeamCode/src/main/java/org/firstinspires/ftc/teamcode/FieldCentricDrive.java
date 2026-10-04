package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@TeleOp
public class FieldCentricDrive extends LinearOpMode {

    static final double X_POD_OFFSET_MM = 5.873605322650099;
    static final double Y_POD_OFFSET_MM = -1.8571279931256153;

    private GoBildaPinpointDriver pinpoint;

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontLeft  = hardwareMap.get(DcMotor.class, "front_left");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "front_right");
        DcMotor backLeft   = hardwareMap.get(DcMotor.class, "back_left");
        DcMotor backRight  = hardwareMap.get(DcMotor.class, "back_right");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addLine("Drive Trian Activated");

        for (DcMotor m : new DcMotor[]{frontLeft, frontRight, backLeft, backRight}) {
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(X_POD_OFFSET_MM, Y_POD_OFFSET_MM, DistanceUnit.MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();

        telemetry.addLine("Calibrating Pinpoint - do not move the robot.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            pinpoint.update();

            Pose2D pose = pinpoint.getPosition();
            double x       = pose.getX(DistanceUnit.INCH);
            double y       = pose.getY(DistanceUnit.INCH);
            double heading = pose.getHeading(AngleUnit.RADIANS);

            if (gamepad1.options) {
                pinpoint.resetPosAndIMU();
            }

            double driveY = gamepad1.left_stick_y;
            double driveX = -gamepad1.left_stick_x;
            double rx     = -gamepad1.right_stick_x;

            double rotX = driveX * Math.cos(-heading) - driveY * Math.sin(-heading);
            double rotY = driveX * Math.sin(-heading) + driveY * Math.cos(-heading);

            rotX *= 1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);

            frontLeft .setPower((rotY + rotX + rx) / denominator);
            backLeft  .setPower((rotY - rotX + rx) / denominator);
            frontRight.setPower((rotY - rotX - rx) / denominator);
            backRight .setPower((rotY + rotX - rx) / denominator);

            telemetry.addData("X (in)", "%.2f", x);
            telemetry.addData("Y (in)", "%.2f", y);
            telemetry.addData("Heading (deg)", "%.1f", pose.getHeading(AngleUnit.DEGREES));
            telemetry.addData("Status", pinpoint.getDeviceStatus());
            telemetry.addData("Pinpoint Hz", "%.0f", pinpoint.getFrequency());
            telemetry.update();
        }
    }
}
