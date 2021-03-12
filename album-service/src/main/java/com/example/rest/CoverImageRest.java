package com.example.rest;

import factories.ManagerFactory;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import repository.core.CoverImage;
import repository.core.ICoverImageManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.sql.SQLException;

@Path("albumImage")
public class CoverImageRest {
    private ICoverImageManager coverImageManager = (ICoverImageManager) ManagerFactory.COVER_IMAGE.getManager();

    @GET
    @Path("{isrc}")
    public Response getCoverImage(@PathParam("isrc") String isrc) {
        String errorMessage;
        try{
            CoverImage coverImage = coverImageManager.getCoverImageByAlbumIsrc(isrc);
            int blobLength = (int) coverImage.getBlob().length();
            byte[] blobAsBytes = coverImage.getBlob().getBytes(1, blobLength);

            return Response.ok(new StreamingOutput () {
                public void write(OutputStream output) throws IOException, WebApplicationException {
                    output.write(blobAsBytes);
                }}).header("Content-Type", coverImage.getMimeType()).build();

        }catch (SQLException ex){
            errorMessage = "There was an error getting the cover image from the server database.";
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("There was an error getting the cover image from the server database.")
                .build();
    }

    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path("{isrc}")
    public Response createCoverImage(@FormDataParam("file") InputStream fileInputStream,
                                     @FormDataParam("file") FormDataContentDisposition fileMetaData,
                                     @FormDataParam("file") final FormDataBodyPart body,
                                     @PathParam("isrc") String isrc) {
        try {
            boolean isCreated = coverImageManager.getCoverImageByAlbumIsrc(isrc) != null;
            CoverImage coverImage = coverImageManager.createOrUpdateCoverImageIfExist(fileInputStream, body.getMediaType().toString(), isrc);

            if (coverImage != null){
                if (isCreated)
                    return Response.status(Response.Status.OK)
                            .build();
                else
                    return Response.status(Response.Status.CREATED)
                            .build();
            }else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Unable to create or update cover image")
                        .build();
            }
        }
        catch(SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error creating or updating the cover image in the server database.")
                    .build();
        }
    }

    @DELETE
    @Path("{isrc}")
    public Response deleteCoverImage(@PathParam("isrc") String isrc){
        try {
            if(coverImageManager.deleteCoverImage(isrc)){
                return Response.status(Response.Status.OK)
                        .build();
            }else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Unable to delete cover image.")
                        .build();
            }
        }
        catch(SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error creating or updating the cover image in the server database.")
                    .build();
        }
    }
}
