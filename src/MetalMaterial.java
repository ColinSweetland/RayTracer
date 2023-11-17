public class MetalMaterial extends AbstractMaterial {

    public static MetalMaterial SILVER = new MetalMaterial(Color.GRAY);
    public static MetalMaterial GOLD = new MetalMaterial(Color.YELLOW);

    private Color attenuation;
    private double fuzz = 0.0;

    public MetalMaterial(Color att_color) {
        attenuation = att_color;
    }

    public MetalMaterial setFuzziness(double amount) {
        fuzz = Math.min(amount, 1.0);
        return this;
    }

    @Override
    public ScatterRecord scatter(Ray r_in, HitInfo h) {
        Vec3 reflection_dir = r_in.getDirection().toUnit().reflect(h.getHitNormal());
        // add fuzziness
        reflection_dir = Vec3.add(reflection_dir, Vec3.scalarMul(fuzz, Vec3.randomUnitVector()));
        Ray scattered_ray = new Ray(r_in.at(h.getHitPointAlongRay()), reflection_dir);
        return new ScatterRecord(attenuation, scattered_ray);
    }

}
