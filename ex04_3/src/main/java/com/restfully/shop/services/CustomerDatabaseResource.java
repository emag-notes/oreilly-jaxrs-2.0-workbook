package com.restfully.shop.services;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Yoshimasa Tanabe
 */
@Path("/customers")
public class CustomerDatabaseResource {

  protected CustomerResource europe = new CustomerResource();
  protected FirstLastCustomerResource northamerica = new FirstLastCustomerResource();

  @Path("{database}-db")
  public Object getDatabase(@PathParam("database") String db) {
    switch (db) {
      case "europe":
        return europe;
      case "northamerica":
        return northamerica;
      default:
        throw new BadRequestException();
    }
  }

}
