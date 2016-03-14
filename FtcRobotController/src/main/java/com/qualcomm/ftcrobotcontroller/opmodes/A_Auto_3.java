package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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

        myA_AutoDrive.rightMotor.setDirection(DcMotor.Direction.REVERSE);

        myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);


        waitForStart();

        //myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        //myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        int flag = 0;

        while(opModeIsActive())
        {

            int[] count = {0,0};

            if(flag == 0) {
                count[0] = myA_AutoDrive.leftMotor.getCurrentPosition();
                count[1] = myA_AutoDrive.rightMotor.getCurrentPosition();
                myA_AutoDrive.encoderDrive3(100, 0.3, 0.3, count[0], count[1]);
                count[0] = myA_AutoDrive.leftMotor.getCurrentPosition();
                count[1] = myA_AutoDrive.rightMotor.getCurrentPosition();
                sleep(200);
                myA_AutoDrive.encoderDrive3(50, 0.3, -0.3, count[0], count[1]);


                flag++;
            }

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
            //telemetry.addData("left encoder goal", myA_AutoDrive.getTargetCounts(50));
            telemetry.addData("odsl", myA_AutoDrive.distance_l());
            telemetry.addData("odsr", myA_AutoDrive.distance_r());
            telemetry.addData("color", myA_AutoDrive.colorDetected());
            telemetry.addData("leftpower", myA_AutoDrive.leftMotor.getPower());
            telemetry.addData("rightpower", myA_AutoDrive.rightMotor.getPower());
            telemetry.addData("countleft", count[0]);
            telemetry.addData("countright", count[1]);
            telemetry.addData("flag", flag);

            waitOneFullHardwareCycle();
        }

    }
}
