public class MetalMaterial extends AbstractMaterial {

    public static MetalMaterial SILVER = new MetalMaterial(Color.GRAY);
    public static MetalMaterial GOLD = new MetalMaterial(Color.YELLOW);

    private Color attenuation;

    public MetalMaterial(Color att_color) {
        attenuation = att_color;
    }

    @Override
    public ScatterRecord scatter(Ray r_in, HitInfo h) {
        Vec3 reflection_dir = r_in.getDirection().toUnit().reflect(h.getHitNormal());
        Ray scattered_ray = new Ray(r_in.at(h.getHitPointAlongRay()), reflection_dir);
        return new ScatterRecord(attenuation, scattered_ray);
    }

}
