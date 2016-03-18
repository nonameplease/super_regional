package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Scott on 12/30/2015.
 */
public class dual_ods_test extends OpMode {

    DeviceInterfaceModule dim;
    OpticalDistanceSensor ods_l;
    OpticalDistanceSensor ods_r;



    DcMotor leftMotor;
    DcMotor rightMotor;

    @Override
    public void init() {
        dim = hardwareMap.deviceInterfaceModule.get("device");
        ods_l = hardwareMap.opticalDistanceSensor.get("odsl");
        ods_r = hardwareMap.opticalDistanceSensor.get("odsr");
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {
        double distance_l = ods_l.getLightDetected();
        double distance_r = ods_r.getLightDetected();

        if(distance_l < 0.25)
        {
            leftMotor.setPower(-0.2);
        }
        else if(distance_l > 0.3 )
        {
            leftMotor.setPower(0.2);
        }
        else
        {
            leftMotor.setPower(0);
        }

        if(distance_r < 0.25)
        {
            rightMotor.setPower(-0.2);
        }
        else if(distance_r > 0.3)
        {
            rightMotor.setPower(0.2 );
        }
        else
        {
            rightMotor.setPower(0);
        }

        telemetry.addData("Distance Detected Left", distance_l);
        telemetry.addData("Distance Detected Right", distance_r);

    }
}
