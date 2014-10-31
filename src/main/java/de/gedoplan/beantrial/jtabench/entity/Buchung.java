package de.gedoplan.beantrial.jtabench.entity;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Buchung.TABLE_NAME)
public class Buchung
{
  public static final String TABLE_NAME = "JTABENCH_BUCHUNG";

  @Id
  @GeneratedValue
  private long               id;

  @ManyToOne
  private Konto              konto;

  private BigDecimal         betrag;

  public Buchung(Konto konto, BigDecimal betrag)
  {
    this.konto = konto;
    this.betrag = betrag;

    this.konto.addToSaldo(betrag);
  }

  public long getId()
  {
    return this.id;
  }

  public Konto getKonto()
  {
    return this.konto;
  }

  public BigDecimal getBetrag()
  {
    return this.betrag;
  }

  protected Buchung()
  {
  }
}
