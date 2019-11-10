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
 *//*


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

*/
/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *//*


@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
@Disabled
public class Base extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor oneDrive = null;
    private DcMotor twoDrive = null;
    private DcMotor threeDrive = null;
    private DcMotor fourDrive = null;
    private Servo oneservo = null;

    */
/*
     * Code to run ONCE when the driver hits INIT
     *//*

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        oneDrive  = hardwareMap.get(DcMotor.class, "oneDrive");
        twoDrive = hardwareMap.get(DcMotor.class, "twoDrive");
        threeDrive = hardwareMap.get(DcMotor.class, "threeDrive");
        fourDrive = hardwareMap.get(DcMotor.class, "fourDrive");
        oneServo = hardwareMap.get(Servo.class, "oneServo")

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        oneDrive.setDirection(DcMotor.Direction.FORWARD);
        twoDrive.setDirection(DcMotor.Direction.REVERSE); // Forward or backward? Test this
        threeDrive.setDirection(DcMotor.Direction.FORWARD);
        fourDrive.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    */
/*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     *//*

    @Override
    public void init_loop() {
    }

    */
/*
     * Code to run ONCE when the driver hits PLAY
     *//*

    @Override
    public void start() {
        runtime.reset();
    }

    */
/*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     *//*

    @Override
    public void loop() {
        */
/*
        Original code:
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;
        leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        // leftPower  = -gamepad1.left_stick_y ;
        // rightPower = -gamepad1.right_stick_y ;

        // Send calculated power to wheels
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
         *//*

        // New stuff
        */
/*int onePower;
        int twoPower;
        int threePower;
        int fourPower;

        onePower = 20;
        twoPower = 60;
        threePower = 100;
        fourPower = 150;

        if (gamepad1.x){
            oneDrive.setPower(onePower);
        } else {
            oneDrive.setPower(0);
        }
        if (gamepad1.b){
            twoDrive.setPower(twoPower);
        } else {
            twoDrive.setPower(0);
        }
        if (gamepad1.y){
            threeDrive.setPower(threePower);
        } else {
            threeDrive.setPower(0);
        }
        if (gamepad1.a){
            fourDrive.setPower(fourPower);
        } else {
            fourDrive.setPower(0);
        }
    }
*//*

        //double tgtPower = 0;
        while (opModeIsActive()) {
            //tgtPower = -this.gamepad1.left_stick_y;
            //motorTest.setPower(tgtPower);
            // check to see if we need to move the servo.
            if(gamepad1.y) {
                // move to 0 degrees.
                servoTest.setPosition(0);
            } else if (gamepad1.x) {
                // move to 90 degrees forward.
                servoTest.setPosition(0.5);
            } else if (gamepad1.b) {
                servoTest.setDirection(REVERSE);
                servoTest.setPosition(0.5);
            }
            } else if (gamepad1.a) {
                // move to 180 degrees.
                servoTest.setPosition(1);
    */
/*
     * Code to run ONCE after the driver hits STOP
     *//*

    @Override
    public void stop() {
    }

}
*/
