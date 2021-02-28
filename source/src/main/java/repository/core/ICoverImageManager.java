package repository.core;

public interface ICoverImageManager extends IManager {
    CoverImage getCoverImage();
    boolean updateCoverImage();
    boolean deleteCoverImage();
}
