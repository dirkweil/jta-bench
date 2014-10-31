package de.gedoplan.beantrial.jtabench.persistence;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.transaction.TransactionRequiredException;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 1)
@Transactional(TxType.MANDATORY)
public class NonJtaTransactionMandatoryInterceptor
{
  @Inject
  EntityManager entityManager;

  @AroundInvoke
  Object manageTransaction(InvocationContext invocationContext) throws Exception
  {
    if (!this.entityManager.getTransaction().isActive())
    {
      throw new TransactionRequiredException();
    }

    return invocationContext.proceed();
  }

}
