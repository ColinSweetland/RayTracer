import java.util.ArrayList;

public class Camera {

    private double ideal_aspect_ratio = 16.0 / 9.0;
    private int image_width = 400;
    private Vec3 origin = new Vec3(0.0, 0.0, 0.0);

    // distance between camera origin and viewport (in negative z direction)
    double focal_length = 1.0;

    public Camera() {
        return;
    }

    public void setIdealAspectRatio(double ratio) {
        ideal_aspect_ratio = ratio;
    }

    public void setImageWidth(int w) {
        image_width = w;
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
        Vec3 pixel_delta_lr = Vec3.scalarDiv(image_width, viewport_lr);
        Vec3 pixel_delta_ud = Vec3.scalarDiv(image_height, viewport_ud);

        // calculate viewport upper left
        Vec3 viewport_upper_left = Vec3.sub(origin, new Vec3(0.0, 0.0, focal_length));
        viewport_upper_left = Vec3.sub(viewport_upper_left, Vec3.scalarDiv(2.0, viewport_lr));
        viewport_upper_left = Vec3.sub(viewport_upper_left, Vec3.scalarDiv(2.0, viewport_ud));

        // center of first pixel is offset half of each pixel delta from upper left
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

                Ray curr_ray = new Ray(origin, Vec3.sub(pixel_pos, origin));

                Color pixeColor = castRay(curr_ray, scene_objects);
                Color.write(pixeColor);
            }
        }
        System.err.println();
    }

    // cast a ray and get the color it sees as a result.
    private Color castRay(Ray r, ArrayList<Hittable> scene_objects) {
        HitInfo closest_hit = null;

        for (Hittable obj : scene_objects) {
            HitInfo h = obj.isHitBy(r);
            if (h != null) {
                if (closest_hit == null || h.getHitPointAlongRay() < closest_hit.getHitPointAlongRay()) {
                    closest_hit = h;
                }
            }
        }

        // ray didn't hit an object, return sky color
        if (closest_hit == null) {
            Vec3 unit = r.getDirection().toUnit();
            double lerp_amount = 0.5 * (unit.y() + 1.0);
            return Color.lerpBetween(Color.WHITE, Color.SKYBLUE, lerp_amount);
        } else {
            double red = 0.5 * (closest_hit.getHitNormal().x() + 1.0);
            double green = 0.5 * (closest_hit.getHitNormal().y() + 1.0);
            double blue = 0.5 * (closest_hit.getHitNormal().z() + 1.0);

            return new Color(red, green, blue);
        }
    }
}
