
import java.lang.Math;
import java.util.Random;

public class Vec3 {

    // the elements of our Vec3 e.g. x,y,z or r,g,b.
    private double[] e = { 0, 0, 0 };

    private static Random rand = new Random();

    public Vec3() {
    }

    public Vec3(final double e0, final double e1, final double e2) {
        e[0] = e0;
        e[1] = e1;
        e[2] = e2;
    }

    public double x() {
        return e[0];
    }

    public double y() {
        return e[1];
    }

    public double z() {
        return e[2];
    }

    // used for some calculations
    public double lenSquared() {
        return e[0] * e[0] + e[1] * e[1] + e[2] * e[2];
    }

    public double len() {
        return Math.sqrt(lenSquared());
    }

    public static Vec3 scalarMul(final double s, final Vec3 v) {
        return new Vec3(v.e[0] * s, v.e[1] * s, v.e[2] * s);
    }

    public static Vec3 scalarDiv(final double s, final Vec3 v) {
        double r = 1 / s;
        return new Vec3(v.e[0] * r, v.e[1] * r, v.e[2] * r);
    }

    public static Vec3 add(final Vec3 lhs, final Vec3 rhs) {
        return new Vec3(lhs.e[0] + rhs.e[0], lhs.e[1] + rhs.e[1], lhs.e[2] + rhs.e[2]);
    }

    public static Vec3 sub(final Vec3 lhs, final Vec3 rhs) {
        return new Vec3(lhs.e[0] - rhs.e[0], lhs.e[1] - rhs.e[1], lhs.e[2] - rhs.e[2]);
    }

    public static Vec3 mul(final Vec3 lhs, final Vec3 rhs) {
        return new Vec3(lhs.e[0] * rhs.e[0], lhs.e[1] * rhs.e[1], lhs.e[2] * rhs.e[2]);
    }

    public static Vec3 cross(final Vec3 lhs, final Vec3 rhs) {
        return new Vec3(lhs.e[1] * rhs.e[2] - lhs.e[2] * rhs.e[1],
                lhs.e[2] * rhs.e[0] - lhs.e[0] * rhs.e[2],
                lhs.e[0] * rhs.e[1] - lhs.e[1] * rhs.e[0]);
    }

    public static double dot(final Vec3 lhs, final Vec3 rhs) {
        return lhs.e[0] * rhs.e[0] + lhs.e[1] * rhs.e[1] + lhs.e[2] * rhs.e[2];
    }

    public static Vec3 random() {
        return new Vec3(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
    }

    public static Vec3 random(double lower_bound, double upper_bound) {
        double r_x = lower_bound + (upper_bound - lower_bound) * rand.nextDouble();
        double r_y = lower_bound + (upper_bound - lower_bound) * rand.nextDouble();
        double r_z = lower_bound + (upper_bound - lower_bound) * rand.nextDouble();
        return new Vec3(r_x, r_y, r_z);
    }

    public static Vec3 randomInUnitSphere() {
        while (true) {
            Vec3 p = Vec3.random(-1, 1);
            if (p.lenSquared() < 1) {
                return p;
            }
        }
    }

    public static Vec3 randomUnitVector() {
        return randomInUnitSphere().toUnit();
    }

    // if the random vector dotted with the normal <= 0, that means they face
    // opposite directions, so invert it
    public static Vec3 randomOnHemisphere(Vec3 normal) {
        Vec3 on_unit_sphere = randomUnitVector();
        if (Vec3.dot(on_unit_sphere, normal) > 0.0) {
            return on_unit_sphere;
        } else {
            return Vec3.scalarMul(-1, on_unit_sphere);
        }
    }

    public Vec3 toUnit() {
        return scalarDiv(len(), this);
    }

    public String toString() {
        return String.format("Vec3( %f, %f, %f )\n", e[0], e[1], e[2]);
    }

    public Color toColor() {
        return new Color(this);
    }
}