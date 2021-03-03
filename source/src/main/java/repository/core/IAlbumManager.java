package repository.core;

import java.util.ArrayList;

public interface IAlbumManager extends IManager{
    ArrayList<Album> listAlbum(String title, String contentDesc, Integer fromYear, Integer toYear, String name);
    Album getAlbum(String isrc) throws RepException;
    boolean addAlbum(Album album) throws RepException;
    boolean updateAlbum(Album album) throws RepException;
    boolean deleteAlbum(String isrc);
}
