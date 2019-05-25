package com._98point6.droptoken.dao;

import com._98point6.droptoken.entity.MoveEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class MoveDAO extends AbstractDAO<MoveEntity> {

  public MoveDAO(SessionFactory factory) {
    super(factory);
  }

  @SuppressWarnings("unchecked")
  public List<MoveEntity> findAll() {
    return (List<MoveEntity>) currentSession().createQuery("from MoveEntity").list();
  }

  public MoveEntity findById(String moveId) {
    return (MoveEntity) currentSession().get(MoveEntity.class, moveId);
  }

  public void delete(MoveEntity moveEntity) {
    currentSession().delete(moveEntity);
  }

  public void deleteAll() {
    findAll().stream().forEach(g -> delete(g));
  }

  public void update(MoveEntity moveEntity) {
    currentSession().saveOrUpdate(moveEntity);
  }

  public MoveEntity insert(MoveEntity moveEntity) {
    return persist(moveEntity);
  }

  public List<MoveEntity> findByStartAndUntil(int start, int until) {
    Query query = currentSession()
        .createQuery("from MoveEntity");
    query.setFirstResult(start);
    query.setMaxResults(until - start);
    return query.getResultList();
  }

}
