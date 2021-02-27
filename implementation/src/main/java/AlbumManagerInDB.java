import database.dao.AlbumDAO;
import repository.core.Album;
import repository.core.IAlbumManager;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class AlbumManagerInDB implements IAlbumManager {
    private CopyOnWriteArrayList<Album> albums;

    public AlbumManagerInDB() {
        albums = new CopyOnWriteArrayList<>();
    }

    public ArrayList<Album> listAlbum() {
        ArrayList<Album> albums = null;
        albums = (AlbumDAO.getAllAlbums());

        return albums;
    }

    public Album getAlbum(String isrc) {
        Album album = null;
        album = AlbumDAO.getAlbumDatabase(isrc);
        return album;
    }

    public synchronized boolean addAlbum(Album album) {
        String isrc = album.getIsrc();
        String title = album.getTitle();
        String contentDesc = album.getContentDesc();
        int year = album.getReleaseYear();
        String fname = album.getArtist().getFirstname();
        String lname= album.getArtist().getLastname();
        //String img = "none";
        boolean posted = AlbumDAO.insertAlbum(isrc, title, contentDesc, year, fname, lname);
        return posted;
    }

    public synchronized boolean updateAlbum(Album album) {
        String isrc = album.getIsrc();
        String title = album.getTitle();
        String contentDesc = album.getContentDesc();
        int year = album.getReleaseYear();
        String fname = album.getArtist().getFirstname();
        String lname= album.getArtist().getLastname();

        boolean updated = AlbumDAO.updateAlbum(isrc, title, contentDesc, year, fname, lname);
        return updated;
    }

    public synchronized boolean deleteAlbum(String isrc) {
        boolean deleted = AlbumDAO.deleteAlbum(isrc);
        return deleted;
    }
}
