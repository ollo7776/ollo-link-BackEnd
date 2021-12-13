package com.usterka.restapi.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Db {
    private Connection connection;

    public Db(String filename) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
    }

    public void insert(Repair repair) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Repair (registrationNo, expire, startDate, status, description, errorCode, repairDescription, spitzName)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, repair.getRegistrationNo());
        statement.setString(2, convertToDate(repair.getExpire()) + "");
        statement.setString(3, convertToDate(repair.getStartDate()) + "");
        statement.setInt(4, repair.getStatus().ordinal());
        statement.setString(5, repair.getDescription());
        statement.setString(6, repair.getErrorCode());
        statement.setString(7, repair.getRepairDescription());
        statement.setString(8, repair.getSpitzName());
        statement.executeUpdate();
        repair.setId(getLastInsertId(statement));
        insertPictures(repair);
    }

    public void insertContinuation(long repairId, Continuation continuation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO RepairContinuation " +
                        "(spitzName, clickDate, repairId) " +
                        "VALUES (?, ?, ?)"
        );
        statement.setString(1, continuation.getSpitzName());
        statement.setString(2, convertToDate(continuation.getClickDate()) + "");
        statement.setLong(3, repairId);
        statement.executeUpdate();
    }


    // GET REPAIR WITH ONE ID
    public Repair getRepair(long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT *\n" +
                        "FROM Repair\n" +
                        "WHERE id = ?\n"
        );
        statement.setLong(1, id);
        ResultSet result = statement.executeQuery();

        if (result.next()) {  // nie while tylko if bo tylko jeden
            Repair repair = repairFromResultSet(result);
            return repair;
        }
        return null;
    }

    public boolean removeRepair(long id) throws SQLException {
        removeRepairPictures(id);
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM Repair WHERE id = ?"
        );
        statement.setLong(1, id);
        return statement.executeUpdate() > 0;
    }

    public List<Repair> getRepairs(String sortBy, boolean isAscending, String statusNo, String registrationNo) throws SQLException {
        return getRepairs(LocalDate.of(1900, 1, 1), LocalDate.MAX, sortBy, isAscending, statusNo, registrationNo);
    }

    public List<Repair> getRepairs(LocalDate date, String sortBy, boolean isAscending, String statusNo, String registrationNo) throws SQLException {
        return getRepairs(date, date, sortBy, isAscending, statusNo, registrationNo);
    }

    public List<Repair> getRepairs(LocalDate fromDate, LocalDate toDate, String sortBy, boolean isAscending, String statusNo, String registrationNo) throws SQLException {
        String direction = isAscending ? "ASC" : "DESC";
        String sign = "==";
        if (statusNo.equals("all")) {
            sign = "< ";
            statusNo = "3";
        }
        // myCode 1-12-2020
        String DBregistrationNo = "";
        if (registrationNo.equals("all")) {
        } else {
            DBregistrationNo = " AND registrationNo == " + registrationNo;
        }
// end of myCode 1-12-2020
        PreparedStatement statement = connection.prepareStatement(
                "SELECT *\n" +
                        "FROM Repair\n" +
                        "WHERE Expire >= ?\n" +
                        "AND Expire <= ?\n" +
                        "AND status " + sign + statusNo +
                        DBregistrationNo +
                        //DBregistrationNo + // my code 1-12-2020
                        " ORDER BY " + sortBy + " " + direction + ", ID DESC"
                //"ORDER BY Id DESC\n"
        );
        statement.setString(1, convertToDate(fromDate) + "");
        statement.setString(2, convertToDate(toDate) + "");

        ResultSet result = statement.executeQuery();
        List<Repair> repairs = new ArrayList<>();
        while (result.next()) {
            Repair repair = repairFromResultSet(result);
            repairs.add(repair);
        }
        return repairs;
    }

    private void removeRepairPictures(long repairId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM RepairPicture WHERE repairId = ?"
        );
        statement.setLong(1, repairId);
        statement.executeUpdate();
    }

    private Repair repairFromResultSet(ResultSet result) throws SQLException {
        Repair repair = new Repair();
        repair.setId(result.getLong("id"));
        repair.setRegistrationNo(result.getString("registrationNo"));
        repair.setExpire(LocalDate.parse(result.getString("expire")));
        repair.setStartDate(LocalDate.parse(result.getString("startDate")));
        repair.setStatus(Status.values()[result.getInt("status")]);
        repair.setDescription(result.getString("description"));
        repair.setErrorCode(result.getString("errorCode"));
        repair.setRepairDescription(result.getString("repairDescription"));
        repair.setSpitzName(result.getString("spitzName"));
        getPictures(repair);
        getContinuations(repair);
        return repair;
    }

    private void getContinuations(Repair repair) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id, spitzName, clickDate " +
                        "FROM RepairContinuation " +
                        "WHERE repairId = ? " +
                        "ORDER BY clickDate"
        );
        statement.setLong(1, repair.getId());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Continuation continuation = new Continuation();
            continuation.setId(result.getLong("id"));
            continuation.setSpitzName(result.getString("spitzName"));
            continuation.setClickDate(LocalDate.parse(result.getString("clickDate")));
            repair.getContinuations().add(continuation);
        }
    }

    private void getPictures(Repair repair) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT PictureNo " +
                        "FROM RepairPicture " +
                        "WHERE repairId = ?"
        );

        statement.setLong(1, repair.getId());
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            int number = result.getInt("PictureNo");
            Picture picture = Picture.values()[number];
            repair.getPictures().add(picture);
        }
    }

    private void insertPictures(Repair repair) throws SQLException {
        for (Picture picture : repair.getPictures()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO RepairPicture(RepairId, PictureNo) VALUES (?,?)");
            statement.setLong(1, repair.getId());
            statement.setLong(2, picture.ordinal());
            statement.executeUpdate();
        }
    }

    private long getLastInsertId(Statement statement) throws SQLException {
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) {
            return result.getLong(1);
        }
        throw new IllegalArgumentException("statement");
    }

    private Date convertToDate(LocalDate date) {
        return Date.valueOf(date);
    }
}


//public void removeRepair(Long id) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement(
//                       "DELETE FROM RepairPicture WHERE repairId = ?"
//                        //"DELETE FROM Repair WHERE id = ?"
//        );
//        statement.setLong(1, id);
//        statement.executeUpdate();
//        removeRepair2(id);
//    }
//    public void removeRepair2(long id) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement(
//               // "DELETE FROM RepairPicture WHERE repairId = ?"
//                "DELETE FROM Repair WHERE id = ?"
//        );
//        statement.setLong(1, id);
//        statement.executeUpdate();
//    }