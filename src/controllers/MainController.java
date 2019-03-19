package controllers;

import dao.CountriesDAO;
import dao.DisasterDAO;
import dao.YearsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Disaster;
import models.DisasterType;

public class MainController {

    @FXML
    private ChoiceBox yearsChoiceBox;
    @FXML
    private ChoiceBox countriesChoiceBox;
    @FXML
    private ChoiceBox eventsChoiceBox;

    @FXML
    void initialize() {
        yearsChoiceBox.setItems(YearsDAO.getAllYears(false));
        yearsChoiceBox.getSelectionModel().select(0);

        countriesChoiceBox.setItems(CountriesDAO.getAllCountries(false));
        countriesChoiceBox.getSelectionModel().select(0);

        ObservableList<DisasterType> events = FXCollections.observableArrayList();
        events.add(DisasterType.EARTHQUAKE);
        events.add(DisasterType.DROUGHT);
        events.add(DisasterType.EPIDEMIC);
        events.add(DisasterType.EXTREME_TEMPERATURE);
        events.add(DisasterType.FLOOD);
        events.add(DisasterType.PLANE_CRASH);
        events.add(DisasterType.STORM);
        events.add(DisasterType.TSUNAMI);

        eventsChoiceBox.setItems(events);
    }

    @FXML
    void onRunClicked() {
        System.out.println("Run clicked!");

        System.out.println("Year selected: " + yearsChoiceBox.getSelectionModel().getSelectedItem());
        System.out.println("Country selected: " + countriesChoiceBox.getSelectionModel().getSelectedItem());

        String countrySelected = countriesChoiceBox.getSelectionModel().getSelectedItem().toString();

        ObservableList<Disaster> disasters = FXCollections.observableArrayList();

        DisasterType type = (DisasterType) eventsChoiceBox.getSelectionModel().getSelectedItem();
        disasters = DisasterDAO.getDisasterForCountry(type, countrySelected);

        NumberAxis xAxis = new NumberAxis(1970, 2020, 10);
        xAxis.setLabel("Years");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Deaths");

        LineChart barChart = new LineChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("No of deaths in a year");

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("No of affected in a year");

        for (Disaster disaster : disasters) {
            System.out.println(disaster.getYear().getYear() + " " + disaster.getDeaths());
            dataSeries1.getData().add(new XYChart.Data(disaster.getYear().getYear(), disaster.getDeaths()));
            dataSeries2.getData().add(new XYChart.Data(disaster.getYear().getYear(), disaster.getAffected()));
        }

        barChart.getData().add(dataSeries1);
        barChart.getData().add(dataSeries2);

        VBox vbox = new VBox(barChart);

        Scene scene = new Scene(vbox, 400, 200);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);

        primaryStage.show();

    }

}
