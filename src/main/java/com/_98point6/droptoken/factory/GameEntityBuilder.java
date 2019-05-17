package com._98point6.droptoken.factory;

import com._98point6.droptoken.entity.GameEntity;

public final class GameEntityBuilder {

  private String gameId;
  private String players;
  private int columns;
  private int rows;

  private GameEntityBuilder() {
  }

  public static GameEntityBuilder aGameEntity() {
    return new GameEntityBuilder();
  }

  public GameEntityBuilder withGameId(String gameId) {
    this.gameId = gameId;
    return this;
  }

  public GameEntityBuilder withPlayers(String players) {
    this.players = players;
    return this;
  }

  public GameEntityBuilder withColumns(int columns) {
    this.columns = columns;
    return this;
  }

  public GameEntityBuilder withRows(int rows) {
    this.rows = rows;
    return this;
  }

  public GameEntity build() {
    GameEntity gameEntity = new GameEntity();
    gameEntity.setGameId(gameId);
    gameEntity.setPlayers(players);
    gameEntity.setColumns(columns);
    gameEntity.setRows(rows);
    return gameEntity;
  }

}
