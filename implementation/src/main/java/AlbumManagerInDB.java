import database.dao.AlbumDAO;
import repository.core.Album;
import repository.core.IAlbumManager;
import repository.core.RepException;

import java.util.ArrayList;

public class AlbumManagerInDB implements IAlbumManager {
    public ArrayList<Album> listAlbum(String title, String contentDesc, Integer fromYear, Integer toYear, String name) {
        return AlbumDAO.filterAlbums(title, contentDesc, fromYear, toYear, name);
    }

    public Album getAlbum(String isrc) throws RepException {
        Album album = AlbumDAO.getAlbumDatabase(isrc);

        if(album == null)
            throw new RepException("No album with an ISRC of " + isrc);

        return album;
    }

    public boolean addAlbum(Album album) throws RepException {
        if(album.getIsrc() == null || album.getIsrc().isEmpty() ||
                album.getTitle() == null || album.getTitle().isEmpty() ||
                album.getArtist() == null ||
                album.getArtist().getFirstname() == null || album.getArtist().getFirstname().isEmpty() ||
                album.getArtist().getLastname() == null || album.getArtist().getLastname().isEmpty() ||
                album.getReleaseYear() == null)
            throw new RepException("Failed to add album: missing required fields");
        else if(album.getReleaseYear() <= 0)
            throw new RepException("Failed to add album: invalid releaseYear");

        return AlbumDAO.insertAlbum(album.getIsrc(), album.getTitle(), album.getContentDesc(),
                album.getReleaseYear(), album.getArtist().getFirstname(), album.getArtist().getLastname());
    }

    public boolean updateAlbum(Album album) throws RepException {
        if(album.getTitle() == null || album.getTitle().isEmpty() ||
                album.getArtist() == null ||
                album.getArtist().getFirstname() == null || album.getArtist().getFirstname().isEmpty() ||
                album.getArtist().getLastname() == null || album.getArtist().getLastname().isEmpty() ||
                album.getReleaseYear() == null)
            throw new RepException("Failed to update album: missing required fields");
        else if(album.getReleaseYear() <= 0)
            throw new RepException("Failed to update album: invalid releaseYear");

        return AlbumDAO.updateAlbum(album.getIsrc(), album.getTitle(), album.getContentDesc(),
                album.getReleaseYear(), album.getArtist().getFirstname(), album.getArtist().getLastname());
    }

    public boolean deleteAlbum(String isrc) {
        return AlbumDAO.deleteAlbum(isrc);
    }
}
