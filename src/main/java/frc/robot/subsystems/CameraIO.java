package frc.robot.subsystems;


import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Packet;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class CameraIO extends SubsystemBase {


    // With eager singleton initialization, any static variables/fields used in the 
    // constructor must appear before the "INSTANCE" variable so that they are initialized 
    // before the constructor is called when the "INSTANCE" variable initializes.

    /**
     * The Singleton instance of this CameraIO. Code should use
     * the {@link #getInstance()} method to get the single instance (rather
     * than trying to construct an instance of this class.)
     */
    private final static CameraIO INSTANCE = new CameraIO();

    /**
     * Returns the Singleton instance of this CameraIO. This static method
     * should be used, rather than the constructor, to get the single instance
     * of this class. For example: {@code CameraIO.getInstance();}
     */
    @SuppressWarnings("WeakerAccess")
    public static CameraIO getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of this CameraIO. This constructor
     * is private since this class is a Singleton. Code should use
     * the {@link #getInstance()} method to get the singleton instance.
     */
    private CameraIO() {
        //loads openCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public CvSource createOutputStream(String name, int width, int height) {
        return CameraServer.getInstance().putVideo(name,width,height);
    }

    public Rect[] runCascade(CascadeClassifier cc, Mat sourceImage) {
        MatOfRect facialDetection = new MatOfRect();
        cc.detectMultiScale(sourceImage, facialDetection);
        return facialDetection.toArray();
    }

    UsbCamera usbCamera = CameraServer.getInstance().startAutomaticCapture(); //starts auto capture


    public Mat getCurrentMat() throws IOException, ClassNotFoundException {
        //I wasted 2 days on this stupid thing just for it to be like 30 lines in total (•_•)
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        byte[] byteMat =  ((byte[]) inputStream.readObject()); //look here
        Mat mat =
            Imgcodecs.imdecode(new MatOfByte(byteMat), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        return mat;

    }

    /**
     * @implNote Use for any camera that the laptop can access directly
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Mat getLaptopMat() throws IOException, ClassNotFoundException {
        Mat mat = new Mat();
        CameraServer.getInstance().getVideo().grabFrame(mat);
        return mat;
    }

    public static final int PORT = 3191;
    private Socket socket;

    /**
     * PORT should be = to one on raspberry pi
     * @throws IOException
     */
    public void startStream() throws IOException {
        socket = new Socket("10.0.0.2", PORT);
    }

    /**
     * Run this if you don't want it to run continously
     * Just prevents causing memory leaks
     * @throws IOException
     */
    public void closeStream() throws IOException {
        this.startStream();
        socket.close();
    }
}

