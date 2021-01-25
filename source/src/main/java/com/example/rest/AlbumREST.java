package com.example.rest;

import repository.business.AlbumManagerFactory;
import repository.core.Album;
import repository.core.IAlbumManager;
import utilities.ExceptionParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Path("album")
public class AlbumREST {
    private IAlbumManager albumManager = AlbumManagerFactory.loadManager();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listAlbum() {
        try {
            ArrayList<Album> albums = albumManager.listAlbum();
            if (albums.size() == 0) // No albums
                return "There are no albums";

            // Build string to return
            String albumListString = albums.stream()
                    .map(a -> a.toString())
                    .collect(Collectors.joining("\n"));

            return albumListString;
        }
        catch(Exception e) {
            return "An error occurred while trying to get the list of albums\n\n"
                    + "Error Details:\n"
                    + ExceptionParser.getStackTraceString(e);
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}")
    public String getAlbum(@PathParam("isrc") String isrc) {
        try {
            Album album = albumManager.getAlbum(isrc);
            if (album == null) { // No such album
                return "No album with an ISRC of " + isrc;
            }

            return album.toString();
        }
        catch(Exception e) {
            return "An error occurred while trying to get the album\n\n"
                    + "Error Details:\n"
                    + ExceptionParser.getStackTraceString(e);
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}/{title}/{releaseYear}/{artist}/{contentDesc}")
    public String addAlbum(@PathParam("isrc") String isrc, @PathParam("title") String title, @PathParam("releaseYear") int releaseYear, @PathParam("artist") String artist, @PathParam("contentDesc") String contentDesc) {
        try {
            Album album = new Album(isrc, title, releaseYear, artist, contentDesc);
            boolean success = albumManager.addAlbum(album);

            if (success) {
                return "Successfully added album \n" + album;
            }
            else {
                return "Failed to add album \n" + album;
            }
        }
        catch(Exception e) {
            return "An error occurred while trying to add the album\n\n"
                    + "Error Details:\n"
                    + ExceptionParser.getStackTraceString(e);
        }
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}/{title}/{releaseYear}/{artist}/{contentDesc}")
    public String updateAlbum(@PathParam("isrc") String isrc, @PathParam("title") String title, @PathParam("releaseYear") int releaseYear, @PathParam("artist") String artist, @PathParam("contentDesc") String contentDesc) {
        try {
            Album album = new Album(isrc, title, releaseYear, artist, contentDesc);
            boolean success = albumManager.updateAlbum(album);

            if (success)
                return "Successfully updated album \n" + album;
            else
                return "Failed to update album \n" + album;
        }
        catch(Exception e) {
            return "An error occurred while trying to update the album\n\n"
                    + "Error Details:\n"
                    + ExceptionParser.getStackTraceString(e);

        }
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}")
    public String deleteAlbum(@PathParam("isrc") String isrc) {
        try {
            boolean success = albumManager.deleteAlbum(isrc);

            if (success)
                return "Successfully deleted album";
            else
                return "Failed to delete album";
        }
        catch(Exception e) {
            return "An error occurred while trying to delete the album\n\n"
                    + "Error Details:\n"
                    + ExceptionParser.getStackTraceString(e);
        }
    }
}
