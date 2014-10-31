package de.gedoplan.beantrial.jtabench.presentation;

import de.gedoplan.beantrial.jtabench.service.BuchungBenchmarkService;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class BuchungBenchmarkPresenter
{
  @Inject
  BuchungBenchmarkService buchungBenchmarkService;

  public void runBenchmark()
  {
    this.buchungBenchmarkService.run();
  }
}
