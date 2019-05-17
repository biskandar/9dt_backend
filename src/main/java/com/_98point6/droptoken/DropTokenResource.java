package com._98point6.droptoken;

import com._98point6.droptoken.dao.GameDAO;
import com._98point6.droptoken.entity.GameEntity;
import com._98point6.droptoken.factory.GameEntityBuilder;
import com._98point6.droptoken.model.CreateGameRequest;
import com._98point6.droptoken.model.CreateGameResponse;
import com._98point6.droptoken.model.GameStatusResponse;
import com._98point6.droptoken.model.GetGamesResponse;
import com._98point6.droptoken.model.GetMoveResponse;
import com._98point6.droptoken.model.GetMovesResponse;
import com._98point6.droptoken.model.PostMoveRequest;
import com._98point6.droptoken.model.PostMoveResponse;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Path("/drop_token")
@Produces(MediaType.APPLICATION_JSON)
public class DropTokenResource {

  private static final Logger logger = LoggerFactory.getLogger(DropTokenResource.class);

  private GameDAO gameDAO;

  public DropTokenResource(GameDAO gameDAO) {
    this.gameDAO = gameDAO;
  }

  @GET
  @Timed
  @UnitOfWork
  public Response getGames() {
    List<GameEntity> listGames = gameDAO.findAll();
    return Response.ok(new GetGamesResponse.Builder()
        .games(listGames.stream().map(g -> g.getGameId()).collect(Collectors.toList()))
        .build()
    ).build();
  }

  @POST
  @Timed
  @UnitOfWork
  public Response createNewGame(CreateGameRequest request) {
    logger.info("request={}", request);
    GameEntity gameEntity = gameDAO.insert(GameEntityBuilder.aGameEntity()
        .withPlayers(request.getPlayers().stream().map(Object::toString).collect(Collectors.joining(",")))
        .withColumns(request.getColumns())
        .withRows(request.getRows())
        .build()
    );
    return Response.ok(new CreateGameResponse.Builder()
        .gameId(gameEntity.getGameId())
        .build()
    ).build();
  }

  @Path("/{id}")
  @GET
  public Response getGameStatus(@PathParam("id") String gameId) {
    logger.info("gameId = {}", gameId);
    return Response.ok(new GameStatusResponse()).build();
  }

  @Path("/{id}/{playerId}")
  @POST
  public Response postMove(@PathParam("id") String gameId, @PathParam("playerId") String playerId, PostMoveRequest request) {
    logger.info("gameId={}, playerId={}, move={}", gameId, playerId, request);
    return Response.ok(new PostMoveResponse()).build();
  }

  @Path("/{id}/{playerId}")
  @DELETE
  public Response playerQuit(@PathParam("id") String gameId, @PathParam("playerId") String playerId) {
    logger.info("gameId={}, playerId={}", gameId, playerId);
    return Response.status(202).build();
  }

  @Path("/{id}/moves")
  @GET
  public Response getMoves(@PathParam("id") String gameId, @QueryParam("start") Integer start, @QueryParam("until") Integer until) {
    logger.info("gameId={}, start={}, until={}", gameId, start, until);
    return Response.ok(new GetMovesResponse()).build();
  }

  @Path("/{id}/moves/{moveId}")
  @GET
  public Response getMove(@PathParam("id") String gameId, @PathParam("moveId") Integer moveId) {
    logger.info("gameId={}, moveId={}", gameId, moveId);
    return Response.ok(new GetMoveResponse()).build();
  }

}
