package com.example.coronatest;

import java.util.Comparator;

public class Corona {
    String country;
    String totalCases, newCases, totalDeaths, newDeaths, totalRecovered, activeCases,
            seriousCritical, totCasesPerMillion, totDeathsPerMillion, totTests, totTestsPerMillion;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
    }

    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(String activeCases) {
        this.activeCases = activeCases;
    }

    public String getSeriousCritical() {
        return seriousCritical;
    }

    public void setSeriousCritical(String seriousCritical) {
        this.seriousCritical = seriousCritical;
    }

    public String getTotCasesPerMillion() {
        return totCasesPerMillion;
    }

    public void setTotCasesPerMillion(String totCasesPerMillion) {
        this.totCasesPerMillion = totCasesPerMillion;
    }

    public String getTotDeathsPerMillion() {
        return totDeathsPerMillion;
    }

    public void setTotDeathsPerMillion(String totDeathsPerMillion) {
        this.totDeathsPerMillion = totDeathsPerMillion;
    }

    public String getTotTests() {
        return totTests;
    }

    public void setTotTests(String totTests) {
        this.totTests = totTests;
    }

    public String getTotTestsPerMillion() {
        return totTestsPerMillion;
    }

    public void setTotTestsPerMillion(String totTestsPerMillion) {
        this.totTestsPerMillion = totTestsPerMillion;
    }

    public Corona(String country, String totalCases, String newCases, String totalDeaths, String newDeaths,
                  String totalRecovered, String activeCases, String seriousCritical, String totCasesPerMillion,
                  String totDeathsPerMillion, String totTests, String totTestsPerMillion) {
        this.country = country;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalRecovered = totalRecovered;
        this.activeCases = activeCases;
        this.seriousCritical = seriousCritical;
        this.totCasesPerMillion = totCasesPerMillion;
        this.totDeathsPerMillion = totDeathsPerMillion;
        this.totTests = totTests;
        this.totTestsPerMillion = totTestsPerMillion;
    }

    public static Comparator<Corona> totalCasesComparator = new Comparator<Corona>() {
        @Override
        public int compare(Corona o1, Corona o2) {
            int totalCases1 = Integer.parseInt(o1.getTotalCases());
            int totalCases2 = Integer.parseInt(o2.getTotalCases());
            return totalCases1 - totalCases2;
        }
    };
}
