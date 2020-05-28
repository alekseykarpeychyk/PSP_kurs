package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Client;

public class TaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane opt;

    @FXML
    private Button exit_button;

    @FXML
    private GridPane neopt;

    @FXML
    void initialize() throws IOException {
        String a_ans = Client.getInstance().get();
        String b_ans = Client.getInstance().get();


        Label label1 = new Label();
        label1.setText(a_ans);
        label1.setFont(new Font("Arial", 14));
        opt.add(label1, 0, 0);

        Label label2 = new Label();
        label2.setText(b_ans);
        label2.setFont(new Font("Arial", 14));
        neopt.add(label2, 0, 0);

    }
}