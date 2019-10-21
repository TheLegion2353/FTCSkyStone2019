package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Autonomous Test")
public class autonomousTesting extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorLift;
    private Servo servoGrab;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLift = hardwareMap.dcMotor.get("motorLift");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorLift.setDirection(DcMotor.Direction.FORWARD);

        motorLeft.setTargetPosition(0);
        motorRight.setTargetPosition(0);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorLeft.setPower(1);
        motorRight.setPower(1);
        waitForStart();
        moveDistance(300);
        telemetry.addData("Target Position", motorLeft.getTargetPosition());
        telemetry.addData("Real Position", motorRight.getCurrentPosition());
        telemetry.update();
    }

    public void bothWheels(int pos) {
        motorLeft.setTargetPosition(pos);
        motorRight.setTargetPosition(pos);
    }

    public boolean reachedTarget() {
        if (motorRight.getCurrentPosition() == motorRight.getTargetPosition() && motorLeft.getCurrentPosition() == motorLeft.getTargetPosition()) {
            return true;
        } else {
            return false;
        }
    }

    public void moveDistance(double dist) { //distance is in millimeters
        dist = dist / 0.25244940977;
        bothWheels((int)dist);
        while (reachedTarget() == false) {
            bothWheels((int)dist);
        }
    }
}
