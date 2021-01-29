package servlet;

import repository.business.ArtistManagerFactory;
import repository.core.Artist;
import repository.core.IArtistManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

@WebServlet(name = "artist")
public class ArtistServlet extends HttpServlet {

    private IArtistManager artistManager = ArtistManagerFactory.loadManager();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final PrintWriter out = response.getWriter();

        String nickname = request.getParameter("nickname");

        try{
            if(nickname == null)
            {
                ArrayList<Artist> artists = artistManager.listArtist();

                if (artists.size() == 0) // No artists
                {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.write("There are no artists");
                }
                else
                {
                    // Build string to return
                    String artistListString = artists.stream()
                            .map(a -> a.toString())
                            .collect(Collectors.joining("\n"));

                    response.setStatus(HttpServletResponse.SC_OK);
                    out.write(artistListString);
                }
            }
            else
            {
                Artist artist = artistManager.getArtist(nickname);
                if (artist == null) { // No such album
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.write("No artist with the nickname of " + nickname);
                }
                else
                {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.write(artist.toString());
                }
            }

            response.setContentType("text/plain;charset=UTF-8");
        }
        catch(Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("An error occurred while trying to get the list of albums\n\n");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final PrintWriter out = response.getWriter();

        String nickname = request.getParameter("nickname");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String bio = request.getParameter("bio");

        if(bio == null)
        {
            bio = "N/A";
        }

        Artist artist = new Artist(nickname, firstName, lastName, bio);

        try{
            if(nickname!= null && firstName != null && lastName != null)
            {

                boolean addArtist = artistManager.addArtist(artist);

                if(addArtist){
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.write("Artist was added!");
                }
                else
                {
                    //response.sendError(403, "CANNOT ADD ARTIST!!!" );
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    out.write("CANNOT ADD ARTIST");
                }
                //response.setContentType("text/plain;charset=UTF-8");
            }
            else
            {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.write("ERROR ADDING ARTIST");
            }
            //response.sendRedirect("index.jsp");
        }
        catch(Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("An error occurred while trying to add an artist\n\n");
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final PrintWriter out = response.getWriter();

        String nickname = request.getParameter("nickname");

        try{
            boolean deleted = artistManager.deleteArtist(nickname);
            if(deleted)
            {
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("Artist has been deleted!!");
            }
            else
            {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.write("Artist not found!");
            }
        }
        catch(Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("An error occurred while trying to delete an artist\n\n");
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final PrintWriter out = response.getWriter();

        String nickname = request.getParameter("nickname");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String bio = request.getParameter("bio");

        try {
            Artist artist = new Artist(nickname, firstName, lastName, bio);
            boolean success = artistManager.updateArtist(artist);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("Successfully updated artist \n" + artist);
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.write("Failed to update artist \n" + artist);
            }
        }
        catch(Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("An error occurred while trying to update the artist\n\n");
        }
    }
}
