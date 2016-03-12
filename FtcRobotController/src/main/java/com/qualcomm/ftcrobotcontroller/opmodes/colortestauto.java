package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Scott on 2/19/2016.
 */


public class colortestauto extends OpMode {
    DeviceInterfaceModule dim;
    ColorSensor color;
    DcMotor leftMotor;
    DcMotor rightMotor;
    Servo resQ_l;//resQ_l port: 6
    Servo resq_r;//resQ_r port: 5
    static int Turn = 1200;
    static int ForwardALittleBit = 1200;
    static boolean f1 = true;
    static boolean f2 = true;

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

    @Override
    public void init() {
        dim = hardwareMap.deviceInterfaceModule.get("device");
        color = hardwareMap.colorSensor.get("color");
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        resq_r = hardwareMap.servo.get("resQ_r");
        resQ_l = hardwareMap.servo.get("resQ_l");

    }

    @Override
    public void start(){
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void loop() {

        int step = 1;
        switch (step){
            case 1:{
                if(colorDetected() == 1){
                    leftMotor.setPower(-0.4);
                    rightMotor.setPower(-0.4);
                }
                else{
                    leftMotor.setPowerFloat();
                    rightMotor.setPowerFloat();
                    if(f1 == true){
                        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                        f1 = false;
                    }
                    step = 2;
                    break;
                }
            }
            case 2:{
                if(Turn >= Math.abs(leftMotor.getCurrentPosition())){
                    leftMotor.setPower(0.4);
                }
                else{
                    leftMotor.setPowerFloat();
                }

                if(Turn >= Math.abs(rightMotor.getCurrentPosition())){
                    rightMotor.setPower(-0.4);
                }
                else{
                    rightMotor.setPowerFloat();
                    if(f2 == true){
                        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                        f2 = false;
                    }
                    step = 3;
                    break;
                }
            }
            case 3:{
                if(ForwardALittleBit >= Math.abs(leftMotor.getCurrentPosition())){
                    leftMotor.setPower(0.4);
                }
                else{
                    leftMotor.setPowerFloat();
                }

                if(ForwardALittleBit >= Math.abs(rightMotor.getCurrentPosition())){
                    rightMotor.setPower(0.4);
                }
                else{
                    rightMotor.setPowerFloat();
                    step = 4;
                    break;
                }
            }
            default:{

            }
        }

        telemetry.addData("colordetected", colorDetected());
        telemetry.addData("step", step);
        telemetry.addData("leftMotor", leftMotor.getPower());
        telemetry.addData("rightMotor", rightMotor.getPower());



    }


}


