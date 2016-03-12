package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Scott on 2/6/2016.
 */
public class A_Auto_3 extends LinearOpMode {
    A_AutoDrive myA_AutoDrive;
    @Override
    public void runOpMode() throws InterruptedException {

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


        waitForStart();



        while(opModeIsActive())
        {
            myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            double leftcount = 0.0;
            double rightcount = 0.0;

            myA_AutoDrive.encoderDrive(50, 0.3, 0.3, leftcount, rightcount);
            myA_AutoDrive.encoderDrive(50, 0.3, -0.3, leftcount, rightcount);

            /*int a = 0;
            if (myA_AutoDrive.colorDetected() == 2)
            {
                a = 1;
            }

            if(a == 0)
            {
                myA_AutoDrive.leftMotor.setPower(-1);
                myA_AutoDrive.rightMotor.setPower(-1);
            }
            else if(a == 1)
            {
                myA_AutoDrive.odsDriveToDistance();
                myA_AutoDrive.resQ_l.setPosition(0.55);
                myA_AutoDrive.resq_r.setPosition(0.45);
            }
            else
            {
                myA_AutoDrive.leftMotor.setPowerFloat();
                myA_AutoDrive.rightMotor.setPowerFloat();
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
            telemetry.addData("count", (leftcount + rightcount)/2);

            waitOneFullHardwareCycle();
        }

    }
}
