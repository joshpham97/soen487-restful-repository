package com.example.rest;

import factories.ManagerFactory;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import repository.core.CoverImage;
import repository.core.IAlbumManager;
import repository.core.ICoverImageManager;
import repository.core.RepException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.sql.SQLException;

@Path("albumImage")
public class CoverImageREST {
    private IAlbumManager albumManager = (IAlbumManager) ManagerFactory.ALBUM.getManager();

    @GET
    @Path("{isrc}")
    public Response getCoverImage(@PathParam("isrc") String isrc) {
        try{
            CoverImage coverImage = albumManager.getCoverImageByAlbumIsrc(isrc);
            long x = coverImage.getBlob().length();
            int blobLength = (int) coverImage.getBlob().length();
            byte[] blobAsBytes = coverImage.getBlob().getBytes(1, blobLength);

            return Response.ok(new StreamingOutput () {
                public void write(OutputStream output) throws IOException, WebApplicationException {
                    output.write(blobAsBytes);
                }}).header("Content-Type", coverImage.getMimeType()).build();

        }catch (RepException ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage())
                    .build();
        }catch (SQLException ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error reading the cover image of the album with isrc: " + isrc + " on the server.")
                    .build();
        }
    }

    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path("{isrc}")
    public Response createCoverImage(@FormDataParam("file") InputStream fileInputStream,
                                     @FormDataParam("file") FormDataContentDisposition fileMetaData,
                                     @FormDataParam("file") final FormDataBodyPart body,
                                     @PathParam("isrc") String isrc) {
        try {
            boolean isCreated = albumManager.getCoverImageByAlbumIsrc(isrc) != null;
            CoverImage coverImage = albumManager.createOrUpdateCoverImageIfExist(fileInputStream, body.getMediaType().toString(), isrc);

            if (coverImage != null){
                if (isCreated)
                    return Response.status(Response.Status.OK)
                            .build();
                else
                    return Response.status(Response.Status.CREATED)
                            .build();
            }else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Unable to create or update cover image for album with the isrc: " + isrc)
                        .build();
            }
        }
        catch(RepException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("{isrc}")
    public Response deleteCoverImage(@PathParam("isrc") String isrc){
        try {
            if(albumManager.deleteCoverImage(isrc)){
                return Response.status(Response.Status.OK)
                        .build();
            }else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("There was error deleting the cover image of the album with the isrc: " + isrc)
                        .build();
            }
        }
        catch(RepException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage())
                    .build();
        }
    }
}
