package com._98point6.droptoken.factory;

import com._98point6.droptoken.common.MoveType;
import com._98point6.droptoken.entity.GameEntity;
import com._98point6.droptoken.entity.MoveEntity;

public final class MoveEntityBuilder {
  private String moveId;
  private GameEntity game;
  private MoveType type;
  private String player;
  private int column;

  private MoveEntityBuilder() {
  }

  public static MoveEntityBuilder aMoveEntity() {
    return new MoveEntityBuilder();
  }

  public MoveEntityBuilder withMoveId(String moveId) {
    this.moveId = moveId;
    return this;
  }

  public MoveEntityBuilder withGame(GameEntity game) {
    this.game = game;
    return this;
  }

  public MoveEntityBuilder withType(MoveType type) {
    this.type = type;
    return this;
  }

  public MoveEntityBuilder withPlayer(String player) {
    this.player = player;
    return this;
  }

  public MoveEntityBuilder withColumn(int column) {
    this.column = column;
    return this;
  }

  public MoveEntity build() {
    MoveEntity moveEntity = new MoveEntity();
    moveEntity.setMoveId(moveId);
    moveEntity.setGame(game);
    moveEntity.setType(type);
    moveEntity.setPlayer(player);
    moveEntity.setColumn(column);
    return moveEntity;
  }
}
