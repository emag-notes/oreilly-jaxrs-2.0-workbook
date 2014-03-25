package com.restfully.shop.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationPath("/services")
public class ShoppingApplication extends Application {

  private Set<Object> singletons = new HashSet<Object>();

  public ShoppingApplication() {
    singletons.add(new CustomerDatabaseResource());
  }

  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }

}
