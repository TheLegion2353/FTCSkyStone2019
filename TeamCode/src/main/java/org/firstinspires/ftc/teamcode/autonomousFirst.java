package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Autonomous First")
public class autonomousFirst extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorLift;
    private CRServo servoGrab;
    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motorLeft"); //left drive motor
        motorRight = hardwareMap.dcMotor.get("motorRight"); //right drive motor
        motorLift = hardwareMap.dcMotor.get("motorLift");
        servoGrab = hardwareMap.crservo.get("servoGrab");

        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setDirection(DcMotor.Direction.FORWARD);
        servoGrab.setDirection(CRServo.Direction.FORWARD);

        waitForStart();
        motorLeft.setPower(0.5f);
        motorRight.setPower(0.5f);
        Thread.sleep(1000);
    }
}
