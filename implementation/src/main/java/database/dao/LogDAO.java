package database.dao;

import database.db.DBConnection;
import repository.core.Album;
import repository.core.Artist;
import repository.core.Log;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogDAO {
    public static boolean addLog(LocalDateTime time, String change, String recordKey)
    {
        boolean success = false;
        try {
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO Log (logged_time, typeOfChange, recordKey)" + " values (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setTimestamp(1, Timestamp.valueOf(time));
            stmt.setString(2, change);
            stmt.setString(3, recordKey);
            int row = stmt.executeUpdate();

            if (row > 0)
                success = true;

        } catch (Exception e) {
            System.err.println("There was an error adding log in database.");
            System.err.println(e.getMessage());
        }
        return success;
    }

    public static ArrayList<Log> getLog(){
        ArrayList<Log> logs = new ArrayList<>();
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Log";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
                logs.add(mapResultSetToLog(rs));
        } catch (Exception e) {
            System.err.println("There was an error adding log in database.");
            System.err.println(e.getMessage());
        }
        return logs;
    }

    private static Log mapResultSetToLog(ResultSet rs) throws SQLException {
        Log log = new Log();
        String str = rs.getString("typeOfChange");
        if(str.equals("ADD"))
        {
            log.setChange(Log.ChangeType.ADD);
        }
        else if(str.equals("UPDATE"))
        {
            log.setChange(Log.ChangeType.UPDATE);
        }
        else
        {
            log.setChange(Log.ChangeType.DELETE);
        }

        log.setDate(rs.getTimestamp("logged_time").toLocalDateTime());
        //log.setChange(rs.getString("typeOfChange"));
        log.setRecordKey(rs.getString("recordKey"));

        return log;
    }

    public static ArrayList<Log> getLog(LocalDateTime fromDate, LocalDateTime toDate){
        ArrayList<Log> logs = new ArrayList<>();
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt;
            if(fromDate != null && toDate == null)
            {
                String sql = "SELECT * FROM Log WHERE logged_time >= ?";
                stmt = conn.prepareStatement(sql);
                stmt.setTimestamp(1, Timestamp.valueOf(fromDate));
            }
            else if(fromDate == null && toDate != null)
            {
                String sql = "SELECT * FROM Log WHERE logged_time <= ?";
                stmt = conn.prepareStatement(sql);
                stmt.setTimestamp(1, Timestamp.valueOf(toDate));
            }
            else
            {
                String sql = "SELECT * FROM Log WHERE logged_time between ? AND ?";
                stmt = conn.prepareStatement(sql);
                stmt.setTimestamp(1, Timestamp.valueOf(fromDate));
                stmt.setTimestamp(2, Timestamp.valueOf(toDate));
            }

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                logs.add(mapResultSetToLog(rs));
        } catch (Exception e) {
            System.err.println("There was an error retrieving log in database.");
            System.err.println(e.getMessage());
        }
        return logs;
    }

    public static ArrayList<Log> getLog(LocalDateTime fromDate, LocalDateTime toDate, String typeOfChange){
        ArrayList<Log> logs = new ArrayList<>();
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt;
            if(fromDate != null && toDate != null && !typeOfChange.equals(""))
            {
                String sql = "SELECT * FROM Log WHERE logged_time between ? AND ? AND typeOfChange = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setTimestamp(1, Timestamp.valueOf(fromDate));
                stmt.setTimestamp(2, Timestamp.valueOf(toDate));
                stmt.setString(3, typeOfChange);
            }
            else if(fromDate == null && (toDate != null && !typeOfChange.equals("")))
            {
                String sql = "SELECT * FROM Log WHERE logged_time <= ? AND typeOfChange = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setTimestamp(1, Timestamp.valueOf(toDate));
                stmt.setString(2, typeOfChange);
            }
            else /**if(toDate == null && (fromDate != null && !typeOfChange.equals("")))*/
            {
                String sql = "SELECT * FROM Log WHERE logged_time >= ? AND typeOfChange = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setTimestamp(1, Timestamp.valueOf(fromDate));
                stmt.setString(2, typeOfChange);
            }

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                logs.add(mapResultSetToLog(rs));
        } catch (Exception e) {
            System.err.println("There was an error retrieving log in database.");
            System.err.println(e.getMessage());
        }
        return logs;
    }

    public static ArrayList<Log> getLog(String typeOfChange){
        ArrayList<Log> logs = new ArrayList<>();
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Log WHERE typeOfChange = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, typeOfChange);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                logs.add(mapResultSetToLog(rs));
        } catch (Exception e) {
            System.err.println("There was an error retrieving log in database.");
            System.err.println(e.getMessage());
        }
        return logs;
    }
}
