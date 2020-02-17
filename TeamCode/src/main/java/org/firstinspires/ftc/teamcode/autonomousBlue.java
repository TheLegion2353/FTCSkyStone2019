package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Autonomous Blue")
public class autonomousBlue extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorLift;
    private DcMotor motorExtend;
    private Servo servoGrab;
    private Servo servoGrab2;
    private Servo servoDrag;
    final float gripFacotr = 0.0f;
    private static final int ONEROTATIONCOUNTS = 455;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLift = hardwareMap.dcMotor.get("motorLift");
        motorExtend = hardwareMap.dcMotor.get("motorExtend");
        servoGrab = hardwareMap.servo.get("servoGrab");
        servoGrab2 = hardwareMap.servo.get("servoGrab2");
        servoDrag = hardwareMap.servo.get("servoDrag");

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

        servoDrag.setPosition(1);
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

        motorLeft.setPower(0.6);
        motorRight.setPower(0.6);
        motorLift.setPower(.75);
        motorExtend.setPower(1);

        /*Notes
        Radius of wheel: 4.4cm
        Circumfirance: 27.65cm
         */
        waitForStart();
        servoDrag.setPosition(1);
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

        lift(-300);
        grab(0.7f);
        extend(580);
        lift(150);
        moveDistance(120);
        grab(1);
        lift(0);
        moveDistance(-60);
        circle(-35);

        motorLeft.setPower(0.85);
        motorRight.setPower(0.85);
        moveDistance(221.5);
        Thread.sleep(100);

        lift(-100);
        circle(35);
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        moveDistance(70);
        lift(0);
        grab(0.75f);
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        moveDistance(-70);
        circle(35);
        motorLeft.setPower(.75);
        motorRight.setPower(.75);
        moveDistance(150);

    }


    public void circle(double dist) throws InterruptedException{ //If +, then turns right.  If -, then turns left.  152 is a full rotation.  38 is a quarter. 76 is a half.
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

        dist = dist / 27.65;
        motorLeft.setPower(0.20);
        motorRight.setPower(0.20);
        rotateLeft(dist);
        rotateRight(-dist);
        while (reached(motorLeft) == false || reached(motorRight) == false) {

        }
        motorLeft.setPower(.5);
        motorRight.setPower(.5);
        Thread.sleep(500);
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
        servoGrab.setPosition(pos + gripFacotr);
        servoGrab2.setPosition(pos - gripFacotr);
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
}
