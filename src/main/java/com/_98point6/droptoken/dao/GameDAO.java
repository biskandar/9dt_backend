package com._98point6.droptoken.dao;

import com._98point6.droptoken.entity.GameEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class GameDAO extends AbstractDAO<GameEntity> {

  public GameDAO(SessionFactory factory) {
    super(factory);
  }

  @SuppressWarnings("unchecked")
  public List<GameEntity> findAll() {
    return (List<GameEntity>) currentSession().createQuery("from GameEntity").list();
  }

  public GameEntity findById(int id) {
    return (GameEntity) currentSession().get(GameEntity.class, id);
  }

  public void delete(GameEntity gameEntity) {
    currentSession().delete(gameEntity);
  }

  public void deleteAll() {
    findAll().stream().forEach(g -> delete(g));
  }

  public void update(GameEntity gameEntity) {
    currentSession().saveOrUpdate(gameEntity);
  }

  public GameEntity insert(GameEntity gameEntity) {
    return persist(gameEntity);
  }

}
