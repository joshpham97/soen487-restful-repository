package repository.core;

import java.io.InputStream;
import java.sql.SQLException;

public interface ICoverImageManager extends IManager {
    CoverImage createOrUpdateCoverImageIfExist(InputStream imageBlob, String mimeType, String isrc) throws SQLException;
    CoverImage getCoverImageByAlbumIsrc(String isrc) throws SQLException;
    boolean deleteCoverImage(String isrc) throws SQLException;
    //boolean updateCoverImage();
    //boolean deleteCoverImage();
}
