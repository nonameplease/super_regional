package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Scott on 2/5/2016.
 */
public class A_Auto_2 extends OpMode {

    A_AutoDrive myA_AutoDrive;

    @Override
    public void init() {

        myA_AutoDrive = new A_AutoDrive(hardwareMap.dcMotor.get("left_drive"),
                hardwareMap.dcMotor.get("right_drive"),
                hardwareMap.dcMotor.get("left_drive_rotate"),
                hardwareMap.dcMotor.get("right_drive_rotate"),
                hardwareMap.dcMotor.get("climber"),
                hardwareMap.servo.get("lifter"),
                hardwareMap.servo.get("claw_r"),
                hardwareMap.servo.get("claw_l"),
                hardwareMap.servo.get("resQ_r"),
                hardwareMap.servo.get("resQ_l"),
                hardwareMap.deviceInterfaceModule.get("device"),
                hardwareMap.colorSensor.get("color"),
                hardwareMap.analogInput.get("odsl"),
                hardwareMap.analogInput.get("odsr"));

        myA_AutoDrive.encoderReset();

    }

    @Override
    public void loop()
    {
        int a = 0;
        int white = 2;
        if (myA_AutoDrive.colorDetected() == white)
        {
            a = 1;
        }

        switch (myA_AutoDrive.colorDetected()){
            case 2:{
                myA_AutoDrive.leftMotor.setPower(-1);
                myA_AutoDrive.rightMotor.setPower(-1);
            }
            case 1:{
                myA_AutoDrive.leftMotor.setPowerFloat();
                myA_AutoDrive.rightMotor.setPowerFloat();
            }
        }

       /* if(a == 0)
        {
            myA_AutoDrive.leftMotor.setPower(-1);
            myA_AutoDrive.rightMotor.setPower(-1);
        }

        if(a == 1)
        {
            myA_AutoDrive.leftMotor.setPowerFloat();
            myA_AutoDrive.rightMotor.setPowerFloat();
            myA_AutoDrive.resQ_l.setPosition(0.55);
            myA_AutoDrive.resq_r.setPosition(0.45);
        }*/

       // if(!myA_AutoDrive.leftMotor.isBusy() && !myA_AutoDrive.rightMotor.isBusy()) {
        //    myA_AutoDrive.resQ_l.setPosition(0.55);
         //   myA_AutoDrive.resq_r.setPosition(0.45);
        //}

        telemetry.addData("left encoder", myA_AutoDrive.leftMotor.getCurrentPosition());
        telemetry.addData("right encoder", myA_AutoDrive.rightMotor.getCurrentPosition());
        telemetry.addData("encoder goal", myA_AutoDrive.getTargetCounts(50));
        telemetry.addData("odsl", myA_AutoDrive.distance_l());
        telemetry.addData("odsr", myA_AutoDrive.distance_r());
        telemetry.addData("color", myA_AutoDrive.colorDetected());
        telemetry.addData("leftpower", myA_AutoDrive.leftMotor.getPower());
        telemetry.addData("rightpower", myA_AutoDrive.rightMotor.getPower());
        telemetry.addData("state", a);
    }
}
