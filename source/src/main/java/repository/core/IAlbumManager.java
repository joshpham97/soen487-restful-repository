package repository.core;

import java.util.ArrayList;

public interface IAlbumManager {
    ArrayList<Album> listAlbum();
    Album getAlbum(String isrc);
    boolean addAlbum(Album album);
    boolean updateAlbum(Album album);
    boolean deleteAlbum(String isrc);
}
