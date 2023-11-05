import java.util.ArrayList;
import java.util.Random;

public class Camera {

    private double ideal_aspect_ratio = 16.0 / 9.0;
    private int image_width = 400;
    private Vec3 origin = new Vec3(0.0, 0.0, 0.0);
    private int samples_per_pixel = 100;
    private static Random rand = new Random();
    private static int MAX_BOUNCES = 50;

    // distance between camera origin and viewport (in negative z direction)
    double focal_length = 1.0;

    private Vec3 pixel_00_pos;
    private Vec3 pixel_lr_delta;
    private Vec3 pixel_ud_delta;

    public Camera() {
        return;
    }

    public void setIdealAspectRatio(double ratio) {
        ideal_aspect_ratio = ratio;
    }

    public void setImageWidth(int w) {
        image_width = w;
    }

    public void setSamplesPerPixel(int spp) {
        samples_per_pixel = spp;
    }

    public void setFocalLength(double fl) {
        focal_length = fl;
    }

    public void setCameraOrigin(Vec3 o) {
        origin = o;
    }

    private int calculateImageHeight() {
        int image_height = (int) (image_width / ideal_aspect_ratio);
        // ensure image height is at least one
        return Math.max(image_height, 1);
    }

    public void renderScene(ArrayList<Hittable> scene_objects) {

        int image_height = calculateImageHeight();
        // Viewport is a "plane" in front of the camera where we cast our rays from
        double viewport_height = 2.0;
        double viewport_width = viewport_height * (double) image_width / (double) image_height;

        // Vector pointing from upper left of viewport to upper left.
        Vec3 viewport_lr = new Vec3(viewport_width, 0.0, 0.0);

        // Vector pointing from upper left of viewport to bottom left.
        Vec3 viewport_ud = new Vec3(0.0, -viewport_height, 0.0);

        // distance between pixels in viewport
        pixel_lr_delta = Vec3.scalarDiv(image_width, viewport_lr);
        pixel_ud_delta = Vec3.scalarDiv(image_height, viewport_ud);

        // calculate viewport upper left
        Vec3 viewport_upper_left = Vec3.sub(origin, new Vec3(0.0, 0.0, focal_length));
        viewport_upper_left = Vec3.sub(viewport_upper_left, Vec3.scalarDiv(2.0, viewport_lr));
        viewport_upper_left = Vec3.sub(viewport_upper_left, Vec3.scalarDiv(2.0, viewport_ud));

        // center of first pixel is offset half of each pixel delta from upper left
        pixel_00_pos = Vec3.add(viewport_upper_left, Vec3.scalarDiv(2.0, pixel_lr_delta));
        pixel_00_pos = Vec3.add(pixel_00_pos, Vec3.scalarDiv(2.0, pixel_ud_delta));

        // PPM magic number
        System.out.println("P3");

        // image size
        System.out.format("%d %d\n", image_width, image_height);

        // maximum color component (colors will be 0-255)
        System.out.println("255");

        for (int y = 0; y < image_height; y++) {
            System.err.format("\rRendering %d/%d", y + 1, image_height);

            for (int x = 0; x < image_width; x++) {
                Color pixel_color = new Color(0, 0, 0);
                for (int s = 0; s < samples_per_pixel; s++) {
                    Ray curr_ray = makeRay(x, y);
                    pixel_color = Vec3.add(pixel_color, castRay(curr_ray, scene_objects, 0)).toColor();
                }
                pixel_color = Vec3.scalarDiv(samples_per_pixel, pixel_color).toColor();
                Color.write(pixel_color);
            }
        }
        System.err.println();
    }

    // Returns a random point in the square surrounding a pixel at the origin.
    private Vec3 pixelSampleSquare() {
        double px = -0.5 + rand.nextDouble();
        double py = -0.5 + rand.nextDouble();
        return Vec3.add(Vec3.scalarMul(px, pixel_lr_delta), Vec3.scalarMul(py, pixel_ud_delta));
    }

    // create a ray from a random point in unit square at camera, to the pixel pos
    private Ray makeRay(int pixel_x, int pixel_y) {
        // Get a randomly sampled camera ray for the pixel at location i,j.

        Vec3 pixel_center = pixel_00_pos;
        pixel_center = Vec3.add(pixel_center, Vec3.scalarMul(pixel_x, pixel_lr_delta));
        pixel_center = Vec3.add(pixel_center, Vec3.scalarMul(pixel_y, pixel_ud_delta));

        Vec3 pixel_sample = Vec3.add(pixel_center, pixelSampleSquare());

        return new Ray(origin, Vec3.sub(pixel_sample, origin));
    }

    // cast a ray and get the color it sees as a result.
    private Color castRay(Ray r, ArrayList<Hittable> scene_objects, int bounce_count) {

        if (bounce_count > MAX_BOUNCES)
            return new Color(0, 0, 0);

        HitInfo closest_hit = null;
        for (Hittable obj : scene_objects) {
            HitInfo h = obj.isHitBy(r);
            if (h != null) {
                if (closest_hit == null || h.getHitPointAlongRay() < closest_hit.getHitPointAlongRay())
                    closest_hit = h;
            }
        }

        // ray didn't hit an object, return sky color
        if (closest_hit == null) {
            Vec3 unit = r.getDirection().toUnit();
            double lerp_amount = 0.5 * (unit.y() + 1.0);
            return Color.lerpBetween(Color.WHITE, Color.SKYBLUE, lerp_amount);
        } else {
            Vec3 bounce_dir = Vec3.add(closest_hit.getHitNormal(), Vec3.randomUnitVector());
            Vec3 collision_point = r.at(closest_hit.getHitPointAlongRay());
            Color bounced_ray = castRay(new Ray(collision_point, bounce_dir), scene_objects, bounce_count + 1);
            return Vec3.scalarMul(0.5, bounced_ray).toColor();
        }
    }
}
