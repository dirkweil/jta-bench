package de.gedoplan.beantrial.jtabench.persistence;

import de.gedoplan.beantrial.jtabench.entity.Konto;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@ApplicationScoped
public class KontoRepository
{
  @Inject
  EntityManager entityManager;

  @Transactional
  public void purge()
  {
    this.entityManager.createQuery("delete Konto x").executeUpdate();
    this.entityManager.clear();
  }

  @Transactional(TxType.MANDATORY)
  public void persist(Konto entity)
  {
    this.entityManager.persist(entity);
  }

  public Konto findById(int id)
  {
    return this.entityManager.find(Konto.class, id);
  }

  public List<Konto> findAll()
  {
    return this.entityManager.createQuery("select x from Konto x", Konto.class).getResultList();
  }

}
