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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Single Driver Op", group="Linear Opmode")
//@Disabled
public class SingleMecanumWheels extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        HardwareOfBot robot = new HardwareOfBot(hardwareMap);
        boolean zoomMode = false;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double vertical = -gamepad1.left_stick_y;
            double horizontal = -gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            if (gamepad1.b) {
                telemetry.addData("Mode", "I WOKE");
            }else {
                telemetry.addData("Mode","i sleep!");
                vertical /= 2;
                horizontal /= 2;
                turn /= 2;
            }

            double frontLeftPower = Range.clip(vertical + horizontal + turn, -1 ,1);
            double frontRightPower = Range.clip(vertical - horizontal - turn, -1, 1);
            double backLeftPower = Range.clip(vertical - horizontal + turn, -1, 1);
            double backRightPower = Range.clip(vertical + horizontal - turn, -1, 1);

            // Send calculated power to wheels
            robot.frontLeft.setPower(frontLeftPower);
            robot.frontRight.setPower(frontRightPower);
            robot.backLeft.setPower(backLeftPower);
            robot.backRight.setPower(backRightPower);

            //Control brick arm
            robot.brickExtender.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
            if (gamepad1.left_bumper) robot.brickClaw.setPosition(1);
            else if (gamepad1.right_bumper) robot.brickClaw.setPosition((double)1/3);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)", frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            telemetry.addData("Encoders", "FL (%d), FR (%d), BL (%d), BR (%d)", robot.frontLeft.getCurrentPosition(), robot.frontRight.getCurrentPosition(), robot.backLeft.getCurrentPosition(), robot.backRight.getCurrentPosition());
            telemetry.addData("Brick Extender", "Power(%.2f), Encoder(%d)", robot.brickExtender.getPower(), robot.brickExtender.getCurrentPosition());
            telemetry.addData("Brick Claw", robot.brickClaw.getPosition());
            telemetry.update();
        }

        // Turn off all wheels when done
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);

        // Display that robot stopped, and total run time
        telemetry.addData("Finished", "Run Time: " + runtime.toString());
        telemetry.update();
    }
}
