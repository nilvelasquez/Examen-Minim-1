package edu.upc.dsa.minim.services;


import edu.upc.dsa.minim.GameManager;
import edu.upc.dsa.minim.GameManagerImpl;
import edu.upc.dsa.minim.models.*;
import edu.upc.dsa.minim.models.exceptions.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/game")
@Path("/")
public class GameService {
    private GameManager manager;

    public GameService() throws GameDoesNotExistException, AlreadyActiveActivityException {
        this.manager = GameManagerImpl.getInstance();

        if (manager.sizeGames()==0) {
            this.manager.addGame("pichi",2, 6);
            this.manager.addGame("so-pa-po",2, 2);

            this.manager.startGame("Pol", "pichi");
        }

    }

    @POST
    @ApiOperation(value = "create a new Game", notes = "If it does not exist a game with that name, it creates a new game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response= Game.class),
            @ApiResponse(code = 409, message = "Conflict, it already exists a game with that name")

    })
    @Path("/games/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGame(Game game) {
        Game gamecreated = this.manager.addGame(game.getGameId(), game.getEstado());

        if (gamecreated==null)  return Response.status(409).build();
        return Response.status(201).entity(gamecreated).build();
    }

    @PUT
    @ApiOperation(value = "start a Partida", notes = "Start of a game from part of a user, if the user doesn't exists it starts.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully started", response= Partida.class),
            @ApiResponse(code = 404, message = "This game does not exists"),
            @ApiResponse(code = 408 , message = "This user doesn't exists"),
            @ApiResponse(code = 400, message = "This user is already playing!")
    })
    @Path("/player/startpartida")
    public Response startGame(VOPlayerGameCredencials credencials) {
        try{
            Partida partida = this.manager.startGame(credencials.getNameJuego(), credencials.getUsername());
            return Response.status(201).entity(partida).build();
        } catch (GameDoesNotExistException e) {
            return Response.status(404).build();
        } catch (AlreadyActiveActivityException e) {
            return Response.status(400).build();
        } catch (UserDoesNotExistException e){
            return Response.status(408).build();
        }
    }

    @GET
    @ApiOperation(value = "get actual lifes", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "This player does not exists"),
            @ApiResponse(code = 409, message = "This player is not playing right now!")
    })
    @Path("/player/{id}/actuallifes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response currentLifes(@PathParam("id") String id) {
        try{
            String lifes = this.manager.currentLifes(lifes);
            return Response.status(200).entity(lifes).build();
        } catch (UserDoesNotExistException e) {
            return Response.status(404).build();
        } catch (PlayerNotCurrentlyPlayingException e) {
            return Response.status(409).build();
        }
    }
    @PUT
    @ApiOperation(value = "endgame", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Partida ended!"),
            @ApiResponse(code = 404, message = "This player does not exists"),
            @ApiResponse(code = 409, message = "This player is not playing right now!")
    })
    @Path("/player/{id}/endgame")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endGame(@PathParam("id") String id) {
        try{
            User user = this.manager.endGame(id);
            return Response.status(200).build();
        } catch (UserDoesNotExistException e) {
            return Response.status(404).build();
        }
    }
}

