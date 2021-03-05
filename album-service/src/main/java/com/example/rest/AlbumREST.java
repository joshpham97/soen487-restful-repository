package com.example.rest;

import factories.ManagerFactory;
import repository.core.*;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("album")
public class AlbumREST {
    private IAlbumManager albumManager = (IAlbumManager) ManagerFactory.ALBUM.getManager();
    private ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAlbum() {
        try {
            List<Album> albums = albumManager.listAlbum();
            GenericEntity<List<Album>> entity = new GenericEntity<List<Album>>(albums) {};

            return Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to get the list of albums")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{isrc}")
    public Response getAlbum(@PathParam("isrc") String isrc) {
        try {
            Album album = albumManager.getAlbum(isrc);

            return Response.status(Response.Status.OK)
                    .entity(album)
                    .build();
        } catch(RepException re) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(re.getMessage())
                    .build();
        } catch(Exception e) {
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
            album.trim();
            boolean success = albumManager.addAlbum(album);

            if(!success) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(String.format("Failed to add album: album with an ISRC of %s already exists", album.getIsrc()))
                        .build();
            }

            logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.ADD, album.getIsrc()));

            return Response.status(Response.Status.OK)
                    .entity(album)
                    .build();
        } catch(RepException re) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(re.getMessage())
                    .build();
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
            album.trim();
            boolean success = albumManager.updateAlbum(album);

            if (!success) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to update album: no album with an ISRC of " + album.getIsrc())
                        .build();
            }

            logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.UPDATE, album.getIsrc()));

            return Response.status(Response.Status.OK)
                    .entity(album)
                    .build();
        } catch(RepException re) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(re.getMessage())
                    .build();
        } catch(Exception e) {
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

            if(!success) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Failed to delete album with an ISRC of " + isrc)
                        .build();
            }

            logManager.addLog(new Log(LocalDateTime.now(), Log.ChangeType.DELETE, isrc));

            return Response.status(Response.Status.OK)
                    .entity("Successfully deleted album")
                    .build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to delete the album")
                    .build();
        }
    }
}
