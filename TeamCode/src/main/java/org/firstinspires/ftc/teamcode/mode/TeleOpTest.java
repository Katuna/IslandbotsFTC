package org.firstinspires.ftc.teamcode.mode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.robot.CompetitionBot;
import org.firstinspires.ftc.teamcode.robot.GamepadButton;


// @TeleOp(name="TeleOpTest", group="Competition")
@Disabled
public class TeleOpTest extends LinearOpMode {

    public boolean waitAndContinue(long initTime, long duration) {
        return (System.currentTimeMillis() - initTime > duration);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        CompetitionBot robot = new CompetitionBot(hardwareMap, telemetry);

        // BUTTON DECLARE
        // Gamepad 1
        GamepadButton slowToggleButton = new GamepadButton(300, false);
        GamepadButton fastHoldButton = new GamepadButton(300, false);
        GamepadButton reverseToggleButton = new GamepadButton(300, false);
        GamepadButton foundationDownButton = new GamepadButton(300, false);
        GamepadButton foundationInterButton = new GamepadButton(300, false);
        GamepadButton foundationUpButton = new GamepadButton(300, false);
        GamepadButton fullForwardButton = new GamepadButton(300, false);
        GamepadButton fullBackwardButton = new GamepadButton(300, false);
        GamepadButton fullLeftButton = new GamepadButton(300, false);
        GamepadButton fullRightButton = new GamepadButton(300, false);

        // Gamepad 2
        GamepadButton slideUpButton = new GamepadButton(300, false);
        GamepadButton slideDownButton = new GamepadButton(300, false);
        GamepadButton slideHomeButton = new GamepadButton(300, false);
        GamepadButton grabberServoButton = new GamepadButton(300, false);
        GamepadButton armRotateButton = new GamepadButton(300, false);
        GamepadButton intakeButton = new GamepadButton(300, false);
        GamepadButton reverseIntakeButton = new GamepadButton(300, false);
        GamepadButton capStoneButton = new GamepadButton(300, false);
        GamepadButton tapeMeasureOutButton = new GamepadButton(300, false);
        GamepadButton tapeMeasureInButton = new GamepadButton(300, false);
        GamepadButton beamsButton = new GamepadButton(300, false);

        boolean slideActive = false;

        long initTime = 0;
        boolean waitForArm = false;

        double[] powerList = {0, 0, 0, 0};

        waitForStart();
        while(opModeIsActive()) {
            // CONTROLS
            // Gamepad 1
            double x = -gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rotation = gamepad1.right_stick_x;

            boolean slowToggleBool = gamepad1.right_stick_button;
            boolean reverseToggleBool = gamepad1.left_stick_button;

            boolean foundationDownBool = gamepad1.a;
            boolean foundationInterBool = gamepad1.b;
            boolean foundationUpBool = gamepad1.y;

            boolean fastHoldBool = gamepad1.right_bumper;

            boolean fullForwardBool = gamepad1.dpad_up;
            boolean fullBackwardBool = gamepad1.dpad_down;
            boolean fullLeftBool = gamepad1.dpad_left;
            boolean fullRightBool = gamepad1.dpad_right;

            // Gamepad 2
            double slide_y = gamepad2.left_stick_y;
            boolean slideHome = gamepad2.a;

            boolean slideUpBool = gamepad2.dpad_up;
            boolean slideDownBool = gamepad2.dpad_down;

            boolean grabberServoBool = gamepad2.right_bumper;
            boolean armRotateServoBool = gamepad2.left_bumper;
            boolean capStoneServoBool = gamepad2.right_stick_button;

            boolean intakeBool = gamepad2.x;
            boolean reverseIntakeBool = gamepad2.b;

            boolean tapeMeasureOutBool = gamepad2.dpad_right;
            boolean tapeMeasureInBool = gamepad2.dpad_left;

            boolean beamsBool = gamepad2.y;

            // BUTTON DEBOUNCE
            // Gamepad 1
            slowToggleButton.checkStatus(slowToggleBool);
            reverseToggleButton.checkStatus(reverseToggleBool);
            foundationDownButton.checkStatus(foundationDownBool);
            foundationInterButton.checkStatus(foundationInterBool);
            foundationUpButton.checkStatus(foundationUpBool);
            fastHoldButton.checkStatus(fastHoldBool);
            fullForwardButton.checkStatus(fullForwardBool);
            fullBackwardButton.checkStatus(fullBackwardBool);
            fullLeftButton.checkStatus(fullLeftBool);
            fullRightButton.checkStatus(fullRightBool);

            // Gamepad 2
            slideHomeButton.checkStatus(slideHome);
            slideUpButton.checkStatus(slideUpBool);
            slideDownButton.checkStatus(slideDownBool);
            grabberServoButton.checkStatus(grabberServoBool);
            armRotateButton.checkStatus(armRotateServoBool);
            capStoneButton.checkStatus(capStoneServoBool);
            intakeButton.checkStatus(intakeBool);
            reverseIntakeButton.checkStatus(reverseIntakeBool);
            tapeMeasureOutButton.checkStatus(tapeMeasureOutBool);
            tapeMeasureInButton.checkStatus(tapeMeasureInBool);
            beamsButton.checkStatus(beamsBool);


            if (reverseToggleButton.pressed) {
                x = gamepad1.left_stick_x;
                y = gamepad1.left_stick_y;
            }

            if (slide_y > .05) {
                robot.SlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.SlideMotor.setPower(slide_y * slide_y);
                slideActive = true;
            } else if (slide_y < -.05) {
                robot.SlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.SlideMotor.setPower(-(slide_y * slide_y));
                slideActive = true;
            } else if (slideUpButton.buttonStatus) {
                robot.SlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.SlideMotor.setPower(-.75);
                slideActive = true;
            } else if (slideDownButton.buttonStatus) {
                robot.SlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.SlideMotor.setPower(.75);
                slideActive = true;
            } else {
                // no active control
                if (slideActive) {
                    // slide was active in previous loop - so we just released controls
                    robot.SlideMotor.setTargetPosition(robot.SlideMotor.getCurrentPosition() + 10);
                    robot.SlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.SlideMotor.setPower(.5);
                    slideActive = false;
                }
            }

            if (slideHomeButton.pressed) {
                if (robot.armRotateServo.getPosition() == CompetitionBot.ARM_OUT) {
                    robot.armRotateServo.setPosition(CompetitionBot.ARM_IN);
                    armRotateButton.pressedSwitchStatus();

                    initTime = System.currentTimeMillis();
                    waitForArm = false;
                    if (robot.SlideMotor.getCurrentPosition() > -1500) waitForArm = true;
                }
                if (!waitForArm || waitAndContinue(initTime, 500)) {
                    robot.SlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    if (robot.SlideMotor.getCurrentPosition() < -100) {
                        robot.SlideMotor.setPower(.75);
                    }
                    else {
                        robot.SlideMotor.setPower(0);
                        slideHomeButton.pressedSwitchStatus();
                    }
                }
            }

            if (grabberServoButton.pressed) {
                robot.grabberServo.setPosition(CompetitionBot.GRABBER_CLOSED);
            } else {
                robot.grabberServo.setPosition(CompetitionBot.GRABBER_OPEN);
            }

            if (armRotateButton.pressed) {
                robot.armRotateServo.setPosition(CompetitionBot.ARM_OUT);
            } else {
                robot.armRotateServo.setPosition(CompetitionBot.ARM_IN);
            }

//            if (capStoneButton.pressed) {
//                robot.capStoneServo.setPosition(CompetitionBot.CAPSTONE_OPEN);
//            } else {
//                robot.capStoneServo.setPosition(CompetitionBot.CAPSTONE_CLOSED);
//            }

            if (reverseIntakeButton.buttonStatus) {
                robot.IntakeMotor.setPower(-1);
            } else {
                if (intakeButton.pressed) {
                    robot.IntakeMotor.setPower(1);
                } else {
                    robot.IntakeMotor.setPower(0);
                }
            }

//            if (foundationDownButton.justPressed) {
//                robot.Lfoundation.setPosition(CompetitionBot.L_FOUND_DOWN);
//                robot.Rfoundation.setPosition(CompetitionBot.R_FOUND_DOWN);
//                foundationUpButton.pressed = false;
//                foundationInterButton.pressed = false;
//            }
//            if (foundationInterButton.justPressed) {
//                robot.Lfoundation.setPosition(CompetitionBot.L_FOUND_INTER);
//                robot.Rfoundation.setPosition(CompetitionBot.R_FOUND_INTER);
//                foundationDownButton.pressed = false;
//                foundationUpButton.pressed = false;
//            }
//            if (foundationUpButton.justPressed) {
//                robot.Lfoundation.setPosition(CompetitionBot.L_FOUND_UP);
//                robot.Rfoundation.setPosition(CompetitionBot.R_FOUND_UP);
//                foundationDownButton.pressed = false;
//                foundationInterButton.pressed = false;
//            }
//
//            if (tapeMeasureOutButton.buttonStatus) {
//                robot.TapeMeasure.setPower(1);
//            } else if (tapeMeasureInButton.buttonStatus) {
//                robot.TapeMeasure.setPower(-1);
//            } else {
//                robot.TapeMeasure.setPower(0);
//            }
//
//            if (beamsButton.pressed) {
//                robot.LbeamServo.setPosition(CompetitionBot.L_BEAM_DOWN);
//                robot.RbeamServo.setPosition(CompetitionBot.R_BEAM_DOWN);
//            } else {
//                robot.LbeamServo.setPosition(CompetitionBot.L_BEAM_UP);
//                robot.RbeamServo.setPosition(CompetitionBot.R_BEAM_UP);
//            }

            // MOVEMENT
            if (fullForwardButton.buttonStatus) {
                robot.setMotors(1, 1, 1, 1);
            } else if (fullBackwardButton.buttonStatus) {
                robot.setMotors(-1, -1, -1, -1);
            } else if (fullLeftButton.buttonStatus) {
                robot.setMotors(-1, 1, 1, -1);
            } else if (fullRightButton.buttonStatus) {
                robot.setMotors(1, -1, -1, 1);
            } else {
                rotation = Math.abs(rotation) < .1 ? 0 : rotation; // "Dead-zone" for joystick
                powerList = robot.mecanumMove(x, y, rotation, slowToggleButton.pressed, fastHoldButton.buttonStatus, telemetry);
            }

            telemetry.addData("LF Pos: ", robot.LFmotor.getCurrentPosition());
            telemetry.addData("LF Pow: ", Math.round(powerList[0] * 100.0) / 100.0);
            telemetry.addData("LB Pos: ", robot.LBmotor.getCurrentPosition());
            telemetry.addData("LB Pow: ", Math.round(powerList[1] * 100.0) / 100.0);
            telemetry.addData("RF Pos: ", robot.RFmotor.getCurrentPosition());
            telemetry.addData("RF Pow: ", Math.round(powerList[2] * 100.0) / 100.0);
            telemetry.addData("RB Pos: ", robot.RBmotor.getCurrentPosition());
            telemetry.addData("RB Pow: ", Math.round(powerList[3] * 100.0) / 100.0);
            telemetry.addData("joyX: ", gamepad1.left_stick_x);
            telemetry.addData("joyY: ", gamepad1.left_stick_y);
            telemetry.addData("X: ", slowToggleButton.pressed);
            Orientation angOrientation = robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Orientation", angOrientation.firstAngle);
            telemetry.addData("Slide Pos: ", robot.SlideMotor.getCurrentPosition());
//            telemetry.addData("Sonar: ", robot.sonarDistance.getVoltage());
            telemetry.update();

        }

    }
}
