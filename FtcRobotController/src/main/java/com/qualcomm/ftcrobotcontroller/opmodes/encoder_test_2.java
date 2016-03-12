package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by owner on 1/13/2016.
 */

/*
This one really works!!!!
 */
public class encoder_test_2 extends LinearOpMode {

    DeviceInterfaceModule dim;
    ColorSensor color;

    DcMotor leftMotor;
    DcMotor rightMotor;
    //change the value if necessary LOL
    final static int ENCODER_CPR = 1120;
    final static double GEAR_RATIO = 1;
    final static int WHEEL_DIAMETER = 4;
    final static int DISTANCE = 72; //in inches

    final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    final static double ROTATIONS = DISTANCE / CIRCUMFERENCE;
    final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

    @Override
    public void runOpMode() throws InterruptedException {

        dim = hardwareMap.deviceInterfaceModule.get("device");
        color = hardwareMap.colorSensor.get("color");

        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");

        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        waitForStart();

        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        while(opModeIsActive()) {

            if(COUNTS > Math.abs(leftMotor.getCurrentPosition()))
            {
                leftMotor.setPower(-0.3);
            }
            else
            {
                leftMotor.setPowerFloat();
            }

            if(COUNTS > Math.abs(rightMotor.getCurrentPosition()))
            {
                rightMotor.setPower(-0.3);
            }
            else
            {
                rightMotor.setPowerFloat();
            }
            /*
            if (COUNTS + 50 > Math.abs(leftMotor.getCurrentPosition())) {

                if (COUNTS > Math.abs(leftMotor.getCurrentPosition())) {
                    leftMotor.setPower(-0.3);
                } else {
                    leftMotor.setPowerFloat();
                }

                if (COUNTS > Math.abs(rightMotor.getCurrentPosition())) {
                    rightMotor.setPower(-0.3);
                } else {
                    rightMotor.setPowerFloat();
                }
            } else if (COUNTS + 50 < Math.abs(leftMotor.getCurrentPosition()) && COUNTS + 2100 > Math.abs(leftMotor.getCurrentPosition())) {
                if (2000 + COUNTS > Math.abs(leftMotor.getCurrentPosition())) {
                    leftMotor.setPower(-0.3);
                } else {
                    leftMotor.setPowerFloat();
                }


                if (COUNTS - 2000 < Math.abs(rightMotor.getCurrentPosition())) {
                    rightMotor.setPower(0.3);
                } else {
                    rightMotor.setPowerFloat();
                }

            }else
            {
                    leftMotor.setPower(-0.3);
                rightMotor.setPower(-0.3);
                sleep(2000);
                leftMotor.setPowerFloat();
                rightMotor.setPowerFloat();
                sleep(30000);
            }
        */


            waitOneFullHardwareCycle();


            telemetry.addData("Counts", COUNTS);
            telemetry.addData("Left Encoder", leftMotor.getCurrentPosition());
            telemetry.addData("Right Encoder", rightMotor.getCurrentPosition());
            telemetry.addData("left power", leftMotor.getPower());
            telemetry.addData("right power", rightMotor.getPower());
        }
        }

    }

