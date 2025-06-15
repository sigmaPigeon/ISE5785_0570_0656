package primitives;

public class Material {
    public Double3 kA=Double3.ONE;
    public Double3 kD=Double3.ZERO;
    public Double3 kS=Double3.ZERO;

    public int nSh = 0;

    public Material setkA(Double3 kA) {
        this.kA = kA;
        return this;
    }

    public Material setkA(double kA) {
        this.kA = new Double3(kA);
        return this;
    }
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    public Material setnSh(int nSh) {
        this.nSh = nSh;
        return this;
    }


}
