package com.example.coronatest;

import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

public class SortFunctions extends MainActivity{
    public static Comparator<Map<String, String>> totalCasesComparator = new Comparator<Map<String, String>>() {
        int totalCases1, totalCases2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                totalCases1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("TotalCases")).intValue();
                totalCases2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("TotalCases")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(totalCasesDescending) return totalCases2 - totalCases1;
            else return totalCases1 - totalCases2;
        }
    };

    public static Comparator<Map<String, String>> newCasesComparator = new Comparator<Map<String, String>>() {
        int newCases1, newCases2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                newCases1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("NewCases")).intValue();
                newCases2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("NewCases")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(newCasesDescending) return newCases2 - newCases1;
            else return newCases1 - newCases2;
        }
    };

    public static Comparator<Map<String, String>> totalDeathsComparator = new Comparator<Map<String, String>>() {
        int totalDeaths1, totalDeaths2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                totalDeaths1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("TotalDeaths")).intValue();
                totalDeaths2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("TotalDeaths")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(totalDeathsDescending) return totalDeaths2 - totalDeaths1;
            else return totalDeaths1 - totalDeaths2;
        }
    };

    public static Comparator<Map<String, String>> newDeathsComparator = new Comparator<Map<String, String>>() {
        int newDeaths1, newDeaths2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                newDeaths1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("NewDeaths")).intValue();
                newDeaths2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("NewDeaths")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(newDeathsDescending) return newDeaths2 - newDeaths1;
            else return newDeaths1 - newDeaths2;
        }
    };

    public static Comparator<Map<String, String>> totalRecoveredComparator = new Comparator<Map<String, String>>() {
        int totalRecovered1, totalRecovered2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                totalRecovered1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("TotalRecovered")).intValue();
                totalRecovered2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("TotalRecovered")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(totalRecoveredDescending) return totalRecovered2 - totalRecovered1;
            else return totalRecovered1 - totalRecovered2;
        }
    };

    public static Comparator<Map<String, String>> activeCasesComparator = new Comparator<Map<String, String>>() {
        int activeCases1, activeCases2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                activeCases1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("ActiveCases")).intValue();
                activeCases2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("ActiveCases")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(activeCasesDescending) return activeCases2 - activeCases1;
            else return activeCases1 - activeCases2;
        }
    };

    public static Comparator<Map<String, String>> seriousCriticalComparator = new Comparator<Map<String, String>>() {
        int seriousCritical1, seriousCritical2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                seriousCritical1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("SeriousCritical")).intValue();
                seriousCritical2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("SeriousCritical")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(seriousCriticalDescending) return seriousCritical2 - seriousCritical1;
            else return seriousCritical1 - seriousCritical2;
        }
    };

    public static Comparator<Map<String, String>> totCasesPerMillionComparator = new Comparator<Map<String, String>>() {
        int totCasesPerMillion1, totCasesPerMillion2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                totCasesPerMillion1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("TotCasesPerMillion")).intValue();
                totCasesPerMillion2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("TotCasesPerMillion")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(totCasesPerMillionDescending) return totCasesPerMillion2 - totCasesPerMillion1;
            else return totCasesPerMillion1 - totCasesPerMillion2;
        }
    };

    public static Comparator<Map<String, String>> totDeathsPerMillionComparator = new Comparator<Map<String, String>>() {
        int totDeathsPerMillion1, totDeathsPerMillion2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                totDeathsPerMillion1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("TotDeathsPerMillion")).intValue();
                totDeathsPerMillion2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("TotDeathsPerMillion")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(totDeathsPerMillionDescending) return totDeathsPerMillion2 - totDeathsPerMillion1;
            else return totDeathsPerMillion1 - totDeathsPerMillion2;
        }
    };

    public static Comparator<Map<String, String>> totTestsComparator = new Comparator<Map<String, String>>() {
        int totTests1, totTests2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                totTests1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("TotTests")).intValue();
                totTests2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("TotTests")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(totTestsDescending) return totTests2 - totTests1;
            else return totTests1 - totTests2;
        }
    };

    public static Comparator<Map<String, String>> totTestsPerMillionComparator = new Comparator<Map<String, String>>() {
        int totTestsPerMillion1, totTestsPerMillion2;
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            try {
                totTestsPerMillion1 = NumberFormat.getNumberInstance(Locale.UK).parse(m1.get("TotTestsPerMillion")).intValue();
                totTestsPerMillion2 = NumberFormat.getNumberInstance(Locale.UK).parse(m2.get("TotTestsPerMillion")).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(totTestsPerMillionDescending) return totTestsPerMillion2 - totTestsPerMillion1;
            else return totTestsPerMillion1 - totTestsPerMillion2;
        }
    };
}



