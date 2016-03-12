package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by owner on 12/30/2015.
 */
public class Encoder_test extends OpMode {

    DeviceInterfaceModule dim;
    OpticalDistanceSensor ods_l;
    OpticalDistanceSensor ods_r;
    ColorSensor color;

    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor leftMotorRear;
    DcMotor rightMotorRear;
    //change the value if necessary LOL
    final static int ENCODER_CPR = 1220;
    final static double GEAR_RATIO = 1;
    final static int WHEEL_DIAMETER = 4;
    final static int DISTANCE = 24;

    final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    final static double ROTATIONS = DISTANCE / CIRCUMFERENCE;
    final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;







    @Override
    public void init() {

        dim = hardwareMap.deviceInterfaceModule.get("device");
        ods_l = hardwareMap.opticalDistanceSensor.get("odsl");
        ods_r = hardwareMap.opticalDistanceSensor.get("odsr");
        color = hardwareMap.colorSensor.get("color");

        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        leftMotorRear = hardwareMap.dcMotor.get("left_drive_rear");
        rightMotorRear = hardwareMap.dcMotor.get("right_drive_rear");

        rightMotorRear.setDirection(DcMotor.Direction.REVERSE);




        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        leftMotorRear.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotorRear.setMode(DcMotorController.RunMode.RESET_ENCODERS);

    }

    public void start()
    {
        leftMotorRear.setTargetPosition((int) COUNTS);
        rightMotorRear.setTargetPosition((int) COUNTS);

        leftMotorRear.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotorRear.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        leftMotorRear.setPower(0.3);
        rightMotorRear.setPower(0.3);
    }

public void loop()
{







        telemetry.addData("Motor Target", COUNTS);
        telemetry.addData("Left Position", leftMotorRear.getCurrentPosition());
        telemetry.addData("Right Position", rightMotorRear.getCurrentPosition());

    }
}
