package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Scott on 1/31/2016.
 */
public class A_Tele extends LinearOpMode {

    A_RobotDrive myA_RobotDrive;

    @Override
    public void runOpMode() throws InterruptedException {

        myA_RobotDrive = new A_RobotDrive(hardwareMap.dcMotor.get("left_drive"),
                                          hardwareMap.dcMotor.get("right_drive"),
                                          hardwareMap.dcMotor.get("left_drive_rotate"),
                                          hardwareMap.dcMotor.get("right_drive_rotate"),
                                          hardwareMap.dcMotor.get("climber"),
                                          hardwareMap.servo.get("lifter"),
                                          //hardwareMap.servo.get("bucket"),
                                          hardwareMap.servo.get("claw_r"),
                                          hardwareMap.servo.get("claw_l"),
                                          hardwareMap.servo.get("resQ_r"),
                                          hardwareMap.servo.get("resQ_l"));


        waitForStart();

        while(opModeIsActive())
        {
            myA_RobotDrive.tankDrive(gamepad1);
            myA_RobotDrive.rotateDrive(gamepad1);
            myA_RobotDrive.climberDrive(gamepad2);
            myA_RobotDrive.resQ(gamepad2);
            myA_RobotDrive.claw(gamepad1);
            myA_RobotDrive.lifter(gamepad2);

        }

        telemetry.addData("left motor power", myA_RobotDrive.leftMotor.getPower());
        telemetry.addData("right motor power", myA_RobotDrive.rightMotor.getPower());
        telemetry.addData("climber position", myA_RobotDrive.climber.getPower());

        waitOneFullHardwareCycle();

    }
}
