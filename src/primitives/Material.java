package primitives;

/**
 * The Material class represents the optical properties of a surface,
 * including ambient, diffusive, and specular reflection coefficients,
 * as well as the shininess factor for specular highlights.
 */
public class Material {
    /**
     * Ambient reflection coefficient.
     */
    public Double3 kA = Double3.ONE;

    /**
     * Diffusive reflection coefficient.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * Specular reflection coefficient.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Shininess factor for specular reflection.
     */
    public int nSh = 0;

    /**
     * Sets the ambient reflection coefficient.
     * @param kA the ambient coefficient as Double3
     * @return this Material instance (for chaining)
     */
    public Material setkA(Double3 kA) {
        this.kA = kA;
        return this;
    }

    /**
     * Sets the ambient reflection coefficient.
     * @param kA the ambient coefficient as double
     * @return this Material instance (for chaining)
     */
    public Material setkA(double kA) {
        this.kA = new Double3(kA);
        return this;
    }

    /**
     * Sets the diffusive reflection coefficient.
     * @param kD the diffusive coefficient as Double3
     * @return this Material instance (for chaining)
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffusive reflection coefficient.
     * @param kD the diffusive coefficient as double
     * @return this Material instance (for chaining)
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     * @param kS the specular coefficient as Double3
     * @return this Material instance (for chaining)
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient.
     * @param kS the specular coefficient as double
     * @return this Material instance (for chaining)
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess factor for specular reflection.
     * @param nSh the shininess factor
     * @return this Material instance (for chaining)
     */
    public Material setnSh(int nSh) {
        this.nSh = nSh;
        return this;
    }
}