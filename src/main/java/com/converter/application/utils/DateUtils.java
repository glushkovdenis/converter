package com.converter.application.utils;

import com.converter.application.SQL.SQLQueryBuilder;
import com.converter.application.mapper.CurrencyMapper;
import com.converter.application.mapper.MapperMethods;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DateUtils {
    public static String updateDataToCurrentDate(DataSource dataSource) throws SQLException, IOException {
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery(SQLQueryBuilder.getDate());
        boolean equals = DateUtils.compareDates(rs);

        if (equals) {
            System.out.println("Всё уже обновлено");
            return "alreadyUpdate";
        } else {
            System.out.println("Данные устарели и будут обновлены");
            String URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Document doc = Jsoup.connect(URL).get();

            dataSource.getConnection()
                    .createStatement()
                    .execute(SQLQueryBuilder.deleteRecords("valutes"));

            ArrayList<Element> array = doc.getElementsByTag("Valute");
            for (Element i : array) {
                ArrayList<Element> children = i.children();

                CurrencyMapper currencyMapper = MapperMethods.fromXMLtoMapper(children);

                currencyMapper.setId(i.attr("ID"));

                Date date = Date.valueOf(LocalDate.now());

                currencyMapper.setDate(date);

                if (currencyMapper.getNominal() != 1) {
                    currencyMapper.setValue(
                            CustomRounder.rounder(currencyMapper.getValue() / currencyMapper.getNominal()));

                    currencyMapper.setNominal(1);
                } else {
                    currencyMapper.setValue(CustomRounder.rounder(currencyMapper.getValue()));
                }

                dataSource.getConnection()
                        .createStatement()
                        .execute(SQLQueryBuilder.insertData(currencyMapper));
            }
        }
        return "update";
    }

    private static boolean compareDates(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rsmd.getColumnCount();
        Date serverDate = null;

        while (rs.next()) {
            for (int i = 1; i < columnNumber + 1; i++) {
                serverDate = rs.getDate(i);
                if (null == serverDate) {
                    return false;
                }
            }
        }
        Date actualDate = Date.valueOf(LocalDate.now());

        assert serverDate != null;
        return serverDate.equals(actualDate);
    }
}
