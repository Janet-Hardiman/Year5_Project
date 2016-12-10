package com.company;
/*Janet Hardiman G00305023
 * 5th Year Project - Facial Recognition Part
 * 07/10/2016 - Starting with Paul Dunne's code to Detect a Face from a still image and draw a rectangle box around it,
 *              this code is using open source code from OPENCV (DetectFaceDemo)
 * 14/10/2016 - Then used code to Capture one frame from a webcam (FrameCapture)
 * 21/10/2016 - Next tried to detect a face in the WebCam frame capture and draw a box around it (WebCamFaceDetect)
 *              the above three classes were called from the first main, one at a time - this is now commented out
 * 28/10/2016 - Next stage live stream webcam image, detect face and draw a circle / rectangle around it.
 *              this is ran from the second main method and uses (FacePanel - to open a JPanel and FaceDetector - as name suggests
 *              & to draw circle / rectangle around the face.
 * 04/11/2016 - research Facial Recognition! and update GitHub
 * 11/11/2016 - spent the week researching eye detection to verify the person is real and not just shown an image
 * 18/11/2016 - detect eyes and draw rectangles around them.
 * 25/11/2016 - working on showing a live image and capturing a photo of it.
 * 09/12/2016 - setting up raspberry pi for database
 */
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HelloOpenCV {
 /*   public static void main(String[] args) throws Exception {
        System.out.println("harr Face Detection with OpenCV");
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new DetectFaceDemo().run();
        try {
        //    new FrameCapture().run();
        //    new WebCamFaceDetect().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String arg[]) throws InterruptedException{
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //or ...     System.loadLibrary("opencv_java244");

        VideoCapture webCam = new VideoCapture(0);

        //make the JFrame
        JFrame frame = new JFrame("WebCam Capture - Face detection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FaceDetector faceDetector = new FaceDetector();
        FacePanel facePanel = new FacePanel();
        frame.setSize(400,400); //give the frame some arbitrary size
        frame.setBackground(Color.BLUE);
        frame.add(facePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton cancel = new JButton("Cancel");
        JButton capture = new JButton("Capture");
        JButton retake = new JButton("Retake");
        JButton save = new JButton("Save");

        buttonPanel.add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webCam.release();
                frame.dispose();
            }
        });

        buttonPanel.add(capture);
        capture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the visualized detection.
                try {
                    new FrameCapture().run();
                    new DetectFaceDemo().run();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonPanel.add(retake);
        retake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open live webcam again
                webCam.isOpened();
                new FaceDetector();
                FacePanel face2Panel = new FacePanel();
                frame.add(face2Panel, BorderLayout.CENTER);
            }
        });


        buttonPanel.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File theDir = new File("newFolder");
                if (!theDir.exists()) {
                    System.out.println("creating directory: " + theDir);
                    boolean result = false;

                    try{
                        theDir.mkdir();
                        result = true;
                    }
                    catch(SecurityException se){
                        //handle it
                    }
                    if(result) {
                        System.out.println("DIR created");
                    }
                }
                String path = "";
                path = theDir.getAbsolutePath();
                JOptionPane.showMessageDialog(facePanel, "saved to:  " + path);
                System.out.println(path);


                File f2 = new File("U:\\Year_5\\Project\\images\\penguin.png");
                //File f2 = new File(path);  //output file path
         /*       try {
                    ImageIO.write(img, "jpg", f2 );
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }*/
            }
        });

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);


        //Open and Read from the video stream
        Mat webcam_image = new Mat();
     //   VideoCapture webCam =new VideoCapture(0);

        if( webCam.isOpened())
        {
            Thread.sleep(500); /// This one-time delay allows the Webcam to initialize itself
            while( true )
            {
                webCam.read(webcam_image);
                if( !webcam_image.empty() )
                {
                    Thread.sleep(200); /// This delay eases the computational load .. with little performance leakage
                    frame.setSize(webcam_image.width() + 40,webcam_image.height() + 60);
                    //Apply the classifier to the captured image
                    webcam_image = faceDetector.detect(webcam_image);
                    //Display the image
                    facePanel.matToBufferedImage(webcam_image);
                    facePanel.repaint();
                }
                else
                {
                    System.out.println(" --(!) No captured frame from webcam !");
                    break;
                }
            }
        }
        webCam.release(); //release the webcam

    } //end main
}
