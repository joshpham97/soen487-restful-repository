package repository.implementation;

import repository.core.Artist;
import repository.core.IArtistManager;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArtistManagerInMemory implements IArtistManager {
    private CopyOnWriteArrayList<Artist> artists;

    public ArtistManagerInMemory() {
        artists = new CopyOnWriteArrayList<>();
    }

    public ArrayList<Artist> listArtist() {
        return new ArrayList<>(artists);
    }

    public Artist getArtist(String nickname) {
        return null;
    }

    public boolean addArtist(Artist artist) {
        try {
            artists.add(artist);
        }
        catch(Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateArtist(Artist artist) {
        try {
            deleteArtist(artist.getNickname());
            addArtist(artist);
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }

    public boolean deleteArtist(String nickname) {
        try {
            // Get length before deleting
            int beforeLength = artists.size();

            artists = artists.stream()
                    .filter(a -> !a.getNickname().equals(nickname))
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

            // Check if anything was deleted
            if(beforeLength == artists.size())
                return false;
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }


}
