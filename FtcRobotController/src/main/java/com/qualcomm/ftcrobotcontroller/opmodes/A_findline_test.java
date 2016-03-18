package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Scott on 3/13/2016.
 */
public class A_findline_test extends LinearOpMode {
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

        myA_AutoDrive.leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        myA_AutoDrive.rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        int flag = 0;

        while(opModeIsActive()){

           /* if(flag == 0) {
                myA_AutoDrive.find_line("white");

                flag++;
            }

            telemetry.addData("color", myA_AutoDrive.colorDetected());*/
            char state = 'n';
            do{
                telemetry.addData("colorDetected", myA_AutoDrive.colorDetected());
                if(myA_AutoDrive.colorDetected() != 2) {
                    myA_AutoDrive.leftMotor.setPower(-0.2);
                    myA_AutoDrive.rightMotor.setPower(-0.2);
                    state = 'y';
                    waitOneFullHardwareCycle();
                }
                else if(myA_AutoDrive.colorDetected() == 2){
                    myA_AutoDrive.leftMotor.setPower(0);
                    myA_AutoDrive.rightMotor.setPower(0);
                    state = 'n';
                    waitOneFullHardwareCycle();
                }
            }
            while(state == 'y');
            myA_AutoDrive.leftMotor.setPowerFloat();
            myA_AutoDrive.rightMotor.setPowerFloat();

            waitOneFullHardwareCycle();
        }

        /*int color = 0;
        sleep(200);
            do{
                myA_AutoDrive.leftMotor.setPower(0.2);
                myA_AutoDrive.rightMotor.setPower(0.2);
                color = myA_AutoDrive.colorDetected();
                sleep(200);

           // }
            telemetry.addData("color detected", myA_AutoDrive.colorDetected());
            telemetry.addData("left motor power", myA_AutoDrive.leftMotor.getPower());
            telemetry.addData("right motor power", myA_AutoDrive.rightMotor.getPower());
            }
            while(color != 2);
        myA_AutoDrive.leftMotor.setPower(0);
        myA_AutoDrive.rightMotor.setPower(0);*/

    }
}
