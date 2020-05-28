package by.bsuir;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Configs{
    Connection dbConnection;
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }


    public String signInUser(String username, String password) throws SQLException {
        JSONObject userJson = new JSONObject();
        User user = new User();
        String result =null;
        try {
            // Statement stmt = getDbConnection().createStatement();
            String select = "SELECT * FROM "+ Const.USER_TABLE + " WHERE "+ Const.USER_USERNAME  +
                    "= ?";
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, username);
           // prSt.setString(2, password);
            ResultSet rs = prSt.executeQuery();
            if (rs.next() == false) {
                result = "nobody";
            } else
            { do {
                user.setFirstName(rs.getString(1));
                user.setLastName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setUserName(rs.getString(4));
                user.setPassword(rs.getString(5));

                userJson.put("firstname", user.getFirstName());
                userJson.put("lastname", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("username", user.getUsername());
                userJson.put("password", user.getPassword());
            } while (rs.next());
                result = userJson.toString();
            }

        } catch (ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }
        //if(user.getName().equals("null")) result = user.getName();

        return result;
    }

    public String signUpUser(String firstname, String lastname, String email, String username, String password)
            throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_FIRSTNAME + ","
                + Const.USER_LASTNAME + "," + Const.USER_EMAIN + "," + Const.USER_USERNAME + ","
                + Const.USER_PASSWORD + ")" + "VALUES(?,?,?,?,?)";
        try {
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, firstname);
        prSt.setString(2, lastname);
        prSt.setString(3, email);
        prSt.setString(4, username);
        prSt.setString(5, password);
        prSt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

        JSONObject userJson = new JSONObject();
        try {
            userJson.put("firstname", firstname);
            userJson.put("lastname", lastname);
            userJson.put("email", email);
            userJson.put("username", username);
            userJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userJson.toString();
    }

    public String getUsers() {
        User user;
        JSONObject userJson;
        JSONArray users = new JSONArray(  );
        try {

            String select = "SELECT * FROM "+Const.USER_TABLE;
            PreparedStatement prep1 = getDbConnection().prepareStatement( select );
            ResultSet rs = prep1.executeQuery();
            while (rs.next()){

                user = new User();
                user.setID(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(6));
                user.setUserName(rs.getString(4));
                user.setPassword(rs.getString(5));

                userJson = new JSONObject();
                userJson.put("idusers", user.getID());
                userJson.put("firstname", user.getFirstName());
                userJson.put("lastname", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("username", user.getUsername());
                userJson.put("password", user.getPassword());

                users.put( userJson );
            }

        } catch (SQLException | ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }

        return users.toString();
    }


    public void deleteUser(Integer userId) throws SQLException, ClassNotFoundException {
        String deletion = "DELETE FROM " + Const.USER_TABLE+ " WHERE "+ Const.USER_ID +" = ?";
        PreparedStatement prSt=getDbConnection().prepareStatement(deletion);
        prSt.setInt(1,userId);
        prSt.executeUpdate();
    }

    public String checkUser(String login){
        JSONObject userJson = new JSONObject();
        User user = new User();
        String result =null;
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_USERNAME + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            ResultSet rs = prSt.executeQuery();

            if (rs.next() == false) {
                result = "nobody";
            } else
            { do {
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setUserName(rs.getString(5));
                user.setPassword(rs.getString(6));

                userJson.put("firstname", user.getFirstName());
                userJson.put("lastname", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("username", user.getUsername());
                userJson.put("password", user.getPassword());
            } while (rs.next());
                result = userJson.toString();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteFirma(Integer firmaId) throws SQLException, ClassNotFoundException {
        String deletion = "DELETE FROM " + Const.FIRMA_TABLE+ " WHERE "+ Const.FIRMA_ID +" = ?";
        PreparedStatement prSt=getDbConnection().prepareStatement(deletion);
        prSt.setInt(1,firmaId);
        prSt.executeUpdate();
    }


    public String firmaInput(int idfirma, String provider, String unp, String material,
                               int cost, int time){
        String insert = "INSERT INTO " + Const.FIRMA_TABLE + "(" + Const.FIRMA_ID + ","+ Const.FIRMA_PROVIDER + "," + Const.FIRMA_UNP + "," +
                Const.FIRMA_MATERIAL + "," + Const.FIRMA_COST + "," + Const.FIRMA_TIME + ")" +
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, idfirma);
            prSt.setString(2, provider);
            prSt.setString(3, unp);
            prSt.setString(4, material);
            prSt.setInt(5, cost);
            prSt.setInt(6, time);


            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject userJson = new JSONObject();
        try {
            userJson.put("idfirma", idfirma);
            userJson.put("provider", provider);
            userJson.put("unp", unp);
            userJson.put("material", material);
            userJson.put("cost", cost);
            userJson.put("time", time);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userJson.toString();
    }

    public String getFirma() {
        Firma firma;
        JSONObject firmaJson;
        JSONArray firm = new JSONArray(  );
        try {

            String select = "SELECT * FROM "+Const.FIRMA_TABLE;
            PreparedStatement prep1 = getDbConnection().prepareStatement( select );
            ResultSet rs = prep1.executeQuery();
            while (rs.next()){

                firma = new Firma();
                firma.setIdfirma(rs.getInt(1));
                firma.setProvider(rs.getString(2));
                firma.setUnp(rs.getString(3));
                firma.setMaterial(rs.getString(4));
                firma.setCost(rs.getInt(5));
                firma.setTime(rs.getInt(6));



                firmaJson = new JSONObject();
                firmaJson.put("idfirma", firma.getIdfirma());
                firmaJson.put("provider", firma.getProvider());
                firmaJson.put("unp", firma.getUnp());
                firmaJson.put("material", firma.getMaterial());
                firmaJson.put("cost", firma.getCost());
                firmaJson.put("time", firma.getTime());


                firm.put( firmaJson );
            }

        } catch (SQLException | ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }

        return firm.toString();
    }


    public String statisticInput(int year, int revenue, int expenses, int profit){
        String insert = "INSERT INTO " + Const.STATISTIC_TABLE + "(" + Const.STATISTIC_YEAR + ","
                + Const.STATISTIC_REVENUE + "," + Const.STATISTIC_EXPENSES + "," +
                Const.STATISTIC_PROFIT +  ")" +   "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, year);
            prSt.setInt(2, revenue);
            prSt.setInt(3, expenses);
            prSt.setInt(4, profit);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject userJson = new JSONObject();
        try {
            userJson.put("year", year);
            userJson.put("revenue", revenue);
            userJson.put("expenses", expenses);
            userJson.put("profit", profit);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userJson.toString();
    }


    public String getStatistic() {
        Statistic statistic;
        JSONObject statisticJson;
        JSONArray firm = new JSONArray(  );
        try {

            String select = "SELECT * FROM "+Const.STATISTIC_TABLE;
            PreparedStatement prep2 = getDbConnection().prepareStatement( select );
            ResultSet rs1 = prep2.executeQuery();
            while (rs1.next()){

                statistic = new Statistic();
                statistic.setYear(rs1.getInt(1));
                statistic.setRevenue(rs1.getInt(2));
                statistic.setExpenses(rs1.getInt(3));
                statistic.setProfit(rs1.getInt(4));



                statisticJson = new JSONObject();
                statisticJson.put("year", statistic.getYear());
                statisticJson.put("revenue", statistic.getRevenue());
                statisticJson.put("expenses", statistic.getExpenses());
                statisticJson.put("profit", statistic.getProfit());


                firm.put( statisticJson );
            }

        } catch (SQLException | ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }

        return firm.toString();
    }
}