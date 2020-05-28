package sample.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.json.JSONObject;
import sample.*;

import static sample.StatisticsCollection.statisticMap;

public class CompanyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Firma> firma_table;

    @FXML
    private Pane deliveries_pane;;

    @FXML
    private Pane calculator_pane;

    @FXML
    private Pane statistics_pane;

    @FXML
    private Pane recordStat_pane;

// Поставщики, таблица
    @FXML
    private TableColumn<Firma, Integer> number_column;

    @FXML
    private TableColumn<Firma, String> firma_column;

    @FXML
    private TableColumn<Firma, String> unp_column;

    @FXML
    private TableColumn<Firma, String> material_column;

    @FXML
    private TableColumn<Firma, Integer> cost_column;

    @FXML
    private TableColumn<Firma, Integer> time_column;

    @FXML
    private TextField find_field;

    @FXML
    private Button edit_button;

    @FXML
    private Button delete_button;

    @FXML
    private Button addTask_button;

    @FXML
    private TextField number_field;

    @FXML
    private TextField firma_field;

    @FXML
    private TextField unp_field;

    @FXML
    private TextField material_field;

    @FXML
    private TextField cost_field;

    @FXML
    private TextField time_field;

    @FXML
    private Button add_button;

    @FXML
    private Button deliveries_button;

    @FXML
    private Button calculator_button;

    @FXML
    private Button recordStat_button;

    @FXML
    private Button exit_button;

    @FXML
    private Button statistics_button;

    @FXML
    private Button enter_button;
