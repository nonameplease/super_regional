package com.qualcomm.ftcrobotcontroller.opmodes;

import android.bluetooth.BluetoothClass;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Scott on 1/31/2016.
 */
public class A_AutoDrive extends A_RobotDrive {

    DeviceInterfaceModule dim;
    ColorSensor color;
    AnalogInput ods_l;
    AnalogInput ods_r;

    /*change the value if necessary LOL
    final static int ENCODER_CPR = 1120;
    final static int GEAR_RATIO = 1;
    final static int WHEEL_DIAMETER = 4;
    final static int DISTANCE = 36; //in inches

    final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    final static double ROTATIONS = DISTANCE / CIRCUMFERENCE;
    final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
    final static int COUNTSINT = (int) COUNTS * -1;
    */

    public A_AutoDrive(DcMotor left, DcMotor right, DcMotor leftrotate, DcMotor rightrotate,
                       DcMotor Climber, Servo Lifter, Servo clawr, Servo clawl,
                       Servo ResQr, Servo ResQl, DeviceInterfaceModule Dim,
                       ColorSensor Color, AnalogInput ODSL, AnalogInput ODSR) {
        super(left, right, leftrotate, rightrotate, Climber,
                Lifter, clawr, clawl, ResQr, ResQl);
        dim = Dim;
        color = Color;
        ods_l = ODSL;
        ods_r = ODSR;
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public int getTargetCounts(int inches)
    {
        final int ENCODER_CPR = 1120;
        final int GEAR_RATIO = 1;
        final int WHEEL_DIAMETER = 4;
        int DISTANCE = inches; //in inches

        final double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = DISTANCE / CIRCUMFERENCE;
        double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        int COUNTSINT = (int) COUNTS;
        return COUNTSINT;
    }

    public void encoderDrive(int COUNTSINT, double leftpower, double rightpower, double leftcount, double rightcount)
    {
        if(leftpower > 0) {
            leftMotor.setTargetPosition(COUNTSINT + (int)leftcount);
        }
        else if(leftpower < 0) {
            leftMotor.setTargetPosition(-COUNTSINT + (int)leftcount);
        }

        if(rightpower > 0){
            rightMotor.setTargetPosition(COUNTSINT + (int)rightcount);
        }
        else if(rightpower < 0){
            rightMotor.setTargetPosition(-COUNTSINT + (int)rightcount);
        }
        leftMotor.setPower(leftpower);

        rightMotor.setPower(rightpower);
        leftcount = leftMotor.getCurrentPosition();
        rightcount = rightMotor.getCurrentPosition();
    }

    public void encoderDriveUsingPosition(int COUNTSINT, double power) {
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        if(COUNTSINT > Math.abs(leftMotor.getCurrentPosition()))
        {
            leftMotor.setPower(-power);
        }
        else
        {
            leftMotor.setPowerFloat();
        }

        if(COUNTSINT > Math.abs(rightMotor.getCurrentPosition()))
        {
            rightMotor.setPower(-power);
        }
        else
        {
            rightMotor.setPowerFloat();
        }
    }

    public void encoderReset()
    {
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void drive(double leftpower, double rightpower)
    {
        leftMotor.setPower(leftpower);
        rightMotor.setPower(rightpower);
    }

    public void turnDrive(char direction, int degree, int power)
    {
        //90 degree is 4.5 * pi inches
        double distance = degree * getTargetCounts(9 * 3) *(1.0/2.0) * 1.0/90.0;
        int distanceint = (int) distance;
        switch (direction)
        {
            case 'l':
            {
                leftMotor.setTargetPosition(-distanceint);
                rightMotor.setTargetPosition(distanceint);
                leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                leftMotor.setPower(power);
                rightMotor.setPower(power);
                break;
            }
            case 'r':
            {
                leftMotor.setTargetPosition(distanceint);
                rightMotor.setTargetPosition(-distanceint);
                leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                leftMotor.setPower(power);
                rightMotor.setPower(power);
                break;
            }
            default:
            {

            }
        }
    }

    public double colorRed()
    {
        int Red = color.red();
        return Red;
    }
    public double colorBlue()
    {
        int Blue = color.blue();
        return Blue;
    }
    public double colorGreen()
    {
        int Green = color.green();
        return Green;
    }

    public int colorDetected()
    {
        /*
        grey
        blue 220 - 245
        green 310 - 330
        red 390 - 410

        white
        blue 600 - 700
        green 750 - 900
        red 800 - 850

        red
        blue 180 - 220
        green 220 - 270
        red 600 - 650
            */
        int Color = 0;
        if(colorRed() < 450 && colorBlue() < 400 && colorGreen() < 400)
        {
            Color = 1;//grey
        }

        if(colorRed() > 500 && colorBlue() > 500 && colorGreen() > 500)
        {
            Color = 2;//white
        }

        if(colorRed() > 500 && colorBlue() < 300 && colorGreen() < 300)
        {
            Color = 3;//red
        }

        return Color;
    }

    public double distance_l()
    {
        double distance_l = ods_l.getValue();
        return distance_l;
    }
    public double distance_r()
    {
        double distance_r = ods_r.getValue();
        return distance_r;
    }

    public void odsDriveToDistance()
    {
        if(distance_l() < 300)
        {
            leftMotor.setPower(-0.4);
        }
        else if(distance_l() > 350)
        {
            leftMotor.setPower(0.4);
        }
        else
        {
            leftMotor.setPowerFloat();
        }

        if(distance_r() < 300)
        {
            rightMotor.setPower(-0.4);
        }
        else if(distance_r() > 350)
        {
            rightMotor.setPower(0.4);
        }
        else
        {
            rightMotor.setPowerFloat();
        }
    }



}
