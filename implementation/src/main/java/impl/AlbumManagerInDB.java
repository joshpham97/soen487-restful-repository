package impl;

import database.dao.AlbumDAO;
import repository.core.Album;
import repository.core.IAlbumManager;
import repository.core.RepException;

import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumManagerInDB implements IAlbumManager {
    public ArrayList<Album> listAlbum() throws SQLException {
        return AlbumDAO.getAllAlbums();
    }

    public Album getAlbum(String isrc) throws SQLException {
        return AlbumDAO.getAlbumDatabase(isrc);
    }

    public boolean addAlbum(Album album) throws SQLException, RepException {
        if(album.getIsrc() == null || album.getIsrc().isEmpty() ||
                album.getTitle() == null || album.getTitle().isEmpty() ||
                album.getArtist() == null ||
                album.getArtist().getFirstname() == null || album.getArtist().getFirstname().isEmpty() ||
                album.getArtist().getLastname() == null || album.getArtist().getLastname().isEmpty() ||
                album.getReleaseYear() == null)
            throw new RepException("Failed to add album: missing required fields");
        else if(album.getReleaseYear() <= 0)
            throw new RepException("Failed to add album: invalid releaseYear");
        else if(getAlbum(album.getIsrc()) != null)
            throw new RepException(String.format("Failed to add album: album with an ISRC of %s already exists", album.getIsrc()));


        return AlbumDAO.insertAlbum(album.getIsrc(), album.getTitle(), album.getContentDesc(),
                album.getReleaseYear(), album.getArtist().getFirstname(), album.getArtist().getLastname());
    }

    public boolean updateAlbum(Album album) throws SQLException, RepException {
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

    public boolean deleteAlbum(String isrc) throws SQLException, RepException {
        boolean success = AlbumDAO.deleteAlbum(isrc);

        if(!success)
            throw new RepException("Failed to delete album with an ISRC of " + isrc);

        return true; // Always true anyway
    }
}