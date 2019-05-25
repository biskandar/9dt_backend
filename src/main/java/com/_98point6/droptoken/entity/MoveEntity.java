package com._98point6.droptoken.entity;

import com._98point6.droptoken.common.MoveType;
import com._98point6.droptoken.common.StringPrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MOVES")
public class MoveEntity {

  @Id
  @Column(name = "MOVE_ID", unique = true, updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "move-id-sequence-generator")
  @GenericGenerator(
      name = "move-id-sequence-generator",
      strategy = "com._98point6.droptoken.common.StringPrefixedSequenceIdGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
          @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "M"),
          @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d"),
      }
  )
  private String moveId;


  @ManyToOne
  @JoinColumn(name = "GAME_ID")
  @NotNull
  private GameEntity game;

  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  private MoveType type;

  @Column(name = "PLAYER", nullable = false)
  private String player;

  @Column(name = "COLUMN", nullable = false)
  private int column;

  public String getMoveId() {
    return moveId;
  }

  public void setMoveId(String moveId) {
    this.moveId = moveId;
  }

  public GameEntity getGame() {
    return game;
  }

  public void setGame(GameEntity game) {
    this.game = game;
  }

  public MoveType getType() {
    return type;
  }

  public void setType(MoveType type) {
    this.type = type;
  }

  public String getPlayer() {
    return player;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MoveEntity)) return false;

    MoveEntity that = (MoveEntity) o;

    if (getColumn() != that.getColumn()) return false;
    if (!getMoveId().equals(that.getMoveId())) return false;
    if (!getGame().equals(that.getGame())) return false;
    if (getType() != that.getType()) return false;
    return getPlayer().equals(that.getPlayer());

  }

  @Override
  public int hashCode() {
    int result = getMoveId().hashCode();
    result = 31 * result + getGame().hashCode();
    result = 31 * result + getType().hashCode();
    result = 31 * result + getPlayer().hashCode();
    result = 31 * result + getColumn();
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MoveEntity{");
    sb.append("moveId='").append(moveId).append('\'');
    sb.append(", game=").append(game);
    sb.append(", type=").append(type);
    sb.append(", player='").append(player).append('\'');
    sb.append(", column=").append(column);
    sb.append('}');
    return sb.toString();
  }

}
