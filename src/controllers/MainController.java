package controllers;

import dao.CountriesDAO;
import dao.YearsDAO;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    void onRunClicked() {
        System.out.println("Run clicked!");
        YearsDAO.getAllYears(true);
        CountriesDAO.getAllCountries(true);
    }

}
