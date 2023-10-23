
public class RayCaster {
    public static void main(String[] args) {

        int image_width = 256;
        int image_height = 256;

        // PPM magic number
        System.out.println("P3");

        // image size
        System.out.format("%d %d\n", image_width, image_height);

        // maximum color component (colors will be 0-255)
        System.out.println("255");

        for (int y = 0; y < image_height; y++) {
            System.err.format("\rRendering %d/%d", y + 1, image_height);

            for (int x = 0; x < image_width; x++) {
                // rg become % of x or y amount as decimal
                Color pixelcol = new Color(((double) x) / (image_width - 1), ((double) y) / (image_height - 1), 0.0);

                Color.write(pixelcol);
            }
        }
        System.err.println();
    }
}