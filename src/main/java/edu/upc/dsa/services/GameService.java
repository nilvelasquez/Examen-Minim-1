package edu.upc.dsa.services;


import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Product;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/Game", description = "Endpoint to Game Service")
@Path("/game")
public class GameService {
    private GameManager manager;
    private GameManagerImpl impl;
    public GameService() {
        this.manager = GameManagerImpl.getInstance();
        if (manager.numUsers()==0) {
            this.manager.addUser("1", "Nil", "Velàsquez");
        }
        if (manager.numProducts()==0){
            this.manager.addProduct("1","Life potion",15);
        }
    }
    @POST
    @ApiOperation(value = "Add a user", notes = "We add a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/Add user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) {

        if (user.getNombre() == null) return Response.status(500).entity(user).build();
        this.manager.addUser(user.getUserId(), user.getNombre(), user.getApellido());
        return Response.status(201).entity(user).build();
    }
    @POST
    @ApiOperation(value = "Create new product", notes = "We create a new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/addProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newProduct(Product product) {
        if (product.getProductId() == null || product.getDescription() == null || product.getPrice() == 0){
            return Response.status(500).entity(product).build();
        }
        this.manager.addProduct(product.getProductId(), product.getDescription(), product.getPrice());
        return Response.status(201).entity(product).build();
    }
    @POST
    @ApiOperation(value = "Buy a product ", notes = "The user buys a product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/compraProduct/{userId}/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response compraProduct(@PathParam("userId") String userId, @PathParam    ("productId") String productId) {
        Product product = this.impl.getProductForProductId(productId);
        User user = this.impl.getUserByUserId(userId);
        if (product.getProductId() == null || product.getDescription() == null) return Response.status(500).build();
        this.manager.comprarProduct(user.getUserId(), product.getProductId());
        return Response.status(201).entity(product).build();
    }
    @GET
    @ApiOperation(value = "Get estado", notes = "We obtain the state of a game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Error")
    })
    @Path("/getEstado/{partyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEstado(@PathParam("partyId") String partyId) {
        if (partyId==null) {
            return Response.status(500).entity(partyId).build();
        }
        else {
            String estado = this.manager.getEstado(partyId);
            return Response.status(201).entity(estado).build();
        }
    }
}
