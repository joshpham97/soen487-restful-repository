package database.dao;

import database.db.DBConnection;
import repository.core.CoverImage;

import java.io.InputStream;
import java.sql.*;

public class CoverImageDAO {
    public static int insertCoverImage(InputStream imageInputStream, String mimeType, String isrc) throws SQLException {
        int coverImageId = -1;

        Connection conn = DBConnection.getConnection();

        String query = "INSERT INTO images (coverImage, coverImageMIMEType, isrc)" + " values (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setBlob(1, imageInputStream);
        stmt.setString(2, mimeType);
        stmt.setString(3, isrc);
        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating cover image failed, no rows affected.");
        }

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            coverImageId = generatedKeys.getInt(1);
        }

        return coverImageId;
    }

    public static CoverImage getCoverImageByAlbumIsrc(String isrc){
        CoverImage coverImage = null;

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM images WHERE isrc =?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, isrc);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                coverImage = mapResultSetToCoverImage(rs);
        } catch (Exception e) {
            System.err.println("There was an error retrieving specific album from database.");
            System.err.println(e.getMessage());
        }

        return coverImage;
    }

    public static CoverImage updateCoverImage(InputStream imageInputStream, String mimeType, String isrc) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "UPDATE images SET coverImage = ?, coverImageMIMEType = ? WHERE isrc = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setBlob(1, imageInputStream);
        stmt.setString(2, mimeType);
        stmt.setString(3, isrc);
        int row = stmt.executeUpdate();

        if (row > 0){
            return getCoverImageByAlbumIsrc(isrc);
        }else{
            throw new SQLException("Update cover image failed, the entry may not exist.");
        }
    }

    public static boolean deleteCoverImage(String isrc) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "DELETE FROM images WHERE isrc = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, isrc);
        int row = stmt.executeUpdate();

        if (row > 0)
            return true;
        else
            throw new SQLException("Delete cover image failed, the entry may not exist.");
    }

    private static CoverImage mapResultSetToCoverImage(ResultSet rs) throws SQLException {
        CoverImage coverImage = new CoverImage();
        coverImage.setCoverImageId(rs.getString("img_id"));
        coverImage.setMimeType(rs.getString("coverImageMIMEType"));
        coverImage.setBlob(rs.getBlob("coverImage"));
        coverImage.setIsrc(rs.getString("isrc"));

        return coverImage;
    }
}
