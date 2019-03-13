package controllers;

import com.sun.tools.javac.util.List;
import dao.CountriesDAO;
import dao.YearsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

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
    private ChoiceBox graphTypeChoiceBox;

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

        ObservableList<String> graphTypes = FXCollections.observableArrayList();
        graphTypes.add("Timeline / Trendline");
        graphTypes.add("Scatter Plot");
        graphTypes.add("Bar Chart");

        graphTypeChoiceBox.setItems(graphTypes);
    }

    @FXML
    void onRunClicked() {
        System.out.println("Run clicked!");

        System.out.println("Year selected: " + yearsChoiceBox.getSelectionModel().getSelectedItem());
        System.out.println("Country selected: " + countriesChoiceBox.getSelectionModel().getSelectedItem());

    }

}
