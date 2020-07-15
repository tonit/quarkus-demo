package org.rebaze.sample;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Path("/demo")
public class GreetingResource {

    @Path("/hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/version")
    @GET
    public Response version(@QueryParam("secret") String givenSecret) throws IOException {
        Properties p = new Properties();
        try (InputStream inp = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("git.properties")) {
            p.load(inp);
            ObjectMapper mapper = new ObjectMapper();
            byte[] bytes = mapper.writeValueAsBytes(p);
            return Response.ok().entity(new String(bytes)).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }
}