package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorController;



@Autonomous(name = "Autonomous Op Mode", group = "Linear Opmode")
public class autonomousv1 extends LinearOpMode {



    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private Servo bottomServo = null;
    private DcMotor topMotor = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // motors
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        // servos
        bottomServo = hardwareMap.get(Servo.class, "servo_bottom");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
// all init code above this line
        waitForStart();
        runtime.reset();
// all active code below this line

        // actual ACTIVE CODE
        while (opModeIsActive()) {

            double leftPower;
            double rightPower;
            double drive;
            double turn;
            double motorPower = 1;



            if (runtime.seconds() < 1.5) {
                drive = 1;
                turn  = 0;
                telemetry.addData("Direction", "Going Straight");

            }else if (runtime.seconds() < 2 ){
                drive = 0;
                turn  = 1;
                telemetry.addData("Direction", "Turning");
            }else {
                drive = 1;
                turn = 0;
                telemetry.addData("Direction", "Going Straight");
            }


            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;


            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);


            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();

        }}}