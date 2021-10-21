package frc.robot.commands.facialcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.stream.IStreamInput;
import frc.stream.InputDataStream;
import frc.robot.subsystems.Drivetrain;
import org.opencv.core.Rect;


public class DriveTowardsFace extends CommandBase implements IStreamInput<Rect[]> {
    private final Drivetrain drivetrain;

    public DriveTowardsFace(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(this.drivetrain);
    }

    @Override
    public void execute() {
        var data = dataStream.readData();
        if(data != null && data.length > 0) { //rotate if no face seen,
            drivetrain.arcadeDrive(.5, 0);
        } else { //if face found, go forward
            drivetrain.arcadeDrive(0, .5);
        }
    }

    //sets up data stream
    private InputDataStream<Rect[]> dataStream;
    @Override
    public void setInputStream(InputDataStream<Rect[]> dataStream) {
        this.dataStream=dataStream;
    }
}
