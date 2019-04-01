/*
 * ChainProof
 * Tsiounis Konstantinos 2630
 * Polyzos Alexios 2338
 * Copyright (c) 2019.
 */

package transformation_scripts;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CSVParser {

    private static ArrayList<String> filenames = new ArrayList<>(Arrays.asList(
            "resources/drought_affected_annual_number.csv",
            "resources/drought_deaths_annual_number.csv",
            "resources/earthquake_affected_annual_number.csv",
            "resources/earthquake_deaths_annual_number.csv",
            "resources/epidemic_affected_annual_number.csv",
            "resources/epidemic_deaths_annual_number.csv",
            "resources/extreme_temperature_affected_annual_number.csv",
            "resources/extreme_temperature_deaths_annual_number.csv",
            "resources/flood_affected_annual_number.csv",
            "resources/flood_deaths_annual_number.csv",
            "resources/income_per_person_gdppercapita_ppp_inflation_adjusted.csv",
            "resources/plane_crash_affected_annual_number.csv",
            "resources/plane_crash_deaths_annual_number.csv",
            "resources/population_total.csv",
            "resources/storm_affected_annual_number.csv",
            "resources/storm_deaths_annual_number.csv",
            "resources/tsunami_affected_annual_number.csv",
            "resources/tsunami_deaths_annual_number.csv"));

    private static ArrayList<String> countries = new ArrayList<>();
    private static ArrayList<String> years = new ArrayList<>();

    public static void main(String[] args) {

        for (String csvFile : filenames ) {
            String line;
            String csvSplitBy = ",";

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    line = replaceCommas(line);
                    System.out.println(line);
                    String[] country = line.split(csvSplitBy);
                    if (i == 0) {
                        for (int j = 1; j < country.length; j++) {
                            if (!years.contains(country[j])) {
                                years.add(country[j]);
                            }
                        }
                    } else {
                        if (country[0].contains("\"")) {
                            country[0] = (country[0] + country[1]).replace("\"","");
                        }
                        if (!countries.contains(country[0])) {
                            countries.add(country[0]);
                        }
                    }
                    i++;
                }
                Collections.sort(years);
                Collections.sort(countries);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File countriesFile = new File("resources/countries.txt");
        File yearsFile = new File("resources/years.txt");
        try {

            BufferedWriter bufferedWriterCountries = new BufferedWriter(new FileWriter(countriesFile));
            for (String country : countries) {
                bufferedWriterCountries.write(country + "\n");
            }

            BufferedWriter bufferedWriterYears = new BufferedWriter(new FileWriter(yearsFile));
            for (String year : years) {
                bufferedWriterYears.write(year + "\n");
            }

            bufferedWriterCountries.close();
            bufferedWriterYears.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create transformation file for droughts
        String droughtAffectedFile = filenames.get(0);
        String droughtDeathsFile = filenames.get(1);
        File droughtsFile = new File("resources/droughts.txt");
        createTransformFile(droughtDeathsFile, droughtAffectedFile, droughtsFile);

        // Create transformation file for earthquakes
        String earthquakesAffectedFile = filenames.get(2);
        String earthquakesDeathsFile = filenames.get(3);
        File earthquakesFile = new File("resources/earthquakes.txt");
        createTransformFile(earthquakesDeathsFile, earthquakesAffectedFile, earthquakesFile);

        // Create transformation file for extreme_temperatures
        String extremeTempAffectedFile = filenames.get(6);
        String extremeTempDeathsFile = filenames.get(7);
        File extremeTempFile = new File("resources/extreme_temperatures.txt");
        createTransformFile(extremeTempDeathsFile, extremeTempAffectedFile, extremeTempFile);

        // Create transformation file for floods
        String floodsAffectedFile = filenames.get(8);
        String floodsDeathsFile = filenames.get(9);
        File floodsFile = new File("resources/floods.txt");
        createTransformFile(floodsDeathsFile, floodsAffectedFile, floodsFile);

        // Create transformation file for plane_crashes
        String planeCrashAffectedFile = filenames.get(11);
        String planeCrashDeathsFile = filenames.get(12);
        File planeCrashFile = new File("resources/plane_crashes.txt");
        createTransformFile(planeCrashDeathsFile, planeCrashAffectedFile, planeCrashFile);

        // Create transformation file for storms
        String stormsAffectedFile = filenames.get(14);
        String stormsDeathsFile = filenames.get(15);
        File stormsFile = new File("resources/storms.txt");
        createTransformFile(stormsDeathsFile, stormsAffectedFile, stormsFile);

        // Create transformation file for tsunami
        String tsunamisAffectedFile = filenames.get(16);
        String tsunamisDeathsFile = filenames.get(17);
        File tsunamisFile = new File("resources/tsunamis.txt");
        createTransformFile(tsunamisDeathsFile, tsunamisAffectedFile, tsunamisFile);

        // Create transformation file for epidemics
        String epidemicsAffectedFile = filenames.get(4);
        String epidemicsDeathsFile = filenames.get(5);
        File epidemicsFile = new File("resources/epidemics.txt");
        createTransformFile(epidemicsDeathsFile, epidemicsAffectedFile, epidemicsFile);

        // Create transformation file for population_total
        String populationTotalsFile = filenames.get(13);
        File populationsFile = new File("resources/populations.txt");
        createTransformFileForIncomesOrPopulations(populationTotalsFile, populationsFile);

        // Create transformation file for income_per_person
        String incomePerPersonFile = filenames.get(10);
        File incomesFile = new File("resources/incomes.txt");
        createTransformFileForIncomesOrPopulations(incomePerPersonFile, incomesFile);

    }

    private static void createTransformFile(String deathsFile, String affectedFile, File exportFile) {
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(exportFile));

            BufferedReader affectedReader = new BufferedReader(new FileReader(affectedFile));
            BufferedReader deathsReader = new BufferedReader(new FileReader(deathsFile));

            String line;
            String deathsLine;
            ArrayList<String> disastersYears = new ArrayList<>();
            ArrayList<String> countryAffected = new ArrayList<>();
            ArrayList<String> countryDeaths = new ArrayList<>();
            ArrayList<TransformationFileEntry> fileEntries = new ArrayList<>();

            int i = 0;
            while (( line = affectedReader.readLine()) != null && ( deathsLine = deathsReader.readLine()) != null) {
                line = replaceCommas(line);
                deathsLine = replaceCommas(deathsLine);
                String[] countryLineSplitted = line.split(",");
                String[] deaths = deathsLine.split(",");
                countryLineSplitted = removeCountryFromFirstPos(countryLineSplitted);
                deaths = removeCountryFromFirstPos(deaths);
                if (i == 0) {
                    disastersYears.clear();
                    disastersYears.addAll(Arrays.asList(countryLineSplitted).subList(1, countryLineSplitted.length));
                } else {
                    countryAffected.clear();
                    countryAffected.addAll(Arrays.asList(countryLineSplitted).subList(1, countryLineSplitted.length));
                    System.out.println(countryAffected);
                    countryDeaths.clear();
                    countryDeaths.addAll(Arrays.asList(deaths).subList(1, deaths.length));
                    System.out.println(countryDeaths);
                    for (int j=0; j<countryAffected.size(); j++) {
                        TransformationFileEntry transformationFileEntry = new TransformationFileEntry();
                        transformationFileEntry.setCountryId(String.valueOf(countries.indexOf(countryLineSplitted[0]) + 1));
                        transformationFileEntry.setYearId(String.valueOf(years.indexOf(disastersYears.get(j)) + 1));
                        transformationFileEntry.setAffected(countryAffected.get(j));
                        transformationFileEntry.setDeaths(countryDeaths.get(j));
                        fileEntries.add(transformationFileEntry);
                    }
                }
                i++;
            }

            for (TransformationFileEntry entry : fileEntries) {
                bufferedWriter.write(entry.toString() + "\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createTransformFileForIncomesOrPopulations(String initialFile, File exportFile) {
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(exportFile));
            BufferedReader initialFileReader = new BufferedReader(new FileReader(initialFile));

            String line;
            ArrayList<String> incomeOrPopulationYears = new ArrayList<>();
            ArrayList<String> countryIncomeOrPopulation = new ArrayList<>();
            ArrayList<TransformationIncomeAndPopulationFileEntry> fileEntries = new ArrayList<>();

            int i = 0;
            while (( line = initialFileReader.readLine()) != null) {
                line = replaceCommas(line);
                String[] countryLineSplitted = line.split(",");
                countryLineSplitted = removeCountryFromFirstPos(countryLineSplitted);
                if (i == 0) {
                    incomeOrPopulationYears.clear();
                    incomeOrPopulationYears.addAll(Arrays.asList(countryLineSplitted).subList(1, countryLineSplitted.length));
                } else {
                    countryIncomeOrPopulation.clear();
                    countryIncomeOrPopulation.addAll(Arrays.asList(countryLineSplitted).subList(1, countryLineSplitted.length));
                    System.out.println(countryIncomeOrPopulation);
                    for (int j=0; j<countryIncomeOrPopulation.size(); j++) {
                        TransformationIncomeAndPopulationFileEntry transformationFileEntry = new TransformationIncomeAndPopulationFileEntry();
                        transformationFileEntry.setCountryId(String.valueOf(countries.indexOf(countryLineSplitted[0]) + 1));
                        transformationFileEntry.setYearId(String.valueOf(years.indexOf(incomeOrPopulationYears.get(j)) + 1));
                        transformationFileEntry.setValue(countryIncomeOrPopulation.get(j));
                        fileEntries.add(transformationFileEntry);
                    }
                }
                i++;
            }

            for (TransformationIncomeAndPopulationFileEntry entry : fileEntries) {
                bufferedWriter.write(entry.toString() + "\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] removeCountryFromFirstPos(String[] countryLineSplitted) {
        if (countryLineSplitted[1].contains("\"")) {
            countryLineSplitted[0] = (countryLineSplitted[0] + countryLineSplitted[1]).replace("\"","");
            System.arraycopy(countryLineSplitted, 2, countryLineSplitted, 1, countryLineSplitted.length - 1 - 1);
            countryLineSplitted = Arrays.copyOfRange(countryLineSplitted, 0, countryLineSplitted.length - 1);
        }
        return countryLineSplitted;
    }

    private static String replaceCommas(String line) {
        while (line.contains(",,")) {
            line = line.replace(",,", ",0,");
        }
        if (line.charAt(line.length()-1) == ',') line = line + "0";
        return line;
    }

}
