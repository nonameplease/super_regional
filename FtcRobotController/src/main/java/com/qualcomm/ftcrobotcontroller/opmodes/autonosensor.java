package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by owner on 12/5/2015.
 */
public class autonosensor extends OpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor leftMotorRear;
    DcMotor rightMotorRear;
    DcMotor leftMotorRotate;
    DcMotor rightMotorRotate;

    final double UP_POSITION = 0.0;
    final double DOWN_POSITION = 0.3;

    Servo climber;

    final double CLOSE_POSITION = 255;
    final double RESQ_POSITION = 140;

    Servo resQ;

    public void LeftPower(double power)
    {
        leftMotor.setPower(power);
        leftMotorRear.setPower(power);
    }

    public void RightPower(double power)
    {
        rightMotorRear.setPower(power);
        rightMotor.setPower(power);
    }

    @Override
    public void init() {
        /* get reference to the motors from hardware map */
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        leftMotorRear = hardwareMap.dcMotor.get("left_drive_rear");
        rightMotorRear = hardwareMap.dcMotor.get("right_drive_rear");
        leftMotorRotate = hardwareMap.dcMotor.get("left_drive_rotate");
        rightMotorRotate = hardwareMap.dcMotor.get("right_drive_rotate");

        /* reverse the right motor */
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotorRear.setDirection(DcMotor.Direction.REVERSE);
        rightMotorRotate.setDirection(DcMotor.Direction.REVERSE);



        climber = hardwareMap.servo.get("climber");
        resQ = hardwareMap.servo.get("resQ");


        climber.setPosition(UP_POSITION);
        resQ.setPosition(CLOSE_POSITION);

        leftMotor.setPower(0);
        rightMotor.setPower(0);
        leftMotorRear.setPower(0);
        rightMotorRear.setPower(0);

    }

    @Override
    public void loop() {
        resetStartTime();
        while(this.getRuntime() < 4)
        {
            LeftPower(-1);
            RightPower(-1);
        }
        resQ.setPosition(RESQ_POSITION);


    }
}
