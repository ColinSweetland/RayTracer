public class DiffuseMaterial extends AbstractMaterial {

    private Color attenuation;

    public DiffuseMaterial(Color c) {
        attenuation = c;
    }

    @Override
    public ScatterRecord scatter(Ray r_in, HitInfo rec) {
        Vec3 scatter_dir = Vec3.add(rec.getHitNormal(), Vec3.randomUnitVector());

        if (scatter_dir.nearly_zero()) {
            scatter_dir = rec.getHitNormal();
        }

        Ray scatter_ray = new Ray(r_in.at(rec.getHitPointAlongRay()), scatter_dir);
        return new ScatterRecord(attenuation, scatter_ray);
    }

}
