package models;

public enum QuestionAboutData {

    COMPOSITION("Do you want to see how many deaths happen the year you selected for this country?"),
    COMPARE_WITH_BAR("Do you want to compare these metrics with a bar chart?"),
    COMPARE_WITH_LINE("Do you want to compare these metrics with a line chart?"),
    COMPARE_WITH_PIE("Do you want to compare these metrics with a pie chart?"),
    UNDERSTAND_WITH_SCATTER("Do you want to understand the distribution of your data with a scatter plot?"),
    UNDERSTAND_WITH_LINE("Do you want to understand the distribution of your data with a line chart?"),
    UNDERSTAND_WITH_BAR("Do you want to understand the distribution of your data with a bar chart?"),
    ANALYZE_TREND_LINE("Are you interested in analyzing trends in your data set?");

    private String question;

    QuestionAboutData(String question) {this.question = question;}

    @Override
    public String toString() {
        return this.question;
    }
}
