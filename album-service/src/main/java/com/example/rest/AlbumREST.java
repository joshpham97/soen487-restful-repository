package com.example.rest;

import factories.ManagerFactory;
import repository.core.Album;
import repository.core.Artist;
import repository.core.IAlbumManager;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("album")
public class AlbumREST {
    private IAlbumManager albumManager = (IAlbumManager) ManagerFactory.ALBUM.getManager();

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
    @Produces(MediaType.APPLICATION_JSON)
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
                    .entity(album)
                    .build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to get the album")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAlbum(Album album) {
        try {
            String isrc = album.getIsrc();
            String title = album.getTitle();
            Integer releaseYear = album.getReleaseYear();
            Artist artist = album.getArtist();
            String firstName = artist.getFirstname();
            String lastName = artist.getLastname();

            if(isrc == null || isrc.trim().isEmpty() ||
                    title == null || title.trim().isEmpty() ||
                    firstName == null || firstName.trim().isEmpty() ||
                    lastName == null || lastName.trim().isEmpty() ||
                    releaseYear == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Failed to add album: missing required fields")
                        .build();
            }
            else if(releaseYear <= 0) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to add album: invalid releaseYear")
                        .build();
            }

            boolean success = albumManager.addAlbum(album);

            if (success) {
                return Response.status(Response.Status.OK)
                        .entity(album)
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAlbum(Album album) {
        try {
            String title = album.getTitle();
            Integer releaseYear = album.getReleaseYear();
            Artist artist = album.getArtist();
            String firstName = artist.getFirstname();
            String lastName = artist.getLastname();

            if(title == null || title.trim().isEmpty() ||
                    firstName == null || firstName.trim().isEmpty() ||
                    lastName == null || lastName.trim().isEmpty() ||
                    releaseYear == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Failed to update album: missing required fields")
                        .build();
            }
            else if(releaseYear <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Failed to update album: invalid releaseYear")
                        .build();
            }

            boolean success = albumManager.updateAlbum(album);

            if (success) {
                return Response.status(Response.Status.OK)
                        .entity(album)
                        .build();
            }
            else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to update album: no album with an ISRC of " + album.getIsrc())
                        .build();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to update the album")
                    .build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{isrc}")
    public Response deleteAlbum(@PathParam("isrc") String isrc) {
        try {
            boolean success = albumManager.deleteAlbum(isrc);

            if (success) {
                return Response.status(Response.Status.OK)
                        .entity("Successfully deleted album")
                        .build();
            }
            else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to delete album with an ISRC of " + isrc)
                        .build();
            }
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to delete the album")
                    .build();
        }
    }
}
