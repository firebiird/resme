package cymru.leewilliams;

import com.sun.imageio.plugins.png.PNGImageReader;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

/**
 * App to create all versions of the drawable files.
 *
 * It implies that the version you have is the drawable-mpi and then scales accordingly.
 *
 */
public class App 
{
    public static String imgLocation; 
    public static String drawableLocation;
    
    public static void main( String[] args ) throws Exception
    {
        
         imgLocation = args[0];
         drawableLocation = args[1];
        
         File img = new File(imgLocation);
         if(img.exists())
         {
             System.out.println("Reading image ...");
             BufferedImage inputImage = ImageIO.read(img);
           
             System.out.println("Found 1 image with dimensions : "+ inputImage.getWidth() + " x "+inputImage.getHeight());
             System.out.println("Okay to proceed?[Y/n]");
             Scanner s = new Scanner(System.in);
             
             String input = s.next();
             
             String[][] directories = { // This writes the outputs to the res directory plus these outputs. 
                  new String[] {"drawable","0.75"},
                  new String[] {"drawable-mdpi","1"},
                 new String[] {"drawable-hdpi","1.5"},
                  new String[] {"drawable-xhdpi","2"},
                  new String[] {"drawable-xxhdpi","3"},
                  new String[] {"drawable-xxxhdpi","4"}
             };
             
             
             if(input.equals("") || input.equalsIgnoreCase("Y"))
             {
                 
                 for(String[] d : directories) {
                 
                     int scaledWidth = Math.round(inputImage.getWidth() * Float.parseFloat(d[1])) ;
                     int scaledHeight = Math.round(inputImage.getHeight() * Float.parseFloat(d[1]));
                     
                     System.out.println("Creating a scaled image for "+ d[0] + " "+ scaledWidth + "x"+scaledHeight);
                     
                    // creates output image
                    BufferedImage outputImage = new BufferedImage(scaledWidth,
                    scaledHeight, inputImage.getType());
                    
                    
                     System.out.println("Rendering ... ");
                     // scales the input image to the output image
                     Graphics2D g2d = outputImage.createGraphics();
                     g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                     g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
                     g2d.dispose();
                     System.out.println("finished Rendering");
                     
                    
                   // extracts extension of output file
                    String formatName = imgLocation.substring(imgLocation
                    .lastIndexOf(".") + 1);
                    
                    String filename = drawableLocation + File.separator + d[0];
                    if(imgLocation.contains(File.separator))
                    {
                        filename += File.separator +  imgLocation.substring(imgLocation.lastIndexOf(File.separator) + 1, imgLocation.length());
                    }
                    else
                    {
                        filename += File.separator + imgLocation;
                    }
 
                     System.out.println("Writing file to "+ d[0]);
                    // writes to output file
                    ImageIO.write(outputImage, formatName, new File(filename));
                    

                 }
                 
                 
             }
             else
             {
                 System.out.println("Aborting ...");
             }
              
              
 
         }
         
         
        
        
        
        
    }
}
