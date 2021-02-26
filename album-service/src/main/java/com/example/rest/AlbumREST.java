package com.example.rest;

import factories.AlbumManagerDB;
import factories.AlbumManagerFactory;
import repository.core.Album;
import repository.core.Artist;
import repository.core.IAlbumManager;
import utilities.UrlParser;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("album")
public class AlbumREST {
    //private IAlbumManager albumManager = AlbumManagerFactory.loadManager();
    private IAlbumManager albumManager = AlbumManagerDB.loadManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAlbum() {
        try {
            List<Album> albums = albumManager.listAlbum();

            GenericEntity<List<Album>> entity = new GenericEntity<List<Album>>(albums) {};

            return Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(null)
                    .build();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}")
    public Response getAlbum(@PathParam("isrc") String isrc) {
        try {
            Album album = albumManager.getAlbum(isrc);
            if (album == null) { // No such album
                return Response.status(Response.Status.OK)
                        .entity("No album with an ISRC of " + isrc)
                        .build();
            }

            return Response.status(Response.Status.OK)
                    .entity(album.toString())
                    .build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to get the album")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addAlbum(@FormParam("isrc") String isrc, @FormParam("title") String title, @FormParam("releaseYear") int releaseYear, @FormParam("contentDesc") String contentDesc, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName) {
        try {
            if(isrc == null || isrc.trim().isEmpty() ||
                    title == null || title.trim().isEmpty()) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to add album: missing required fields")
                        .build();
            }
            else if(releaseYear <= 0) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to add album: invalid releaseYear")
                        .build();
            }
            Artist artist = new Artist(firstName, lastName);
            Album album = new Album(isrc, title, releaseYear, contentDesc, artist);
            boolean success = albumManager.addAlbum(album);

            if (success) {
                return Response.status(Response.Status.OK)
                        .entity("Successfully added album \n" + album)
                        .build();
            }
            else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(String.format("Failed to add album: album with an ISRC of %s already exists", isrc))
                        .build();
            }
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to add the album")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAlbum(String strAlbum) {
        try {
            // Parse string data
            Map<String, String> params = UrlParser.parseStrParams(strAlbum);
            String isrc = params.get("isrc");
            String title = params.get("title");
            int releaseYear = Integer.parseInt(params.get("releaseYear"));
            //String artist = params.get("artist");
            String contentDesc = params.get("contentDesc");
            String firstName = params.get("firstName");
            String lastName = params.get("lastName");


            if(isrc == null || isrc.trim().isEmpty() ||
                    title == null || title.trim().isEmpty()) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to update album: missing required fields")
                        .build();
            }
            else if(releaseYear <= 0) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to update album: invalid releaseYear")
                        .build();
            }
            Artist artistNew = new Artist(firstName, lastName);

            Album album = new Album(isrc, title, releaseYear, contentDesc, artistNew);
            boolean success = albumManager.updateAlbum(album);

            if (success) {
                return Response.status(Response.Status.OK)
                        .entity("Successfully updated album \n" + album)
                        .build();
            }
            else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to update album: no album with an ISRC of " + album.getIsrc())
                        .build();
            }
        }
        catch(NumberFormatException ne) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to update album: invalid field value")
                    .build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to update the album")
                    .build();
        }
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}")
    public Response deleteAlbum(@PathParam("isrc") String isrc) {
        try {
            boolean success = albumManager.deleteAlbum(isrc);

            if (success) {
                return Response.status(Response.Status.OK)
                        .entity("Successfully deleted album with ISRC of " + isrc)
                        .build();
            }
            else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to delete album with ISRC of " + isrc)
                        .build();
            }
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to delete the album of ISRC of " + isrc)
                    .build();
        }
    }
}
