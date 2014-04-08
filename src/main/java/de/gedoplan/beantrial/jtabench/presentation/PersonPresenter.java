package de.gedoplan.beantrial.jtabench.presentation;

import de.gedoplan.beantrial.jtabench.service.PersonBench;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class PersonPresenter
{
  @Inject
  PersonBench  personBench;

  int          rampUpCount  = 5;
  int          benchCount   = 20;

  int          insertCount  = 1000;
  int          insertsPerTx = 1;

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

  public int getInsertCount()
  {
    return this.insertCount;
  }

  public void setInsertCount(int benchSize)
  {
    this.insertCount = benchSize;
  }

  public int getInsertsPerTx()
  {
    return this.insertsPerTx;
  }

  public void setInsertsPerTx(int chunkSize)
  {
    this.insertsPerTx = chunkSize;
  }

  public long getMillis()
  {
    return this.millis;
  }

  public void doBench()
  {
    for (int i = 0; i < this.rampUpCount; ++i)
    {
      this.personBench.doBench(this.insertCount, this.insertsPerTx);
    }

    long m = 0;
    for (int i = 0; i < this.benchCount; ++i)
    {
      m += this.personBench.doBench(this.insertCount, this.insertsPerTx);
    }
    this.millis = m / this.benchCount;
  }

}
