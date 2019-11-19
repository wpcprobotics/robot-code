package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;




@Autonomous(name="Experimental Autonomous (Comp)", group="Pushbot")
//@Disabled
public class Autonomousv3 extends LinearOpMode {

    private HardwareofBot         robot   = new HardwareofBot();   // Uses the hardware we have
    private ElapsedTime     runtime = new ElapsedTime();

    private static final double     TICKS_PER_REVOLUTION    = 1680 ;    // eg: Neverest 60 Motor Encoder
    private static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    private static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;
    private static final double     COUNTS_PER_INCH         = (TICKS_PER_REVOLUTION* DRIVE_GEAR_REDUCTION) /
                                                        (WHEEL_DIAMETER_INCHES * 3.1415);
    private static final double     DRIVE_SPEED             = 0.6;
    private static final double     TURN_SPEED              = 0.5;
    private static final double     DISTANCE_BETWEEN_WHEELS = 11;



    @Override
    public void runOpMode() {


        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftDrive.getCurrentPosition(),
                robot.rightDrive.getCurrentPosition());
        telemetry.update();


        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // Strat: Shooting for 5th block to not start in depot.

        encoderDrive(DRIVE_SPEED,  30,  30, 5.0);  // S1: fw 48 5 sec to
        centerTurn(90);
        encoderDrive(DRIVE_SPEED, 7, 7, 2.0);  // S3: fw 30 2 sec to
        centerTurn(90);
        encoderDrive(DRIVE_SPEED, 18, 18, 3.0);  // S5: fw 29 3 sec to
        centerTurn(-90);
        encoderDrive(DRIVE_SPEED,  30,  30, 6.0);  // S7: fw 56 6 sec to
        encoderDrive(DRIVE_SPEED,  -10,  -10, 5.0);  // S9: fw 10 3 sec to


        //encoderDrive(0.05, 19, 19, 28.0); // Backup autonomous

        /*     // NOTE: Previous autonomous changed at competition to reflect change in strategy.
        encoderDrive(DRIVE_SPEED,  46,  46, 5.0);  // S1: fw 48 5 sec to
        encoderDrive(TURN_SPEED,   6, -6, 4.0);  // S2: right 4 sec to
        encoderDrive(DRIVE_SPEED, 30, 30, 2.0);  // S3: fw 30 2 sec to
        encoderDrive(TURN_SPEED,  6, -6, 4.0);  // S4: right 4 sec to
        encoderDrive(DRIVE_SPEED, 29, 29, 3.0);  // S5: fw 29 3 sec to
        encoderDrive(TURN_SPEED,   -6, 6, 4.0);  // S6: left 4 sec to
        encoderDrive(DRIVE_SPEED,  56,  56, 6.0);  // S7: fw 56 6 sec to
        encoderDrive(TURN_SPEED,   -6, 6, 4.0);  // S8: left 4 sec to
        encoderDrive(DRIVE_SPEED,  10,  10, 5.0);  // S9: fw 10 3 sec to
        encoderDrive(DRIVE_SPEED,  -14,  -14, 5.0);  // S10: fw 48 5 sec to
        encoderDrive(TURN_SPEED,   -6, 6, 4.0);  // S11: left 4 sec to
        encoderDrive(DRIVE_SPEED,  38.2,  38.2, 5.0);  // S11: fw 38 5 sec to
        */

        encoderDrive(0.05, 24, 24, 28.0);



        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    private void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftDrive.setPower(Math.abs(speed));
            robot.rightDrive.setPower(Math.abs(speed));


            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
    private void centerTurn(double degrees) {
        double circumference = DISTANCE_BETWEEN_WHEELS * Math.PI;
        double distance = (degrees / 360) * circumference;
        encoderDrive(TURN_SPEED, distance, -distance, 20);
    }
}

