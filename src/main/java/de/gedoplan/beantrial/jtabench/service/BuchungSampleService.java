package de.gedoplan.beantrial.jtabench.service;

import de.gedoplan.beantrial.jtabench.entity.Buchung;
import de.gedoplan.beantrial.jtabench.entity.Konto;
import de.gedoplan.beantrial.jtabench.persistence.BuchungRepository;
import de.gedoplan.beantrial.jtabench.persistence.KontoRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class BuchungSampleService
{
  private static final int         MAX_KONTO_ID = 10000;
  private static final int         MAX_CENTS    = 1000;
  private static final int         FAIL_PERCENT = 10;

  private Random                   random       = new Random(0);

  private Map<Integer, BigDecimal> saldoMap     = new HashMap<>();

  @Inject
  KontoRepository                  kontoRepository;

  @Inject
  BuchungRepository                buchungRepository;

  @Transactional
  public void createSampleBuchung()
  {
    int kontoId = this.random.nextInt(MAX_KONTO_ID) + 1;
    Konto konto = this.kontoRepository.findById(kontoId);
    if (konto == null)
    {
      konto = new Konto(kontoId);
      this.kontoRepository.persist(konto);
    }

    BigDecimal betrag = BigDecimal.valueOf(this.random.nextInt(MAX_CENTS) + 1, 2);

    Buchung buchung = new Buchung(konto, betrag);
    this.buchungRepository.persist(buchung);

    boolean fail = this.random.nextInt(100) < FAIL_PERCENT;
    if (fail)
    {
      throw new SampleFailedException();
    }

    BigDecimal saldo = this.saldoMap.get(kontoId);
    if (saldo == null)
    {
      saldo = betrag;
    }
    else
    {
      saldo = saldo.add(betrag);
    }
    this.saldoMap.put(kontoId, saldo);
  }

  public Map<Integer, BigDecimal> getSaldoMap()
  {
    return this.saldoMap;
  }
}
