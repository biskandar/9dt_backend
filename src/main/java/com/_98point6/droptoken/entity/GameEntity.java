package com._98point6.droptoken.entity;

import com._98point6.droptoken.common.GameState;
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
          @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "G"),
          @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d"),
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

  @Column(name = "STATE")
  @Enumerated(EnumType.STRING)
  private GameState state;

  @Column(name = "WINNER")
  private String winner;

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

  public GameState getState() {
    return state;
  }

  public void setState(GameState state) {
    this.state = state;
  }

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GameEntity)) return false;

    GameEntity that = (GameEntity) o;

    if (getColumns() != that.getColumns()) return false;
    if (getRows() != that.getRows()) return false;
    if (getGameId() != null ? !getGameId().equals(that.getGameId()) : that.getGameId() != null) return false;
    if (getPlayers() != null ? !getPlayers().equals(that.getPlayers()) : that.getPlayers() != null) return false;
    if (getState() != null ? !getState().equals(that.getState()) : that.getState() != null) return false;
    return getWinner() != null ? getWinner().equals(that.getWinner()) : that.getWinner() == null;

  }

  @Override
  public int hashCode() {
    int result = getGameId() != null ? getGameId().hashCode() : 0;
    result = 31 * result + (getPlayers() != null ? getPlayers().hashCode() : 0);
    result = 31 * result + getColumns();
    result = 31 * result + getRows();
    result = 31 * result + (getState() != null ? getState().hashCode() : 0);
    result = 31 * result + (getWinner() != null ? getWinner().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("GameEntity{");
    sb.append("gameId='").append(gameId).append('\'');
    sb.append(", players='").append(players).append('\'');
    sb.append(", columns=").append(columns);
    sb.append(", rows=").append(rows);
    sb.append(", state='").append(state).append('\'');
    sb.append(", winner='").append(winner).append('\'');
    sb.append('}');
    return sb.toString();
  }

}
