package frc.robot.commands;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.stream.IStreamOutput;
import frc.stream.OutputDataStream;
import frc.robot.subsystems.CameraIO;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.IOException;


public class RecognizeFace extends CommandBase implements IStreamOutput<Rect[]> {
    //gets casscade file for face detection
    private final String cmlFacialDetectionFile =  System.getProperty("user.dir")+"\\src\\main\\java\\frc\\robot\\face-resources\\data\\haarcascades\\haarcascade_frontalface_alt.xml";
    private final CascadeClassifier cc = new CascadeClassifier(cmlFacialDetectionFile);

    private final CameraIO cameraIO = CameraIO.getInstance();
    private Mat source;
    private CvSource outputStream;
    private Thread currentThread;

    public RecognizeFace() {
        addRequirements(this.cameraIO);
        outputStream = cameraIO.createOutputStream("test",1280,720);        //starts output stream(for debugging purposes)
    }

    @Override
    public void initialize() {
        try {
            cameraIO.startStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void execute() {

        //runs every time the thread has ended
        //thread as this takes avg 30 ms, while command loop runs at 20 ms
        if(currentThread == null || !currentThread.isAlive()) {
            currentThread = new Thread(() -> {
                //gets current frame, makes sures
                try {
                    source = cameraIO.getLaptopMat(); //CAMERA CHANGE HERE
                } catch (Exception e) {
                    e.printStackTrace();
                    source = null;
                }
                if (source == null) {
                    return;
                }
                Rect[] faces = cameraIO.runCascade(cc, source);
                for (Rect rect : faces) { //this is just for output stream in smart dashboard
                    Imgproc.rectangle(source,
                            new Point(rect.x, rect.y),
                            new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 0, 255, 1), 5);
                }
                dataStream.changeData(faces); //updates stream
                outputStream.putFrame(source);
            });
            currentThread.start();
        }
    }


    @Override
    public void end(boolean interrupted) {
        try {
            cameraIO.closeStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OutputDataStream<Rect[]> dataStream;
    @Override
    public void setOutputStream(OutputDataStream<Rect[]> dataStream) {
        this.dataStream = dataStream;
    }
}
