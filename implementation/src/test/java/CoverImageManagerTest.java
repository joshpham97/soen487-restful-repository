import database.dao.AlbumDAO;
import database.dao.CoverImageDAO;
import factories.ManagerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.core.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CoverImageManagerTest {
    static InputStream imageInputStream;
    static String mimeType;
    static InputStream imageUpdatedInputStream;
    static String updatedMimeType;
    static Album testAlbum;

    @BeforeClass
    public static void init() {
        //coverImageManager = (ICoverImageManager) ManagerFactory.COVER_IMAGE.getManager();
        //logManager = (ILogManager) ManagerFactory.COVER_IMAGE.getManager();

        imageInputStream = CoverImageManagerTest.class.getClassLoader().getResourceAsStream("coverImageTest.jpg");
        mimeType = "image/jpeg";
        imageUpdatedInputStream = CoverImageManagerTest.class.getClassLoader().getResourceAsStream("updatedCoverImageTest.png");
        updatedMimeType = "image/png";

        Artist testArtist = new Artist();
        testArtist.setFirstname("Josh");
        testArtist.setLastname("Pham");

        testAlbum = new Album();
        testAlbum.setIsrc("CoverImageTestAlbum");
        testAlbum.setTitle("Album Title");
        testAlbum.setContentDesc("Album description");
        testAlbum.setReleaseYear(2021);
        testAlbum.setArtist(testArtist);
    }

    /*@Test
    public void insertWithNonExistingIsrc(){
        ICoverImageManager coverImageManager = (ICoverImageManager) ManagerFactory.COVER_IMAGE.getManager();
        String notExistIsrc = "DoesNotExist";

        //Check that inserting a cover image with an invalid isrc throws an SQLException
        assertThrows(SQLException.class, () -> {
            coverImageManager.createOrUpdateCoverImageIfExist(imageInputStream, mimeType, notExistIsrc);
        });
    }

    @Test
    public void insert() throws SQLException, RepException {
        //arrange
        ICoverImageManager coverImageManager = (ICoverImageManager) ManagerFactory.COVER_IMAGE.getManager();
        IAlbumManager albumManager = (IAlbumManager) ManagerFactory.ALBUM.getManager();
        albumManager.addAlbum(testAlbum);

        //Action
        CoverImage coverImage = coverImageManager.(imageInputStream, mimeType, testAlbum.getIsrc());
        Log addedLog = cover
        //Assert
        assertNotNull(coverImage);

        //Clean up
        coverImageManager.deleteCoverImage(testAlbum.getIsrc());
        albumManager.deleteAlbum(testAlbum.getIsrc());
    }*/

    @Test
    public void get() throws SQLException, RepException {
        //Arrange, create album and image
        ICoverImageManager coverImageManager = (ICoverImageManager) ManagerFactory.COVER_IMAGE.getManager();
        IAlbumManager albumManager = (IAlbumManager) ManagerFactory.ALBUM.getManager();
        ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
        albumManager.addAlbum(testAlbum);
        coverImageManager.createOrUpdateCoverImageIfExist(imageInputStream, mimeType, testAlbum.getIsrc());

        //Action
        CoverImage coverImage = CoverImageDAO.getCoverImageByAlbumIsrc(testAlbum.getIsrc());
        Log createdLog = getLastActionLogOfAlbum();

        //Assert
        assertEquals(coverImage.getIsrc(), testAlbum.getIsrc());
        assertEquals(coverImage.getMimeType(), mimeType);
        assertEquals(createdLog.getChange(), Log.ChangeType.UPDATE);

        //Clean up
        coverImageManager.deleteCoverImage(testAlbum.getIsrc());
        albumManager.deleteAlbum(testAlbum.getIsrc());
    }

    /*@Test
    public void updateNonExistingImage() {
        assertThrows(SQLException.class, () -> {
            CoverImageDAO.updateCoverImage(imageUpdatedInputStream, updatedMimeType, testAlbum.getIsrc());
        });
    }*/

    /*@Test
    public void update() throws SQLException, IOException {
        boolean insertAlbumSuccess = AlbumDAO.insertAlbum(testAlbum.getIsrc(),
                testAlbum.getTitle(),
                testAlbum.getContentDesc(),
                testAlbum.getReleaseYear(),
                testAlbum.getArtist().getFirstname(),
                testAlbum.getArtist().getLastname());
        int imageId = CoverImageDAO.insertCoverImage(imageInputStream, mimeType, testAlbum.getIsrc());

        CoverImage updatedCoverImage = CoverImageDAO.updateCoverImage(imageUpdatedInputStream, updatedMimeType, testAlbum.getIsrc());
        assertNotNull(updatedCoverImage);
        assertEquals(updatedCoverImage.getMimeType(), updatedMimeType);
        //assertTrue(IOUtils.contentEquals(imageUpdatedInputStream, updatedCoverImage.getBlob().getBinaryStream()));
        assertEquals(updatedCoverImage.getIsrc(), testAlbum.getIsrc());

        CoverImageDAO.deleteCoverImage(testAlbum.getIsrc());
        AlbumDAO.deleteAlbum(testAlbum.getIsrc());
    }*/

    @Test
    public void deleteNonExistingImage() throws RepException {
        ICoverImageManager coverImageManager = (ICoverImageManager) ManagerFactory.COVER_IMAGE.getManager();
        assertThrows(RepException.class, () -> {
            coverImageManager.deleteCoverImage(testAlbum.getIsrc());
        });
    }

    @Test
    public void delete() throws SQLException, RepException {
        //arrange
        ICoverImageManager coverImageManager = (ICoverImageManager) ManagerFactory.COVER_IMAGE.getManager();
        IAlbumManager albumManager = (IAlbumManager) ManagerFactory.ALBUM.getManager();
        ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
        albumManager.addAlbum(testAlbum);
        coverImageManager.createOrUpdateCoverImageIfExist(imageInputStream, mimeType, testAlbum.getIsrc());

        //Action
        boolean success = coverImageManager.deleteCoverImage(testAlbum.getIsrc());
        Log deletedLog = getLastActionLogOfAlbum();

        //Assert
        assertTrue(success);
        assertEquals(deletedLog.getChange(), Log.ChangeType.DELETE);

        //Clean up
        AlbumDAO.deleteAlbum(testAlbum.getIsrc());
    }

    @Test
    public void createOrUpdate() throws SQLException, RepException {
        //arrange
        ICoverImageManager coverImageManager = (ICoverImageManager) ManagerFactory.COVER_IMAGE.getManager();
        IAlbumManager albumManager = (IAlbumManager) ManagerFactory.ALBUM.getManager();

        albumManager.addAlbum(testAlbum);


        //Action
        CoverImage coverImage = coverImageManager.createOrUpdateCoverImageIfExist(imageInputStream, mimeType, testAlbum.getIsrc());
        assertTrue(coverImage.getCoverImageId() > 0);
        assertNotNull(coverImage);
        assertEquals(coverImage.getMimeType(), mimeType);
        assertEquals(coverImage.getIsrc(), testAlbum.getIsrc());

        Log createdLog = getLastActionLogOfAlbum();
        assertEquals(createdLog.getChange(), Log.ChangeType.UPDATE);

        CoverImage updatedCoverImage = CoverImageDAO.createOrUpdateCoverImageIfExist(imageUpdatedInputStream, updatedMimeType, testAlbum.getIsrc());
        assertEquals(coverImage.getCoverImageId(), updatedCoverImage.getCoverImageId());
        assertNotNull(updatedCoverImage);
        assertEquals(updatedCoverImage.getMimeType(), updatedMimeType);
        //assertTrue(IOUtils.contentEquals(imageUpdatedInputStream, updatedCoverImage.getBlob().getBinaryStream()));
        assertEquals(updatedCoverImage.getIsrc(), testAlbum.getIsrc());

        Log updatedLog = getLastActionLogOfAlbum();
        assertEquals(updatedLog.getChange(), Log.ChangeType.UPDATE);

        CoverImageDAO.deleteCoverImage(testAlbum.getIsrc());
        AlbumDAO.deleteAlbum(testAlbum.getIsrc());
    }

    public Log getLastActionLogOfAlbum(){
        ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();
        List<Log> filteredLogs = logManager.listLog()
                .stream()
                .filter(log -> log.getRecordKey().equals(testAlbum.getIsrc()))
                .sorted(Comparator.comparing(Log::getDate))
                .collect(Collectors.toList());
        return filteredLogs.get(filteredLogs.size() - 1);
    }
}
