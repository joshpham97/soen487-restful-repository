package repository.core;

import java.util.ArrayList;

public interface IAlbumManager {
    ArrayList<Album> listAlbum();
    Album getAlbum(String isrc);
    boolean addAlbum(Album artist);
    boolean updateAlbum(Album artist);
    boolean deleteAlbum(String isrc);
}
