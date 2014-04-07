package de.gedoplan.beantrial.jtabench.presentation;

import de.gedoplan.beantrial.jtabench.service.PersonBench;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class PersonPresenter
{
  @Inject
  PersonBench  personBench;

  int          rampUpCount = 5;
  int          benchCount  = 100;
  int          benchSize   = 100;
  int          chunkSize   = 20;

  private long millis;

  public int getRampUpCount()
  {
    return this.rampUpCount;
  }

  public void setRampUpCount(int rampUpCount)
  {
    this.rampUpCount = rampUpCount;
  }

  public int getBenchCount()
  {
    return this.benchCount;
  }

  public void setBenchCount(int retries)
  {
    this.benchCount = retries;
  }

  public int getBenchSize()
  {
    return this.benchSize;
  }

  public void setBenchSize(int benchSize)
  {
    this.benchSize = benchSize;
  }

  public int getChunkSize()
  {
    return this.chunkSize;
  }

  public void setChunkSize(int chunkSize)
  {
    this.chunkSize = chunkSize;
  }

  public long getMillis()
  {
    return this.millis;
  }

  public void doBench()
  {
    for (int i = 0; i < this.rampUpCount; ++i)
    {
      this.personBench.doBench(this.benchSize, this.chunkSize);
    }

    long m = 0;
    for (int i = 0; i < this.benchCount; ++i)
    {
      m += this.personBench.doBench(this.benchSize, this.chunkSize);
    }
    this.millis = m / this.benchCount;
  }

}
