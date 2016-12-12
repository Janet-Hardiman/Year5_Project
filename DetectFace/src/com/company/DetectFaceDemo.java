package com.company;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

// Code adapted from: http://docs.opencv.org/doc/tutorials/introduction/desktop_java/java_dev_intro.html
// Detects faces in an image, draws boxes around them, and writes the results to image file
// Images from: http://www.uni-regensburg.de/Fakultaeten/phil_Fak_II/Psychologie/Psy_II/beautycheck/english/

class DetectFaceDemo  {
    private CascadeClassifier face_cascade, eye_cascade;
    public void run() {
        System.out.println("\nRunning DetectFaceDemo");

        //http://stackoverflow.com/questions/8791178/haar-cascades-vs-lbp-cascades-in-face-detection
        //    LBP ( Local Binary Patterns)is faster (a few times faster) but less accurate (10-20% less than Haar).
        //    If you want to detect faces on an embedded system, I think LBP is the choice,
        //    because it does all the calculations in integers. Haar uses floats, which is
        //    a killer for embedded/mobile.
        //	  Also, in training stages, LBP is faster than Haar. With 2000 pos sample and 300 neg sample,
        //    training using Haar type, it took about 5-6 days to complete, but with LBP, it took only some hours

        //check where cascade file stored, here it's stored within the project so as to be portable.
        face_cascade = new CascadeClassifier("haarcascade_frontalface_default.xml");
    //    eye_cascade = new CascadeClassifier("haarcascade_eye.xml");

        Mat image = Highgui.imread("webcam_capture.png");

        System.out.println("Image details \n Channels: "

                + image.channels()+ " width: " + image.width()+ " height: " + image.height());

        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        face_cascade.detectMultiScale(image, faceDetections); //detectMultiScale will perform the detection

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y),

                    new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        }

        // Find the eyes and turn square in the array
        MatOfRect eyes = new MatOfRect();
    //    eye_cascade.detectMultiScale(image, eyes);
    /*    for (Rect rect : eyes.toArray()) {
            //Write text to upper left corner
            Core.putText(image, "Eye", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));  //new cvScalar(blue, green, red, unused)
            //Draw a square
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(200, 200, 100),1);
        }*/

        // Save the visualized detection.
        String filename = "webCamImg.png";
        System.out.println(String.format("Writing %s", filename));
        Highgui.imwrite(filename, image);
    }
}



