public abstract class AbstractMaterial {

    // return a ScatterRecord if the ray should scatter, else return null
    public abstract ScatterRecord scatter(Ray r_in, HitInfo h);
}
