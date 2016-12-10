package com.company;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * Created by Dinmahr on 29/10/2016.
 */
class FaceDetector {
    private CascadeClassifier face_cascade, eye_cascade;
    // Create a constructor method
    public FaceDetector(){

        face_cascade = new CascadeClassifier("haarcascade_frontalface_alt.xml");
        eye_cascade = new CascadeClassifier("haarcascade_mcs_eyepair_big.xml");
        if(face_cascade.empty())
        {
            System.out.println("--(!)Error loading A\n");
            return;
        }
        else
        {
            System.out.println("Face classifier loooaaaaaded up");
        }
    }
    public Mat detect(Mat inputframe){
        Mat mRgba=new Mat();
        Mat mGrey=new Mat();
        MatOfRect faces = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist( mGrey, mGrey );
        face_cascade.detectMultiScale(mGrey, faces);
        System.out.println(String.format("Detected %s faces", faces.toArray().length));
        for(Rect rect:faces.toArray())
        {
//            Point center= new Point(rect.x + rect.width*0.5, rect.y + rect.height*0.5 );
            //draw a blue eclipse around face - didn't work for me
//            Size s = new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 0, 0, 255 );
//            Core.ellipse( mRgba, center,s , 4, 8, 0 );
            //draw a pink eclipse around face
//            Core.ellipse( mRgba, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );

            //draw a bounding box around each face
            Core.rectangle(mRgba, //where to draw the box
                    new Point(rect.x, rect.y), //bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), //top right
                    new Scalar(255,0,0));   //RGB colour - red
        }


        // Find the eyes and turn square in the array
        MatOfRect eyes = new MatOfRect();
        eye_cascade.detectMultiScale(mGrey, eyes);
        for (Rect rect : eyes.toArray()) {
            //Write text to upper left corner
            Core.putText(mRgba, "Eye", new Point(rect.x,rect.y-5), 1, 2, new Scalar(255,0,0));
            //Draw a square
            Core.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(200, 200, 100),2);
        }
        return mRgba;
    }
}
