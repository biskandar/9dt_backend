package com._98point6.droptoken;

import com._98point6.droptoken.common.GameState;
import com._98point6.droptoken.common.MoveType;
import com._98point6.droptoken.dao.GameDAO;
import com._98point6.droptoken.dao.MoveDAO;
import com._98point6.droptoken.entity.GameEntity;
import com._98point6.droptoken.entity.MoveEntity;
import com._98point6.droptoken.factory.GameEntityBuilder;
import com._98point6.droptoken.factory.MoveEntityBuilder;
import com._98point6.droptoken.model.*;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@Path("/drop_token")
@Produces(MediaType.APPLICATION_JSON)
public class DropTokenResource {

  private static final Logger logger = LoggerFactory.getLogger(DropTokenResource.class);

  private GameDAO gameDAO;
  private MoveDAO moveDAO;

  public DropTokenResource(GameDAO gameDAO, MoveDAO moveDAO) {
    this.gameDAO = gameDAO;
    this.moveDAO = moveDAO;
  }

  @GET
  @Timed
  @UnitOfWork
  public Response getGames() {
    List<GameEntity> listGames = gameDAO.findAll();
    List<String> listGameNames = listGames.stream().map(g -> g.getGameId()).collect(Collectors.toList());
    logger.debug("listGameNames={}", listGameNames);
    GetGamesResponse response = new GetGamesResponse.Builder()
        .games(listGameNames)
        .build();
    logger.debug("response={}", response);
    return Response.ok(response).build();
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
        .withState(GameState.IN_PROGRESS)
        .build()
    );
    logger.debug("gameEntity={}", gameEntity);
    CreateGameResponse response = new CreateGameResponse.Builder()
        .gameId(gameEntity.getGameId())
        .build();
    logger.debug("response={}", response);
    return Response.ok(response).build();
  }

  @Path("/{id}")
  @GET
  @Timed
  @UnitOfWork
  public Response getGameStatus(@PathParam("id") String gameId) {
    logger.info("gameId = {}", gameId);
    GameEntity gameEntity = gameDAO.findById(gameId);
    logger.debug("gameEntity={}", gameEntity);
    if (gameEntity == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    List<String> players = Stream.of(gameEntity.getPlayers().split(",")).map(s -> new String(s)).collect(Collectors.toList());
    logger.debug("players={}", players);
    GameStatusResponse.Builder builder = new GameStatusResponse.Builder()
        .players(players)
        .state(gameEntity.getState().toString());
    if (gameEntity.getState() == GameState.DONE) {
      builder.winner(gameEntity.getWinner());
    }
    GameStatusResponse response = builder.build();
    logger.debug("response={}", response);
    return Response.ok(response).build();
  }

  @Path("/{id}/{playerId}")
  @POST
  @Timed
  @UnitOfWork
  public Response postMove(@PathParam("id") String gameId, @PathParam("playerId") String playerId, PostMoveRequest request) {
    logger.info("gameId={}, playerId={}, move={}", gameId, playerId, request);
    GameEntity gameEntity = gameDAO.findById(gameId);
    if (gameEntity == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    logger.debug("gameEntity={}", gameEntity);
    MoveEntity moveEntity = moveDAO.insert(MoveEntityBuilder.aMoveEntity()
        .withType(MoveType.MOVE)
        .withGame(gameEntity)
        .withPlayer(playerId)
        .withColumn(request.getColumn())
        .build()
    );
    logger.debug("moveEntity={}", moveEntity);
    StringBuilder moveLink = new StringBuilder();
    moveLink.append(gameEntity.getGameId()).append("/moves/").append(moveEntity.getMoveId());
    logger.debug("moveLink={}", moveLink.toString());
    PostMoveResponse response = new PostMoveResponse.Builder()
        .moveLink(moveLink.toString())
        .build();
    logger.debug("response={}", response);
    return Response.ok(response).build();
  }

  @Path("/{id}/{playerId}")
  @DELETE
  @Timed
  @UnitOfWork
  public Response playerQuit(@PathParam("id") String gameId, @PathParam("playerId") String playerId) {
    logger.info("gameId={}, playerId={}", gameId, playerId);
    GameEntity gameEntity = gameDAO.findById(gameId);
    logger.debug("gameEntity={}", gameEntity);
    if (gameEntity == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    MoveEntity moveEntity = moveDAO.insert(MoveEntityBuilder.aMoveEntity()
        .withType(MoveType.QUIT)
        .withGame(gameEntity)
        .withPlayer(playerId)
        .build()
    );
    logger.debug("moveEntity={}", moveEntity);
    logger.debug("Change game state : {} -> {}", gameEntity.getState(), GameState.DONE);
    gameEntity.setState(GameState.DONE);
    gameDAO.update(gameEntity);
    return Response.status(Response.Status.ACCEPTED).build();
  }

  @Path("/{id}/moves")
  @GET
  @Timed
  @UnitOfWork
  public Response getMoves(@PathParam("id") String gameId, @QueryParam("start") Integer start, @QueryParam("until") Integer until) {
    logger.info("gameId={}, start={}, until={}", gameId, start, until);
    GameEntity gameEntity = gameDAO.findById(gameId);
    logger.debug("gameEntity={}", gameEntity);
    if (gameEntity == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    List<MoveEntity> listMoveEntities = moveDAO.findByStartAndUntil(start, until);
    logger.debug("listMoveEntities.size={}", listMoveEntities.size());
    List<GetMoveResponse> listGetMoveResponses = listMoveEntities.stream()
        .map(e -> new GetMoveResponse.Builder()
            .type(e.getType().toString())
            .player(e.getPlayer())
            .column(e.getColumn())
            .build())
        .collect(Collectors.toList());
    listGetMoveResponses.stream().forEach(r -> logger.debug("getMoveResponse={}", r));
    GetMovesResponse response = new GetMovesResponse.Builder()
        .moves(listGetMoveResponses)
        .build();
    logger.debug("response={}", response);
    return Response.ok(response).build();
  }

  @Path("/{id}/moves/{moveId}")
  @GET
  @Timed
  @UnitOfWork
  public Response getMove(@PathParam("id") String gameId, @PathParam("moveId") String moveId) {
    logger.info("gameId={}, moveId={}", gameId, moveId);
    GameEntity gameEntity = gameDAO.findById(gameId);
    logger.debug("gameEntity={}", gameEntity);
    if (gameEntity == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    MoveEntity moveEntity = moveDAO.findById(moveId);
    logger.debug("moveEntity={}", moveEntity);
    GetMoveResponse response = new GetMoveResponse.Builder()
        .type(moveEntity.getType().toString())
        .player(moveEntity.getPlayer())
        .column(moveEntity.getColumn())
        .build();
    logger.debug("response={}", response);
    return Response.ok(response).build();
  }

}
