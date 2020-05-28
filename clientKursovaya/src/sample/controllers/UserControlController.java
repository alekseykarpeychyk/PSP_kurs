package sample.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import sample.Client;
import sample.UserCollection;
import sample.UserInf;

public class UserControlController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button delete_button;

    @FXML
    private TableView<UserInf> users_table;

    @FXML
    private TableColumn<UserInf, Integer> id_column;

    @FXML
    private TableColumn<UserInf, String> firstname_column;

    @FXML
    private TableColumn<UserInf, String> lastname_column;

    @FXML
    private TableColumn<UserInf, String> email_column;

    @FXML
    private TableColumn<UserInf, String> username_column;

    @FXML
    private TableColumn<UserInf, String> password_column;

    @FXML
    private TextField find_field;

    @FXML
    private Button find_button;

    @FXML
    private Button exit_button;

    @FXML
    void searchUser() {

    FilteredList<UserInf> filterUsers;
        filterUsers = new FilteredList<>(UserCollection.getInstance().getUsers(), e->true);
        find_field.textProperty().addListener((observableValue, oldValue, newValue)->{
            filterUsers.setPredicate((UserInf user)->{

                String newVal = newValue.toLowerCase();
                return  user.getFirstname().toLowerCase().contains(newVal)
                        || user.getLastname().toLowerCase().contains(newVal)
                        || user.getEmail().toLowerCase().contains(newVal)
                        || user.getUsername().toLowerCase().contains(newVal)
                        || user.getPassword().toLowerCase().contains(newVal);

            });
            users_table.setItems(filterUsers);
        });
    }

    @FXML
    void initialize() {
        userInTable();

        exit_button.setOnAction(event -> {
            exit_button.getScene().getWindow().hide();
            Client.getInstance().send("back");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/MenuAdmin.fxml"));

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
    }

    public void userInTable(){
       
        UserCollection.getInstance().fillData();
        id_column.setCellValueFactory(new PropertyValueFactory<UserInf, Integer>("idusers"));
        firstname_column.setCellValueFactory(new PropertyValueFactory<UserInf, String>("firstname"));
        lastname_column.setCellValueFactory(new PropertyValueFactory<UserInf, String>("lastname"));
        username_column.setCellValueFactory(new PropertyValueFactory<UserInf, String>("username"));
        password_column.setCellValueFactory(new PropertyValueFactory<UserInf, String>("password"));
        email_column.setCellValueFactory(new PropertyValueFactory<UserInf, String>("email"));
        users_table.setItems( UserCollection.getInstance().getUsers());

        firstname_column.setCellFactory(TextFieldTableCell.forTableColumn());
        lastname_column.setCellFactory(TextFieldTableCell.forTableColumn());
        username_column.setCellFactory(TextFieldTableCell.forTableColumn());
        password_column.setCellFactory(TextFieldTableCell.forTableColumn());
        email_column.setCellFactory(TextFieldTableCell.forTableColumn());

    }



    @FXML
    public void deleteUser() {
        UserInf selectedUser = (UserInf) users_table.getSelectionModel().getSelectedItem();
        UserCollection.getInstance().delete( selectedUser );
        Client.getInstance().send( "deleteUser" );
        Client.getInstance().send( selectedUser.getIdusers());

        Alert alert = new Alert(  Alert.AlertType.INFORMATION);
        alert.setTitle( "Действие совершено" );
        alert.setHeaderText( "Пользователь успешно удален!" );
        alert.showAndWait();
    }
}