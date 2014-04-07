package de.gedoplan.beantrial.jtabench.presentation;

import javax.inject.Inject;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonPresenterTest
{
  private static CdiContainer cdiContainer;

  @BeforeClass
  public static void beforeClass()
  {
    // CDI-Container starten
    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    // Standard-Kontexte starten
    cdiContainer.getContextControl().startContexts();
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
  }

  @Inject
  PersonPresenter personPresenter;

  @Test
  public void testDoBench()
  {
    System.out.println("RampUp Count: " + this.personPresenter.getRampUpCount());
    System.out.println("Bench Count: " + this.personPresenter.getBenchCount());
    System.out.println("Bench Size: " + this.personPresenter.getBenchSize());
    System.out.println("Chunk Size: " + this.personPresenter.getChunkSize());

    this.personPresenter.doBench();

    System.out.println("Millis: " + this.personPresenter.getMillis());
  }
}
