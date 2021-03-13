package repository.core;

import java.io.InputStream;
import java.sql.SQLException;

public interface ICoverImageManager extends IManager {
    CoverImage createOrUpdateCoverImageIfExist(InputStream imageBlob, String mimeType, String isrc) throws RepException;
    CoverImage getCoverImageByAlbumIsrc(String isrc) throws RepException;
    boolean deleteCoverImage(String isrc) throws RepException;
    //boolean updateCoverImage();
    //boolean deleteCoverImage();
}
