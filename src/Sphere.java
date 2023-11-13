public class Sphere implements Hittable {
    private Vec3 pos;
    private double r;
    private AbstractMaterial material;

    public Sphere(Vec3 position, double radius, AbstractMaterial mat) {
        pos = position;
        r = radius;
        material = mat;
    }

    public Vec3 getPosition() {
        return pos;
    }

    public double getRadius() {
        return r;
    }

    @Override
    public HitInfo isHitBy(Ray ray) {
        // We are just using the quadratic equation.
        Vec3 ray_origin_minus_pos = Vec3.sub(ray.getOrigin(), getPosition());
        double a = ray.getDirection().lenSquared();
        double half_b = Vec3.dot(ray_origin_minus_pos, ray.getDirection());
        double c = ray_origin_minus_pos.lenSquared() - (getRadius() * getRadius());

        double discriminant = half_b * half_b - a * c;
        // if discriminant has no solution, the ray does not hit the sphere,
        // if discriminant = 0, it has one solution (grazes the sphere)
        // if discriminant > 0, it hits twice (goes through the sphere)
        if (discriminant < 0) {
            return null;

        } else {
            double point_along_ray = ((-half_b - Math.sqrt(discriminant)) / a);

            if (point_along_ray < Ray.MIN_RAY_LEN || point_along_ray > Ray.MAX_RAY_LEN) {
                return null;
            }

            Vec3 hit_normal = Vec3.scalarDiv(r, Vec3.sub(ray.at(point_along_ray), pos));
            HitInfo hit_rec = new HitInfo(point_along_ray, hit_normal);
            hit_rec.setFrontFace(ray);
            hit_rec.setHitMaterial(material);

            return hit_rec;
        }
    }
}
