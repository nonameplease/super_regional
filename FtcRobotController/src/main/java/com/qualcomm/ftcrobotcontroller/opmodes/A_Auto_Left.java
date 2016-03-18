package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Scott on 2/6/2016.
 */
public class A_Auto_Left extends LinearOpMode {
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
                hardwareMap.opticalDistanceSensor.get("odsl"),
                hardwareMap.opticalDistanceSensor.get("odsr"),
                hardwareMap.analogInput.get("ultrasonic"));

        myA_AutoDrive.rightMotor.setDirection(DcMotor.Direction.REVERSE);

        myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);


        waitForStart();

        //myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        //myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        int flag = 0;
        int ods_failsafe = myA_AutoDrive.getTargetCounts(100);
        String a = "nothing is running";
        int ForwardDistance = 100; //inches
        int TurningDistance = 20; //inches

        while(opModeIsActive())
        {

            int[] count = {0,0};

            if(flag == 0) {
                a = " start encoder drive";
                count[0] = myA_AutoDrive.leftMotor.getCurrentPosition();
                count[1] = myA_AutoDrive.rightMotor.getCurrentPosition();
                myA_AutoDrive.encoderDrive3(ForwardDistance, -0.3, -0.3, count[0], count[1]);
                count[0] = myA_AutoDrive.leftMotor.getCurrentPosition();
                count[1] = myA_AutoDrive.rightMotor.getCurrentPosition();
                sleep(200);
                myA_AutoDrive.encoderDrive3(TurningDistance, 0.3, -0.3, count[0], count[1]);
                count[0] = myA_AutoDrive.leftMotor.getCurrentPosition();
                count[1] = myA_AutoDrive.rightMotor.getCurrentPosition();
                sleep(300);
                a = "start finding line";
                myA_AutoDrive.find_line("white", this);
                sleep(300);
                a = "start finding wall";
                myA_AutoDrive.odsDriveToDistance(ods_failsafe, this);
                a = "program finished";

                flag++;
            }

        /*    if(flag == 1) {
                myA_AutoDrive.resQ_l.setPosition(0.6);
                myA_AutoDrive.resq_r.setPosition(0.4);
                sleep(1000);
                myA_AutoDrive.resQ_l.setPosition(0.5);
                myA_AutoDrive.resq_r.setPosition(0.5);

                flag++;
            }*/
            telemetry.addData("This program needs to calibrate encoder, color sensor, and climbers", null);
            telemetry.addData("Status: ", a);
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
