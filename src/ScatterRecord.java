public class ScatterRecord {
    private Color attenuation;
    private Ray scattered_ray;

    public ScatterRecord(Color att_color, Ray scatter) {
        attenuation = att_color;
        scattered_ray = scatter;
    };

    public Color getAttenuation() {
        return attenuation;
    }

    public Ray getScatteredRay() {
        return scattered_ray;
    }
}
