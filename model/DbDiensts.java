package com.usterka.restapi.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbDiensts {
    private Connection connection;

    public DbDiensts(String filename) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
    }

    public void insert(Dienst dienst) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Diensts (number, date, hourBegin, minutesBegin, hourEnd, minutesEnd)" +
                "VALUES(?,?,?,?,?,?)");
        statement.setString(1, dienst.getNumber());
        statement.setString(2, convertToDate(dienst.getDate()) + "");
        statement.setString(3, dienst.getHourBegin() + "");
        statement.setString(4, dienst.getMinutesBegin() + "");
        statement.setString(5, dienst.getHourEnd() + "");
        statement.setString(6, dienst.getMinutesEnd() + ""); //why minutes are not formate into two digits '05'
        statement.executeUpdate();
        //dienst.setId(getLastInsertedId(statement));
    }

    public List<Dienst> getDiensts(String sortBy, boolean isAscending) throws SQLException {
        String direction = isAscending ? "ASC" : "DSC";
        PreparedStatement statement = connection.prepareStatement(
                "SELECT* from Diensts ORDER BY " + sortBy + " " + direction + ", ID DESC"
        );
        ResultSet result = statement.executeQuery();
        List<Dienst> diensts = new ArrayList<>();
        while (result.next()) {
            Dienst dienst = dienstFromResultSet(result);
            diensts.add(dienst);
        }
        return diensts;
    }

    private Dienst dienstFromResultSet(ResultSet result) throws SQLException {
        Dienst dienst = new Dienst();
        dienst.setId(result.getLong("id"));
        dienst.setNumber(result.getString("number"));
        dienst.setDate(LocalDate.parse(result.getString("date")));
        dienst.setHourBegin(Integer.parseInt(result.getString("hourBegin")));
        dienst.setMinutesBegin(Integer.parseInt(result.getString("minutesBegin")));
        dienst.setHourEnd(Integer.parseInt(result.getString("hourEnd")));
        dienst.setMinutesEnd(Integer.parseInt(result.getString("minutesEnd")));
        return dienst;
    }

    private Date convertToDate(LocalDate date) {
        return Date.valueOf(date);
    }

}
