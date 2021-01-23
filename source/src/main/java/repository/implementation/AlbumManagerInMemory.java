package repository.implementation;

import repository.core.Album;
import repository.core.IAlbumManager;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class AlbumManagerInMemory implements IAlbumManager {
    CopyOnWriteArrayList<Album> albums;

    AlbumManagerInMemory() {
        albums = new CopyOnWriteArrayList<>();
    }

    public ArrayList<Album> listAlbum() {
        return new ArrayList<>(albums);
    }

   public Album getAlbum(String isrc) {
        return null;
    }

    public boolean addAlbum(Album album) {
        try {
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

            // Check if anything was deleted
            if(beforeLength == albums.size())
                return false;
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }
}
