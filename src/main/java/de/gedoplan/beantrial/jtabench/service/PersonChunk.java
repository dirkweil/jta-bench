package de.gedoplan.beantrial.jtabench.service;

import de.gedoplan.beantrial.jtabench.entity.Person;
import de.gedoplan.beantrial.jtabench.persistence.PersonRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class PersonChunk
{
  @Inject
  PersonRepository personRepository;

  @Transactional
  public void createPersonen(int count)
  {
    for (int i = 0; i < count; ++i)
    {
      Person person = new Person("Testperson");
      this.personRepository.persist(person);
    }
  }
}
