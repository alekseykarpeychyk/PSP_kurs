package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import sample.Client;
import sample.User;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField username_field;

    @FXML
    private Button signup_buttom;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button login_buttom;

    @FXML
    private Button exit_button;

    @FXML

    void initialize() {
        exit_button.setOnAction(actionEvent -> {
            Client.getInstance().send("exit");
            Client.getInstance().close();
            Stage stage = (Stage) exit_button.getScene().getWindow();
            stage.close();
        });

        login_buttom.setOnAction(event -> {

            try {
                Client.getInstance().send("authorization");

                JSONObject userJson = new JSONObject();
                userJson.put("username", username_field.getText().trim());
                userJson.put("password", password_field.getText().trim());

                Client.getInstance().send(userJson.toString());

                String status = Client.getInstance().get();
                if (!status.equals("nobody")) {
                    openMenu(status);
                } else {
                    System.out.println("Повторите ввод");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Такой пользователь не зарегистрирован!");
                    alert.showAndWait();
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        });


        signup_buttom.setOnAction(actionEvent -> {

            try {
                signup_buttom.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/Registration.fxml"));

                loader.load();

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle("Регистрация");


                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }


    public void openMenu(String status) {
        if (status.equals("admin")) {
            try {
                login_buttom.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/MenuAdmin.fxml"));

                loader.load();

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Меню администратора");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (status.equals("user")) {

            //  User user = User.getInstance();

            try {
                login_buttom.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/MenuUser.fxml"));

                loader.load();

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Повторите ввод");//дописать
        }


    }
}
