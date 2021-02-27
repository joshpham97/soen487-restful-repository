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
    /**
    public ArrayList<Artist> listArtist() {
        return artists.stream()
                .sorted(Comparator.comparing(Artist::getNickname).thenComparing(Artist::getFirstname).thenComparing(Artist::getLastname))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Artist getArtist(String nickname) {
        Optional<Artist> artist = artists.stream()
                    .filter(a -> a.getNickname().equals(nickname))
                    .findFirst();

        if(!artist.isPresent())
            return null;

        return artist.get();
    }

    public synchronized boolean addArtist(Artist artist) {
        Artist duplicate = getArtist(artist.getNickname());
        if(duplicate != null)  // Constraint: duplicate nickname
            return false;

        return artists.add(artist);
    }

    public synchronized boolean updateArtist(Artist artist) {
        Artist current = getArtist(artist.getNickname());
        if(current == null)
            return false;

        int index = artists.indexOf(current);
        artists.set(index, artist);

        return artist.equals(artists.get(index));
    }

    public synchronized boolean deleteArtist(String nickname) {
        // Get length before deleting
        int beforeLength = artists.size();

        artists = artists.stream()
                .filter(a -> !a.getNickname().equals(nickname))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        // Check if anything was deleted
        if(beforeLength == artists.size())
            return false;

        return true;
    }
*/
}
