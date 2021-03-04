package impl;

import database.dao.CoverImageDAO;
import repository.core.CoverImage;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class CoverImageManager {
    public static CoverImage getCoverImageByAlbumIsrc(String isrc){
        return CoverImageDAO.getCoverImageByAlbumIsrc(isrc);
    }

    public static int insertCoverImage(InputStream imageBlob, String mimeType, String isrc) throws SQLException {
        /*Album album = AlbumDAO.getAlbumDatabase(isrc);
        if (album != null)
            return CoverImageDAO.insertCoverImage(imageBlob, mimeType, isrc);
        else
            return -1;*/

        return CoverImageDAO.insertCoverImage(imageBlob, mimeType, isrc);
    }

    public static CoverImage updateCoverImage(InputStream imageBlob, String mimeType, String isrc) throws SQLException {
        /*CoverImage album = CoverImageDAO.getCoverImageByAlbumIsrc(isrc);
        if (album != null)
            return CoverImageDAO.updateCoverImage(imageBlob, mimeType, isrc);
        else
            return null;*/
        return CoverImageDAO.updateCoverImage(imageBlob, mimeType, isrc);
    }

    public static boolean deleteCoverImage(String isrc) throws SQLException {
        return CoverImageDAO.deleteCoverImage(isrc);
    }
}
