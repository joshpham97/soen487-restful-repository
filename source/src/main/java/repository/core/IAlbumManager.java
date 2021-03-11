package repository.core;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAlbumManager extends IManager{
    ArrayList<Album> listAlbum() throws SQLException;
    Album getAlbum(String isrc) throws SQLException, RepException;
    boolean addAlbum(Album album) throws SQLException, RepException;
    boolean updateAlbum(Album album) throws SQLException, RepException;
    boolean deleteAlbum(String isrc) throws SQLException, RepException;
}
