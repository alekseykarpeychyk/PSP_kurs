package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Client;

public class MenuAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit_button;

    @FXML
    private Button usersControl_button;

    @FXML
    private Button supply_button;

    @FXML
    void initialize() {
        exit_button.setOnAction(actionEvent -> {
            Client.getInstance().send("exit");
            Client.getInstance().close();
            Stage stage = (Stage) exit_button.getScene().getWindow();
            stage.close();
        });

        usersControl_button.setOnAction(event -> {
            usersControl_button.getScene().getWindow().hide();
            Client.getInstance().send("user manage");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/userControl.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Управление пользователями");
            stage.show();

        });


        supply_button.setOnAction(event -> {
            supply_button.getScene().getWindow().hide();
            Client.getInstance().send("supply manage");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/Company.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Управление компанией");
            stage.show();

        });
    }
}
