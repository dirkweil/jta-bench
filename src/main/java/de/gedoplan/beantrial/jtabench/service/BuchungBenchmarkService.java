package de.gedoplan.beantrial.jtabench.service;

import de.gedoplan.beantrial.jtabench.entity.Konto;
import de.gedoplan.beantrial.jtabench.persistence.BuchungRepository;
import de.gedoplan.beantrial.jtabench.persistence.KontoRepository;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ApplicationScoped
public class BuchungBenchmarkService
{
  private int                       rampupCount = 100;
  private int                       sampleCount = 10000;

  @Inject
  KontoRepository                   kontoRepository;
  @Inject
  BuchungRepository                 buchungRepository;
  @Inject
  BuchungSampleService              buchungSampleService;
  @Inject
  EntityManager                     entityManager;

  private static final Log          LOG         = LogFactory.getLog(BuchungBenchmarkService.class);
  private static final NumberFormat FORMAT      = NumberFormat.getIntegerInstance();

  public void run()
  {

    // Mit leerem Datenbestand starten
    this.buchungRepository.purge();
    this.kontoRepository.purge();
    this.buchungSampleService.getSaldoMap().clear();
    this.entityManager.clear();

    // Warmlaufen
    for (int i = 0; i < this.rampupCount; ++i)
    {
      try
      {
        this.buchungSampleService.createSampleBuchung();
      }
      catch (BuchungFailedException e)
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
        this.buchungSampleService.createSampleBuchung();
      }
      catch (BuchungFailedException e)
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

    // Testen
    for (Entry<Integer, BigDecimal> entry : this.buchungSampleService.getSaldoMap().entrySet())
    {
      int kontoId = entry.getKey();
      BigDecimal sollSaldo = entry.getValue();

      Konto konto = this.kontoRepository.findById(kontoId);
      if (konto == null)
      {
        LOG.error("Konto " + kontoId + " fehlt in der DB");
      }
      else
      {
        BigDecimal istSaldo = konto.getSaldo();
        if (istSaldo.compareTo(sollSaldo) != 0)
        {
          LOG.error("Konto " + kontoId + " hat falschen Saldo (Soll=" + sollSaldo + ", Ist=" + istSaldo + ")");
        }
      }
    }
  }
}
