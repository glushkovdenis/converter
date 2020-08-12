package com.converter.application.SQL;

import com.converter.application.mapper.CurrencyMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface SQLQueryBuilder {
    static String insertData(CurrencyMapper currencyMapper) {
        return "INSERT INTO valutes (id, numcode, charcode, nominal, name, value, date) VALUES "
                + "(" +
                "'" + currencyMapper.getId() + "', " +
                "'" + currencyMapper.getNumCode() + "', " +
                "'" + currencyMapper.getCharCode() + "', " +
                "'" + currencyMapper.getNominal() + "', " +
                "'" + currencyMapper.getName() + "', " +
                "'" + currencyMapper.getValue() + "', " +
                "'" + currencyMapper.getDate() + "'" +
                ");";
    }

    static String updateData() {
        return "";
    }

    static String getHistory() {
        return "SELECT * FROM history;";
    }

    static String getData(String valute) {
        return "SELECT charcode, value FROM valutes WHERE charcode='" + valute + "';";
    }

    static String getData(String firstValute, String secondValute) {
        return "SELECT charcode, value FROM valutes WHERE charcode='" + firstValute +
                "' OR charcode='" + secondValute + "';";
    }

    static String addHistoryRecord(String sourceValute, String targetValute, double originalAmount, double receivedAmount) {
        return "INSERT INTO history (source_valute, target_valute, original_amount, received_amount, date)" +
                "VALUES ('" + sourceValute + "', '" + targetValute +
                "', " + originalAmount + ", " + receivedAmount + ", '" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "');";
    }

    static String deleteRecords(String tableName) {
        return "DELETE FROM " + tableName + ";";
    }

    static String getDate() {
        return "SELECT MIN(date) FROM valutes;";
    }
}

