import repository.core.Album;
import repository.core.CoverImage;
import repository.core.IAlbumManager;
import repository.core.RepException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class AlbumManagerInMemory implements IAlbumManager  {
    private CopyOnWriteArrayList<Album> albums;

    public AlbumManagerInMemory() {
        albums = new CopyOnWriteArrayList<>();
    }

    public ArrayList<Album> listAlbum() {
        return albums.stream()
                .sorted(Comparator.comparing(Album::getIsrc).thenComparing(Album::getTitle)) // Sort by ISRC and title
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Album getAlbum(String isrc) {
        Optional<Album> album = albums.stream()
                .filter(a -> a.getIsrc().equals(isrc))
                .findFirst();

        if (!album.isPresent())
            return null;

        return album.get();
    }

    public synchronized boolean addAlbum(Album album) {
        Album duplicate = getAlbum(album.getIsrc());
        if(duplicate != null)  // Constraint: duplicate ISRC
            return false;

        return albums.add(album);
    }

    public synchronized boolean updateAlbum(Album album) {
        Album current = getAlbum(album.getIsrc());
        if(current == null)
            return false;

        int index = albums.indexOf(current);
        albums.set(index, album);

        return album.equals(albums.get(index));
    }

    public synchronized boolean deleteAlbum(String isrc) {
        // Get length before deleting
        int beforeLength = albums.size();

        albums = albums.stream()
                .filter(a -> !a.getIsrc().equals(isrc))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        if(beforeLength == albums.size()) // Nothing was deleted
            return false;

        return true;
    }

    @Override
    public CoverImage createOrUpdateCoverImageIfExist(InputStream imageBlob, String mimeType, String isrc) throws RepException {
        return null;
    }

    @Override
    public CoverImage getCoverImageByAlbumIsrc(String isrc) throws RepException {
        return null;
    }

    @Override
    public boolean deleteCoverImage(String isrc) throws RepException {
        return false;
    }
}
