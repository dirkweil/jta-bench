package de.gedoplan.beantrial.jtabench.service;

import de.gedoplan.beantrial.jtabench.persistence.PersonRepository;

import javax.inject.Inject;

public class PersonBench
{
  @Inject
  PersonRepository personRepository;

  @Inject
  PersonChunk      personChunk;

  public long doBench(int benchSize, int chunkSize)
  {
    this.personRepository.purge();

    long start = System.currentTimeMillis();

    for (int i = 0; i < benchSize; ++i)
    {
      this.personChunk.createPersonen(chunkSize);
    }

    return System.currentTimeMillis() - start;
  }

}
