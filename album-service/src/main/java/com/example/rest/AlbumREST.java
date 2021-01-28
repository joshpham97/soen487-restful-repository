package com.example.rest;

import repository.business.AlbumManagerFactory;
import repository.core.Album;
import repository.core.IAlbumManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Path("album")
public class AlbumREST {
    private IAlbumManager albumManager = AlbumManagerFactory.loadManager();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response listAlbum() {
        try {
            ArrayList<Album> albums = albumManager.listAlbum();
            if (albums.size() == 0) // No albums
                return Response.status(Response.Status.OK)
                        .entity("There are no albums")
                        .build();

            // Build string to return
            String albumListString = albums.stream()
                    .map(a -> a.toString())
                    .collect(Collectors.joining("\n"));

            return Response.status(Response.Status.OK)
                    .entity(albumListString)
                    .build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to get the list of albums")
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
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}/{title}/{releaseYear}/{artist}/{contentDesc}")
    public Response addAlbum(@PathParam("isrc") String isrc, @PathParam("title") String title, @PathParam("releaseYear") int releaseYear, @PathParam("artist") String artist, @PathParam("contentDesc") String contentDesc) {
        try {
            Album album = new Album(isrc, title, releaseYear, artist, contentDesc);
            boolean success = albumManager.addAlbum(album);

            if (success) {
                return Response.status(Response.Status.OK)
                        .entity("Successfully added album \n" + album)
                        .build();
            }
            else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to add album \n" + album)
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
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}/{title}/{releaseYear}/{artist}/{contentDesc}")
    public Response updateAlbum(@PathParam("isrc") String isrc, @PathParam("title") String title, @PathParam("releaseYear") int releaseYear, @PathParam("artist") String artist, @PathParam("contentDesc") String contentDesc) {
        try {
            Album album = new Album(isrc, title, releaseYear, artist, contentDesc);
            boolean success = albumManager.updateAlbum(album);

            if (success) {
                return Response.status(Response.Status.OK)
                        .entity("Successfully updated album \n" + album)
                        .build();
            }
            else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to update album \n" + album)
                        .build();
            }
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