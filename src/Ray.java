public class Ray {
    private Vec3 origin;
    private Vec3 direction;

    public Ray(Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3 getOrigin() {
        return origin;
    }

    public Vec3 getDirection() {
        return direction;
    }

    // gives us a point P(s) = Origin + s * Direction,
    // returning a point along our ray after moving s "direction units".
    // negative values will move backwards. Higher values move further.
    Vec3 at(double s) {
        return Vec3.add(origin, Vec3.scalarMul(s, direction));
    }

}
