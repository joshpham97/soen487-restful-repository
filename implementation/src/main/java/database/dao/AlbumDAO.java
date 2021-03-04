package database.dao;

import database.db.DBConnection;
import repository.core.Album;
import repository.core.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumDAO {
    public static Album getAlbumDatabase(String isrc){
        Album album = null;
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM Albums WHERE isrc =?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, isrc);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                album = mapResultSetToAlbum(rs);
        } catch (Exception e) {
            System.err.println("There was an error retrieving specific album from database.");
            System.err.println(e.getMessage());
        }
        return album;
    }
    private static Album mapResultSetToAlbum(ResultSet rs) throws SQLException {
        Album album = new Album();
        Artist artist = new Artist();
        String isrc = rs.getString("isrc");

        if (isrc.equals("")) { // Means that db field was null
            isrc = null;
        }

        album.setIsrc(rs.getString("isrc"));
        album.setTitle(rs.getString("title"));
        album.setReleaseYear(rs.getInt("releasedYear"));
        album.setContentDesc(rs.getString("contentDesc"));
        artist.setFirstname(rs.getString("artistFirstName"));
        artist.setLastname(rs.getString("artistLastName"));
        album.setArtist(artist);

        return album;
    }
    public static ArrayList<Album> getAllAlbums() {
        // Get query result
        String sql = "SELECT * FROM Albums ORDER BY title ASC, isrc ASC";

        return getAllAlbumsHelper(sql);
    }

    private static ArrayList<Album> getAllAlbumsHelper(String sql) {
        ArrayList<Album> albums = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // For each element of query result
            while(rs.next())
                albums.add(mapResultSetToAlbum(rs));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return albums;
    }

    public static boolean insertAlbum(String isrc, String title, String contentDesc, int releasedYear, String firstName, String lastName) {
        boolean success = false;
        try {
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO Albums (isrc, title, contentDesc, releasedYear, artistFirstName, artistLastName)" + " values (?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, isrc);
            stmt.setString(2, title);
            stmt.setString(3, contentDesc);
            stmt.setInt(4, releasedYear);
            stmt.setString(5, firstName);
            stmt.setString(6, lastName);
            int row = stmt.executeUpdate();

            if (row > 0)
                success = true;

        } catch (Exception e) {
            System.err.println("There was an error creating a new album in database.");
            System.err.println(e.getMessage());
        }
        return success;
    }

    public static boolean updateAlbum(String isrc, String title, String contentDesc, int releasedYear, String firstName, String lastName) {
        boolean success = false;
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE Albums SET title = ?, contentDesc = ?, releasedYear = ?, artistFirstName = ?, artistLastName = ? WHERE isrc = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, contentDesc);
            stmt.setInt(3, releasedYear);
            stmt.setString(4, firstName);
            stmt.setString(5, lastName);
            stmt.setString(6, isrc);
            int row = stmt.executeUpdate();

            if (row > 0)
                success = true;

        } catch (Exception e) {
            System.err.println("There was an error updating album in database.");
            System.err.println(e.getMessage());
        }
        return success;
    }

    public static boolean deleteAlbum(String isrc) {
        boolean success = false;
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM Albums WHERE isrc = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, isrc);
            int row = stmt.executeUpdate();

            if (row > 0)
                success = true;

        } catch (Exception e) {
            System.err.println("There was an error deleting the album in database.");
            System.err.println(e.getMessage());
        }
        return success;
    }
}
