package de.gedoplan.beantrial.jtabench.entity;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = Konto.TABLE_NAME)
public class Konto
{
  public static final String TABLE_NAME = "JTABENCH_KONTO";

  @Id
  private int                id;

  private BigDecimal         saldo;

  public Konto(int id)
  {
    this.id = id;
    this.saldo = BigDecimal.ZERO;
  }

  public int getId()
  {
    return this.id;
  }

  public BigDecimal getSaldo()
  {
    return this.saldo;
  }

  void addToSaldo(BigDecimal betrag)
  {
    this.saldo = this.saldo.add(betrag);
  }

  protected Konto()
  {
  }
}
