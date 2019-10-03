package org.firstinspires.ftc.teamcode;
/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Main TeleOp", group="TeleOp")

public class LegionOpMode extends LinearOpMode {
    final float fastSpeed = 1.0f;
    final float slowSpeed = 0.5f;
    final float evenSlower = 0.5f; //Multiplier to make the lifter even slow
    float speedMultiplier = 1.0f;
    float servoPow = 0.0f;
    float liftPow = 0.0f;
    float noPow;
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorLift;
    private CRServo servoGrab;
    @Override
    public void runOpMode() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft"); //left drive motor
        motorRight = hardwareMap.dcMotor.get("motorRight"); //right drive motor
        motorLift = hardwareMap.dcMotor.get("motorLift");
        servoGrab = hardwareMap.crservo.get("servoGrab");

        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setDirection(DcMotor.Direction.FORWARD);
        servoGrab.setDirection(CRServo.Direction.FORWARD);

        motorLift.setPower(0);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) { //slow speed code
                speedMultiplier = slowSpeed;
            } else {
                speedMultiplier = fastSpeed;
            }

            if (gamepad1.a && gamepad1.b) {
                servoPow = 0.0f;
            } else if (gamepad1.a) {
                servoPow = 1.0f;
            } else if (gamepad1.b) {
                servoPow = -1.0f;
            } else {
                servoPow = 0.0f;
            }
            liftPow = speedMultiplier * (gamepad1.right_trigger - gamepad1.left_trigger);
            if (liftPow == 0) {
                motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorLift.setTargetPosition((int)noPow);
            } else {
                motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                noPow = motorLift.getCurrentPosition();
            }
            //Motor movement
            motorLift.setPower((liftPow));
            motorLeft.setPower(-gamepad1.left_stick_y * speedMultiplier);
            motorRight.setPower(-gamepad1.right_stick_y * speedMultiplier);
            servoGrab.setPower(servoPow * speedMultiplier * evenSlower);
            //Telemetry
            telemetry.addData("Position", motorLift.getCurrentPosition());
            telemetry.addData("Lift Power", liftPow);
            telemetry.update();
            idle();
        }
    }
}
