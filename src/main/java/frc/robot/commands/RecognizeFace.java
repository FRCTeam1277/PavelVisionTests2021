package frc.robot.commands;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.stream.IStreamOutput;
import frc.stream.OutputDataStream;
import frc.robot.subsystems.CameraIO;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.IOException;


public class RecognizeFace extends CommandBase implements IStreamOutput<Integer> {
    //gets casscade file for face detection
    private final String cmlFacialDetectionFile =  System.getProperty("user.dir")+"\\src\\main\\java\\frc\\robot\\face-resources\\data\\haarcascades\\haarcascade_frontalface_alt.xml";
    private final CascadeClassifier cc = new CascadeClassifier(cmlFacialDetectionFile);

    private Mat source;
    private CvSource outputStream;
    private Thread currentThread;
    private NetworkTable visionTable;
    private final NetworkTableInstance t = NetworkTableInstance.create();
    public RecognizeFace() {
    }

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
