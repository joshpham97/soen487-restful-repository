package database.dao;

import database.db.DBConnection;
import repository.core.pojo.Log;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogDAO {
    public static boolean addLog(LocalDateTime time, String change, String recordKey) throws SQLException
    {
        boolean success = false;
        Connection conn = DBConnection.getConnection();

        String query = "INSERT INTO Log (logged_time, typeOfChange, recordKey)" + " values (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setTimestamp(1, Timestamp.valueOf(time));
        stmt.setString(2, change);
        stmt.setString(3, recordKey);
        int row = stmt.executeUpdate();

        if (row > 0)
            success = true;

        return success;
    }

    public static ArrayList<Log> getLog() throws SQLException{
        ArrayList<Log> logs = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM Log ORDER BY logged_time DESC";
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next())
            logs.add(mapResultSetToLog(rs));

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

    public static ArrayList<Log> getLog(LocalDateTime fromDate, LocalDateTime toDate) throws SQLException{
        ArrayList<Log> logs = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt;

        if(fromDate != null && toDate == null)
        {
            String sql = "SELECT * FROM Log WHERE logged_time >= ? ORDER BY logged_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(fromDate));
        }
        else if(fromDate == null && toDate != null)
        {
            String sql = "SELECT * FROM Log WHERE logged_time <= ? ORDER BY logged_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(toDate));
        }
        else {
            String sql = "SELECT * FROM Log WHERE logged_time between ? AND ? ORDER BY logged_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(fromDate));
            stmt.setTimestamp(2, Timestamp.valueOf(toDate));
        }

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            logs.add(mapResultSetToLog(rs));

        return logs;
    }

    public static ArrayList<Log> getLog(LocalDateTime fromDate, LocalDateTime toDate, String typeOfChange) throws SQLException{
        ArrayList<Log> logs = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt;
        if(fromDate != null && toDate != null && !typeOfChange.equals(""))
        {
            String sql = "SELECT * FROM Log WHERE logged_time between ? AND ? AND typeOfChange = ? ORDER BY logged_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(fromDate));
            stmt.setTimestamp(2, Timestamp.valueOf(toDate));
            stmt.setString(3, typeOfChange);
        }
        else if(fromDate == null && (toDate != null && !typeOfChange.equals("")))
        {
            String sql = "SELECT * FROM Log WHERE logged_time <= ? AND typeOfChange = ? ORDER BY logged_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(toDate));
            stmt.setString(2, typeOfChange);
        }
        else /**if(toDate == null && (fromDate != null && !typeOfChange.equals("")))*/
        {
            String sql = "SELECT * FROM Log WHERE logged_time >= ? AND typeOfChange = ? ORDER BY logged_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(fromDate));
            stmt.setString(2, typeOfChange);
        }

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            logs.add(mapResultSetToLog(rs));

        return logs;
    }

    public static ArrayList<Log> getLog(String typeOfChange) throws SQLException{
        ArrayList<Log> logs = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM Log WHERE typeOfChange = ? ORDER BY logged_time DESC";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, typeOfChange);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            logs.add(mapResultSetToLog(rs));

        return logs;
    }
}
