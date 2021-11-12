package frc.robot.commands;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.stream.IStreamOutput;
import frc.stream.OutputDataStream;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;


public class RecognizeFace extends CommandBase implements IStreamOutput<Integer> {
    private NetworkTable visionTable;
    private final NetworkTableInstance t = NetworkTableInstance.create();
    public RecognizeFace() {}

    @Override
    public void initialize() {
        t.startClient("10.0.0.2");
        visionTable = t.getTable("Vision");
    }
    @Override
    public void execute() {
        Number value = visionTable.getEntry("facereg").getNumber(0);
        dataStream.changeData(value.intValue());
    }


    @Override
    public void end(boolean interrupted) {
        t.close();
    }

    private OutputDataStream<Integer> dataStream;
    @Override
    public void setOutputStream(OutputDataStream<Integer> dataStream) {
        this.dataStream = dataStream;
    }
}
