package org.tron.common.dispatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tron.common.dispatch.strategy.Dispatcher;
import org.tron.common.dispatch.strategy.Level1Strategy;
import org.tron.common.dispatch.strategy.Level2Strategy;
import org.tron.protos.Protocol;

import java.io.IOException;

public class TransactionFactory {
  private static final ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

  private static final Dispatcher dispatcher = context.getBean(Dispatcher.class);

  public static Protocol.Transaction newTransaction() {
    Level1Strategy level1Strategy = dispatcher.dispatch();
    if (level1Strategy == null) {
      return null;
    }

    Level2Strategy level2Strategy = level1Strategy.dispatch();
    if (level2Strategy == null) {
      return null;
    }

    return level2Strategy.dispatch();
  }

  public static void main(String[] args) throws IOException {
    newTransaction();

  }

}
