package repository.core.interfaces;

import repository.core.exception.RepException;
import repository.core.pojo.Album;
import repository.core.pojo.CoverImage;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAlbumManager extends IManager{
    ArrayList<Album> listAlbum() throws SQLException;
    Album getAlbum(String isrc) throws SQLException, RepException;
    boolean addAlbum(Album album) throws SQLException, RepException;
    boolean updateAlbum(Album album) throws SQLException, RepException;
    boolean deleteAlbum(String isrc) throws SQLException, RepException;
    CoverImage createOrUpdateCoverImageIfExist(InputStream imageBlob, String mimeType, String isrc) throws RepException;
    CoverImage getCoverImageByAlbumIsrc(String isrc) throws RepException;
    boolean deleteCoverImage(String isrc) throws RepException;
}
