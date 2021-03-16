package impl;

import database.dao.AlbumDAO;
import database.dao.CoverImageDAO;
import factories.ManagerFactory;
import repository.core.exception.RepException;
import repository.core.interfaces.IAlbumManager;
import repository.core.interfaces.ILogManager;
import repository.core.pojo.Album;
import repository.core.pojo.CoverImage;
import repository.core.pojo.Log;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

        boolean success = AlbumDAO.insertAlbum(album.getIsrc(), album.getTitle(), album.getContentDesc(),
                album.getReleaseYear(), album.getArtist().getFirstname(), album.getArtist().getLastname());

        if(success) {
            ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
            logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.ADD, album.getIsrc()));
        }

        return success;
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

        boolean success = AlbumDAO.updateAlbum(album.getIsrc(), album.getTitle(), album.getContentDesc(),
                album.getReleaseYear(), album.getArtist().getFirstname(), album.getArtist().getLastname());

        if(success) {
            ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
            logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.UPDATE, album.getIsrc()));
        }

        return success;
    }

    public boolean deleteAlbum(String isrc) throws SQLException, RepException {
        if(getCoverImageByAlbumIsrc(isrc) != null) {
            // Use DAO to avoid getting an "UPDATE" log before deletion
            boolean coverImageSuccess = CoverImageDAO.deleteCoverImage(isrc);
            if(!coverImageSuccess)
                throw new RepException("There was error deleting the cover image with the isrc: " + isrc);
        }

        boolean success = AlbumDAO.deleteAlbum(isrc);
        if(!success)
            throw new RepException("Failed to delete album with an ISRC of " + isrc);

        ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
        logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.DELETE, isrc));

        return true; // Always true anyway
    }

    public CoverImage getCoverImageByAlbumIsrc(String isrc) throws RepException{
        try{
            return CoverImageDAO.getCoverImageByAlbumIsrc(isrc);
        }catch (SQLException ex){
            throw new RepException("There was an error getting the cover image of the album with isrc: " + isrc + " on the server.");
        }
    }

    public CoverImage createOrUpdateCoverImageIfExist(InputStream imageBlob, String mimeType, String isrc) throws RepException {
        try{
            ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
            //CoverImage coverImage = CoverImageDAO.createOrUpdateCoverImageIfExist(imageBlob, mimeType, isrc);

            CoverImage coverImage = CoverImageDAO.getCoverImageByAlbumIsrc(isrc);

            if(coverImage == null){
                CoverImageDAO.insertCoverImage(imageBlob, mimeType,isrc);
                coverImage = CoverImageDAO.getCoverImageByAlbumIsrc(isrc);
            }else{
                coverImage = CoverImageDAO.updateCoverImage(imageBlob, mimeType,isrc);
            }

            if (coverImage != null) {
                logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.UPDATE, isrc));
            }

            return coverImage;
        }catch(SQLException ex){
            throw new RepException("Unable to create or update cover image for album with the isrc: " + isrc);
        }
    }

    public boolean deleteCoverImage(String isrc) throws RepException {
        try{
            ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
            boolean isDeleted = CoverImageDAO.deleteCoverImage(isrc);

            if(isDeleted){
                logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.UPDATE, isrc));
            }

            return isDeleted;
        }catch (SQLException ex){
            throw new RepException("There was error deleting the cover image with the isrc: " + isrc);
        }
    }

    //Please do not remove -- back up code for insert and update cover image
    /*public static int insertCoverImage(InputStream imageBlob, String mimeType, String isrc) throws SQLException {
        return CoverImageDAO.insertCoverImage(imageBlob, mimeType, isrc);
    }

    public CoverImage updateCoverImage(InputStream imageBlob, String mimeType, String isrc) throws SQLException {
        CoverImage album = CoverImageDAO.getCoverImageByAlbumIsrc(isrc);
        if (album != null)
            return CoverImageDAO.updateCoverImage(imageBlob, mimeType, isrc);
        else
            return null;
        return CoverImageDAO.updateCoverImage(imageBlob, mimeType, isrc);
    }*/
}
