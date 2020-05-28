package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public final class StatisticsCollection {
    private ObservableList<Statistics> stat = FXCollections.observableArrayList();
    public static Map<Integer, Integer> statisticMap = new HashMap<>();

    private ObservableList<Statistics> selectedStat = FXCollections.observableArrayList();



    private static StatisticsCollection instance;

    public static synchronized  StatisticsCollection getInstance(){
        if(instance == null){
            instance = new StatisticsCollection();
        }
        return instance;
    }

    public ObservableList<Statistics> getStatistics() {
        return stat;
    }

    public ObservableList<Statistics> getSelectedStat() {
        return selectedStat;
    }

    public void delete(Statistics statistic){
        stat.remove(statistic);
    }

    public void deleteFirmTask(Statistics statistic){
        selectedStat.remove(statistic);
    }

    public void change(Statistics statistic){

    }


    public void fillData(){
        try {
            stat.removeAll(stat);
            statisticMap.clear();
            String array = Client.getInstance().get();
            JSONArray newArray = new JSONArray( array );
            int count = newArray.length();
            for(int i = 0; i<count; i++) {
                JSONObject object = newArray.getJSONObject( i );
                int year = object.getInt( "year" );
                int revenue = object.getInt("revenue");
                int expenses = object.getInt( "expenses" );
                int profit = object.getInt("profit");

                Statistics statistic = new Statistics(year, revenue, expenses, profit);
                stat.add( statistic );
                statisticMap.put(year, profit);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }


    public void fillNewFirma(){

            Statistics array = Statistics.getInstance();
            JSONObject object = new JSONObject(array);

            int year = object.getInt( "year" );
            int revenue = object.getInt("revenue");
            int expenses = object.getInt( "expenses" );
            int profit = object.getInt("profit");

            Statistics statistic = new Statistics(year, revenue, expenses, profit);
            stat.add( statistic );





    }




}
