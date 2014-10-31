package de.gedoplan.beantrial.jtabench.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer
{
  @PersistenceContext(unitName = "jta")
  EntityManager entityManager;

  @Produces
  public EntityManager getEntityManager()
  {
    return this.entityManager;
  }

}
