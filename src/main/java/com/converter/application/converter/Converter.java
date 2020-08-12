package com.converter.application.converter;

import com.converter.application.SQL.SQLQueryBuilder;
import com.converter.application.mapper.CurrencyMapper;
import com.converter.application.mapper.MapperMethods;
import com.converter.application.utils.CustomRounder;
import org.springframework.ui.Model;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Converter {

    static void firstCurrencyIsRub(DataSource dataSource, String secondValute,
                                   Double amount, Model model) throws SQLException {
        CurrencyMapper notRubCurrency = new CurrencyMapper();
        ResultSet rs = dataSource
                .getConnection()
                .createStatement()
                .executeQuery(SQLQueryBuilder.getData(secondValute));

        while (rs.next()) {
            notRubCurrency = MapperMethods.fromDBtoCurrencyMapper(rs);
        }

        double result = CustomRounder.rounder(amount * (1 / notRubCurrency.getValue()));

        model.addAttribute("result", result + " " + notRubCurrency.getCharCode());
        dataSource
                .getConnection()
                .createStatement()
                .execute(SQLQueryBuilder
                        .addHistoryRecord(
                                "RUB",
                                notRubCurrency.getCharCode(),
                                amount,
                                CustomRounder.rounder(result)));
    }

    static void secondCurrencyIsRub(DataSource dataSource, String firstValute,
                                   Double amount, Model model) throws SQLException {
        CurrencyMapper notRubCurrency = new CurrencyMapper();
        ResultSet rs = dataSource
                .getConnection()
                .createStatement()
                .executeQuery(SQLQueryBuilder.getData(firstValute));

        while (rs.next()) {
            notRubCurrency = MapperMethods.fromDBtoCurrencyMapper(rs);
        }
        double result = CustomRounder.rounder(
                amount * (notRubCurrency.getValue() / 1));

        model.addAttribute("result", result + " " + notRubCurrency.getCharCode());
        dataSource
                .getConnection()
                .createStatement()
                .execute(SQLQueryBuilder
                        .addHistoryRecord(
                                "RUB",
                                notRubCurrency.getCharCode(),
                                amount,
                                CustomRounder.rounder(result)));
    }

    static void foreignCurrencies(DataSource dataSource, String firstValute, String secondValute,
                                    Double amount, Model model) throws SQLException {
        CurrencyMapper firstValuteCM = null;
        CurrencyMapper secondValuteCM = null;

        ResultSet rs = dataSource
                .getConnection()
                .createStatement()
                .executeQuery(SQLQueryBuilder
                        .getData(firstValute, secondValute));

        while (rs.next()) {
            CurrencyMapper cm = MapperMethods.fromDBtoCurrencyMapper(rs);

            if (cm.getCharCode().equals(firstValute)) {
                firstValuteCM = cm;
            } else {
                secondValuteCM = cm;
            }
        }

        double result = CustomRounder.rounder(
                amount * (firstValuteCM.getValue() / secondValuteCM.getValue()));

        model.addAttribute("result", result + " " + secondValuteCM.getCharCode());
        dataSource
                .getConnection()
                .createStatement()
                .execute(SQLQueryBuilder
                        .addHistoryRecord(
                                firstValuteCM.getCharCode(),
                                secondValuteCM.getCharCode(),
                                amount,
                                CustomRounder.rounder(result)));
    }
}
