package servlet;

import repository.core.Artist;
import repository.core.IArtistManager;
import repository.implementation.ArtistManagerInMemory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class ArtistServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("doGet");
        final PrintWriter out = response.getWriter();
        out.write("GET method (retrieving data) was invoked!");
        response.setContentType("text/plain;charset=UTF-8");

        ArtistManagerInMemory artistManager = new ArtistManagerInMemory();
        ArrayList<Artist> artists = artistManager.listArtist();

        Iterator<Artist> itr = artists.iterator();
        System.out.println("works");
        if(artists.isEmpty())
        {
            System.out.println("Empty list");
        }
        else
        {
            System.out.println("Note empty");
        }
        while(itr.hasNext()){
            System.out.println("works");
            System.out.println(itr.next());
        }
       // response.sendRedirect("index.jsp");
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
        Iterator<Artist> itr = artists.iterator();
        while(itr.hasNext()){
            System.out.println("works");
            System.out.println(itr.next());
        }

    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PrintWriter out = response.getWriter();
        out.write("DELETE method (removing data) was invoked!");
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PrintWriter out = response.getWriter();
        out.write("PUT method (inserting data) was invoked!");
    }
}
