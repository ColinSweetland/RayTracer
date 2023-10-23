

public class main {
    public static void main(String[] args) {

        int image_width = 255;
        int image_height = 255;
        
        // PPM magic number
        System.out.println("P3");
    
        // image size
        System.out.format("%d %d\n", image_width, image_height);

        // maximum color component (colors will be 0-255)
        System.out.println("255");

        for (int y = 0; y < image_height; y++) {
            System.err.format("Writing Line %d/%d\r", y+1, image_height);
            
            for (int x = 0; x < image_width; x++) {
                // rg become % of x or y amount as decimal
                double r = ((double) x) / (image_width - 1);
                double g = ((double) y) / (image_height - 1);
                double b = 0;

                // convert to int from 0-255
                int ir = (int) (255.999 * r);
                int ig = (int) (255.999 * g);
                int ib = (int) (255.999 * b);

                //output single pixel at coordinate
                System.out.format("%d %d %d\n", ir, ig, ib);
            }
        }
    }
}