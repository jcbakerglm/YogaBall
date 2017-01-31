import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

/********************************************************************
 * 
 * Things to do:
 * 
 * 1. TODO: Allow command-line arguments:
 *    1. showProcessedImage (boolean)
 *    2. windowWidth (int)
 *    3. windowHeight (int)
 * 
 ********************************************************************/

public class App {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	// Constants for calculating distance
    private static final double BALL_DIAMETER   =  25.0;  // Measured
    private static final double FOCAL_LENGTH    = 543.45; // Test value
    private static final double FL_RESOLUTION_W = 640.0;  // Webcam image width for FL photos
    private static final double FL_RESOLUTION_H = 480.0;  // Webcam image height for FL photos
    
	//private int windowWidth = 320;
    //private int windowHeight = 240;
    private int windowWidth  = 640;
    private int windowHeight = 480;

    private static App a = new App();
	private ImageProcessor imp = new ImageProcessor();
	private Mat webcamMat = new Mat();
	private Pipeline imagePipeline = new Pipeline();

    private GUI processedFrame;
	private GUI augmentedFrame;

	private JLabel processedImageLabel;
	private JLabel augmentedImageLabel;

    public static void main(String[] args) {
		a.runMainLoop();
	}

	private void runMainLoop() {
        processedImageLabel = new JLabel();
        augmentedImageLabel = new JLabel();

        processedFrame = new GUI("Processed Frame", 400, 400, true, false);
        augmentedFrame = new GUI("Augmented Frame", 400, 400, true, false);

        processedFrame.add(processedImageLabel);
        augmentedFrame.add(augmentedImageLabel);

        augmentedFrame.setLocation(~-(windowWidth + 30), ~0);

        Image processedImage;
        Image augmentedImage;

        VideoCapture capture = new VideoCapture(1);

        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, windowWidth);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, windowHeight);

/////////////////////////////////////////////////////////////////////
        
