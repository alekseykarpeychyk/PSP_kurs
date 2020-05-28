package sample;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Statistics {

    private int year;
    private int revenue;
    private int expenses;
    private int profit;

    public Statistics(int year, int revenue, int expenses, int profit) {
        this.year = year;
        this.revenue = revenue;
        this.expenses = expenses;
        this.profit = profit;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }



    public Statistics (){
        try {
            String str = Client.getInstance().get();
            JSONObject json = new JSONObject(str);

            year = json.getInt("year");
            revenue = json.getInt("revenue");
            expenses = json.getInt("expenses");
            profit = json.getInt("profit");

        } catch(JSONException | IOException e){
            System.err.println(e);
        }
    }

    private static Statistics instance;

    public static synchronized Statistics getInstance(){
        if(instance == null){
            instance = new Statistics();
        }
        return instance;
    }
}
