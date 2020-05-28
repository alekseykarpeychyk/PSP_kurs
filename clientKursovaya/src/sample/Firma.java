package sample;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Firma {
    public Firma(int idfirma, String provider, String unp, String material, int cost, int time) {
        this.idfirma = idfirma;
        this.provider = provider;
        this.unp = unp;
        this.material = material;
        this.cost = cost;
        this.time = time;
    }

    private int idfirma;
    private String provider;
    private String unp;
    private String material;
    private int cost;
    private int time;

   public Firma(){
       try {

           String str = Client.getInstance().get();

           JSONObject json = new JSONObject(str);

           idfirma = json.getInt("idfirma");
           provider = json.getString("provider");
           unp = json.getString("unp");
           material = json.getString("material");
           cost = json.getInt("cost");
           time = json.getInt("time");

       } catch(JSONException | IOException e){
           System.err.println(e);
       }

   }

    private static Firma instance;

    public static synchronized Firma getInstance(){
        if(instance == null){
            instance = new Firma();
        }
        return instance;
    }

    public int getIdfirma() {
        return idfirma;
    }

    public String getProvider() {
        return provider;
    }

    public String getUnp() {
        return unp;
    }

    public String getMaterial() {
        return material;
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }



    public void setIdfirma(int idfirma) {
        this.idfirma = idfirma;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setUnp(String unp) {
        this.unp = unp;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static void setInstance(Firma instance) {
        Firma.instance = instance;
    }
}
