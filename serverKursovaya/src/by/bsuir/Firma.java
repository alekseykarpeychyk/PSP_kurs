package by.bsuir;

public class Firma {

    private int idfirma;
    private String provider;
    private String unp;
    private String material;
    private int cost;
    private int time;

    public Firma(){}

    public Firma(int idfirma, String provider, String unp, String material, int cost, int time) {
        this.idfirma = idfirma;
        this.provider = provider;
        this.unp = unp;
        this.material = material;
        this.cost = cost;
        this.time = time;

    }


    public int getIdfirma() {
        return idfirma;
    }

    public void setIdfirma(int idfirma) {
        this.idfirma = idfirma;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUnp() {
        return unp;
    }

    public void setUnp(String unp) {
        this.unp = unp;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
