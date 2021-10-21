package frc.robot;

import org.opencv.core.Mat;

import java.io.Serializable;

public class Packet implements Serializable {
    public byte[] mat;
    public Packet(byte[] mat) {
        this.mat = mat;
    }
}
