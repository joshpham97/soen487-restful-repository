package repository.core;

import java.util.ArrayList;

public interface IAlbumManager extends IManager{
    ArrayList<Album> listAlbum();
    Album getAlbum(String isrc);
    boolean addAlbum(Album album);
    boolean updateAlbum(Album album);
    boolean deleteAlbum(String isrc);
}
