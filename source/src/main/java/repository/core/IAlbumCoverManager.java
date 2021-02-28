package repository.core;

public interface IAlbumCoverManager {
    CoverImage getCoverImage();
    boolean updateCoverImage();
    boolean deleteCoverImage();
}
