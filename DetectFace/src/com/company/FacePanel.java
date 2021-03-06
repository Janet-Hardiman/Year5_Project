package com.company;

/**
 * Created by Dinmahr on 29/10/2016.
 */
/*
 * Captures the camera stream with OpenCV
 * Search for the faces
 * Display a circle around the faces using Java
 */
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

class FacePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    private BufferedImage image;
    // Create a constructor method
    public FacePanel(){
        super();
    }
    /*
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public boolean matToBufferedImage(Mat matrix) {
        MatOfByte mb=new MatOfByte();
        Highgui.imencode(".jpg", matrix, mb);
        try {
            this.image = ImageIO.read(new ByteArrayInputStream(mb.toArray()));
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error
        }
        return true; // Successful
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (this.image==null) return;
        g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null);
    }

}
