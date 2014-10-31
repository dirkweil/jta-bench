package de.gedoplan.beantrial.jtabench.persistence;

import de.gedoplan.beantrial.jtabench.entity.Buchung;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@ApplicationScoped
public class BuchungRepository
{
  @Inject
  EntityManager entityManager;

  @Transactional
  public void purge()
  {
    this.entityManager.createQuery("delete Buchung x").executeUpdate();
    this.entityManager.clear();
  }

  @Transactional(TxType.MANDATORY)
  public void persist(Buchung entity)
  {
    this.entityManager.persist(entity);
  }

  public List<Buchung> findAll()
  {
    return this.entityManager.createQuery("select x from Buchung x", Buchung.class).getResultList();
  }

}
