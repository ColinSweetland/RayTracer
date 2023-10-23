public class Color extends Vec3 {

    public static Color RED = new Color(1.0, 0.0, 0.0);
    public static Color WHITE = new Color(1.0, 1.0, 1.0);
    public static Color SKYBLUE = new Color(0.5, 0.7, 1.0);

    // r, g, and b are from 0.0 (black) to 1.0, translated to 0-255 only when needed
    public Color(double r, double g, double b) {
        super(r, g, b);
        assert r() <= 1.0 && r() >= 0.0;
        assert g() <= 1.0 && g() >= 0.0;
        assert b() <= 1.0 && b() >= 0.0;
    }

    public Color(Vec3 v3) {
        super(v3.x(), v3.y(), v3.z());
        assert r() <= 1.0 && r() >= 0.0;
        assert g() <= 1.0 && g() >= 0.0;
        assert b() <= 1.0 && b() >= 0.0;
    }

    // lerp amount from 0.0-1.0. blends between c1 and c2
    public static Color lerpBetween(Color c1, Color c2, double lerp_amount) {
        assert lerp_amount <= 1.0 && lerp_amount >= 0.0;
        return new Color(Vec3.add(Vec3.scalarMul((1 - lerp_amount), c1), Vec3.scalarMul(lerp_amount, c2)));
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
