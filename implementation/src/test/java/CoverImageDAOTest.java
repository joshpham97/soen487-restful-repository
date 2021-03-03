import database.dao.AlbumDAO;
import database.dao.CoverImageDAO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import repository.core.Album;
import repository.core.Artist;
import repository.core.CoverImage;

import static org.junit.jupiter.api.Assertions.*;
import  org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class CoverImageDAOTest {
    static InputStream imageInputStream;
    static String mimeType;
    static InputStream imageUpdatedInputStream;
    static String updatedMimeType;
    static Album testAlbum;

    @BeforeClass
    public static void init() {
        imageInputStream = CoverImageDAOTest.class.getClassLoader().getResourceAsStream("coverImageTest.jpg");
        mimeType = "image/jpeg";
        imageUpdatedInputStream = CoverImageDAOTest.class.getClassLoader().getResourceAsStream("updatedCoverImageTest.png");
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

    @Test
    public void insertWithNonExistingIsrc(){
        String notExistIsrc = "DoesNotExist";

        //Check that inserting a cover image with an invalid isrc throws an SQLException
        assertThrows(SQLException.class, () -> {
            CoverImageDAO.insertCoverImage(imageInputStream, mimeType, notExistIsrc);
        });
    }

    @Test
    public void insert() throws SQLException {
        //arrange
        boolean insertAlbumSuccess = AlbumDAO.insertAlbum(testAlbum.getIsrc(),
                                                          testAlbum.getTitle(),
                                                          testAlbum.getContentDesc(),
                                                          testAlbum.getReleaseYear(),
                                                          testAlbum.getArtist().getFirstname(),
                                                          testAlbum.getArtist().getLastname());

        //Action
        int imageId = CoverImageDAO.insertCoverImage(imageInputStream, mimeType, testAlbum.getIsrc());

        //Assert
        assertTrue(imageId > 0);
        System.out.println("Image id is: " + imageId);

        //Clean up
        CoverImageDAO.deleteCoverImage(testAlbum.getIsrc());
        AlbumDAO.deleteAlbum(testAlbum.getIsrc());
    }

    @Test
    public void get() throws SQLException {
        //Arrange, create album and image
        boolean insertAlbumSuccess = AlbumDAO.insertAlbum(testAlbum.getIsrc(),
                testAlbum.getTitle(),
                testAlbum.getContentDesc(),
                testAlbum.getReleaseYear(),
                testAlbum.getArtist().getFirstname(),
                testAlbum.getArtist().getLastname());
        int imageId = CoverImageDAO.insertCoverImage(imageInputStream, mimeType, testAlbum.getIsrc());

        //Action
        CoverImage coverImage = CoverImageDAO.getCoverImageByAlbumIsrc(testAlbum.getIsrc());

        //Assert
        assertEquals(coverImage.getIsrc(), testAlbum.getIsrc());
        assertEquals(coverImage.getMimeType(), mimeType);

        //Clean up
        CoverImageDAO.deleteCoverImage(testAlbum.getIsrc());
        AlbumDAO.deleteAlbum(testAlbum.getIsrc());
    }

    @Test
    public void updateNonExistingImage() {
        assertThrows(SQLException.class, () -> {
            CoverImageDAO.updateCoverImage(imageUpdatedInputStream, updatedMimeType, testAlbum.getIsrc());
        });
    }

    @Test
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
    }

    @Test
    public void deleteNonExistingImage() throws SQLException {
        assertThrows(SQLException.class, () -> {
            CoverImageDAO.deleteCoverImage(testAlbum.getIsrc());
        });
    }

    @Test
    public void delete() throws SQLException {
        //arrange
        boolean insertAlbumSuccess = AlbumDAO.insertAlbum(testAlbum.getIsrc(),
                testAlbum.getTitle(),
                testAlbum.getContentDesc(),
                testAlbum.getReleaseYear(),
                testAlbum.getArtist().getFirstname(),
                testAlbum.getArtist().getLastname());
        int imageId = CoverImageDAO.insertCoverImage(imageInputStream, mimeType, testAlbum.getIsrc());

        //Action
        boolean success = CoverImageDAO.deleteCoverImage(testAlbum.getIsrc());

        //Assert
        assertTrue(success);

        //Clean up
        AlbumDAO.deleteAlbum(testAlbum.getIsrc());
    }
}
