package com._98point6.droptoken;

import com._98point6.droptoken.common.GameState;
import com._98point6.droptoken.dao.GameDAO;
import com._98point6.droptoken.dao.MoveDAO;
import com._98point6.droptoken.entity.GameEntity;
import com._98point6.droptoken.factory.GameEntityBuilder;
import com._98point6.droptoken.model.GameStatusResponse;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class DropTokenResourceTest {

  private static final GameDAO gameDAO = mock(GameDAO.class);
  private static final MoveDAO moveDAO = mock(MoveDAO.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new DropTokenResource(gameDAO, moveDAO))
      .build();

  private final GameEntity gameEntityFact = GameEntityBuilder.aGameEntity()
      .withGameId("id1")
      .withPlayers("p1,p2")
      .withRows(4).withColumns(4)
      .withState(GameState.IN_PROGRESS)
      .build();

  private final GameStatusResponse gameStatusResponseFact = new GameStatusResponse.Builder()
      .players(Arrays.asList("p1", "p2"))
      .state(GameState.IN_PROGRESS.toString())
      .build();

  @Before
  public void before() {
    when(gameDAO.findById(eq("id1")))
        .thenReturn(gameEntityFact);
  }

  @After
  public void after() {
    reset(gameDAO);
  }

  @Test
  public void testGetGameId1() {
    GameStatusResponse gameStatusResponse = resources.target("/drop_token/id1").request().get(GameStatusResponse.class);
    assertThat(gameStatusResponse.getPlayers())
        .isEqualTo(gameStatusResponseFact.getPlayers());
    assertThat(gameStatusResponse.getState())
        .isEqualTo(gameStatusResponseFact.getState());
  }

}
