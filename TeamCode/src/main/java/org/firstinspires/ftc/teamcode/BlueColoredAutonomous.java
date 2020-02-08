/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Colored Blue Autonomous", group="Blue")
//@Disabled
public class BlueColoredAutonomous extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private HardwareOfBot robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new HardwareOfBot(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // Centimeters for vertical and horizontal, degrees for turn
        moveExtender(700,false);
        sleep(1000);
        encoderDrive(80,0,0);//Approach block
        robot.frontLeft.setPower(-0.2);
        robot.frontRight.setPower(0.2);
        robot.backLeft.setPower(0.2);
        robot.backRight.setPower(-0.2);
        while (robot.colorSensor.red() > 60 & opModeIsActive()) {
            telemetry.addData("Red",robot.colorSensor.red());
            telemetry.addData("Blue",robot.colorSensor.blue());
            telemetry.addData("Green",robot.colorSensor.green());
            telemetry.update();
        }
        sleep(200);
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
        encoderDrive(-5,0,0);
        moveExtender(-700,true);
        encoderDrive(10,0,0);
        robot.brickClaw.setPosition(1);//Grab block
        sleep(1500);
        encoderDrive(-70,0,0);
        moveExtender(430, true);
        encoderDrive(0,-300,0);//Cross under bridge
        encoderDrive(0,50,0);
        moveExtender(400,true);
        encoderDrive(70,0,0);
        robot.brickClaw.setPosition((double) 1/3);
        encoderDrive(-30,0,0);
        /*

        moveExtender(900, true);
        encoderDrive(62,0,0);
        robot.brickClaw.setPosition((double)1/3);
        encoderDrive(0,40,0);
        moveExtender(-1000, true);
        encoderDrive(-90,0,0);
        moveExtender(250, true);
        encoderDrive(0,-150,0);
         */



        // Turn off all wheels when done
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);

        // Display that robot stopped, and total run time
        telemetry.addData("Finished", "Run Time: " + runtime.toString());
        telemetry.update();
    }

    private void encoderDrive(double vertical, double horizontal, double turn) {
        int frontLeftPosition = (int) (robot.frontLeft.getCurrentPosition() + HardwareOfBot.ONE_CENTIMETER * vertical - HardwareOfBot.ONE_CENTIMETER * horizontal + HardwareOfBot.ONE_DEGREE * turn);
        int frontRightPosition = (int) (robot.frontRight.getCurrentPosition() + HardwareOfBot.ONE_CENTIMETER * vertical + HardwareOfBot.ONE_CENTIMETER * horizontal - HardwareOfBot.ONE_DEGREE * turn);
        int backLeftPosition = (int) (robot.backLeft.getCurrentPosition() + HardwareOfBot.ONE_CENTIMETER * vertical + HardwareOfBot.ONE_CENTIMETER * horizontal + HardwareOfBot.ONE_DEGREE * turn);
        int backRightPosition = (int) (robot.backRight.getCurrentPosition() + HardwareOfBot.ONE_CENTIMETER * vertical - HardwareOfBot.ONE_CENTIMETER * horizontal - HardwareOfBot.ONE_DEGREE * turn);

        robot.frontLeft.setTargetPosition(frontLeftPosition);
        robot.frontRight.setTargetPosition(frontRightPosition);
        robot.backLeft.setTargetPosition(backLeftPosition);
        robot.backRight.setTargetPosition(backRightPosition);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeft.setPower(1);
        robot.frontRight.setPower(1);
        robot.backLeft.setPower(1);
        robot.backRight.setPower(1);

        while (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) {
            telemetry.addData("Target Encoders", "FL (%d), FR (%d), BL (%d), BR (%d)", frontLeftPosition, frontRightPosition, backLeftPosition, backRightPosition);
            telemetry.addData("Current Encoders", "FL (%d), FR (%d), BL (%d), BR (%d)", robot.frontLeft.getCurrentPosition(), robot.frontRight.getCurrentPosition(), robot.backLeft.getCurrentPosition(), robot.backRight.getCurrentPosition());
            telemetry.update();
        }

        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void moveExtender(int encoderChange, boolean wait) {
        int targetPosition = robot.brickExtender.getCurrentPosition() + encoderChange;
        robot.brickExtender.setTargetPosition(targetPosition);
        robot.brickExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.brickExtender.setPower(1);
        if (wait) {
            while (robot.brickExtender.isBusy()) {
                telemetry.addData("Target", targetPosition);
                telemetry.addData("Position", robot.brickExtender.getCurrentPosition());
                telemetry.update();
            }
            robot.brickExtender.setPower(0);
            robot.brickExtender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
