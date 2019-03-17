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

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        ObservableList<String> events = FXCollections.observableArrayList();
        events.add("Earthquakes");
        events.add("Droughts");
        events.add("Epidemics");
        events.add("Extreme Temperatures");
        events.add("Floods");
        events.add("Plane Crashes");
        events.add("Storms");
        events.add("Tsunamis");

        eventsChoiceBox.setItems(events);
    }

    @FXML
    void onRunClicked() {
        System.out.println("Run clicked!");

        System.out.println("Year selected: " + yearsChoiceBox.getSelectionModel().getSelectedItem());
        System.out.println("Country selected: " + countriesChoiceBox.getSelectionModel().getSelectedItem());

        String countrySelected = countriesChoiceBox.getSelectionModel().getSelectedItem().toString();

        ObservableList<Disaster> disasters = FXCollections.observableArrayList();

        switch (eventsChoiceBox.getSelectionModel().getSelectedItem().toString().toLowerCase()) {
            case "epidemics":
               disasters = DisasterDAO.getDisasterForCountry(DisasterType.EPIDEMIC, countrySelected);
               break;
        }

        NumberAxis xAxis = new NumberAxis(1800, 2020, 10);
        xAxis.setLabel("Years");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Deaths");

        LineChart barChart = new LineChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("No of deaths in a year");

        for (Disaster disaster : disasters) {
            System.out.println(disaster.getYear().getYear() + " " + disaster.getDeaths());
            dataSeries1.getData().add(new XYChart.Data(disaster.getYear().getYear(), disaster.getDeaths()));
        }

        barChart.getData().add(dataSeries1);

        VBox vbox = new VBox(barChart);

        Scene scene = new Scene(vbox, 400, 200);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);

        primaryStage.show();

    }

}
