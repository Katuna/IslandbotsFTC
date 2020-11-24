package org.firstinspires.ftc.teamcode.mode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.CompetitionBot;
import org.firstinspires.ftc.teamcode.vision.RingsOpenCV;

@Autonomous(name="BlueMainAuto", group="Autonomous")
public class BlueMainAuto extends AutonomousNew {
    @Override
    public void runOpMode() {
        robot = new CompetitionBot(hardwareMap, telemetry);
        RingsOpenCV vision = new RingsOpenCV(hardwareMap, telemetry);

        waitForStart();
        blueMainAuto(vision, telemetry);
    }

}
