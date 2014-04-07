package de.gedoplan.beantrial.jtabench.persistence;

import de.gedoplan.beantrial.jtabench.entity.Person;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class PersonRepository
{
  @Inject
  EntityManager entityManager;

  @Transactional
  public void purge()
  {
    this.entityManager.createQuery("delete Person x").executeUpdate();
    this.entityManager.clear();
  }

  @Transactional
  public void persist(Person entity)
  {
    this.entityManager.persist(entity);
  }

  public Person findById(String id)
  {
    return this.entityManager.find(Person.class, id);
  }

  public List<Person> findAll()
  {
    return this.entityManager.createQuery("select x from Person x", Person.class).getResultList();
  }

}
