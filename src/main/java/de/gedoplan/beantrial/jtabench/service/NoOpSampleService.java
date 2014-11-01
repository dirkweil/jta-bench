package de.gedoplan.beantrial.jtabench.service;

import de.gedoplan.beantrial.jtabench.entity.Konto;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class NoOpSampleService
{
  private static final int FAIL_PERCENT    = 10;

  private Random           random          = new Random(0);

  @Inject
  EntityManager            entityManager;

  int                      successfulCount = 0;

  @Transactional
  public void doSomething()
  {
    int rowsAffected = this.entityManager.createNativeQuery("update " + Konto.TABLE_NAME + " set SALDO=SALDO+1 where ID=0").executeUpdate();
    if (rowsAffected == 0)
    {
      this.entityManager.createNativeQuery("insert into " + Konto.TABLE_NAME + "(ID, SALDO) values (0,1)").executeUpdate();
    }

    boolean fail = this.random.nextInt(100) < FAIL_PERCENT;
    if (fail)
    {
      throw new SampleFailedException();
    }

    ++this.successfulCount;
  }

  public int getSuccessfulCount()
  {
    return this.successfulCount;
  }

  public void clearSuccessfulCount()
  {
    this.successfulCount = 0;
  }

}
