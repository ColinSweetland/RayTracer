public class Color extends Vec3 {

    // r, g, and b are from 0.0 (black) to 1.0, translated to 0-255 only when needed
    public Color(double r, double g, double b) {
        super(r, g, b);
    }

    public double r() {
        return x();
    }

    public double g() {
        return y();
    }

    public double b() {
        return z();
    }

    public static void write(Color c) {
        int ir = (int) (255.999 * c.r());
        int ig = (int) (255.999 * c.g());
        int ib = (int) (255.999 * c.b());

        System.out.format("%d %d %d\n", ir, ig, ib);
    }
}
