package impl.db;

import database.dao.CoverImageDAO;
import database.dao.LogDAO;
import repository.core.CoverImage;
import repository.core.ICoverImageManager;
import repository.core.IManager;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CoverImageManager implements ICoverImageManager {
    public CoverImage getCoverImageByAlbumIsrc(String isrc) throws SQLException{
        //Logging the data first
        return CoverImageDAO.getCoverImageByAlbumIsrc(isrc);
    }

    public static int insertCoverImage(InputStream imageBlob, String mimeType, String isrc) throws SQLException {
        return CoverImageDAO.insertCoverImage(imageBlob, mimeType, isrc);
    }

    public CoverImage updateCoverImage(InputStream imageBlob, String mimeType, String isrc) throws SQLException {
        /*CoverImage album = CoverImageDAO.getCoverImageByAlbumIsrc(isrc);
        if (album != null)
            return CoverImageDAO.updateCoverImage(imageBlob, mimeType, isrc);
        else
            return null;*/
        return CoverImageDAO.updateCoverImage(imageBlob, mimeType, isrc);
    }

    public CoverImage createOrUpdateCoverImageIfExist(InputStream imageBlob, String mimeType, String isrc) throws SQLException {
        return CoverImageDAO.createOrUpdateCoverImageIfExist(imageBlob, mimeType, isrc);
    }

    public boolean deleteCoverImage(String isrc) throws SQLException {
        return CoverImageDAO.deleteCoverImage(isrc);
    }
}
