package com.converter.application.controller;

import com.converter.application.SQL.SQLQueryBuilder;
import com.converter.application.converter.Converter;
import com.converter.application.mapper.HistoryTransactionMapper;
import com.converter.application.mapper.MapperMethods;

import com.converter.application.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/convert")
    public String convert() {
        return "converter";
    }

    @GetMapping("/valute")
    public String valute(@RequestParam("firstValute") String firstValute,
                         @RequestParam("secondValute") String secondValute,
                         @RequestParam("amount") Double amount,
                         Model model) throws SQLException, IOException {

        if (null == amount) {
            amount = 0.0;
        }

        DateUtils.updateDataToCurrentDate(dataSource);

        if (firstValute.equals("")) {
            return "errorFirstValute";
        } else if (secondValute.equals("")) {
            return "errorSecondValute";
        }

        if (firstValute.equals("RUB")) {
            Converter.firstCurrencyIsRub(dataSource,secondValute, amount, model);

        } else if (secondValute.equals("RUB")) {
            Converter.secondCurrencyIsRub(dataSource, firstValute, amount, model);

        } else {
            Converter.foreignCurrencies(dataSource, firstValute, secondValute, amount, model);

        }

        return "result";
    }

    @GetMapping("/history")
    public String history(Model model) throws SQLException {

        ResultSet rs = dataSource
                .getConnection()
                .createStatement()
                .executeQuery(SQLQueryBuilder.getHistory());

        ArrayList<HistoryTransactionMapper> array = new ArrayList<>();
        while (rs.next()) {
            HistoryTransactionMapper htm = MapperMethods.fromDBtoHistoryMapper(rs);
            array.add(htm);
        }

        model.addAttribute("array", array);
        return "history";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/update")
    public String update() throws IOException, SQLException {

        return DateUtils.updateDataToCurrentDate(dataSource);

    }
}
