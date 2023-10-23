public class Sphere implements Hittable {
    private Vec3 pos;
    private double r;

    public Sphere(Vec3 position, double radius) {
        pos = position;
        r = radius;
    }

    public Vec3 getPosition() {
        return pos;
    }

    public double getRadius() {
        return r;
    }

    @Override
    public boolean isHitBy(Ray r) {
        // We are just using the quadratic equation.
        Vec3 ray_origin_minus_pos = Vec3.sub(r.getOrigin(), getPosition());
        double a = Vec3.dot(r.getDirection(), r.getDirection());
        double b = 2.0 * Vec3.dot(ray_origin_minus_pos, r.getDirection());
        double c = Vec3.dot(ray_origin_minus_pos, ray_origin_minus_pos) - (getRadius() * getRadius());

        double discriminant = b * b - 4 * a * c;
        // if discriminant has no solution, the ray does not hit the sphere,
        // if discriminant = 0, it has one solution (grazes the sphere)
        // if discriminant > 0, it hits twice (goes through the sphere)
        return discriminant >= 0;
    }
}
