import java.util.ArrayList;

public class RayCaster {

    static ArrayList<Hittable> scene_objects = new ArrayList<Hittable>();

    // cast a ray and get the color it sees as a result. Currently hardcode black
    public static Color castRay(Ray r) {
        for (Hittable h : scene_objects) {
            if (h.isHitBy(r)) {
                return Color.RED;
            }
        }

        Vec3 unit = r.getDirection().toUnit();
        double lerp_amount = 0.5 * (unit.y() + 1.0);
        return Color.lerpBetween(Color.WHITE, Color.SKYBLUE, lerp_amount);
    }

    public static void setup_scene() {
        scene_objects.add(new Sphere(new Vec3(0.0, 0.0, -1.0), 0.5));
    }

    public static void main(String[] args) {

        setup_scene();

        double ideal_aspect_ratio = 16.0 / 9.0;

        int image_width = 400;
        int image_height = (int) (image_width / ideal_aspect_ratio);

        // ensure image height is at least one
        image_height = Math.max(image_height, 1);

        // Camera will start at the origin, for now
        // y axis is up, positive x is right, negative z is forward (right hand
        // coordinates)
        Vec3 camera_origin = new Vec3(0.0, 0.0, 0.0);

        // distance between camera origin and viewport (in negative z direction)
        double focal_length = 1.0;

        // Viewport is a "plane" in front of the camera where we cast our rays from
        double viewport_height = 2.0;
        double viewport_width = viewport_height * (double) image_width / (double) image_height;

        // Vector pointing from upper left of viewport to upper left.
        Vec3 viewport_lr = new Vec3(viewport_width, 0.0, 0.0);

        // Vector pointing from upper left of viewport to bottom left.
        Vec3 viewport_ud = new Vec3(0.0, -viewport_height, 0.0);

        // distance between pixels in viewport
        Vec3 pixel_delta_lr = Vec3.scalarDiv(image_width, viewport_lr);
        Vec3 pixel_delta_ud = Vec3.scalarDiv(image_height, viewport_ud);

        // calculate viewport upper left
        Vec3 viewport_upper_left = Vec3.sub(camera_origin, new Vec3(0.0, 0.0, focal_length));
        viewport_upper_left = Vec3.sub(viewport_upper_left, Vec3.scalarDiv(2.0, viewport_lr));
        viewport_upper_left = Vec3.sub(viewport_upper_left, Vec3.scalarDiv(2.0, viewport_ud));

        // center of first pixel is offset half of each pixel delta from upper keft
        Vec3 pixel_00_pos = Vec3.add(viewport_upper_left, Vec3.scalarDiv(2.0, pixel_delta_lr));
        pixel_00_pos = Vec3.add(pixel_00_pos, Vec3.scalarDiv(2.0, pixel_delta_ud));

        // PPM magic number
        System.out.println("P3");

        // image size
        System.out.format("%d %d\n", image_width, image_height);

        // maximum color component (colors will be 0-255)
        System.out.println("255");

        for (int y = 0; y < image_height; y++) {
            System.err.format("\rRendering %d/%d", y + 1, image_height);

            for (int x = 0; x < image_width; x++) {
                // shift to current pixel position
                Vec3 pixel_pos = Vec3.add(pixel_00_pos, Vec3.scalarMul(x, pixel_delta_lr));
                pixel_pos = Vec3.add(pixel_pos, Vec3.scalarMul(y, pixel_delta_ud));

                Ray curr_ray = new Ray(camera_origin, Vec3.sub(pixel_pos, camera_origin));

                Color pixeColor = castRay(curr_ray);
                Color.write(pixeColor);
            }
        }
        System.err.println();
    }
}