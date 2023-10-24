public class HitInfo {
    // point along the ray the collision happened (pass to Ray.at())
    private double point_along_ray;
    private Vec3 hit_normal;

    public HitInfo(double point_along_ray, Vec3 hit_normal) {
        this.point_along_ray = point_along_ray;
        this.hit_normal = hit_normal;
    }

    public double getHitPointAlongRay() {
        return point_along_ray;
    }

    public Vec3 getHitNormal() {
        return hit_normal;
    }
}
