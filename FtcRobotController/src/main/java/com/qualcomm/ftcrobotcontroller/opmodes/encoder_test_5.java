package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

/**
 * Created by Scott on 1/24/2016.
 * it works!
 */
public class encoder_test_5 extends OpMode {

    DeviceInterfaceModule dim;
    ColorSensor color;

    DcMotor leftMotor;
    DcMotor rightMotor;
    //change the value if necessary LOL
    final static int ENCODER_CPR = 1120;
    final static int GEAR_RATIO = 1;
    final static int WHEEL_DIAMETER = 4;
    final static int DISTANCE = 36; //in inches

    final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    final static double ROTATIONS = DISTANCE / CIRCUMFERENCE;
    final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
    final static int COUNTSINT = (int) COUNTS;
    String state = "running";

    @Override
    public void init()
    {
        dim = hardwareMap.deviceInterfaceModule.get("device");
        color = hardwareMap.colorSensor.get("color");

        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");

        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

    }




    @Override
    public void loop() {
        for(int i=1; i < 2; i++)
        {

            leftMotor.setTargetPosition(COUNTSINT);
            rightMotor.setTargetPosition(COUNTSINT);
            leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

            leftMotor.setPower(0.5);
            rightMotor.setPower(-0.5);
        }

        if (!leftMotor.isBusy() && !rightMotor.isBusy())
        {
            state = "stopped";
        }
        else
        {
            state = "running";
        }




        telemetry.addData("Counts", COUNTSINT);
        telemetry.addData("left counts = ", leftMotor.getCurrentPosition());
        telemetry.addData("right counts =", rightMotor.getCurrentPosition());
        telemetry.addData("running status", state);
        telemetry.addData("left power", leftMotor.getPower());
        telemetry.addData("right power", rightMotor.getPower());


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
