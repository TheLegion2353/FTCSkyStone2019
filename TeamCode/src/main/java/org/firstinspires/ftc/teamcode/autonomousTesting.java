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
    private DcMotor motorExtend;
    private Servo servoGrab;
    private Servo servoGrab2;
    private static final int ONEROTATIONCOUNTS = 455;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLift = hardwareMap.dcMotor.get("motorLift");
        motorExtend = hardwareMap.dcMotor.get("motorExtend");
        servoGrab = hardwareMap.servo.get("servoGrab");
        servoGrab2 = hardwareMap.servo.get("servoGrab2");


        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setDirection(DcMotor.Direction.FORWARD);
        motorExtend.setDirection(DcMotor.Direction.FORWARD);
        servoGrab.setDirection(Servo.Direction.FORWARD);
        servoGrab2.setDirection(Servo.Direction.REVERSE);

        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setTargetPosition(0);
        motorRight.setTargetPosition(0);
        motorLift.setTargetPosition(0);
        motorExtend.setTargetPosition(0);

        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorLeft.setPower(0.35);
        motorRight.setPower(0.35);
        motorLift.setPower(.5);
        motorExtend.setPower(1);

        /*Notes
        Radius of wheel: 4.4cm
        Circumfirance: 27.65cm
         */
        waitForStart();
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setTargetPosition(0);
        motorRight.setTargetPosition(0);
        motorLift.setTargetPosition(0);
        motorExtend.setTargetPosition(0);

        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grab(0);
        motorLeft.setTargetPosition(0);
        motorRight.setTargetPosition(0);

        lift(-500);
        grab(0.75f);
        extend(580);
        lift(100);
        moveDistance(100);
        grab(1);
        lift(0);
        moveDistance(-75);
        circle(15);
        moveDistance(200);
        Thread.sleep(10000);
    }


    public void circle(double dist) throws InterruptedException{ //If +, then turns right.  If -, then turns left.  152 is a full rotation.  38 is a quarter. 76 is a half.
        dist = dist / 27.65;
        rotateLeft(dist);
        rotateRight(-dist);
        while (reached(motorLeft) == false || reached(motorRight) == false) {

        }
        Thread.sleep(1500);
    }


    public void moveDistance(double dist) throws InterruptedException {
        dist = dist / 27.65;
        rotate((int)dist);
    }

    public void extend(int pos) {
        motorExtend.setTargetPosition(pos);
        while (reached(motorExtend) == false) {

        }
    }

    public void grab(float pos) {
        servoGrab.setPosition(pos);
        servoGrab2.setPosition(pos);
    }

    public void lift(int pos) throws InterruptedException {
        motorLift.setTargetPosition(pos);
        while (reached(motorLift) == false) {

        }
        Thread.sleep(500);
    }
    public void rotate(double rot) throws InterruptedException {
        rot = rot * ONEROTATIONCOUNTS;
        bothWheels((int)rot);
        while (reached(motorLeft) == false || reached(motorRight) == false) {

        }
    }

    public void rotateRight(double rot) {
        rot = rot * ONEROTATIONCOUNTS;
        rightWheel((int)rot);
    }


    public void rotateLeft(double rot){
        rot = rot * ONEROTATIONCOUNTS;
        leftWheel((int)rot);
    }

    public void rightWheel(int pos) {
        motorRight.setTargetPosition(pos + motorLift.getTargetPosition());
    }

    public void leftWheel(int pos) {
        motorLeft.setTargetPosition(pos + motorRight.getTargetPosition());
    }

    public void bothWheels(int pos) {
        motorLeft.setTargetPosition(pos + motorLeft.getTargetPosition());
        motorRight.setTargetPosition(pos + motorRight.getTargetPosition());
    }

    public boolean reached(DcMotor motor) {
        if (Math.abs(motor.getCurrentPosition() - motor.getTargetPosition()) < 10) {
            return true;
        } else {
            return false;
        }
    }

    /*
    public void bothWheels(int pos) {
        motorLeft.setTargetPosition(pos + motorLeft.getCurrentPosition());
        motorRight.setTargetPosition(pos + motorRight.getCurrentPosition());
    }

    public boolean reachedTarget(boolean backward) {
        if (backward == false) {
            if (motorRight.getCurrentPosition() - motorRight.getTargetPosition() <= 3 && motorLeft.getCurrentPosition() - motorLeft.getTargetPosition() <= 3) {
                return true;
            } else {
                return false;
            }
        } else {
            if (-motorRight.getCurrentPosition() + motorRight.getTargetPosition() <= 3 && -motorLeft.getCurrentPosition() + motorLeft.getTargetPosition() <= 3) {
                return true;
            } else {
                return false;
            }
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
        if (dist > 0) {
            while (reachedTarget(false) == false) {
            }
        } else {
            while (reachedTarget(true) == false) {
            }
        }
    }

    public void moveRightDistance(double dist) {
        dist = dist / 0.25244940977;
        motorRight.setTargetPosition((int)dist);
        bothWheels((int) dist);
        if (dist > 0) {
            while (reachedTarget(false) == false) {
            }
        } else {
            while (reachedTarget(true) == false) {
            }
        }
    }

    public void moveLeftDistance(double dist) {
        dist = dist / 0.25244940977;
        motorLeft.setTargetPosition((int)dist);
        bothWheels((int) dist);
        if (dist > 0) {
            while (reachedTarget(false) == false) {
            }
        } else {
            while (reachedTarget(true) == false) {
            }
        }
    }
     */
}
