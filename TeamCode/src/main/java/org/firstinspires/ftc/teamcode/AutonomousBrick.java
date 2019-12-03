package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Autonomous Right of Bridge Brick")
public class AutonomousBrick extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorLift;
    private Servo servoGrab;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLift = hardwareMap.dcMotor.get("motorLift");

        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setDirection(DcMotor.Direction.FORWARD);

        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setTargetPosition(0);
        motorRight.setTargetPosition(0);

        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);

        waitForStart();
        motorLeft.setTargetPosition(0);
        motorRight.setTargetPosition(0);

        telemetry.addData("Right Encoder Value: ", motorRight.getCurrentPosition());
        telemetry.addData("Left Encoder Value: ", motorLeft.getCurrentPosition());
        moveBothDistance(100);
        telemetry.addLine("Finsihed moving");
        telemetry.update();
        turnAngle(90);
        telemetry.addLine("Finished turning");
        telemetry.update();
        telemetry.addData("Target Position", motorLeft.getTargetPosition());
        telemetry.addData("Real Position", motorRight.getCurrentPosition());
        telemetry.update();
        Thread.sleep(1);
    }


    public void bothWheels(int pos) {
        motorLeft.setTargetPosition(pos + motorLeft.getCurrentPosition());
        motorRight.setTargetPosition(pos + motorRight.getCurrentPosition());
    }

    public boolean reachedTarget() {
        if (Math.abs(motorRight.getCurrentPosition() - motorRight.getTargetPosition()) <= 3 && Math.abs(motorLeft.getCurrentPosition() - motorLeft.getTargetPosition()) <= 3) {
            return true;
        } else {
            return false;
        }
    }


    public void turnAngle(double angle) {// Angle is in degrees; Turns the robot with the left wheel staying still and the right wheel moving
        angle = angle * ((37*Math.PI)/360);
        moveRightDistance((int)angle + motorRight.getCurrentPosition());
        moveLeftDistance(motorLeft.getCurrentPosition() - (int)angle);
    }

    public void turnAnglePivRight(double angle) {// Angle is in degrees; Turns the robot with the right wheel staying still and the left wheel moving
        angle = angle * ((37*Math.PI)/360);
        moveRightDistance((int)angle + motorLeft.getCurrentPosition());
    }


    public void moveBothDistance(double dist) { //distance is in millimeters
        dist = dist / 0.25244940977;
        bothWheels((int)dist);
        while (reachedTarget() == false) {
            bothWheels((int)dist);
        }
    }

    public void moveRightDistance(double dist) {
        dist = dist / 0.25244940977;
        motorRight.setTargetPosition((int)dist);
        while (reachedTarget() == false) {
            bothWheels((int)dist);
        }
    }

    public void moveLeftDistance(double dist) {
        dist = dist / 0.25244940977;
        motorLeft.setTargetPosition((int)dist);
        while (reachedTarget() == false) {
            bothWheels((int)dist);
        }
    }
}
