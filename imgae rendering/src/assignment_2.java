import java.awt.image.BufferedImage;

public interface assignment_2 {

    //Image Blending method
    //Takes the 2 image paths and the blending ratio for each image
    //Returns a newly created image after blending with the specified ratios
    public BufferedImage Blending(String imagePath1, String imagePath2, double r1, double r2);

    //Video down-sampling method
    //Takes the path for the input video sequence images and the result's destination
    //void method however the new donsampled images should be written in the specified outputPath
    public void downsampling(String inputPath, String outputPath);

    //Image Enhancement method
    //Takes the input path for the image, the key for the operation and the output path to store the result
    //The key value determines between whether the operation is (1) +50, (2) squaring or (3) sqrt
    //void method where the newly created image after enhancement is written at the output path.
    public void imageEnhancement (String inputPath, String outputPath, int key);

    //Please use the main method to call the methods above to meet the requirement.

}

