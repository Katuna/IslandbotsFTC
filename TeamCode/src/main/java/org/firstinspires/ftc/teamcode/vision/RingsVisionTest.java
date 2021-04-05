package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.CompetitionBot;

@Autonomous(name="RingsVisionTest", group="Test")
public class RingsVisionTest extends LinearOpMode {

    public void runOpMode() {
        CompetitionBot robot = new CompetitionBot(hardwareMap, telemetry);
        RingsOpenCV vision = new RingsOpenCV(robot.STREAM_WIDTH - robot.REGION_WIDTH, (robot.STREAM_HEIGHT - robot.REGION_HEIGHT) / 2 - 70, hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            vision.analyzeRings(telemetry);
            sleep(50);
        }
    }

}
