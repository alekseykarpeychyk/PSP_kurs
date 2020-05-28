package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public final class FirmaCollection {
    private ObservableList<Firma> firm = FXCollections.observableArrayList();

    private ObservableList<Firma> selectedFirma = FXCollections.observableArrayList();



    private static FirmaCollection instance;

    public static synchronized  FirmaCollection getInstance(){
        if(instance == null){
            instance = new FirmaCollection();
        }
        return instance;
    }

    public ObservableList<Firma> getFirma() {
        return firm;
    }

    public ObservableList<Firma> getSelectedFirmaFirm() {
        return selectedFirma;
    }

    public void delete(Firma firma){
        firm.remove(firma);
    }

    public void deleteFirmTask(Firma firma){
        selectedFirma.remove(firma);
    }

    public void change(Firma firma){

    }

    public void selectFirma(Firma firma){
        if(selectedFirma.size()>2){
            Alert alert = new Alert(  Alert.AlertType.WARNING);
            alert.setTitle( "Ошибка" );
            alert.setHeaderText( "В задаче уже выбранно 3 проекта" );
            alert.showAndWait();
        }
        else {
            selectedFirma.add(firma);
        }
    }

    public void fillData(){
        try {
            firm.removeAll(firm);
            String array = Client.getInstance().get();
            JSONArray newArray = new JSONArray( array );
            int count = newArray.length();
            for(int i = 0; i<count; i++) {
                JSONObject object = newArray.getJSONObject( i );
                Integer idfirma = object.getInt("idfirma");
                String provider = object.getString( "provider" );
                String unp = object.getString( "unp" );
                String material = object.getString( "material" );
                int cost = object.getInt( "cost" );
                int time = object.getInt("time");

                Firma firma = new Firma(idfirma, provider, unp, material, cost, time);
                firm.add( firma );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }


    public void fillNewFirma(){

   //     try {

            //  String array = Client.getInstance().get();
              Firma array = Firma.getInstance();

            JSONObject object = new JSONObject(array);

            Integer idfirma = object.getInt("idfirma");
            String provider = object.getString( "provider" );
            String unp = object.getString( "unp" );
            String material = object.getString( "material" );
            int cost = object.getInt( "cost" );
            int time = object.getInt("time");

            Firma firma = new Firma(idfirma, provider, unp, material, cost, time);
            firm.add( firma );


 /*       } catch (JSONException | IOException e) {
            e.printStackTrace();
        }*/


    }



    public Firma getLastProject(final Collection c) {
        final Iterator itr = c.iterator();
        Firma lastElement = (Firma) itr.next();
        while(itr.hasNext()) {
            lastElement = (Firma) itr.next();
        }
        return lastElement;
    }


}

