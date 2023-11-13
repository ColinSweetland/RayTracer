import java.util.ArrayList;

public class RayCaster {

    static ArrayList<Hittable> scene_objects = new ArrayList<Hittable>();

    public static void setup_scene() {
        DiffuseMaterial ground = new DiffuseMaterial(new Color(0.1, 0.8, 0.2));
        DiffuseMaterial center = new DiffuseMaterial(Color.RED);

        scene_objects.add(new Sphere(new Vec3(0.0, 0.0, -1.0), 0.5, center));
        scene_objects.add(new Sphere(new Vec3(0.0, -100.5, -1.0), 100.0, ground));
        scene_objects.add(new Sphere(new Vec3(1.0, 0.0, -1.0), 0.5, MetalMaterial.SILVER));
        scene_objects.add(new Sphere(new Vec3(-1.0, 0.0, -1.0), 0.5, MetalMaterial.GOLD));

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