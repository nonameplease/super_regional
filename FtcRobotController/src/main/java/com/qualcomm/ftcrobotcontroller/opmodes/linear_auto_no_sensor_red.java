package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

/**
 * Created by owner on 12/19/2015.
 */
public class linear_auto_no_sensor_red extends LinearOpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor leftMotorRotate;
    DcMotor rightMotorRotate;
    DcMotor climber;
    VoltageSensor voltage;

    @Override
    public void runOpMode() throws InterruptedException {

                /* get reference to the motors from hardware map */
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        leftMotorRotate = hardwareMap.dcMotor.get("left_drive_rotate");
        rightMotorRotate = hardwareMap.dcMotor.get("right_drive_rotate");
        climber = hardwareMap.dcMotor.get("climber");
        voltage = this.hardwareMap.voltageSensor.iterator().next();

        /* reverse the right motor */
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotorRotate.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();

        sleep(2000);

        leftMotor.setPower(1);
        rightMotor.setPower(1);

        sleep(3300);

        leftMotor.setPowerFloat();
        rightMotor.setPowerFloat();

        sleep(200);

        leftMotor.setPower(-1);
        rightMotor.setPower(1);

        sleep(700);
        
        leftMotor.setPower(1);
        rightMotor.setPower(1);

        sleep(1200);

        leftMotor.setPowerFloat();
        rightMotor.setPowerFloat();

    }
}
