package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.ModernRoboticsUsbDcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

/**
 * Created by owner on 12/19/2015.
 */
public class linear_auto_no_sensor extends LinearOpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor leftMotorRear;
    DcMotor rightMotorRear;
    DcMotor leftMotorRotate;
    DcMotor rightMotorRotate;
    DcMotor pickUp;
    VoltageSensor voltage;

    @Override
    public void runOpMode() throws InterruptedException {

                /* get reference to the motors from hardware map */
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        leftMotorRear = hardwareMap.dcMotor.get("left_drive_rear");
        rightMotorRear = hardwareMap.dcMotor.get("right_drive_rear");
        leftMotorRotate = hardwareMap.dcMotor.get("left_drive_rotate");
        rightMotorRotate = hardwareMap.dcMotor.get("right_drive_rotate");
        pickUp = hardwareMap.dcMotor.get("pick_up");
        voltage = this.hardwareMap.voltageSensor.iterator().next();

        /* reverse the right motor */
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotorRotate.setDirection(DcMotor.Direction.REVERSE);
        leftMotorRear.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();

        sleep(2000);

        leftMotor.setPower(1);
        rightMotor.setPower(1);
        leftMotorRear.setPower(1);
        rightMotorRear.setPower(1);

        wait(3000);

        leftMotor.setPowerFloat();
        rightMotor.setPowerFloat();
        leftMotorRear.setPowerFloat();
        rightMotorRear.setPowerFloat();

        wait(200);

        leftMotor.setPower(-1);
        leftMotorRear.setPower(-1);
        rightMotor.setPower(1);
        rightMotorRear.setPower(-1);

        wait(1000);

        leftMotor.setPower(1);
        leftMotorRear.setPower(1);
        rightMotor.setPower(1);
        rightMotorRear.setPower(1);

        wait(1500);

        leftMotor.setPowerFloat();
        leftMotorRear.setPowerFloat();
        rightMotor.setPowerFloat();
        rightMotorRear.setPowerFloat();

    }
}
