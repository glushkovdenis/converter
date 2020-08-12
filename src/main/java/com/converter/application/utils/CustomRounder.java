package com.converter.application.utils;

import java.text.DecimalFormat;

public interface CustomRounder {

    String ROUND = "0.0000";

    static double rounder(double number) {
        return Double.parseDouble(
                new DecimalFormat(ROUND)
                        .format(number)
                        .replaceAll(",", "."));
    }
}
