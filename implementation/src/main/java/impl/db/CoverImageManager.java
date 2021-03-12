package impl.db;

import database.dao.CoverImageDAO;
import factories.ManagerFactory;
import repository.core.*;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CoverImageManager implements ICoverImageManager {
    public CoverImage getCoverImageByAlbumIsrc(String isrc) throws SQLException{
        //Logging the data first
        ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
        CoverImage coverImage = CoverImageDAO.getCoverImageByAlbumIsrc(isrc);

        if(coverImage != null){
            logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.ADD, isrc));
        }

        return coverImage;
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
        ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
        CoverImage coverImage = CoverImageDAO.createOrUpdateCoverImageIfExist(imageBlob, mimeType, isrc);
        if (coverImage != null){
            logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.UPDATE, isrc));
        }

        return coverImage;
    }

    public boolean deleteCoverImage(String isrc) throws SQLException {
        ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
        boolean isDeleted = CoverImageDAO.deleteCoverImage(isrc);

        if(isDeleted){
            logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.DELETE, isrc));
        }

        return isDeleted;
    }
}
