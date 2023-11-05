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
        cam.renderScene(scene_objects);
    }
}