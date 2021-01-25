package repository.implementation;

import repository.core.Album;
import repository.core.IAlbumManager;

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
        Optional<Album> album;

        try {
            album = albums.stream()
                    .filter(a -> a.getIsrc().equals(isrc))
                    .findFirst();

            if (!album.isPresent())
                return null;
        }
        catch(Exception e) {
            return null;
        }

        return album.get();
    }

    public boolean addAlbum(Album album) {
        try {
            Album duplicate = getAlbum(album.getIsrc());
            if(duplicate != null)  // Constraint: duplicate ISRC
                return false;

            albums.add(album);
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateAlbum(Album album) {
        try {
            deleteAlbum(album.getIsrc());
            addAlbum(album);
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }

    public boolean deleteAlbum(String isrc) {
        try {
            // Get length before deleting
            int beforeLength = albums.size();

            albums = albums.stream()
                    .filter(a -> !a.getIsrc().equals(isrc))
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

            if(beforeLength == albums.size()) // Nothing was deleted
                return false;
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }
}
