
import java.lang.Math;

public class Vec3 {

    // the elements of our Vec3 e.g. x,y,z or r,g,b.
    private double[] e = { 0, 0, 0 };

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