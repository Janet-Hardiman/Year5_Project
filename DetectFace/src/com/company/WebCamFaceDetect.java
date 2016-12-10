package com.company;

/**
 * Created by Dinmahr on 29/10/2016.
 */
// this code will grab ONE frame from the Web-Cam and detect the face in that frame

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;


class WebCamFaceDetect {
    private CascadeClassifier face_cascade, eye_cascade;
    public void run() throws Exception{
        System.out.println("\nRunning DetectFaceDemo");


        //http://stackoverflow.com/questions/8791178/haar-cascades-vs-lbp-cascades-in-face-detection
        //    LBP ( Local Binary Patterns)is faster (a few times faster) but less accurate (10-20% less than Haar).
        //    If you want to detect faces on an embedded system, I think LBP is the choice,
        //    because it does all the calculations in integers. Haar uses floats, which is
        //    a killer for embedded/mobile.
        //	  Also, in training stages, LBP is faster than Haar. With 2000 pos sample and 300 neg sample,
        //    training using Haar type, it took about 5-6 days to complete, but with LBP, it took only some hours


        //CascadeClassifier faceDetectorClassifier = new CascadeClassifier("C:/opencv/data/lbpcascades/lbpcascade_frontalface.xml");
        //CascadeClassifier faceDetectorClassifier = new CascadeClassifier("../cascades/lbpcascade_frontalface.xml");

        face_cascade = new CascadeClassifier("haarcascade_frontalface_default.xml");
        eye_cascade = new CascadeClassifier("haarcascade_eye.xml");

        //Get a frame from the webcam
        Mat frame = new Mat();
        VideoCapture webcam = new VideoCapture(0); //Assuming that webcam will be device '0'

        //Pause
        Thread.sleep(500);   // 0.5 -> 1 sec of a delay. This is not obvious but its necessary
        // as the camera simply needs time to initialize itself

        if(!webcam.isOpened()){

            System.out.println("Did not connect to camera");
        }else

            System.out.println("found webcam: "+ webcam.toString());

        //grab a frame  from the web-cam
        webcam.retrieve(frame);

        System.out.println("Captured image with "+ frame.width()+ " pixels wide by " + frame.height() + " pixels tall.");
        Highgui.imwrite("webcam_face.jpg", frame);
        //Now I'm finished with the webcam


        // Now detect the face in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        face_cascade.detectMultiScale(frame, faceDetections); //detectMultiScale will perform the detection
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Core.putText(frame, "Face", new Point(rect.x, rect.y=5), 1,2, new Scalar(0,0,255)); //new cvScalar(blue, green, red, unused)
            Core.rectangle(frame,  //where to draw the box
                    new Point(rect.x, rect.y),   //bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), //top right
                    new Scalar(255, 0, 0)); //RGB colour
        }

        // Find the eyes and turn square in the array
        MatOfRect eyes = new MatOfRect();
        eye_cascade.detectMultiScale(frame, eyes);
        for (Rect rect : eyes.toArray()) {
            //Write text to upper left corner
            Core.putText(frame, "Eye", new Point(rect.x,rect.y-5), 1, 2, new Scalar(255,0,0));
            //Draw a square
            Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(200, 200, 100),2);
        }


        System.out.println("Bounding boxes drawn");

        // Save the visualized detection.
        String filename = "haar_detected_webcam_face.png";
        Highgui.imwrite(filename, frame);
        System.out.println("Writing: " + filename);
        webcam.release();
    }
}

//public class WebCamFaceDetection {
//    public static void main(String[] args) throws Exception{
//        System.out.println("Web Cam Face Detection with OpenCV and Harr classifier ");
//        // Load the native library.
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        new WebCamFaceDetect().run();
//    }
//}
