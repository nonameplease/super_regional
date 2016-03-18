package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.concurrent.TimeUnit;

/**
 * Created by Scott on 1/31/2016.
 */
public class A_RobotDrive {
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor leftMotorRotate;
    DcMotor rightMotorRotate;
    DcMotor climber;
    Servo lifter;//lifter port: 1
    //Servo bucket;//bucket port: 2
    Servo claw_r;//claw_r port: 3
    Servo claw_l;//claw_l port: 4
    Servo resQ_l;//resQ_l port: 6
    Servo resq_r;//resQ_r port: 5

    public A_RobotDrive(DcMotor left, DcMotor right, DcMotor leftrotate,
                        DcMotor rightrotate, DcMotor Climber,
                        Servo Lifter, Servo clawr, Servo clawl, Servo ResQr, Servo ResQl)
    {
        leftMotor = left;
        rightMotor = right;
        leftMotorRotate = leftrotate;
        rightMotorRotate = rightrotate;
        climber = Climber;
        lifter = Lifter;
        //bucket = Bucket;
        claw_r = clawr;
        claw_l = clawl;
        resQ_l = ResQl;
        resq_r = ResQr;

        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotorRotate.setDirection(DcMotor.Direction.REVERSE);
    }


    public void tankDrive(float gamepad1leftsticky, float gamepadrightsticky)
    {
        /*
        get value from gamepad
        caution that gamepad's value is reversed so all value should muptiply by -1
        */
        float leftY = -1 * gamepad1leftsticky;
        float rightY = -1 * gamepadrightsticky;


        /* set the power of motors with the gamepad values */
        leftMotor.setPower(leftY);
        rightMotor.setPower(rightY);
    }

    public void tankDrive(Gamepad gamepad1)
    {
        tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y);
    }



    public void rotateDrive(double gamepad1lefttrigger, double gamepad1righttrigger, boolean gamepad1leftbumper, boolean gamepad1rightbumper) {
          /*
        rotate drive
         */
        double valueFeedFromLT = gamepad1lefttrigger;
        double valueFeedFromRT = gamepad1righttrigger;

        if (gamepad1leftbumper) {
            leftMotorRotate.setPower(-1);
        } else if (valueFeedFromLT > 0.5) {
            leftMotorRotate.setPower(1);
        } else {
            leftMotorRotate.setPower(0);
        }

        if (gamepad1rightbumper) {
            rightMotorRotate.setPower(-1);
        } else if (valueFeedFromRT > 0.5) {
            rightMotorRotate.setPower(1);
        } else {
            rightMotorRotate.setPower(0);
        }
    }

    public void rotateDrive(Gamepad gamepad1)
    {
        rotateDrive(gamepad1.left_trigger, gamepad1.right_trigger, gamepad1.left_bumper, gamepad1.right_bumper);
    }

    public void climberDrive(boolean gamepad2x, boolean gamepad2b)
    {
        if(gamepad2x)
        {
            climber.setPower(1);
        }
        else if(gamepad2b)
        {
            climber.setPower(-1);
        }
        else
        {
            climber.setPower(0);
        }
    }

    public void climberDrive(Gamepad gamepad2)
    {
        climberDrive(gamepad2.x, gamepad2.b);
    }

    public void resQ(boolean gamepad2x, boolean gamepad2a, boolean gamepad2leftbumper, boolean gamepad2rightbumper)
    {
            final double UP_POSITION = 0.0;
            final double DOWN_POSITION = 0.8;

            final double CLOSE_POSITION = 1;
            final double RESQ_POSITION = 0.3;
            final double Buffer1 = 0.9;
            final double Buffer2 = 0.8;
            final double Buffer3 = 0.7;
            final double Buffer4 = 0.6;
            final double Buffer5 = 0.5;


            if (gamepad2leftbumper) {
                resQ_l.setPosition(CLOSE_POSITION);
            }

            if (gamepad2rightbumper) {
                resQ_l.setPosition(Buffer1);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
                catch(InterruptedException e)
                {}
                resQ_l.setPosition(Buffer2);
                try{
                    TimeUnit.MILLISECONDS.sleep(200);
                }
                catch (InterruptedException e)
                {}
                resQ_l.setPosition(Buffer3);
                try{
                    TimeUnit.MILLISECONDS.sleep(200);
                }
                catch (InterruptedException e)
                {}
                resQ_l.setPosition(Buffer4);
                try{
                    TimeUnit.MILLISECONDS.sleep(200);
                }
                catch (InterruptedException e)
                {}
                resQ_l.setPosition(Buffer5);
                try{
                    TimeUnit.MILLISECONDS.sleep(200);
                }
                catch (InterruptedException e)
                {}
                resQ_l.setPosition(RESQ_POSITION);

            }

    }

    public void resQ(boolean gamepad2y, boolean gamepad2a)
    {
        if(gamepad2y)
        {
            resQ_l.setPosition(0.6);
            resq_r.setPosition(0.4);
        }
        else if(gamepad2a)
        {
            resQ_l.setPosition(0.4);
            resq_r.setPosition(0.6);
        }
        else
        {
            resQ_l.setPosition(0.5);
            resq_r.setPosition(0.5);
        }
    }

    public void resQ(Gamepad gamepad2)
    {
        resQ(gamepad2.y, gamepad2.a, gamepad2.left_bumper, gamepad2.right_bumper);
        resQ(gamepad2.y,gamepad2.a);
    }

    public void claw(boolean gamepad1y, boolean gamepad1a, boolean gamepad1up, boolean gamepad1down)
    {
        if(gamepad1y)
        {
            //claw_l.setPosition(0.6);
            claw_r.setPosition(0.4);
        }
        else if(gamepad1a)
        {
            //claw_l.setPosition(0.4);
            claw_r.setPosition(0.6);
        }
        else
        {
            //claw_l.setPosition(0.5);
            claw_r.setPosition(0.5);
        }

        if(gamepad1up){
            claw_l.setPosition(0.6);
        }
        else if(gamepad1down){
            claw_l.setPosition(0.4);
        }
        else{
            claw_l.setPosition(0.5);
        }

    }

    public void claw(Gamepad gamepad1)
    {
        claw(gamepad1.y, gamepad1.a, gamepad1.dpad_up, gamepad1.dpad_down);
    }

    public void lifter(boolean gamepad2dpadup, boolean gamepad2dpaddown)
    {
        if(gamepad2dpadup)
        {
            lifter.setPosition(0.6);
        }
        else if(gamepad2dpaddown)
        {
            lifter.setPosition(0.4);
        }
        else
        {
            lifter.setPosition(0.5);
        }
    }

    public void lifter(Gamepad gamepad2)
    {
        lifter(gamepad2.dpad_up, gamepad2.dpad_down);
    }

}

