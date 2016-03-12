package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

/**
 * Created by Scott on 1/17/2016.
 */
public class encoder_test_4 extends OpMode {

    DeviceInterfaceModule dim;
    ColorSensor color;

    DcMotor leftMotorRear;
    DcMotor rightMotorRear;
    //change the value if necessary LOL
    final static int ENCODER_CPR = 1120;
    final static int GEAR_RATIO = 1;
    final static int WHEEL_DIAMETER = 4;
    final static int DISTANCE = 36; //in inches

    final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    final static double ROTATIONS = DISTANCE / CIRCUMFERENCE;
    final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
    final static int COUNTSINT = (int) COUNTS * -1;
    String state = "running";

    @Override
    public void init()
    {
        dim = hardwareMap.deviceInterfaceModule.get("device");
        color = hardwareMap.colorSensor.get("color");

        leftMotorRear = hardwareMap.dcMotor.get("left_drive_rear");
        rightMotorRear = hardwareMap.dcMotor.get("right_drive_rear");

        rightMotorRear.setDirection(DcMotor.Direction.REVERSE);

        leftMotorRear.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotorRear.setMode(DcMotorController.RunMode.RESET_ENCODERS);

    }

    @Override
    public void start()
    {


        leftMotorRear.setTargetPosition(COUNTSINT);
        rightMotorRear.setTargetPosition(COUNTSINT);
        leftMotorRear.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotorRear.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        leftMotorRear.setPower(-0.5);
        rightMotorRear.setPower(-0.5);

        if (!leftMotorRear.isBusy() && !rightMotorRear.isBusy())
        {
            state = "stopped";
        }
        else
        {
            state = "running";
        }




    }


    @Override
    public void loop() {



        telemetry.addData("Counts", COUNTSINT);
        telemetry.addData("left counts = ", leftMotorRear.getCurrentPosition());
        telemetry.addData("right counts =", rightMotorRear.getCurrentPosition());
        telemetry.addData("running status", state);


        /** if (COUNTS > Math.abs(leftMotorRear.getCurrentPosition())) {
         leftMotorRear.setPower(0.3);
         } else {
         leftMotorRear.setPowerFloat();
         }

         if (COUNTS > Math.abs(rightMotorRear.getCurrentPosition())) {
         rightMotorRear.setPower(0.3);
         } else {
         rightMotorRear.setPowerFloat();
         } **/

    }
}
