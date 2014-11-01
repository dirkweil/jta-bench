package de.gedoplan.beantrial.jtabench.service;

import de.gedoplan.beantrial.jtabench.entity.Konto;
import de.gedoplan.beantrial.jtabench.persistence.BuchungRepository;
import de.gedoplan.beantrial.jtabench.persistence.KontoRepository;

import java.text.NumberFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ApplicationScoped
public class NoOpBenchmarkService
{
  private int                       rampupCount = 1000;
  private int                       sampleCount = 100000;

  @Inject
  KontoRepository                   kontoRepository;
  @Inject
  BuchungRepository                 buchungRepository;
  @Inject
  NoOpSampleService                 noOpSampleService;
  @Inject
  EntityManager                     entityManager;

  private static final Log          LOG         = LogFactory.getLog(NoOpBenchmarkService.class);
  private static final NumberFormat FORMAT      = NumberFormat.getIntegerInstance();

  public void run()
  {

    // Mit leerem Datenbestand starten
    this.buchungRepository.purge();
    this.kontoRepository.purge();
    this.noOpSampleService.clearSuccessfulCount();
    this.entityManager.clear();

    // Warmlaufen
    for (int i = 0; i < this.rampupCount; ++i)
    {
      try
      {
        this.noOpSampleService.doSomething();
      }
      catch (SampleFailedException e)
      {
        // ignore
      }
    }

    // Messen
    long startNanos = System.nanoTime();
    for (int i = 0; i < this.sampleCount; ++i)
    {
      try
      {
        this.noOpSampleService.doSomething();
      }
      catch (SampleFailedException e)
      {
        // ignore
      }
    }
    long stopNanos = System.nanoTime();
    long usedNanos = stopNanos - startNanos;

    long usedNanosPerSample = usedNanos / this.sampleCount;

    LOG.info(FORMAT.format(this.sampleCount) + " samples");
    LOG.info(FORMAT.format(usedNanos) + " ns used ");
    LOG.info(FORMAT.format(usedNanosPerSample) + " ns used per sample");

    // Test
    Number saldo0 = (Number) this.entityManager.createNativeQuery("select SALDO from " + Konto.TABLE_NAME + " where ID=0").getSingleResult();
    if (saldo0.doubleValue() != this.noOpSampleService.getSuccessfulCount())
    {
      LOG.error("Konto 0 hat falschen Saldo (Ist=" + saldo0 + ", Soll=" + this.noOpSampleService.getSuccessfulCount() + ")");
    }

  }
}
