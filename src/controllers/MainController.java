package controllers;

import dao.*;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    private ChoiceBox questionChoiceBox;

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

        ObservableList<String> questions = FXCollections.observableArrayList();
        questionChoiceBox.setItems(questions);

        eventsListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener) c -> {
            if (!countriesListView.getSelectionModel().getSelectedItems().isEmpty()) {
                if (Integer.parseInt(periodToChoiceBox.getSelectionModel().getSelectedItem().toString()) - Integer.parseInt(periodFromChoiceBox.getSelectionModel().getSelectedItem().toString()) == 1
                        && !eventsListView.getSelectionModel().getSelectedItems().isEmpty()
                        && countriesListView.getSelectionModel().getSelectedItems().size()==1) {
                    questions.clear();
                    questions.add(QuestionAboutData.COMPOSITION.toString());
                    questions.add(QuestionAboutData.COMPARE_WITH_BAR.toString());
                    questions.add(QuestionAboutData.COMPARE_WITH_LINE.toString());
                }
            }
        });

        countriesListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener) c -> {
            if (!eventsListView.getSelectionModel().getSelectedItems().isEmpty()) {
                if (Integer.parseInt(periodToChoiceBox.getSelectionModel().getSelectedItem().toString()) - Integer.parseInt(periodFromChoiceBox.getSelectionModel().getSelectedItem().toString()) == 1
                        && !countriesListView.getSelectionModel().getSelectedItems().isEmpty()
                        && countriesListView.getSelectionModel().getSelectedItems().size()==1) {
                    questions.clear();
                    questions.add(QuestionAboutData.COMPOSITION.toString());
                    questions.add(QuestionAboutData.COMPARE_WITH_BAR.toString());
                    questions.add(QuestionAboutData.COMPARE_WITH_LINE.toString());
                }
            }
        });

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
        int lowerBound = Integer.parseInt(periodFromChoiceBox.getSelectionModel().getSelectedItem().toString());
        int upperBound = Integer.parseInt(periodToChoiceBox.getSelectionModel().getSelectedItem().toString());
        int tickUnit = Math.floorDiv((upperBound - lowerBound), 20);

        NumberAxis xAxis = new NumberAxis(lowerBound, upperBound, tickUnit);
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
                        incomes = IncomeDAO.getIncomeForCountryInAPeriod(lowerBound, upperBound, countrySelected.getName());
                        break;
                    case "population":
                        populations = PopulationDAO.getPopulationForCountryInAPeriod(lowerBound, upperBound, countrySelected.getName());
                        break;
                    default:
                        disasters = DisasterDAO.getDisasterForCountryInAPeriod(DisasterType.fromString(type), countrySelected.getName(), lowerBound, upperBound);
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
        vbox.setMinSize(810, 400);
        vbox.setMaxSize(1920, 1080);
        chartAnchorPane.getChildren().clear();
        chartAnchorPane.getChildren().add(vbox);

    }

}
