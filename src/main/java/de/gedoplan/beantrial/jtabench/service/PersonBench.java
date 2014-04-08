package de.gedoplan.beantrial.jtabench.service;

import de.gedoplan.beantrial.jtabench.persistence.PersonRepository;

import javax.inject.Inject;

public class PersonBench
{
  @Inject
  PersonRepository personRepository;

  @Inject
  PersonChunk      personChunk;

  public long doBench(int insertCount, int insertsPerTx)
  {
    this.personRepository.purge();

    long start = System.currentTimeMillis();

    while (insertCount > 0)
    {
      int count = insertsPerTx;
      if (count > insertCount)
      {
        count = insertCount;
      }

      this.personChunk.createPersonen(count);
      insertCount -= count;
    }

    return System.currentTimeMillis() - start;
  }
}
