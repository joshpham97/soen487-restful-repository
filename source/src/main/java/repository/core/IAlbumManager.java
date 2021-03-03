package repository.core;

import java.util.ArrayList;

public interface IAlbumManager extends IManager{
    ArrayList<Album> listAlbum();
    Album getAlbum(String isrc) throws RepException;
    boolean addAlbum(Album album) throws RepException;
    boolean updateAlbum(Album album) throws RepException;
    boolean deleteAlbum(String isrc);
}
