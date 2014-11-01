package de.gedoplan.beantrial.jtabench.presentation;

import de.gedoplan.beantrial.jtabench.service.NoOpBenchmarkService;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class NoOpBenchmarkPresenter
{
  @Inject
  NoOpBenchmarkService noOpBenchmarkService;

  public void runBenchmark()
  {
    this.noOpBenchmarkService.run();
  }
}
