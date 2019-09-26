package org.firstinspires.ftc.teamcode;
/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Main TeleOp", group="TeleOp")

public class LegionOpMode extends LinearOpMode {
    final float fastSpeed = 1.0f;
    final float slowSpeed = 0.5f;
    final float evenSlower = 0.5f; //Multiplier to make the lifter even slow
    float speedMultiplier = 1.0f;
    float servoPow = 0.0f;
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
        waitForStart();
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

            /*
            add settargetposition stufff here for the motorLift
            */


            //Motor movement
            motorLeft.setPower(-gamepad1.left_stick_y * speedMultiplier);
            motorRight.setPower(-gamepad1.right_stick_y * speedMultiplier);
            motorLift.setPower((gamepad1.right_trigger - gamepad1.left_trigger) * speedMultiplier);
            servoGrab.setPower(servoPow * speedMultiplier * evenSlower);

            //Telemetry
            telemetry.addData("position", motorLift.getCurrentPosition());
            telemetry.update();
            idle();
        }
    }
}
