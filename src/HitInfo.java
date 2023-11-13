public class HitInfo {
    // point along the ray the collision happened (pass to Ray.at())
    private double point_along_ray;
    private Vec3 hit_normal;
    // does the normal point with the ray, or against it
    private boolean front_face;

    private AbstractMaterial hit_material;

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

    public AbstractMaterial getHitMaterial() {
        return hit_material;
    }

    public void setHitMaterial(AbstractMaterial mat) {
        hit_material = mat;
    }

    // hit_normal is assumed to already be set to the proper outward facing normal,
    // at this point.
    // this function will flip it to face against the ray if it it faces with it.
    public void setFrontFace(Ray ray) {
        front_face = Vec3.dot(ray.getDirection(), hit_normal) < 0;
        if (!front_face) {
            hit_normal = Vec3.scalarMul(-1, hit_normal);
        }
    }

    public boolean isFrontFace() {
        return front_face;
    }
}
