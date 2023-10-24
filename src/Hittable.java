public interface Hittable {
    // return NULL if ray did not hit
    HitInfo isHitBy(Ray r);
}
