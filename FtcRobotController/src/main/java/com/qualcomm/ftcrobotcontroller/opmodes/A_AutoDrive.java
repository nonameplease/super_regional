package com.qualcomm.ftcrobotcontroller.opmodes;

import android.bluetooth.BluetoothClass;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Scott on 1/31/2016.
 * Encoder Drive 3 works
 * Encoder Drive 2 drive motors in sequence
 */
public class  A_AutoDrive extends A_RobotDrive {

    DeviceInterfaceModule dim;
    ColorSensor color;
    AnalogInput ultrasonic;
    OpticalDistanceSensor ods_l;
    OpticalDistanceSensor ods_r;

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
                       ColorSensor Color, OpticalDistanceSensor ODSL, OpticalDistanceSensor ODSR, AnalogInput Ultra) {
        super(left, right, leftrotate, rightrotate, Climber,
                Lifter, clawr, clawl, ResQr, ResQl);
        dim = Dim;
        color = Color;
        ods_l = ODSL;
        ods_r = ODSR;
        ultrasonic = Ultra;
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

    public void encoderDrive2(int inches, double leftpower, double rightpower, int leftcount, int rightcount)
    {
        int COUNTSINT = getTargetCounts(inches);
        int multiplierLeft = 1;
        int multiplierRight = 1;
        if(leftpower < 0) {
            multiplierLeft = -1;
        }

        if(rightpower < 0){
            multiplierRight = -1;
        }

        int leftTargetPosition = multiplierLeft * COUNTSINT + leftcount;
        int rightTargetPosition = multiplierRight * COUNTSINT + rightcount;

        if(leftpower > 0){
            while(leftTargetPosition > leftMotor.getCurrentPosition()){
                leftMotor.setPower(leftpower);
                if(leftTargetPosition < leftMotor.getCurrentPosition()) {
                    break;
                }
            }
            leftMotor.setPower(0);
        }
        else if(leftpower < 0){
            while(leftTargetPosition < leftMotor.getCurrentPosition()){
                leftMotor.setPower(leftpower);
                if(leftTargetPosition > leftMotor.getCurrentPosition()){
                    break;
                }
            }
            leftMotor.setPower(0);
        }

        if(rightpower > 0){
            while(rightTargetPosition > rightMotor.getCurrentPosition()){
                rightMotor.setPower(rightpower);
                if(rightTargetPosition < rightMotor.getCurrentPosition()){
                    break;
                }
            }
            rightMotor.setPower(0);
        }
        else if(rightpower < 0){
            while(rightTargetPosition < rightMotor.getCurrentPosition()){
                rightMotor.setPower(rightpower);
                if(rightTargetPosition > rightMotor.getCurrentPosition()){
                    break;
                }
            }
            rightMotor.setPower(0);
        }
    }

    public void encoderDrive3(int inches, double leftpower, double rightpower, int leftcount, int rightcount)
    {
        int COUNTSINT = getTargetCounts(inches);
        int multiplierLeft = 1;
        int multiplierRight = 1;
        if(leftpower < 0) {
            multiplierLeft = -1;
        }

        if(rightpower < 0){
            multiplierRight = -1;
        }

        int leftTargetPosition = multiplierLeft * COUNTSINT + leftcount;
        int rightTargetPosition = multiplierRight * COUNTSINT + rightcount;

        char statel = 'n';
        char stater = 'n';

        do {
            if (leftpower > 0) {
                if (leftTargetPosition > leftMotor.getCurrentPosition()) {
                    leftMotor.setPower(leftpower);
                    statel = 'y';
                } else {
                    leftMotor.setPower(0);
                    statel = 'n';
                }
            } else if (leftpower < 0) {
                if (leftTargetPosition < leftMotor.getCurrentPosition()) {
                    leftMotor.setPower(leftpower);
                    statel = 'y';
                } else {
                    leftMotor.setPower(0);
                    statel = 'n';
                }
            }

            if (rightpower > 0) {
                if (rightTargetPosition > rightMotor.getCurrentPosition()) {
                    rightMotor.setPower(rightpower);
                    stater = 'y';
                } else {
                    rightMotor.setPower(0);
                    stater = 'n';
                }
            } else if (rightpower < 0) {
                if (rightTargetPosition < rightMotor.getCurrentPosition()) {
                    rightMotor.setPower(rightpower);
                    stater = 'y';
                } else {
                    rightMotor.setPower(0);
                    stater = 'n';
                }
            }

        }
        while(statel == 'y' && stater == 'y');
        leftMotor.setPowerFloat();
        rightMotor.setPowerFloat();
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
        double distance_l = ods_l.getLightDetected();
        return distance_l;
    }
    public double distance_r()
    {
        double distance_r = ods_r.getLightDetected();
        return distance_r;
    }


    public void odsDriveToDistance(int stop_distance, A_Auto_Right autoright) throws InterruptedException
    {
        char state_l = 'n';
        char state_r = 'n';
        int current_distance = 0;

        do {
            if (distance_l() < 0.25) {
                leftMotor.setPower(-0.2);
                current_distance = Math.abs(leftMotor.getCurrentPosition());
                state_l = 'y';
            } else if (distance_l() > 0.3) {
                leftMotor.setPower(0.2);
                current_distance = Math.abs(leftMotor.getCurrentPosition());
                state_l = 'y';
            } else {
                leftMotor.setPower(0);
                current_distance = Math.abs(leftMotor.getCurrentPosition());
                state_l = 'n';
            }

            if (distance_r() < 0.25) {
                rightMotor.setPower(-0.2);
                current_distance = Math.abs(rightMotor.getCurrentPosition());
                state_r = 'y';
            } else if (distance_r() > 0.3) {
                rightMotor.setPower(0.2);
                current_distance = Math.abs(rightMotor.getCurrentPosition());
                state_r = 'y';
            } else {
                rightMotor.setPower(0);
                current_distance = Math.abs(rightMotor.getCurrentPosition());
                state_r = 'n';
            }

            autoright.waitOneFullHardwareCycle();

        }
        while((state_l == 'y' || state_r == 'y'));
        leftMotor.setPowerFloat();
        rightMotor.setPowerFloat();
    }

    public void find_line(String color, A_Auto_Right autoright) throws InterruptedException{
        //1 grey
        //2 whilte
        //3 red

        char state = 'n';
        int colorDesired = 0;

        if(color.equals("grey")){
            colorDesired = 1;
        }
        else if(color.equals("white")){
            colorDesired = 2;
        }
        else if(color.equals("red")){
            colorDesired = 3;
        }


        do{
            if(colorDetected() != colorDesired) {
                leftMotor.setPower(-0.2);
                rightMotor.setPower(-0.2);
                state = 'y';
            }
            else if(colorDetected() == colorDesired){
                leftMotor.setPower(0);
                rightMotor.setPower(0);
                state = 'n';
            }
            autoright.waitOneFullHardwareCycle();
        }
        while(state == 'y');
        leftMotor.setPowerFloat();
        rightMotor.setPowerFloat();
    }

    public void odsDriveToDistance(int stop_distance, A_Auto_Left autoleft) throws InterruptedException
    {
        char state_l = 'n';
        char state_r = 'n';
        int current_distance = 0;

        do {
            if (distance_l() < 0.25) {
                leftMotor.setPower(-0.2);
                current_distance = Math.abs(leftMotor.getCurrentPosition());
                state_l = 'y';
            } else if (distance_l() > 0.3) {
                leftMotor.setPower(0.2);
                current_distance = Math.abs(leftMotor.getCurrentPosition());
                state_l = 'y';
            } else {
                leftMotor.setPower(0);
                current_distance = Math.abs(leftMotor.getCurrentPosition());
                state_l = 'n';
            }

            if (distance_r() < 0.25) {
                rightMotor.setPower(-0.2);
                current_distance = Math.abs(rightMotor.getCurrentPosition());
                state_r = 'y';
            } else if (distance_r() > 0.3) {
                rightMotor.setPower(0.2);
                current_distance = Math.abs(rightMotor.getCurrentPosition());
                state_r = 'y';
            } else {
                rightMotor.setPower(0);
                current_distance = Math.abs(rightMotor.getCurrentPosition());
                state_r = 'n';
            }

            autoleft.waitOneFullHardwareCycle();

        }
        while((state_l == 'y' || state_r == 'y'));
        leftMotor.setPowerFloat();
        rightMotor.setPowerFloat();
    }

    public void find_line(String color, A_Auto_Left autoleft) throws InterruptedException{
        //1 grey
        //2 whilte
        //3 red

        char state = 'n';
        int colorDesired = 0;

        if(color.equals("grey")){
            colorDesired = 1;
        }
        else if(color.equals("white")){
            colorDesired = 2;
        }
        else if(color.equals("red")){
            colorDesired = 3;
        }


        do{
            if(colorDetected() != colorDesired) {
                leftMotor.setPower(-0.2);
                rightMotor.setPower(-0.2);
                state = 'y';
            }
            else if(colorDetected() == colorDesired){
                leftMotor.setPower(0);
                rightMotor.setPower(0);
                state = 'n';
            }
            autoleft.waitOneFullHardwareCycle();
        }
        while(state == 'y');
        leftMotor.setPowerFloat();
        rightMotor.setPowerFloat();
    }

}
