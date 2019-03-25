package controllers;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.*;

public class MainController {

    @FXML
    private ChoiceBox periodFromChoiceBox;
    @FXML
    private ChoiceBox periodToChoiceBox;
    @FXML
    private ListView eventsListView;
    @FXML
    private AnchorPane chartAnchorPane;
    @FXML
    private ListView countriesListView;
    @FXML
    private Label noGraphLabel;

    @FXML
    void initialize() {
        ObservableList<Year> years = YearsDAO.getAllYears(false);
        periodFromChoiceBox.setItems(years);
        periodFromChoiceBox.getSelectionModel().select(0);

        periodToChoiceBox.setItems(years);
        periodToChoiceBox.getSelectionModel().select(0);

        ObservableList<Country> countries = CountriesDAO.getAllCountries(false);
        countriesListView.setItems(countries);
        countriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<String> events = FXCollections.observableArrayList();
        events.add(DisasterType.EARTHQUAKE.toString());
        events.add(DisasterType.DROUGHT.toString());
        events.add(DisasterType.EPIDEMIC.toString());
        events.add(DisasterType.EXTREME_TEMPERATURE.toString());
        events.add(DisasterType.FLOOD.toString());
        events.add(DisasterType.PLANE_CRASH.toString());
        events.add(DisasterType.STORM.toString());
        events.add(DisasterType.TSUNAMI.toString());
        events.add("Income");
        events.add("Population");

        eventsListView.setItems(events);
        eventsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    void onRunClicked() {
        System.out.println("Run clicked!");

        System.out.println("Year from selected: " + periodFromChoiceBox.getSelectionModel().getSelectedItem());
        System.out.println("Year to selected: " + periodToChoiceBox.getSelectionModel().getSelectedItem());

        ObservableList<Country> countriesSelected = countriesListView.getSelectionModel().getSelectedItems();
        ObservableList<String> metricsSelected = eventsListView.getSelectionModel().getSelectedItems();

        if (countriesSelected.isEmpty() || metricsSelected.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Input");
            alert.setHeaderText("Please check your choices again");
            alert.setContentText("You need to select at least 1 metric and at least 1 country to build a graph.");

            alert.showAndWait();
            return;
        }

        noGraphLabel.setVisible(false);

        NumberAxis xAxis = new NumberAxis(1970, 2020, 10);
        xAxis.setLabel("Years");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Deaths");

        LineChart barChart = new LineChart(xAxis, yAxis);

        for (Country countrySelected : countriesSelected) {

            for (String type : metricsSelected) {

                ObservableList<Disaster> disasters = FXCollections.observableArrayList();
                ObservableList<Income> incomes = FXCollections.observableArrayList();
                ObservableList<Population> populations = FXCollections.observableArrayList();

                switch (type.toLowerCase()) {
                    case "income":
                        incomes = IncomeDAO.getIncomeForCountries(countrySelected.getName());
                        break;
                    case "population":
                        populations = PopulationDAO.getPopulationForCountry(countrySelected.getName());
                        break;
                    case "epidemics":
                        disasters = DisasterDAO.getDisasterForCountry(DisasterType.EPIDEMIC, countrySelected.getName());
                        break;
                    case "droughts":
                        disasters = DisasterDAO.getDisasterForCountry(DisasterType.DROUGHT, countrySelected.getName());
                        break;
                    case "earthquakes":
                        disasters = DisasterDAO.getDisasterForCountry(DisasterType.EARTHQUAKE, countrySelected.getName());
                        break;
                    case "extreme temperatures":
                        disasters = DisasterDAO.getDisasterForCountry(DisasterType.EXTREME_TEMPERATURE, countrySelected.getName());
                        break;
                    case "floods":
                        disasters = DisasterDAO.getDisasterForCountry(DisasterType.FLOOD, countrySelected.getName());
                        break;
                    case "plane crashes":
                        disasters = DisasterDAO.getDisasterForCountry(DisasterType.PLANE_CRASH, countrySelected.getName());
                        break;
                    case "storms":
                        disasters = DisasterDAO.getDisasterForCountry(DisasterType.STORM, countrySelected.getName());
                        break;
                    case "tsunamis":
                        disasters = DisasterDAO.getDisasterForCountry(DisasterType.TSUNAMI, countrySelected.getName());
                        break;
                }

                if (incomes.size()>0) {
                    XYChart.Series dataSeries1 = new XYChart.Series();
                    dataSeries1.setName("Annual Income for " + countrySelected.getName());

                    for (Income income : incomes) {
                        dataSeries1.getData().add(new XYChart.Data(income.getYear().getYear(), income.getIncome()));
                    }

                    barChart.getData().add(dataSeries1);
                } else if (populations.size()>0) {
                    XYChart.Series dataSeries1 = new XYChart.Series();
                    dataSeries1.setName("Annual Population for " + countrySelected.getName());

                    for (Population population : populations) {
                        dataSeries1.getData().add(new XYChart.Data(population.getYear().getYear(), population.getPopulation()));
                    }

                    barChart.getData().add(dataSeries1);
                } else {

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
                }
            }
        }

        VBox vbox = new VBox(barChart);

        chartAnchorPane.getChildren().clear();
        chartAnchorPane.getChildren().add(vbox);

    }

}
