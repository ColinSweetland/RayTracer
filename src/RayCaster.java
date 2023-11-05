import java.util.ArrayList;

public class RayCaster {

    static ArrayList<Hittable> scene_objects = new ArrayList<Hittable>();

    public static void setup_scene() {
        scene_objects.add(new Sphere(new Vec3(0.0, 0.0, -1.0), 0.5));
        scene_objects.add(new Sphere(new Vec3(0.0, -100.5, -1), 100.0));

    }

    public static void main(String[] args) {

        setup_scene();

        Camera cam = new Camera();

        long start_time = System.currentTimeMillis();
        cam.renderScene(scene_objects);
        long end_time = System.currentTimeMillis();

        System.err.println("Render Time: " + (end_time - start_time) / 1000.0 + " seconds");
    }
}