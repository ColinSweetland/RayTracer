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
    public HitInfo isHitBy(Ray r) {
        // We are just using the quadratic equation.
        Vec3 ray_origin_minus_pos = Vec3.sub(r.getOrigin(), getPosition());
        double a = r.getDirection().lenSquared();
        double half_b = Vec3.dot(ray_origin_minus_pos, r.getDirection());
        double c = ray_origin_minus_pos.lenSquared() - (getRadius() * getRadius());

        double discriminant = half_b * half_b - a * c;
        // if discriminant has no solution, the ray does not hit the sphere,
        // if discriminant = 0, it has one solution (grazes the sphere)
        // if discriminant > 0, it hits twice (goes through the sphere)
        if (discriminant < 0) {
            return null;

        } else {
            double point_along_ray = ((-half_b - Math.sqrt(discriminant)) / a);

            if (point_along_ray < Ray.min_ray_len || point_along_ray > Ray.max_ray_len) {
                return null;
            }

            return new HitInfo(point_along_ray, Vec3.sub(r.at(point_along_ray), pos).toUnit());
        }
    }
}
