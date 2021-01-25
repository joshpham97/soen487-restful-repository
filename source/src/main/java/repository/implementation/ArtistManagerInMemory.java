package repository.implementation;

import repository.core.Artist;
import repository.core.IArtistManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArtistManagerInMemory implements IArtistManager {
    private CopyOnWriteArrayList<Artist> artists;

    public ArtistManagerInMemory() {
        artists = new CopyOnWriteArrayList<>();
    }

    /**STEFAN: MAYBE NOT GOOD FOR NOW!! **/
    public ArrayList<Artist> listArtist() {
        return artists.stream()
                .sorted(Comparator.comparing(Artist::getNickname).thenComparing(Artist::getFirstname).thenComparing(Artist::getLastname))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Artist getArtist(String nickname) {
        Optional<Artist> artist;

        try {
            artist = artists.stream()
                    .filter(a -> a.getNickname().equals(nickname))
                    .findFirst();

            if (!artist.isPresent())
                return null;
        }
        catch(Exception e) {
            return null;
        }

        return artist.get();
    }

    public boolean addArtist(Artist artist) {
        try {
            Artist duplicate = getArtist(artist.getNickname());
            if(duplicate != null)  // Constraint: duplicate ISRC
                return false;

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
