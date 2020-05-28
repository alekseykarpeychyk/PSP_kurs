package sample;

import javafx.scene.control.Alert;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public final class User {

    private int idusers;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;

    private static User instance;

    public static synchronized User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }



    public User() {
        try{
            // client.Client.getInstance().send("setUser");
            String str = sample.Client.getInstance().get();
            JSONObject json = new JSONObject(str);
            if(!str.equals("exist")) {
                idusers = json.getInt("idusers");
                firstname = json.getString("firstName");
                lastname = json.getString("lastName");
                email = json.getString("email");
                username = json.getString("userName");
                password = json.getString("password");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Такой логин уже существует");
                alert.showAndWait();
            }

        } catch(JSONException | IOException e){
            System.err.println(e);
        }

    }


    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}