package de.gedoplan.beantrial.jtabench.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NoOpBenchmarkServiceTest
{
  private static CdiContainer cdiContainer;

  @BeforeClass
  public static void beforeClass()
  {
    System.setProperty("java.util.logging.config.file", "jul.properties");

    // CDI-Container starten
    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    // Application-Kontext starten
    cdiContainer.getContextControl().startContext(ApplicationScoped.class);
  }

  @AfterClass
  public static void afterClass()
  {
    // CDI-Container stoppen
    cdiContainer.shutdown();
  }

  @Before
  public void before()
  {
    // Injektionen in this ausführen
    BeanProvider.injectFields(this);

    // Request-Kontext starten
    cdiContainer.getContextControl().startContext(RequestScoped.class);
  }

  @After
  public void after()
  {
    // Request-Kontext stoppen
    cdiContainer.getContextControl().stopContext(RequestScoped.class);
  }

  @Inject
  NoOpBenchmarkService noOpBenchmarkService;

  @Test
  public void testDoBench()
  {
    this.noOpBenchmarkService.run();
  }
}
