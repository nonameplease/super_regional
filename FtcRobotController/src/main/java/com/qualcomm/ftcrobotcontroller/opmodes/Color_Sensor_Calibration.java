package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by owner on 12/4/2015.
 */
public class Color_Sensor_Calibration extends OpMode {

    DeviceInterfaceModule dim;
   // OpticalDistanceSensor leftDistance;
    //OpticalDistanceSensor rightDistance;
    //double reflectanceLeft = leftDistance.getLightDetected();
    //double reflectanceRight = rightDistance.getLightDetected();
    ColorSensor colorRGB;

    @Override
    public void init() {
        dim = hardwareMap.deviceInterfaceModule.get("device");
        //leftDistance = hardwareMap.opticalDistanceSensor.get("odslf");
        //rightDistance = hardwareMap.opticalDistanceSensor.get("odsrf");
        colorRGB = hardwareMap.colorSensor.get("color");

    }

    @Override
    public void loop() {
        int Red = colorRGB.red();
        int Blue = colorRGB.blue();
        int Green = colorRGB.green();

        //telemetry.addData("LeftDistance", reflectanceLeft);
        //telemetry.addData("RightDistance", reflectanceRight);
        telemetry.addData("Red", Red);
        telemetry.addData("Blue", Blue);
        telemetry.addData("Green", Green);

    }
}
