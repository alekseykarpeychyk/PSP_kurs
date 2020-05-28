package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public final class UserCollection{

    private ObservableList<UserInf> users = FXCollections.observableArrayList();

    private static UserCollection instance;

    public static synchronized UserCollection getInstance(){
        if(instance == null){
            instance = new UserCollection();
        }
        return instance;
    }

    public ObservableList<UserInf> getUsers() {
        return users;
    }

    public void delete(UserInf user){
        users.remove(user);
    }

    public void fillData(){
        try {
            String array = Client.getInstance().get();
            JSONArray newArray = new JSONArray( array );
            int count = newArray.length();
            for(int i = 0; i<count; i++) {
                JSONObject object = newArray.getJSONObject( i );
                Integer idusers = object.getInt("idusers");
                String firstname = object.getString( "firstname" );
                String lastname = object.getString( "lastname" );
                String email = object.getString( "email" );
                String username = object.getString( "username" );
                String password=object.getString("password");
                UserInf user = new UserInf(idusers, firstname, lastname, email, username, password );
                users.add( user );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

}