// статистика, график

    @FXML
    private TextField year_field;

    @FXML
    private TextField revenue_field;

    @FXML
    private TextField expenses_field;

    @FXML
    private TableView<Statistics> stat_table;

    @FXML
    private TableColumn<Statistics, Integer> year_column;

    @FXML
    private TableColumn<Statistics, Integer> revenue_column;

    @FXML
    private TableColumn<Statistics, Integer> expenses_column;

    @FXML
    private TableColumn<Statistics, Integer> profit_column;

    @FXML
    private TableView<Firma> mat_table;

    @FXML
    private TableColumn<Firma, String> mat_column;

    @FXML
    private Button build_button;

    @FXML
    private Button addInf_button;

    @FXML
    private TextField po1_field;

    @FXML
    private TextField oo1_field;

    @FXML
    private TextField po2_field;

    @FXML
    private TextField oo2_field;

    @FXML
    private TextField po3_field;

    @FXML
    private TextField oo3_field;

    @FXML
    private TextField oProfit_field;

    @FXML
    void handleCliks(ActionEvent event) {

        if(event.getSource()==deliveries_button){
            deliveries_pane.toFront();
        }
      /*  else if(event.getSource()==statistics_button){
            statistics_pane.toFront();}*/

        else if(event.getSource()==recordStat_button){
            recordStat_pane.toFront();
        }
        else if(event.getSource()==calculator_button){
            calculator_pane.toFront();
        }
        else if(event.getSource()==exit_button){

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
        }
    }

    @FXML
    void initialize() {
        firmaInTable();
        statisticInTable();

    }

    @FXML
    void addFirma() {


        boolean key = true;
        JSONObject projectJson = new JSONObject();
        Firma firma;
        if( firma_field.getText().isEmpty() ){key = false;}
        else {projectJson.put("provider", firma_field.getText().trim()); }
        if( unp_field.getText().isEmpty() ){key = false;}
        else{projectJson.put("unp", unp_field.getText().trim());}
        if( material_field.getText().isEmpty() ){key = false;}
        else{projectJson.put("material", material_field.getText().trim());}
        if( cost_field.getText().isEmpty() ){key = false;}
        else{projectJson.put("cost", cost_field.getText().trim());}
        if( time_field.getText().isEmpty() ){key = false;}
        else{projectJson.put("time", time_field.getText().trim());}

        if(key) {
            Client.getInstance().send("getFirma");
            Client.getInstance().send( projectJson.toString() );
            firma = Firma.getInstance();

            FirmaCollection.getInstance().fillNewFirma();
           // number_column.setCellValueFactory(new PropertyValueFactory<Firma, Integer>("idfirma"));
            firma_column.setCellValueFactory(new PropertyValueFactory<Firma, String>("provider"));
            unp_column.setCellValueFactory(new PropertyValueFactory<Firma, String>("unp"));
            material_column.setCellValueFactory(new PropertyValueFactory<Firma, String>("material"));
            cost_column.setCellValueFactory(new PropertyValueFactory<Firma, Integer>("cost"));
            time_column.setCellValueFactory(new PropertyValueFactory<Firma, Integer>("time"));
            firma_table.setItems( FirmaCollection.getInstance().getFirma());

        }
        else {
            Alert alert = new Alert(  Alert.AlertType.WARNING);
            alert.setTitle( "Ошибка" );
            alert.setHeaderText( "Поля введены некорректно!" );
            alert.showAndWait();
        }
    }

    private boolean checkWord(String source) {
        Pattern pattern = Pattern.compile("^([А-Я][а-я]+)$");
        Matcher matcher = pattern.matcher(source);

        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }
    private boolean checkNum(String source) {
        Pattern pattern = Pattern.compile("^([0-9]+)$");
        Matcher matcher = pattern.matcher(source);

        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }

    public void firmaInTable(){

        FirmaCollection.getInstance().fillData();
        number_column.setCellValueFactory(new PropertyValueFactory<Firma, Integer>("idfirma"));
        firma_column.setCellValueFactory(new PropertyValueFactory<Firma, String>("provider"));
        unp_column.setCellValueFactory(new PropertyValueFactory<Firma, String>("unp"));
        material_column.setCellValueFactory(new PropertyValueFactory<Firma, String>("material"));
        cost_column.setCellValueFactory(new PropertyValueFactory<Firma, Integer>("cost"));
        time_column.setCellValueFactory(new PropertyValueFactory<Firma, Integer>("time"));
        firma_table.setItems( FirmaCollection.getInstance().getFirma());

        firma_column.setCellFactory(TextFieldTableCell.forTableColumn());
        unp_column.setCellFactory(TextFieldTableCell.forTableColumn());
        material_column.setCellFactory(TextFieldTableCell.forTableColumn());
        cost_column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return ""+object;
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        }));
        time_column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return ""+object;
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        }));
    }

    @FXML
    public void deleteFirm() {
        Firma selectedProject = (Firma) firma_table.getSelectionModel().getSelectedItem();
        FirmaCollection.getInstance().delete( selectedProject );
        Client.getInstance().send( "deleteFirma" );
        Client.getInstance().send( selectedProject.getIdfirma());
    }

    @FXML
    void searchFirma(ActionEvent event) {
        FilteredList<Firma> filterFirma;
        filterFirma = new FilteredList<>(FirmaCollection.getInstance().getFirma(), e->true);
        find_field.textProperty().addListener((observableValue, oldValue, newValue)->{
            filterFirma.setPredicate((Firma firma)->{

                String newVal = newValue.toLowerCase();
                return  firma.getProvider().toLowerCase().contains(newVal)
                        || firma.getUnp().toLowerCase().contains(newVal)
                        || firma.getMaterial().toLowerCase().contains(newVal);

            });
            firma_table.setItems(filterFirma);
        });
    }

    @FXML
    void addStatistic() {
        boolean key = true;
        JSONObject projectJson = new JSONObject();
        Statistics statistic;
        if( year_field.getText().isEmpty() ){key = false;}
        else {projectJson.put("year", year_field.getText().trim()); }
        if( revenue_field.getText().isEmpty() ){key = false;}
        else{projectJson.put("revenue", revenue_field.getText().trim());}
        if( expenses_field.getText().isEmpty() ){key = false;}
        else{projectJson.put("expenses", expenses_field.getText().trim());}
        int resultRevenue = Integer.parseInt(revenue_field.getText());
        int resultExpenses = Integer.parseInt( expenses_field.getText());
        int resultProfit = resultRevenue - resultExpenses;
        String profit = Integer.toString(resultProfit);
        projectJson.put("profit", profit);

        if(key) {
            Client.getInstance().send("getStatistics");
            Client.getInstance().send( projectJson.toString() );
            statistic = Statistics.getInstance();

            StatisticsCollection.getInstance().fillNewFirma();

            year_column.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("year"));
            revenue_column.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("revenue"));
            expenses_column.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("expenses"));
            profit_column.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("profit"));


        }
        else {
            Alert alert = new Alert(  Alert.AlertType.WARNING);
            alert.setTitle( "Ошибка" );
            alert.setHeaderText( "Поля введены некорректно!" );
            alert.showAndWait();
        }
    }

    public void statisticInTable(){

        StatisticsCollection.getInstance().fillData();
        year_column.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("year"));
        revenue_column.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("revenue"));
        expenses_column.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("expenses"));
        profit_column.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("profit"));
        stat_table.setItems( StatisticsCollection.getInstance().getStatistics());

    }

    @FXML
    public void buildGraph()
    {
        Stage stage = new Stage();
        ObservableList<XYChart.Series> seriesList = FXCollections.observableArrayList();
        List<XYChart.Data> listData = new ArrayList<>();
        for(Map.Entry<Integer, Integer> item : statisticMap.entrySet()) {
            listData.add(new XYChart.Data(item.getKey(), item.getValue()));
        }

        ObservableList<XYChart.Data> aList = FXCollections.observableArrayList(listData);
        seriesList.add(new XYChart.Series("Прибыль ", aList));
        Axis xAxis = new NumberAxis("Год", 2010, 2020, 1);
        Axis yAxis = new NumberAxis("Ден.ед.", 50000, 850000, 100000);
        LineChart statistics_graph = new LineChart(xAxis, yAxis, seriesList);
        Button exitBtn = new Button("Назад");
        exitBtn.setOnAction(event -> stage.hide());
        FlowPane root = new FlowPane(Orientation.VERTICAL, statistics_graph,exitBtn);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



     @FXML
    void deletePr(ActionEvent event) {
        Firma selectedProject = (Firma) mat_table.getSelectionModel().getSelectedItem();
        FirmaCollection.getInstance().deleteFirmTask( selectedProject );
    }


    public void selectedFirmaInTable(){
        mat_column.setCellValueFactory(new PropertyValueFactory<Firma, String>("material"));
        mat_table.setItems(FirmaCollection.getInstance().getSelectedFirmaFirm());
    }




    @FXML
    void adPrForTask(ActionEvent event) {
        Firma selectedFirma = (Firma) firma_table.getSelectionModel().getSelectedItem();
        FirmaCollection.getInstance().selectFirma( selectedFirma );
        selectedFirmaInTable();
    }

    @FXML
    void singlSolution(ActionEvent event) throws IOException {


        boolean key = true;
        JSONObject  taskJson = new JSONObject();

        if( po1_field.getText().isEmpty() || po1_field.getText()==null || !checkNum(po1_field.getText()) ){key = false;}
        else {taskJson.put("po1", po1_field.getText().trim()); }
        if( po2_field.getText().isEmpty() || po2_field.getText()==null || !checkNum(po2_field.getText())){key = false;}
        else {taskJson.put("po2", po2_field.getText().trim()); }
        if( po3_field.getText().isEmpty() || po3_field.getText()==null || !checkNum(po3_field.getText())){key = false;}
        else {taskJson.put("po3", po3_field.getText().trim()); }
        if( oo1_field.getText().isEmpty() || oo1_field.getText()==null || !checkNum(oo1_field.getText())){key = false;}
        else {taskJson.put("oo1", oo1_field.getText().trim()); }
        if( oo2_field.getText().isEmpty() || oo2_field.getText()==null || !checkNum(oo2_field.getText())){key = false;}
        else {taskJson.put("oo2", oo2_field.getText().trim()); }
        if( oo3_field.getText().isEmpty() || oo3_field.getText()==null || !checkNum(oo3_field.getText())){key = false;}
        else {taskJson.put("oo3", oo3_field.getText().trim()); }
        if( oProfit_field.getText().isEmpty() || oProfit_field.getText()==null || !checkNum(oProfit_field.getText())){key = false;}
        else {taskJson.put("oProfit", oProfit_field.getText().trim()); }


        if(key) {
            Client.getInstance().send("getSinglSolution");
            Client.getInstance().send(taskJson.toString());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/Task.fxml"));

            loader.load();

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            stage.setTitle("Решение");

        }
        else {
            Alert alert = new Alert(  Alert.AlertType.WARNING);
            alert.setTitle( "Ошибка" );
            alert.setHeaderText( "Заполните все поля корректно!" );
            alert.showAndWait();
        }

    }

}


