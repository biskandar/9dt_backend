package com._98point6.droptoken;

import com._98point6.droptoken.dao.GameDAO;
import com._98point6.droptoken.dao.MoveDAO;
import com._98point6.droptoken.entity.GameEntity;
import com._98point6.droptoken.entity.MoveEntity;
import io.dropwizard.testing.junit.DAOTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryTablesTest {

  @Rule
  public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
      .addEntityClass(GameEntity.class)
      .addEntityClass(MoveEntity.class)
      .build();

  private GameDAO gameDAO;
  private MoveDAO moveDAO;

  @Before
  public void setUp() throws Exception {
    gameDAO = new GameDAO(daoTestRule.getSessionFactory());
    moveDAO = new MoveDAO(daoTestRule.getSessionFactory());
  }

  @Test
  public void queryTableGames() {
    List<GameEntity> listGames = gameDAO.findAll();
    assertThat(listGames).isNotNull();
    listGames.stream().forEach(System.out::println);
  }

  @Test
  public void queryTableMoves() {

    List<MoveEntity> listMoves = null;

    listMoves = moveDAO.findAll();
    assertThat(listMoves).isNotNull();
    listMoves.stream().forEach(System.out::println);

    listMoves = moveDAO.findByStartAndUntil(0, 2);
    assertThat(listMoves).isNotNull();
    listMoves.stream().forEach(System.out::println);

  }


}