//        Mat hsv_image=new Mat();  
//        Mat thresholded=new Mat();  
//        Mat thresholded2=new Mat();  
//
//        Scalar hsv_min = new Scalar(0, 50, 50, 0);  
//        Scalar hsv_max = new Scalar(6, 255, 255, 0);  
//        Scalar hsv_min2 = new Scalar(175, 50, 50, 0);  
//        Scalar hsv_max2 = new Scalar(179, 255, 255, 0);  
//
////        double[] hsvThresholdHue = {76.07913669064747, 129.31740614334473};
////        double[] hsvThresholdSaturation = {151.34892086330936, 255.0};
////        double[] hsvThresholdValue = {59.62230215827338, 255.0};
//
//        Mat circles = new Mat();
//        
//        // Mat array255=new Mat(webcam_image.height(),webcam_image.width(),CvType.CV_8UC1);  
//        Mat array255 = new Mat(windowHeight, windowWidth, CvType.CV_8UC1);  
//        array255.setTo(new Scalar(255));  
//
//        // Mat distance=new Mat(webcam_image.height(),webcam_image.width(),CvType.CV_8UC1); 
//        Mat distance = new Mat(windowHeight, windowWidth, CvType.CV_8UC1);  
//
//        double[] data=new double[3];  
//        List<Mat> lhsv = new ArrayList<Mat>(3);      
        
        
        if (capture.isOpened()) {
            while (true) {
                capture.read(webcamMat);
                
                if (!webcamMat.empty()) {
//                    Imgproc.cvtColor(webcamMat, hsv_image, Imgproc.COLOR_BGR2HSV);  
//                    Core.inRange(hsv_image, hsv_min, hsv_max, thresholded);           
//                    Core.inRange(hsv_image, hsv_min2, hsv_max2, thresholded2);  
//                    Core.bitwise_or(thresholded, thresholded2, thresholded);  
//
//                    Core.split(hsv_image, lhsv); // We get 3 2D one channel Mats  
//                    Mat S = lhsv.get(1);  
//                    Mat V = lhsv.get(2);  
//
//                    Core.subtract(array255, S, S);  
//                    Core.subtract(array255, V, V);  
//                    S.convertTo(S, CvType.CV_32F);  
//                    V.convertTo(V, CvType.CV_32F);  
//                    Core.magnitude(S, V, distance);  
//                    Core.inRange(distance,new Scalar(0.0), new Scalar(200.0), thresholded2);  
//                    Core.bitwise_and(thresholded, thresholded2, thresholded);  
//     
//                    Imgproc.GaussianBlur(thresholded, thresholded, new Size(9,9),0,0);  
//                    Imgproc.HoughCircles(thresholded, circles, Imgproc.CV_HOUGH_GRADIENT, 2, thresholded.height()/4, 500, 50, 0, 0);   
//
//                    Imgproc.line(webcamMat, new Point(150,50), new Point(202,200), new Scalar(100,10,10), 3);  
//                    Imgproc.circle(webcamMat, new Point(210,210), 10, new Scalar(100,10,10),3);  
//                    data=webcamMat.get(210, 210);  
//                    Imgproc.putText(webcamMat,String.format("("+String.valueOf(data[0])+","+String.valueOf(data[1])+","+String.valueOf(data[2])+")"),new Point(30, 30) , 3 //FONT_HERSHEY_SCRIPT_SIMPLEX  
//                         ,1.0,new Scalar(100,10,10,255),3);  
//
//                    int rows = circles.rows();  
//                    int elemSize = (int)circles.elemSize();  
//                    float[] data2 = new float[rows * elemSize/4];  
//                    if (data2.length>0){  
//                       circles.get(0, 0, data2);
//                       for(int i=0; i<data2.length; i=i+3) {  
//                         Point center= new Point(data2[i], data2[i+1]);  
//                         Imgproc.ellipse( webcamMat, center, new Size((double)data2[i+2], (double)data2[i+2]), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );  
//                       }  
//                    }  
//                    Imgproc.line(hsv_image, new Point(150,50), new Point(202,200), new Scalar(100,10,10)/*CV_BGR(100,10,10)*/, 3);  
//                    Imgproc.circle(hsv_image, new Point(210,210), 10, new Scalar(100,10,10),3);  
//                    data=hsv_image.get(210, 210);  
//                    Imgproc.putText(hsv_image,String.format("("+String.valueOf(data[0])+","+String.valueOf(data[1])+","+String.valueOf(data[2])+")"),new Point(30, 30) , 3 //FONT_HERSHEY_SCRIPT_SIMPLEX  
//                         ,1.0,new Scalar(100,10,10,255),3);  
//                    distance.convertTo(distance, CvType.CV_8UC1);  
//                    Imgproc.line(distance, new Point(150,50), new Point(202,200), new Scalar(100)/*CV_BGR(100,10,10)*/, 3);  
//                    Imgproc.circle(distance, new Point(210,210), 10, new Scalar(100),3);  
//                    data=(double[])distance.get(210, 210);  
//                    Imgproc.putText(distance,String.format("("+String.valueOf(data[0])+")"),new Point(30, 30) , 3 //FONT_HERSHEY_SCRIPT_SIMPLEX  
//                         ,1.0,new Scalar(100),3);   

                    
                    //Perform GRIP processing on image
                    imagePipeline.setsource0(webcamMat);
                    imagePipeline.process();

                    // Get contours for overlay
                    ArrayList<MatOfPoint> contourArray = imagePipeline.convexHullsOutput();

                    Mat overlayedImage = webcamMat.clone();

                    Mat matForHough = new Mat(windowHeight, windowWidth, CvType.CV_8UC1);
                    matForHough.setTo(new Scalar(0));

                    if (contourArray.size() > 0) {
                        // Draw contours into blank image.
                        for (int i = 0; i < contourArray.size(); i++) {
                            Imgproc.drawContours(matForHough, contourArray, i, new Scalar(255, 255, 255), 3);
                        }

                        // Use HoughCircles to determine if target is a circle. If so, mark it.
                        Mat circles = new Mat(); 
                        Imgproc.GaussianBlur(matForHough, matForHough, new Size(9, 9), 0, 0);  
                        Imgproc.HoughCircles(matForHough, circles, Imgproc.CV_HOUGH_GRADIENT, 1, matForHough.height()/4, 500, 50, 20, 0);   

                        int rows = circles.rows();  
                        int elemSize = (int)circles.elemSize(); // Returns 12 (3 * 4bytes in a float)  
                        float[] circleData = new float[rows * elemSize/4];  
                        
                        if (circleData.length > 0) {  
                            // Points to the first element and reads the whole thing into circleData
                            circles.get(0, 0, circleData);  

                            // Draw largest found circles onto overlay image
                            double centerLargestX = -1.0;
                            double centerLargestY = -1.0;
                            double radiusLargest  = -1.0;
                            
                            for (int i = 0; i < (circleData.length - 2); i = i + 3) {  
                                double radiusCurrent = circleData[i+2];
                                
                                if (radiusCurrent > radiusLargest) {
                                    centerLargestX = circleData[i];
                                    centerLargestY = circleData[i+1];
                                    radiusLargest  = radiusCurrent;
                                }
                            }
                            
                            // Draw largest circle on overlay
                            Point ballCenter = new Point(centerLargestX, centerLargestY);
                            Imgproc.circle(overlayedImage, ballCenter, (int) radiusLargest, new Scalar(255, 0, 255), 2);
                            
                            // Calculate and display distance

                            // ******* Need to allow for other video resolutions ******* 
                            //private static final double FL_RESOLUTION_W = 640.0;
                            //private static final double FL_RESOLUTION_H = 480.0;

//                            double estimatedDistance = (BALL_DIAMETER * FOCAL_LENGTH) / (radiusLargest * 2.0);
//                            DecimalFormat df2 = new DecimalFormat("#.##");
//                            
//                            String distanceText = "Distance (inches): " + df2.format(estimatedDistance);
//                            
//                            Imgproc.rectangle(overlayedImage, new Point(0, 0), new Point(augmentedImageLabel.getWidth(), 20), new Scalar(255, 255, 255), -1);
//                            Imgproc.putText(overlayedImage, distanceText, new Point(15, 15), Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 0, 0));
                        
                        }
                    }

                    // Convert thresholded matrix to image to display in JLabel
                    processedImage = imp.toBufferedImage(matForHough);

                    // Convert enhanced raw footage matrix to image to display in JLabel
                    augmentedImage = imp.toBufferedImage(overlayedImage);
                    //augmentedImage = imp.toBufferedImage(distance);
                    
                    // Associate the Image with the JLabel
                    ImageIcon processedImageIcon = new ImageIcon(processedImage, "Processed Image");
                    processedImageLabel.setIcon(processedImageIcon);

                    ImageIcon augmentedImageIcon = new ImageIcon(augmentedImage, "Augmented Image");
                    augmentedImageLabel.setIcon(augmentedImageIcon);

                    // Resize the windows to fit the image
                    augmentedFrame.pack();
                    processedFrame.pack();
                } else {
                    System.out.println(" -- Frame not captured -- Break!");
                    break;
                }
            }
        } else {
            System.out.println("Couldn't open capture.");
        }
    }
}
