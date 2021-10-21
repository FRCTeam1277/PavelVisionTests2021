import java.io.Serializable;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class Packet implements Serializable {
    
    byte[] matByte;

    public Packet(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
            
        
        // byte[] return_buff = new byte[(int) (mat.total() * 
        //                                     mat.channels())];
        // mat.get(640,480, return_buff);
        // this.matByte = return_buff;
    }
}
