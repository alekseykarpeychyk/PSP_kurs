package sample.controllers;

import sample.Client;
import sample.User;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstname_field;

    @FXML
    private TextField lastname_field;

    @FXML
    private TextField email_field;

    @FXML
    private TextField username_field;

    @FXML
    private TextField password_field;

    @FXML
    private Button Signup_button;

    @FXML
    private Button back_button;

    @FXML
    void initialize() {
        back_button.setOnAction(event -> {
            back_button.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/Authorization.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        });

        Signup_button.setOnAction(actionEvent -> {

            boolean key = true;
            JSONObject userJson = new JSONObject();
            if( firstname_field.getText().isEmpty() || firstname_field.getText()==null
                    || firstname_field.getText().length()<4 || firstname_field.getText().length()>12
                    ||checkName(firstname_field.getText())==false){key = false;}
            else {userJson.put("firstname", firstname_field.getText().trim());}
            if( lastname_field.getText().isEmpty() || lastname_field.getText()==null
                    || lastname_field.getText().length()<4 || lastname_field.getText().length()>12
                    ||checkName(lastname_field.getText())==false){key = false;}
            else{userJson.put("lastname", lastname_field.getText().trim());}
            if( email_field.getText().isEmpty() || email_field.getText()==null
                    || email_field.getText().length()<4 || email_field.getText().length()>32){key = false;}
            else{userJson.put("email", email_field.getText().trim());}
            if( username_field.getText().isEmpty() || username_field.getText()==null
                    || username_field.getText().length()<4 || username_field.getText().length()>12){key = false;}
            else{userJson.put("username", username_field.getText().trim());}
            if( password_field.getText().isEmpty() || password_field.getText()==null
                    || password_field.getText().length()<6 || password_field.getText().length()>15){key = false;}
            else{userJson.put("password", password_field.getText().trim());}
            if(key == true) {//false
                Client.getInstance().send("registration");
                Client.getInstance().send( userJson.toString() );
                User user = User.getInstance();
                try {
                    Signup_button.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/Authorization.fxml"));

                    loader.load();

                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setTitle( "Меню пользователя" );
                    stage.setResizable( false );
                    stage.setScene( new Scene( root ) );
                    stage.show();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(  Alert.AlertType.WARNING);
                alert.setTitle( "Ошибка" );
                alert.setHeaderText( "Заполните все поля!" );
                alert.showAndWait();
            }
        });


    }


    private boolean checkName(String source){
        Pattern pattern = Pattern.compile("^([А-Я][а-я]+)$");
        Matcher matcher = pattern.matcher(source);

        if (!matcher.matches()) {
            System.out.println("false");
            return false;
        }
        else{
            System.out.println("true");
            return true;
        }
    }
}

