package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Scott on 2/6/2016.
 */
public class odsdrivetodistance_linear extends LinearOpMode {
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

        while(opModeIsActive())
        {

            if(flag == 0) {
                //myA_AutoDrive.odsDriveToDistance(ods_failsafe, this);

                flag++;
            }

            myA_AutoDrive.leftMotor.setPower(0);
            myA_AutoDrive.rightMotor.setPower(0);


            telemetry.addData("left encoder", myA_AutoDrive.leftMotor.getCurrentPosition());
            telemetry.addData("right encoder", myA_AutoDrive.rightMotor.getCurrentPosition());
            //telemetry.addData("left encoder goal", myA_AutoDrive.getTargetCounts(50));
            telemetry.addData("odsl", myA_AutoDrive.distance_l());
            telemetry.addData("odsr", myA_AutoDrive.distance_r());
            telemetry.addData("color", myA_AutoDrive.colorDetected());
            telemetry.addData("leftpower", myA_AutoDrive.leftMotor.getPower());
            telemetry.addData("rightpower", myA_AutoDrive.rightMotor.getPower());
            telemetry.addData("flag", flag);

            waitOneFullHardwareCycle();
        }

    }
}
