package servlet;

import repository.core.Artist;
import repository.core.IArtistManager;
import repository.implementation.ArtistManagerInMemory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class ArtistServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("doGet");

        ArtistManagerInMemory artistManager = new ArtistManagerInMemory();
        ArrayList<Artist> artists = artistManager.listArtist();

        Iterator<Artist> itr = artists.iterator();
        System.out.println("works");
        while(itr.hasNext()){
            System.out.println("works");
            System.out.println(itr.next());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("testing");

        String nickname = request.getParameter("nickname");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String bio = request.getParameter("bio");

        Artist artist = new Artist(nickname, firstName, lastName, bio);
        ArtistManagerInMemory artistManager = new ArtistManagerInMemory();
        boolean addArtist = artistManager.addArtist(artist);
        System.out.println("Artist added: " + addArtist);

        ArrayList<Artist> artists = artistManager.listArtist();
        System.out.println("Here's the latest artist: "+artists);

    }
}
