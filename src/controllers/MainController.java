/*
 * ChainProof
 * Tsiounis Konstantinos 2630
 * Polyzos Alexios 2338
 * Copyright (c) 2019.
 */

package controllers;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
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
    private Label noDataLabel;

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

        for (DisasterType type: DisasterType.values()) {
            events.add(type.toString());
        }

        events.add("Income");
        events.add("Population");

        eventsListView.setItems(events);
        eventsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<String> questions = FXCollections.observableArrayList();
        questionChoiceBox.setItems(questions);

        addListenerToList(countriesListView, eventsListView, questions);

        addListenerToList(eventsListView, countriesListView, questions);

    }

    @FXML
    void onRunClicked() {
        System.out.println("Run clicked!");

        noDataLabel.setText("");

        ObservableList<Country> countriesSelected = countriesListView.getSelectionModel().getSelectedItems();
        ObservableList<String> metricsSelected = eventsListView.getSelectionModel().getSelectedItems();
        String questionSelected = questionChoiceBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(questionSelected);
        int lowerBound = getIntValueFromBox(periodFromChoiceBox);
        int upperBound = getIntValueFromBox(periodToChoiceBox);

        if (countriesSelected.isEmpty() || metricsSelected.isEmpty() || (upperBound-lowerBound)<=0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Input");
            alert.setHeaderText("Please check your choices again");
            alert.setContentText("You need to select at least 1 metric and at least 1 country to build a graph.");

            alert.showAndWait();
            return;
        }

        noGraphLabel.setVisible(false);
        int tickUnit = Math.floorDiv((upperBound - lowerBound), 10);

        VBox vbox = null;

        if (questionSelected.equals(QuestionAboutData.COMPARE_WITH_BAR.toString()) ||
                questionSelected.equals(QuestionAboutData.UNDERSTAND_WITH_BAR.toString())) {
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Metrics");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("People");

            BarChart barChart = new BarChart(xAxis, yAxis);

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

                        if (!disasters.isEmpty()) {

                            XYChart.Series dataSeries1 = new XYChart.Series();

                            XYChart.Series dataSeries2 = new XYChart.Series();

                            for (Disaster disaster : disasters) {
                                System.out.println(disaster.getYear().getYear() + " " + disaster.getDeaths());
                                dataSeries2.setName("No of affected in a year for " + disaster.getCountry().getName() + " (" + disaster.getDisaster() + ")");
                                dataSeries1.setName("No of deaths in a year for " + disaster.getCountry().getName() + " (" + disaster.getDisaster() + ")");
                                dataSeries1.getData().add(new XYChart.Data(String.valueOf(disaster.getYear().getYear()), disaster.getDeaths()));
                                dataSeries2.getData().add(new XYChart.Data(String.valueOf(disaster.getYear().getYear()), disaster.getAffected()));
                            }

                            barChart.getData().add(dataSeries1);
                            barChart.getData().add(dataSeries2);

                        } else {
                            noDataLabel.setVisible(true);
                            if (noDataLabel.getText().equals("")) {
                                noDataLabel.setText("There were no data for " + countrySelected.getName());
                            } else {
                                noDataLabel.setText(noDataLabel.getText() + ", " + countrySelected.getName());
                            }
                        }
                    }
                }
            }

            vbox = new VBox(barChart);

        } else if (questionSelected.equals(QuestionAboutData.COMPARE_WITH_PIE.toString()) ||
                questionSelected.equals(QuestionAboutData.COMPOSITION.toString())) {

            PieChart pieChart = new PieChart();
            pieChart.setLegendSide(Side.LEFT);
            pieChart.setPrefSize(400, 400);

            for (Country countrySelected : countriesSelected) {

                for (String type : metricsSelected) {

                    ObservableList<Disaster> disasters;

                    disasters = DisasterDAO.getDisasterForCountryInAPeriod(DisasterType.fromString(type), countrySelected.getName(), lowerBound, upperBound);

                    if (!disasters.isEmpty()) {

                        Integer numOfDeaths = 0;

                        for (Disaster disaster : disasters) {

                            System.out.println(disaster.getId());

                            numOfDeaths += disaster.getDeaths();
                        }

                        PieChart.Data dataSeries2 = new PieChart.Data("No of deaths in a year for " + countrySelected.getName() + " (" + type + ")", numOfDeaths);

                        pieChart.getData().add(dataSeries2);

                    } else {
                        noDataLabel.setVisible(true);
                        if (noDataLabel.getText().equals("")) {
                            noDataLabel.setText("There were no data for " + type + " in " + countrySelected.getName());
                        } else {
                            noDataLabel.setText(noDataLabel.getText() + ", " + type + " in " + countrySelected.getName());
                        }
                    }


                }
            }

            vbox = new VBox(pieChart);

        } else if (questionSelected.equals(QuestionAboutData.UNDERSTAND_WITH_LINE.toString()) ||
                questionSelected.equals(QuestionAboutData.COMPARE_WITH_LINE.toString()) ||
                questionSelected.equals(QuestionAboutData.ANALYZE_TREND_LINE.toString())) {

            NumberAxis xAxis = new NumberAxis(lowerBound, upperBound, tickUnit);
            xAxis.setLabel("Years");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("People");

            LineChart lineChart = new LineChart(xAxis, yAxis);

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

                        lineChart.getData().add(dataSeries1);
                    } else if (populations.size()>0) {
                        XYChart.Series dataSeries1 = new XYChart.Series();
                        dataSeries1.setName("Annual Population for " + countrySelected.getName());

                        for (Population population : populations) {
                            dataSeries1.getData().add(new XYChart.Data(population.getYear().getYear(), population.getPopulation()));
                        }

                        lineChart.getData().add(dataSeries1);
                    } else {

                        if (!disasters.isEmpty()) {

                            XYChart.Series dataSeries1 = new XYChart.Series();

                            XYChart.Series dataSeries2 = new XYChart.Series();

                            for (Disaster disaster : disasters) {
                                System.out.println(disaster.getYear().getYear() + " " + disaster.getDeaths());
                                dataSeries2.setName("No of affected in a year for " + disaster.getCountry().getName() + " (" + disaster.getDisaster() + ")");
                                dataSeries1.setName("No of deaths in a year for " + disaster.getCountry().getName() + " (" + disaster.getDisaster() + ")");
                                dataSeries1.getData().add(new XYChart.Data(disaster.getYear().getYear(), disaster.getDeaths()));
                                dataSeries2.getData().add(new XYChart.Data(disaster.getYear().getYear(), disaster.getAffected()));
                            }

                            lineChart.getData().add(dataSeries1);
                            lineChart.getData().add(dataSeries2);

                        } else {
                            noDataLabel.setVisible(true);
                            if (noDataLabel.getText().equals("")) {
                                noDataLabel.setText("There were no data for " + countrySelected.getName());
                            } else {
                                noDataLabel.setText(noDataLabel.getText() + ", " + countrySelected.getName());
                            }
                        }
                    }
                }
            }

            vbox = new VBox(lineChart);
        }

        if (vbox != null) {
            vbox.setMinSize(810, 400);
            vbox.setMaxSize(1920, 1080);
            chartAnchorPane.getChildren().clear();
            chartAnchorPane.getChildren().add(vbox);
            chartAnchorPane.getChildren().add(noDataLabel);
        }

    }

    private String getStringValueFromBox(ChoiceBox box) {
        return box.getSelectionModel().getSelectedItem().toString();
    }

    private int getIntValueFromBox(ChoiceBox box) {
        return Integer.parseInt(getStringValueFromBox(box));
    }

    private ObservableList getSelectedList(ListView list) {
        return list.getSelectionModel().getSelectedItems();
    }

    private void addListenerToList(ListView list, ListView checkingList, ObservableList<String> valueList) {
        getSelectedList(list).addListener((ListChangeListener) c -> {
            if (!getSelectedList(checkingList).isEmpty()) {
                if (getIntValueFromBox(periodToChoiceBox) - getIntValueFromBox(periodFromChoiceBox) == 1) {
                    if (!getSelectedList(list).isEmpty()
                            && getSelectedList(checkingList).size() == 1) {
                        valueList.clear();
                        valueList.add(QuestionAboutData.COMPOSITION.toString());
                        valueList.add(QuestionAboutData.COMPARE_WITH_BAR.toString());
                        valueList.add(QuestionAboutData.COMPARE_WITH_LINE.toString());
                    } else if (getSelectedList(list).size() == 1
                            && !getSelectedList(checkingList).isEmpty()) {
                        valueList.clear();
                        valueList.add(QuestionAboutData.COMPOSITION.toString());
                        valueList.add(QuestionAboutData.COMPARE_WITH_BAR.toString());
                        valueList.add(QuestionAboutData.COMPARE_WITH_LINE.toString());
                    }
                } else {
                    valueList.clear();
                    valueList.add(QuestionAboutData.ANALYZE_TREND_LINE.toString());
                    valueList.add(QuestionAboutData.COMPARE_WITH_BAR.toString());
                    valueList.add(QuestionAboutData.COMPARE_WITH_LINE.toString());
                    valueList.add(QuestionAboutData.UNDERSTAND_WITH_BAR.toString());
                    valueList.add(QuestionAboutData.UNDERSTAND_WITH_LINE.toString());
                    valueList.add(QuestionAboutData.UNDERSTAND_WITH_SCATTER.toString());
                }
            }
            if (getSelectedList(checkingList).contains("Income") ||
                    getSelectedList(list).contains("Income") ||
                    getSelectedList(checkingList).contains("Population") ||
                    getSelectedList(list).contains("Population") ||
                    (getSelectedList(checkingList).contains("Income") && getSelectedList(checkingList).contains("Population")) ||
                    (getSelectedList(list).contains("Income") && getSelectedList(list).contains("Population"))) {
                valueList.clear();
                valueList.add(QuestionAboutData.ANALYZE_TREND_LINE.toString());
                valueList.add(QuestionAboutData.COMPARE_WITH_LINE.toString());
                valueList.add(QuestionAboutData.UNDERSTAND_WITH_LINE.toString());
            }
        });
    }
}
