package com.company;

/**
 * Created by G00305023 on 25/10/2016.
 */
// this code will grab one frame from the Web-Cam and save the frame as an image


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;


//Code runs fine with OpenCV 2.4.4

 class FrameCapture {

    public void run() throws InterruptedException {
        System.out.println("\nRunning Frame Capture");

        Mat frame = new Mat();
        VideoCapture cap = new VideoCapture(0);

        Thread.sleep(500);	// 0.5 sec of a delay. This is not obvious but its necessary
        // as the camera simply needs time to initialize itself

        if(!cap.isOpened()){
            System.out.println("Did not connect to camera");
        }else System.out.println("found webcam: "+ cap.toString());


        cap.retrieve(frame);// The current frame in the camera is saved in "frame"
        System.out.println("Captured image with "+ frame.width()+ " pixels wide by " + frame.height() + " pixels tall.");
        Highgui.imwrite("webcam_capture.png", frame);
        /*Mat frameBlur = new Mat();
        Imgproc.blur(frame, frameBlur, new Size(5,5) );
        Highgui.imwrite("me2-blurred.jpg", frameBlur);*/

        cap.release(); // Remember to release the camera
    }


}