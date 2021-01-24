import repository.core.Artist;

import javax.ws.rs.Path;
import java.util.ArrayList;

@Path("artist")
public class ArtistRest {
    private static ArrayList<Artist> artists = new ArrayList<>();
}
