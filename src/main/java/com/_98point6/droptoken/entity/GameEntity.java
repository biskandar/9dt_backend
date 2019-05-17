package com._98point6.droptoken.entity;

import com._98point6.droptoken.common.StringPrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "GAMES")
public class GameEntity {

  @Id
  @Column(name = "GAME_ID", unique = true, updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game-id-sequence-generator")
  @GenericGenerator(
      name = "game-id-sequence-generator",
      strategy = "com._98point6.droptoken.common.StringPrefixedSequenceIdGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
          @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "gameid"),
          @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d"),
      }
  )
  private String gameId;

  @Column(name = "PLAYERS", length = 128, nullable = false)
  @NotNull
  private String players;

  @Column(name = "COLUMNS", nullable = false)
  @NotNull
  private int columns;

  @Column(name = "ROWS", nullable = false)
  @NotNull
  private int rows;

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public String getPlayers() {
    return players;
  }

  public void setPlayers(String players) {
    this.players = players;
  }

  public int getColumns() {
    return columns;
  }

  public void setColumns(int columns) {
    this.columns = columns;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GameEntity)) return false;
    GameEntity that = (GameEntity) o;
    if (getColumns() != that.getColumns()) return false;
    if (getRows() != that.getRows()) return false;
    if (getGameId() != null ? !getGameId().equals(that.getGameId()) : that.getGameId() != null) return false;
    return getPlayers() != null ? getPlayers().equals(that.getPlayers()) : that.getPlayers() == null;

  }

  @Override
  public int hashCode() {
    int result = getGameId() != null ? getGameId().hashCode() : 0;
    result = 31 * result + (getPlayers() != null ? getPlayers().hashCode() : 0);
    result = 31 * result + getColumns();
    result = 31 * result + getRows();
    return result;
  }

  @Override
  public String toString() {
    return "GameEntity{" +
        "gameId='" + gameId + '\'' +
        ", players='" + players + '\'' +
        ", columns=" + columns +
        ", rows=" + rows +
        '}';
  }

}
