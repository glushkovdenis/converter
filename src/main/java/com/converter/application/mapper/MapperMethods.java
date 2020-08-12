package com.converter.application.mapper;

import org.jsoup.nodes.Element;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MapperMethods {

    static CurrencyMapper fromXMLtoMapper(ArrayList<Element> children) {
        CurrencyMapper currencyMapper = new CurrencyMapper();
        for (Element j : children) {
            switch (j.tagName()) {
                case "NumCode":
                    currencyMapper.setNumCode(j.text());
                    break;
                case "CharCode":
                    currencyMapper.setCharCode(j.text());
                    break;
                case "Nominal":
                    currencyMapper.setNominal(Integer.parseInt(j.text()));
                    break;
                case "Name":
                    currencyMapper.setName(j.text());
                    break;
                case "Value":
                    currencyMapper.setValue(Float.parseFloat(j.text().replaceAll(",", ".")));
                    break;
            }
        }
        return currencyMapper;
    }

    static CurrencyMapper fromDBtoCurrencyMapper(ResultSet rs) throws SQLException {
        CurrencyMapper currencyMapper = new CurrencyMapper();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rsmd.getColumnCount();

        for (int i = 1; i < columnNumber + 1; i++) {
            switch (rsmd.getColumnName(i)) {
                case "numcode":
                    currencyMapper.setNumCode(rs.getString(i));
                    break;
                case "charcode":
                    currencyMapper.setCharCode(rs.getString(i));
                    break;
                case "nominal":
                    currencyMapper.setNominal(Integer.parseInt(rs.getString(i)));
                    break;
                case "name":
                    currencyMapper.setName(rs.getString(i));
                    break;
                case "value":
                    currencyMapper.setValue(Float.parseFloat(rs.getString(i).replaceAll(",", ".")));
                    break;
            }
        }
        return currencyMapper;
    }

    static HistoryTransactionMapper fromDBtoHistoryMapper(ResultSet rs) throws SQLException {
        HistoryTransactionMapper historyTransactionMapper = new HistoryTransactionMapper();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rsmd.getColumnCount();

        for (int i = 1; i < columnNumber + 1; i++) {
            switch (rsmd.getColumnName(i)) {
                case "source_valute":
                    historyTransactionMapper.setSourceValute(rs.getString(i));
                    break;
                case "target_valute":
                    historyTransactionMapper.setTargetValute(rs.getString(i));
                    break;
                case "original_amount":
                    historyTransactionMapper.setOriginalAmount(rs.getString(i));
                    break;
                case "received_amount":
                    historyTransactionMapper.setReceivedAmount(rs.getString(i));
                    break;
                case "date":
                    historyTransactionMapper.setDate(rs.getString(i).replaceAll(",", "."));
                    break;
            }
        }

        return historyTransactionMapper;
    }
}
